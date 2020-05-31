package qingcheng.dao;

import com.qingcheng.pojo.order.CategoryReport;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.time.LocalDate;
import java.util.List;

/**
 * @ClassName CategoryReportMapper
 * @Description TODO: 统计查询
 * @Author guanxin
 * @Date 2020/5/30 18:49
 * @Version 1.0
 */
public interface CategoryReportMapper extends Mapper<CategoryReport> {

    /*
     * @Author guanxin
     * @Description //TODO: 统计查询
     * @Date 18:59 2020/5/30
     * @Param [date]
     * @return java.util.List<com.qingcheng.pojo.order.CategoryReport>
     **/
    @Select("select oi.category_id1,oi.category_id2,oi.category_id3,date_format(o.pay_time,'%Y-%m-%d') count_date,sum(oi.num) num,sum(oi.pay_money) money " +
            "from qingcheng_order.tb_order_item oi, qingcheng_order.tb_order o " +
            "where oi.order_id = o.id and o.pay_status ='1' and o.is_delete = '0' and date_format(o.pay_time,'%Y-%m-%d') = #{date} " +
            "group by oi.category_id1,oi.category_id2,oi.category_id3,date_format(o.pay_time,'%Y-%m-%d')")
    List<CategoryReport> categoryReport(@Param("date") LocalDate date);
}
