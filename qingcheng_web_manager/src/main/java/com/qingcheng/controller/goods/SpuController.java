package com.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.entity.PageResult;
import com.qingcheng.entity.Result;
import com.qingcheng.pojo.goods.Goods;
import com.qingcheng.pojo.goods.Spu;
import com.qingcheng.service.goods.SpuService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/spu")
public class SpuController {

    @Reference
    private SpuService spuService;

    @GetMapping("/findAll")
    public List<Spu> findAll(){
        return spuService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult<Spu> findPage(int page, int size){
        return spuService.findPage(page, size);
    }

    @PostMapping("/findList")
    public List<Spu> findList(@RequestBody Map<String,Object> searchMap){
        return spuService.findList(searchMap);
    }

    @PostMapping("/findPage")
    public PageResult<Spu> findPage(@RequestBody Map<String,Object> searchMap,int page, int size){
        return  spuService.findPage(searchMap,page,size);
    }

    @GetMapping("/findById")
    public Spu findById(String id){
        return spuService.findById(id);
    }


    @PostMapping("/add")
    public Result add(@RequestBody Spu spu){
        spuService.add(spu);
        return new Result();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Spu spu){
        spuService.update(spu);
        return new Result();
    }

    @GetMapping("/delete")
    public Result delete(String id){
        spuService.delete(id);
        return new Result();
    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品保存
     * @Date 23:01 2020/5/26
     * @Param [goods]
     * @return com.qingcheng.entity.Result
     **/
    @PostMapping("/saveGoods")
    public Result saveGoods(@RequestBody Goods goods){
        spuService.saveGoods(goods);
        return new Result();
    }

    /*
     * @Author guanxin
     * @Description //TODO: 根据id查询goods信息
     * @Date 23:04 2020/5/26
     * @Param [id]
     * @return com.qingcheng.pojo.goods.Goods
     **/
    @GetMapping("/findGoodsById")
    public Goods findGoodsById(String id){
        return spuService.findGoodsById(id);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品审核
     * @Date 22:43 2020/5/28
     * @Param [map]
     * @return com.qingcheng.entity.Result
     **/
    @PostMapping("/audit")
    public Result audit(@RequestBody Map<String,String> map){
        spuService.audit(map.get("id"),map.get("status"),map.get("message"));
        return new Result();
    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品下架
     * @Date 22:44 2020/5/28
     * @Param [id]
     * @return void
     **/
    @GetMapping("/pull")
    public Result pull(String id){
        spuService.pull(id);
        return new Result();
    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品上架
     * @Date 22:50 2020/5/28
     * @Param [id]
     * @return void
     **/
    @GetMapping("/put")
    public Result put(String id){
        spuService.put(id);
        return new Result();
    }

    /*
     * @Author guanxin
     * @Description //TODO: 批量上架
     * @Date 10:32 2020/5/30
     * @Param [ids]
     * @return void
     **/
    @GetMapping("/putBatch")
    public Result putBatch(String[] ids){
        int count = spuService.putBatch(ids);
        return new Result(0,"成功上架了" + count + "个商品");
    }

    /*
     * @Author guanxin
     * @Description //TODO: 批量下架
     * @Date 10:32 2020/5/30
     * @Param [ids]
     * @return void
     **/
    @GetMapping("/pullBatch")
    public Result pullBatch(String[] ids){
        int count = spuService.pullBatch(ids);
        return new Result(0,"成功上架了" + count + "个商品");
    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品删除
     * @Date 10:32 2020/5/30
     * @Param [ids]
     * @return void
     **/
    @GetMapping("/isDelete")
    public Result isDelete(String id){
        int count = spuService.isDelete(id);
        return new Result(0,"删除成功");
    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品还原
     * @Date 10:32 2020/5/30
     * @Param [ids]
     * @return void
     **/
    @GetMapping("/reDelete")
    public Result reDelete(String id){
        int count = spuService.reDelete(id);
        return new Result(0,"还原成功");
    }
}
