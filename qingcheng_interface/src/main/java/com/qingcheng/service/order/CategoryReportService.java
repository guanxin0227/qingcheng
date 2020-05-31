package com.qingcheng.service.order;

import com.qingcheng.pojo.order.CategoryReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CategoryReportService
 * @Description TODO: 统计查询
 * @Author guanxin
 * @Date 2020/5/30 19:00
 * @Version 1.0
 */
public interface CategoryReportService {
    /*
     * @Author guanxin
     * @Description //TODO: 查询前一天数据
     * @Date 16:32 2020/5/31
     * @Param [date]
     * @return java.util.List<com.qingcheng.pojo.order.CategoryReport>
     **/
    List<CategoryReport> categoryReport(LocalDate date);

    /*
     * @Author guanxin
     * @Description //TODO: 定时任务，生成统计数据
     * @Date 16:33 2020/5/31
     * @Param []
     * @return void
     **/
    void createDate();

    /*
     * @Author guanxin
     * @Description //TODO: 按日期统计一级分类数据
     * @Date 17:53 2020/5/31
     * @Param [startDate, endDate]
     * @return java.util.List<java.util.Map>
     **/
    List<Map> category1Count(String startDate,String endDate);
}
