package com.qingcheng.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qingcheng.dao.CategoryReportMapper;
import com.qingcheng.pojo.order.CategoryReport;
import com.qingcheng.service.order.CategoryReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CategoryReportServiceImpl
 * @Description TODO: 统计查询
 * @Author guanxin
 * @Date 2020/5/30 19:01
 * @Version 1.0
 */
@Service(interfaceClass = CategoryReportService.class)
public class CategoryReportServiceImpl implements CategoryReportService {

    @Autowired
    private CategoryReportMapper categoryReportMapper;

    /*
     * @Author guanxin
     * @Description //TODO: 查询前一天数据
     * @Date 16:32 2020/5/31
     * @Param [date]
     * @return java.util.List<com.qingcheng.pojo.order.CategoryReport>
     **/
    @Override
    public List<CategoryReport> categoryReport(LocalDate date) {
        return categoryReportMapper.categoryReport(date);
    }

    /*
     * @Author guanxin
     * @Description //TODO: 定时任务，生成统计数据
     * @Date 16:33 2020/5/31
     * @Param []
     * @return void
     **/
    @Override
    @Transactional
    public void createDate() {

        //1.查询前一天数据统计
        LocalDate localDate = LocalDate.now().minusDays(1);
        List<CategoryReport> categoryReportList = categoryReportMapper.categoryReport(localDate);

        //2.保存到表中tb_category_report
        for (CategoryReport categoryReport : categoryReportList) {
            categoryReportMapper.insert(categoryReport);
        }
    }

    /*
     * @Author guanxin
     * @Description //TODO: 按日期统计一级分类数据
     * @Date 17:53 2020/5/31
     * @Param [startDate, endDate]
     * @return java.util.List<java.util.Map>
     **/
    @Override
    public List<Map> category1Count(String startDate,String endDate) {
        return categoryReportMapper.category1Count(startDate,endDate);
    }
}
