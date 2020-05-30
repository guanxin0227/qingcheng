package com.qingcheng.pojo.goods;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName CategoryBrand
 * @Description TODO: 分类与品牌关联  中间表
 * @Author guanxin
 * @Date 2020/5/26 21:46
 * @Version 1.0
 */
@Table(name = "tb_category_brand")
public class CategoryBrand implements Serializable {

    @Id
    private Integer categoryId;//分类id
    @Id
    private Integer brandId;//品牌id

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }
}
