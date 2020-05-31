package com.qingcheng.service.impl;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qingcheng.dao.CategoryBrandMapper;
import com.qingcheng.dao.CategoryMapper;
import com.qingcheng.dao.SkuMapper;
import com.qingcheng.dao.SpuMapper;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.*;
import com.qingcheng.service.goods.SpuService;
import com.qingcheng.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SpuService.class)
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    /**
     * 返回全部记录
     * @return
     */
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    /**
     * 分页查询
     * @param page 页码
     * @param size 每页记录数
     * @return 分页结果
     */
    public PageResult<Spu> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        Page<Spu> spus = (Page<Spu>) spuMapper.selectAll();
        return new PageResult<Spu>(spus.getTotal(),spus.getResult());
    }

    /**
     * 条件查询
     * @param searchMap 查询条件
     * @return
     */
    public List<Spu> findList(Map<String, Object> searchMap) {
        Example example = createExample(searchMap);
        return spuMapper.selectByExample(example);
    }

    /**
     * 分页+条件查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    public PageResult<Spu> findPage(Map<String, Object> searchMap, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(searchMap);
        Page<Spu> spus = (Page<Spu>) spuMapper.selectByExample(example);
        return new PageResult<Spu>(spus.getTotal(),spus.getResult());
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    public Spu findById(String id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增
     * @param spu
     */
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }

    /**
     * 修改
     * @param spu
     */
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     *  删除
     * @param id
     */
    public void delete(String id) {
        spuMapper.deleteByPrimaryKey(id);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品保存，添加和修改公用一个接口
     * @Date 21:47 2020/5/25
     * @Param [goods]
     * @return void
     **/
    @Override
    @Transactional
    public void saveGoods(Goods goods) {

        //1.保存spu信息
        Spu spu = goods.getSpu();

        if(null == spu.getId()){
            spu.setId(String.valueOf(idWorker.nextId()));
            spuMapper.insert(spu);
        }else{
            //根据id删除原来sku列表
            Example example = new Example(Sku.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("spuId",spu.getId());
            skuMapper.deleteByExample(example);
            //保存
            spuMapper.updateByPrimaryKeySelective(spu);
        }

        //2.保存sku信息
        //获取分类对象
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());

        List<Sku> skuList = goods.getSkuList();
        for (Sku sku : skuList) {

            if(null == sku.getId()){
                sku.setId(String.valueOf(idWorker.nextId()));
                sku.setCreateTime(new Date());//创建日期
            }

            sku.setSpuId(spu.getId());

            if(null == sku.getSpec() || "".equals(sku.getSpec())){
                sku.setSpec("{}");
            }

            //sku名称=spu名称+规格值列表
            String name = spu.getName();
            Map<String,String> specMap = JSON.parseObject(sku.getSpec(), Map.class);
            for(String value: specMap.values()){
                name += " " + value;
            }
            sku.setName(name);

            sku.setUpdateTime(new Date());//修改日期

            sku.setCategoryId(spu.getCategory3Id());//分类id

            sku.setCategoryName(category.getName());//分类名称

            sku.setCommentNum(0);//评论数
            sku.setSaleNum(0);//销售数量

            skuMapper.insert(sku);
        }

        //建立分类与品牌关联
        CategoryBrand categoryBrand = new CategoryBrand();
        categoryBrand.setCategoryId(spu.getCategory3Id());
        categoryBrand.setBrandId(spu.getBrandId());
        //插入之前先进行统计，若有则不插入
        int count = categoryBrandMapper.selectCount(categoryBrand);

        if(count == 0){
            categoryBrandMapper.insert(categoryBrand);
        }
    }

    /*
     * @Author guanxin
     * @Description //TODO: 根据id查询goods信息
     * @Date 22:56 2020/5/26
     * @Param [id]
     * @return com.qingcheng.pojo.goods.Goods
     **/
    @Override
    public Goods findGoodsById(String id) {
        //查询spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //查询sku列表
        Example example = new Example(Sku.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("spuId",id);
        List<Sku> skuList = skuMapper.selectByExample(example);
        //组合信息
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);

        return goods;
    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品审核
     * @Date 22:14 2020/5/28
     * @Param [id, status, message]
     * @return void
     **/
    @Override
    @Transactional
    public void audit(String id, String status, String message) {
        //1.修改状态  审核状态和上架状态
        Spu spu = new Spu();
        spu.setId(id);
        spu.setStatus(status);

        //审核通过
        if("1".equals(status)){
            //自动上架
            spu.setIsMarketable("1");
        }
        spuMapper.updateByPrimaryKeySelective(spu);
        //2.记录商品审核记录

        //3.记录商品日志

    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品下架
     * @Date 22:44 2020/5/28
     * @Param [id]
     * @return void
     **/
    @Override
    public void pull(String id) {

        //1.修改状态
        Spu spu = new Spu();
        spu.setId(id);
        spu.setIsMarketable("0");
        //保存数据
        spuMapper.updateByPrimaryKeySelective(spu);

        //2.记录日志
    }

    /*
     * @Author guanxin
     * @Description //TODO: 商品上架
     * @Date 22:50 2020/5/28
     * @Param [id]
     * @return void
     **/
    @Override
    public void put(String id) {
        //修改状态
        Spu spu = spuMapper.selectByPrimaryKey(id);
        //判断商品是否审核通过
        if(!spu.getStatus().equals("1")){
            throw new RuntimeException("此商品未通过审核，不能进行上架操作");
        }
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
        //记录日志
    }

    /*
     * @Author guanxin
     * @Description //TODO: 批量上架
     * @Date 10:32 2020/5/30
     * @Param [ids]
     * @return void
     **/
    @Override
    public int putBatch(String[] ids) {

        //1.修改状态
        Spu spu = new Spu();
        spu.setIsMarketable("1");

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        //下架的
        criteria.andEqualTo("isMarketable","0");
        //审核通过的
        criteria.andEqualTo("status","1");

        int count = spuMapper.updateByExampleSelective(spu, example);

        //2.记录日志信息

        return count;
    }

    /*
     * @Author guanxin
     * @Description //TODO: 批量下架
     * @Date 10:56 2020/5/30
     * @Param [ids]
     * @return int
     **/
    @Override
    public int pullBatch(String[] ids) {

        //1.修改状态
        Spu spu = new Spu();
        spu.setIsMarketable("0");

        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",Arrays.asList(ids));
        criteria.andEqualTo("isMarketable","0");
        criteria.andEqualTo("status","1");

        int count = spuMapper.updateByExampleSelective(spu, example);

        //2.记录日志

        return count;
    }

    /*
     * @Author guanxin
     * @Description //TODO: 逻辑删除商品
     * @Date 11:10 2020/5/30
     * @Param [id]
     * @return int
     **/
    @Override
    public int isDelete(String id) {

        Spu spu = new Spu();
        //状态1表示删除，0表示还原
        spu.setIsDelete("1");

        if(spu.getIsMarketable().equals("1")){
            throw new RuntimeException("该商品上架中，不能进行删除操作");
        }

        int count = spuMapper.updateByPrimaryKeySelective(spu);
        return count;
    }

    /*
     * @Author guanxin
     * @Description //TODO: 还原删除商品
     * @Date 11:11 2020/5/30
     * @Param [id]
     * @return int
     **/
    @Override
    public int reDelete(String id) {
        Spu spu = new Spu();
        //状态1表示删除，0表示还原
        spu.setIsDelete("0");

        int count = spuMapper.updateByPrimaryKeySelective(spu);
        return count;
    }

    /**
     * 构建查询条件
     * @param searchMap
     * @return
     */
    private Example createExample(Map<String, Object> searchMap){
        Example example=new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            // 主键
            if(searchMap.get("id")!=null && !"".equals(searchMap.get("id"))){
                criteria.andLike("id","%"+searchMap.get("id")+"%");
            }
            // 货号
            if(searchMap.get("sn")!=null && !"".equals(searchMap.get("sn"))){
                criteria.andLike("sn","%"+searchMap.get("sn")+"%");
            }
            // SPU名
            if(searchMap.get("name")!=null && !"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+searchMap.get("name")+"%");
            }
            // 副标题
            if(searchMap.get("caption")!=null && !"".equals(searchMap.get("caption"))){
                criteria.andLike("caption","%"+searchMap.get("caption")+"%");
            }
            // 图片
            if(searchMap.get("image")!=null && !"".equals(searchMap.get("image"))){
                criteria.andLike("image","%"+searchMap.get("image")+"%");
            }
            // 图片列表
            if(searchMap.get("images")!=null && !"".equals(searchMap.get("images"))){
                criteria.andLike("images","%"+searchMap.get("images")+"%");
            }
            // 售后服务
            if(searchMap.get("saleService")!=null && !"".equals(searchMap.get("saleService"))){
                criteria.andLike("saleService","%"+searchMap.get("saleService")+"%");
            }
            // 介绍
            if(searchMap.get("introduction")!=null && !"".equals(searchMap.get("introduction"))){
                criteria.andLike("introduction","%"+searchMap.get("introduction")+"%");
            }
            // 规格列表
            if(searchMap.get("specItems")!=null && !"".equals(searchMap.get("specItems"))){
                criteria.andLike("specItems","%"+searchMap.get("specItems")+"%");
            }
            // 参数列表
            if(searchMap.get("paraItems")!=null && !"".equals(searchMap.get("paraItems"))){
                criteria.andLike("paraItems","%"+searchMap.get("paraItems")+"%");
            }
            // 是否上架
            if(searchMap.get("isMarketable")!=null && !"".equals(searchMap.get("isMarketable"))){
                criteria.andLike("isMarketable","%"+searchMap.get("isMarketable")+"%");
            }
            // 是否启用规格
            if(searchMap.get("isEnableSpec")!=null && !"".equals(searchMap.get("isEnableSpec"))){
                criteria.andLike("isEnableSpec","%"+searchMap.get("isEnableSpec")+"%");
            }
            // 是否删除
            if(searchMap.get("isDelete")!=null && !"".equals(searchMap.get("isDelete"))){
                criteria.andLike("isDelete","%"+searchMap.get("isDelete")+"%");
            }
            // 审核状态
            if(searchMap.get("status")!=null && !"".equals(searchMap.get("status"))){
                criteria.andLike("status","%"+searchMap.get("status")+"%");
            }

            // 品牌ID
            if(searchMap.get("brandId")!=null ){
                criteria.andEqualTo("brandId",searchMap.get("brandId"));
            }
            // 一级分类
            if(searchMap.get("category1Id")!=null ){
                criteria.andEqualTo("category1Id",searchMap.get("category1Id"));
            }
            // 二级分类
            if(searchMap.get("category2Id")!=null ){
                criteria.andEqualTo("category2Id",searchMap.get("category2Id"));
            }
            // 三级分类
            if(searchMap.get("category3Id")!=null ){
                criteria.andEqualTo("category3Id",searchMap.get("category3Id"));
            }
            // 模板ID
            if(searchMap.get("templateId")!=null ){
                criteria.andEqualTo("templateId",searchMap.get("templateId"));
            }
            // 运费模板id
            if(searchMap.get("freightId")!=null ){
                criteria.andEqualTo("freightId",searchMap.get("freightId"));
            }
            // 销量
            if(searchMap.get("saleNum")!=null ){
                criteria.andEqualTo("saleNum",searchMap.get("saleNum"));
            }
            // 评论数
            if(searchMap.get("commentNum")!=null ){
                criteria.andEqualTo("commentNum",searchMap.get("commentNum"));
            }

        }
        return example;
    }

}
