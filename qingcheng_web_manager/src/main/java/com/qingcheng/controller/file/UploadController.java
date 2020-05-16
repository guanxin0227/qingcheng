package com.qingcheng.controller.file;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
}
