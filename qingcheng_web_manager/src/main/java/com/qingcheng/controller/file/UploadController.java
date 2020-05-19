package com.qingcheng.controller.file;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyun.oss.OSSClient;
import com.qingcheng.pojo.goods.Album;
import com.qingcheng.service.goods.AlbumService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName UploadController
 * @Description TODO: 文件上传模块
 * @Author guanxin
 * @Date 2020/5/16 21:23
 * @Version 1.0
 */

@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private OSSClient ossClient;

    /*
     * @Author guanxin
     * @Description //TODO: 本地上传方式
     * @Date 23:27 2020/5/16
     * @Param [file]
     * @return java.lang.String
     **/
    @PostMapping (value = "/native")
    public String nativeUpload(@RequestParam("file") MultipartFile file){

        //获取工程下img文件夹的绝对路径
        String imgPath = httpServletRequest.getSession().getServletContext().getRealPath("img");

        //获取文件名字
        String fileName = file.getOriginalFilename();

        //获取上传路径+文件名
        String pathName = imgPath + "\\" + fileName;

        //创建文件
        File destFile = new File(pathName);

        //判断文件夹是否存在，不存在则创建文件夹
        if(!destFile.getParentFile().exists()){
            destFile.mkdirs();
        }

        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "http://localhost:9101/img/" + fileName;
    }

    /*
     * @Author guanxin
     * @Description //TODO: 上传到阿里云
     * @Date 23:51 2020/5/16
     * @Param [file：文件, folder：文件夹类型]
     * @return java.lang.String
     **/
    @PostMapping (value = "/aliyunOss")
    public String aliyunOssUpload(@RequestParam("file") MultipartFile file,String folder){

        //空间名
        String bucketName = "guanxin-qingcheng";
        //文件名
        String fileName = folder + "/" + UUID.randomUUID() + file.getOriginalFilename();

        try {
            ossClient.putObject(bucketName,fileName,file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + fileName;
    }

    /*
     * @Author guanxin
     * @Description //TODO: 图库上传到阿里云
     * @Date 23:51 2020/5/16
     * @Param [file：文件, folder：文件夹类型]
     * @return java.lang.String
     **/

    @Reference
    private AlbumService albumService;

    @PostMapping (value = "/aliyunOssTK")
    public String aliyunOssUploadTK(@RequestParam("file") MultipartFile file,String folder,Long id){

        List<Map<String,Object>> list = new ArrayList();
        Map<String,Object> map = new HashMap<>();

        //空间名
        String bucketName = "guanxin-qingcheng";

        //上传前文件名
        String originalFilename = file.getOriginalFilename();

        //上传后文件名
        String fileName = folder + "/" + UUID.randomUUID() + originalFilename;

        //上传路径
        String url = "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + fileName;

        try {
            //上传到阿里云服务器
            ossClient.putObject(bucketName,fileName,file.getInputStream());

            //上传后存入数据库
            Album byId = albumService.findById(id);

            map.put("name",originalFilename);
            map.put("url",url);

            if(StringUtils.isEmpty(byId.getImageItems())){

                String jsonData = map.toString().replace("{", "{\"").replace("}", "\"}").replace("=","\":\"").replace(", ", "\",\"").replace("}\",\"{", "},{");
                byId.setImageItems(jsonData);
                albumService.update(byId);
            }else{

                String jsonData = map.toString().replace("{", "{\"").replace("}", "\"}").replace("=","\":\"").replace(", ", "\",\"").replace("}\",\"{", "},{");
                JSONArray json = JSONArray.fromObject(byId.getImageItems());
                json.add(jsonData);
                String json1 = json.toString();
                byId.setImageItems(json1);
                albumService.update(byId);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return url;
    }
}
