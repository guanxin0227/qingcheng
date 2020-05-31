package com.qingcheng.controller.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qingcheng.service.order.CategoryReportService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName OrderTask
 * @Description TODO: 定时任务管理类
 * @Author guanxin
 * @Date 2020/5/31 16:42
 * @Version 1.0
 */
@Component
public class OrderTask {

    @Reference
    private CategoryReportService categoryReportService;

    /*
     * @Author guanxin
     * @Description //TODO: 每天凌晨一点执行一次
     * @Date 18:58 2020/5/31
     * @Param []
     * @return void
     **/
    @Scheduled(cron = "0 0 1 * * ?")
//    @Scheduled(cron = "0 * * * * ?")
    public void createCategoryReport(){
        System.out.print("生成类目统计查询");
        categoryReportService.createDate();
    }
}
