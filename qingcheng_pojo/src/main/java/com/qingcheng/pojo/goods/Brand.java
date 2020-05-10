package com.qingcheng.pojo.goods;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName Brand
 * @Description TODO:品牌实体类
 * @Author guanxin
 * @Date 2020/5/10 10:36
 * @Version 1.0
 */
@Table(name = "tb_brand")
public class Brand implements Serializable {

    //品牌id
    @Id
    private Integer id;

    //品牌名称
    private String name;

    //品牌图片地址
    private String image;

    //品牌首字母
    private String letter;

    //排序
    private Integer seq;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
