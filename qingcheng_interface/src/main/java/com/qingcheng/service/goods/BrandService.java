package com.qingcheng.service.goods;

import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Brand;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BrandService
 * @Description TODO:品牌管理接口
 * @Author guanxin
 * @Date 2020/5/10 10:36
 * @Version 1.0
 */
public interface BrandService {

    /*
     * @Author guanxin
     * @Description //TODO:查询所有品牌信息
     * @Date 10:37 2020/5/10
     * @Param []
     * @return java.util.List<com.qingcheng.pojo.goods.Brand>
     **/
    public List<Brand> findAll();

    /*
     * @Author guanxin
     * @Description //TODO:分页查询品牌，返回分页数据
     * @Date 13:26 2020/5/10
     * @Param [page, size]
     * @return com.qingcheng.entity.PageResult<com.qingcheng.pojo.goods.Brand>
     **/
    public PageResult<Brand> findPage(int page,int size);

    /*
     * @Author guanxin
     * @Description //TODO: 条件查询
     * @Date 19:51 2020/5/10
     * @Param [searchMap]
     * @return java.util.List<com.qingcheng.pojo.goods.Brand>
     **/
    public List<Brand> findList(Map<String,Object> searchMap);

    /*
     * @Author guanxin
     * @Description //TODO: 条件分页查询
     * @Date 20:16 2020/5/10
     * @Param [searchMap, page, size]
     * @return com.qingcheng.entity.PageResult<com.qingcheng.pojo.goods.Brand>
     **/
    public PageResult<Brand> findPage(Map<String,Object> searchMap,int page,int size);

    /*
     * @Author guanxin
     * @Description //TODO: 根据id查询商品信息
     * @Date 20:23 2020/5/10
     * @Param [id]
     * @return com.qingcheng.pojo.goods.Brand
     **/
    public Brand findById(Integer id);

    /*
     * @Author guanxin
     * @Description //TODO: 添加商品
     * @Date 20:26 2020/5/10
     * @Param [brand]
     * @return void
     **/
    public void add(Brand brand);

    /*
     * @Author guanxin
     * @Description //TODO: 品牌修改
     * @Date 20:43 2020/5/10
     * @Param [brand]
     * @return void
     **/
    public void update(Brand brand);

    /*
     * @Author guanxin
     * @Description //TODO: 根据id删除品牌
     * @Date 20:49 2020/5/10
     * @Param [id]
     * @return void
     **/
    public void delete(Integer id);
}
