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

    public List<Brand> findList(Map<String,Object> searchMap);


    public PageResult<Brand> findPage(Map<String,Object> searchMap,int page,int size);

    public Brand findById(Integer id);

    public void add(Brand brand);

    public void update(Brand brand);

    public void delete(Integer id);
}
