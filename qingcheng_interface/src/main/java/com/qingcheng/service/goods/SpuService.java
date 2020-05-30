package com.qingcheng.service.goods;
import com.qingcheng.entity.PageResult;
import com.qingcheng.pojo.goods.Goods;
import com.qingcheng.pojo.goods.Spu;

import java.util.*;

/**
 * spu业务逻辑层
 */
public interface SpuService {


    List<Spu> findAll();


    PageResult<Spu> findPage(int page, int size);


    List<Spu> findList(Map<String, Object> searchMap);


    PageResult<Spu> findPage(Map<String, Object> searchMap, int page, int size);


    Spu findById(String id);

    void add(Spu spu);


    void update(Spu spu);


    void delete(String id);

    /*
     * @Author guanxin
     * @Description //TODO: 保存商品信息
     * @Date 21:46 2020/5/25
     * @Param [goods]
     * @return void
     **/
    void saveGoods(Goods goods);

    /*
     * @Author guanxin
     * @Description //TODO: 根据id查询goods信息
     * @Date 22:54 2020/5/26
     * @Param [id]
     * @return com.qingcheng.pojo.goods.Goods
     **/
    Goods findGoodsById(String id);

    /*
     * @Author guanxin
     * @Description //TODO: 商品审核
     * @Date 22:13 2020/5/28
     * @Param [id, status, message]
     * @return void
     **/
    void audit(String id, String status, String message);

    /*
     * @Author guanxin
     * @Description //TODO: 商品下架
     * @Date 22:44 2020/5/28
     * @Param [id]
     * @return void
     **/
    void pull(String id);

    /*
     * @Author guanxin
     * @Description //TODO: 商品上架
     * @Date 22:50 2020/5/28
     * @Param [id]
     * @return void
     **/
    void put(String id);
}
