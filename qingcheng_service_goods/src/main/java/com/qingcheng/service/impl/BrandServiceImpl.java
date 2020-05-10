package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.BrandMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Brand;
import com.qingcheng.service.goods.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BrandServiceImpl
 * @Description TODO：品牌管理
 * @Author guanxin
 * @Date 2020/5/10 10:36
 * @Version 1.0
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /*
     * @Author guanxin
     * @Description //TODO:查询所有品牌信息
     * @Date 10:28 2020/5/10
     * @Param []
     * @return java.util.List<com.qingcheng.pojo.goods.Brand>
     **/
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    /*
     * @Author guanxin
     * @Description //TODO:分页查询品牌，返回分页数据
     * @Date 13:26 2020/5/10
     * @Param [page, size]
     * @return com.qingcheng.entity.PageResult<com.qingcheng.pojo.goods.Brand>
     **/
    @Override
    public PageResult<Brand> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Brand> pageResult=(Page<Brand>) brandMapper.selectAll();
        return new PageResult<>(pageResult.getTotal(),pageResult.getResult());
    }

    /*
     * @Author guanxin
     * @Description //TODO: 条件查询
     * @Date 19:51 2020/5/10
     * @Param [searchMap]
     * @return java.util.List<com.qingcheng.pojo.goods.Brand>
     **/
    @Override
    public List<Brand> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return brandMapper.selectByExample(example);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 条件分页查询
     * @Date 20:18 2020/5/10
     * @Param [searchMap, page, size]
     * @return com.qingcheng.entity.PageResult<com.qingcheng.pojo.goods.Brand>
     **/
    @Override
    public PageResult<Brand> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<Brand> pageResult=(Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult<>(pageResult.getTotal(),pageResult.getResult());
    }

    /*
     * @Author guanxin
     * @Description //TODO: 根据id查询商品信息
     * @Date 20:24 2020/5/10
     * @Param [id]
     * @return com.qingcheng.pojo.goods.Brand
     **/
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 添加商品
     * @Date 20:26 2020/5/10
     * @Param [brand]
     * @return void
     **/
    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 品牌修改
     * @Date 20:43 2020/5/10
     * @Param [brand]
     * @return void
     **/
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 根据id删除品牌
     * @Date 20:49 2020/5/10
     * @Param [id]
     * @return void
     **/
    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 条件查询，抽取公共方法
     * @Date 19:55 2020/5/10
     * @Param [searchMap]
     * @return tk.mybatis.mapper.entity.Example
     **/
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        if(searchMap!=null){

            //名称模糊查询
            if(searchMap.get("name")!=null && !"".equals(searchMap.get("name")) ){
                criteria.andLike("name","%"+(String)searchMap.get("name")+"%");
            }
            //首字母精确匹配
            if(searchMap.get("letter")!=null && !"".equals(searchMap.get("letter")) ){
                criteria.andEqualTo("letter",(String)searchMap.get("letter"));
            }
        }
        return example;
    }

}
