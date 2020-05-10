package com.qingcheng.controller.goods;

import com.alibaba.dubbo.config.annotation.Reference;


import com.qingcheng.entity.PageResult;
import com.qingcheng.entity.Result;
import com.qingcheng.pojo.goods.Brand;
import com.qingcheng.service.goods.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BrandController
 * @Description TODO:品牌管理
 * @Author guanxin
 * @Date 2020/5/10 10:36
 * @Version 1.0
 */
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Reference
    private BrandService brandService;

    /*
     * @Author guanxin
     * @Description //TODO：查询所有品牌
     * @Date 10:41 2020/5/10
     * @Param []
     * @return java.util.List<com.qingcheng.pojo.goods.Brand>
     **/
    @RequestMapping("/findAll")
    public List<Brand> findAll(){
        return brandService.findAll();
    }

    /*
     * @Author guanxin
     * @Description //TODO：分页查询
     * @Date 10:41 2020/5/10
     * @Param []
     * @return java.util.List<com.qingcheng.pojo.goods.Brand>
     **/
    @GetMapping("/findPage")
    public PageResult<Brand> findPage(int page,int size){
        return  brandService.findPage(page,size);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 条件查询
     * @Date 20:01 2020/5/10
     * @Param [searchMap]
     * @return java.util.List<com.qingcheng.pojo.goods.Brand>
     **/
    @PostMapping("/findList")
    public List<Brand> findList( @RequestBody Map<String,Object> searchMap){
        return brandService.findList(searchMap);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 条件分页查询
     * @Date 20:19 2020/5/10
     * @Param [searchMap, page, size]
     * @return com.qingcheng.entity.PageResult<com.qingcheng.pojo.goods.Brand>
     **/
    @PostMapping("/findPage")
    public PageResult<Brand> findPage(@RequestBody Map<String,Object> searchMap,int page,int size ){
        return brandService.findPage(searchMap,page,size);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 根据id查询商品信息
     * @Date 20:24 2020/5/10
     * @Param [id]
     * @return com.qingcheng.pojo.goods.Brand
     **/
    @GetMapping("/findById")
    public Brand findById(Integer id){
        return brandService.findById(id);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 添加商品
     * @Date 20:27 2020/5/10
     * @Param [brand]
     * @return com.qingcheng.entity.Result
     **/
    @PostMapping("/add")
    public Result add(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result();
    }

    /*
     * @Author guanxin
     * @Description //TODO: 品牌修改
     * @Date 20:46 2020/5/10
     * @Param [brand]
     * @return com.qingcheng.entity.Result
     **/
    @PostMapping("/update")
    public Result update(@RequestBody Brand brand){
        brandService.update(brand);
        return new Result();
    }

    /*
     * @Author guanxin
     * @Description //TODO: 根据id删除品牌
     * @Date 20:50 2020/5/10
     * @Param [id]
     * @return com.qingcheng.entity.Result
     **/
    @GetMapping("delete")
    public Result delete(Integer id){
        brandService.delete(id);
        return new Result();
    }

}
