package com.qingcheng.pojo.goods;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName Goods
 * @Description TODO: 用于封装品牌的 sku spu
 * @Author guanxin
 * @Date 2020/5/25 21:42
 * @Version 1.0
 */
public class Goods implements Serializable {

    private Spu spu;

    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
