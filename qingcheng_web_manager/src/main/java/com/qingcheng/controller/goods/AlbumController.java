package com.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.entity.PageResult;
import com.qingcheng.entity.Result;
import com.qingcheng.pojo.goods.Album;
import com.qingcheng.service.goods.AlbumService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Reference
    private AlbumService albumService;

    @GetMapping("/findAll")
    public List<Album> findAll(){
        return albumService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Album> findPage(int page, int size){
        return albumService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Album> findList(@RequestBody Map<String,Object> searchMap){
        return albumService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Album> findPage(@RequestBody Map<String,Object> searchMap, int page, int size){
        return  albumService.findPage(searchMap,page,size);
    }

    @GetMapping("/findById")
    public Album findById(Long id){
        return albumService.findById(id);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 图库分页列表实现
     * @Date 23:09 2020/5/18
     * @Param [page, size]
     * @return com.qingcheng.entity.PageResult<com.qingcheng.pojo.goods.Album>
     **/
    @GetMapping("/findPicsById")
    public  List<Object> findPicsById(Long id){

        List<Object> stringArray = new ArrayList<>();

        Album album = albumService.findById(id);
        String imageItems = album.getImageItems();
        JSONArray json = JSONArray.fromObject(imageItems);

        if(json.size()>0) {
            for (int i = 0; i < json.size(); i++) {
                JSONObject job = json.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
                stringArray.add(job);
            }
        }
        return stringArray;
    }

    @PostMapping("/add")
    public Result add(@RequestBody Album album){
        albumService.add(album);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Album album){
        albumService.update(album);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(Long id){
        albumService.delete(id);
        return new Result();
    }

}
