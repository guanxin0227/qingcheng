package com.qingcheng.service.order;

import com.qingcheng.pojo.order.CategoryReport;

import java.time.LocalDate;
import java.util.List;

/**
 * @ClassName CategoryReportService
 * @Description TODO: 统计查询
 * @Author guanxin
 * @Date 2020/5/30 19:00
 * @Version 1.0
 */
public interface CategoryReportService {
    List<CategoryReport> categoryReport(LocalDate date);
}
