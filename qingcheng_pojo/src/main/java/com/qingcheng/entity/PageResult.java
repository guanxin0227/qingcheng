package com.qingcheng.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PageResult
 * @Description TODO：分页封装
 * @Author guanxin
 * @Date 2020/5/10 13:19
 * @Version 1.0
 */
public class PageResult<T> implements Serializable {

    //总记录数
    private Long total;
    //返回数据
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
