package com.qingcheng.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.pojo.order.CategoryReport;
import com.qingcheng.service.order.CategoryReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CategoryReportController
 * @Description TODO: 统计查询
 * @Author guanxin
 * @Date 2020/5/30 19:03
 * @Version 1.0
 */
@RestController
@RequestMapping("/categoryReport")
public class CategoryReportController {

    @Reference
    private CategoryReportService categoryReportService;

    /*
     * @Author guanxin
     * @Description //TODO: 获取昨天的数据统计
     * @Date 19:08 2020/5/30
     * @Param []
     * @return java.util.List<com.qingcheng.pojo.order.CategoryReport>
     **/
    @GetMapping("/yesterdayReport")
    public List<CategoryReport> yesterdayReport(){

        //获取昨天时间
        LocalDate localDate = LocalDate.now().minusDays(1);

        return categoryReportService.categoryReport(localDate);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 按日期统计一级分类数据
     * @Date 17:53 2020/5/31
     * @Param [startDate, endDate]
     * @return java.util.List<java.util.Map>
     **/
    @GetMapping("/category1Count")
    public List<Map> category1Count(String startDate,String endDate){
        return categoryReportService.category1Count(startDate,endDate);
    }
}
