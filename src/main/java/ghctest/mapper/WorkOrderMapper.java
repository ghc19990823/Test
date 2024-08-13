package ghctest.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ghctest.entity.WorkOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {

    /*自定义SQL，防止输入id，项目中id是点击获取，测试中id选择输入，输入则会进行提示。*/
    @Update("update work_order set order_no=#{orderNo},order_type=#{orderType},title=#{title}" +
            ",content=#{content},dept_id=#{deptId},create_time=#{createTime},fenpai_time=#{fenpaiTime},is_overdue=#{isOverdue} where order_no=#{orderNo}")
    int  updateFenPai(@Param("orderNo")Long orderNo,
                     @Param("orderType") Integer orderType,
                     @Param("title") String title,
                     @Param("content") String content,
                     @Param("deptId")Long deptId,
                     @Param("createTime") LocalDateTime createTime,
                     @Param("fenpaiTime")LocalDateTime fenpaiTime,
                     @Param("isOverdue") Integer isOverdue
                     );

    @Select("SELECT DATE(create_time) AS date,COUNT(is_overdue) AS total_orders,SUM(is_overdue=1) / COUNT(*) AS overdue_rate FROM `work_order` WHERE MONTH(create_time) = 7 GROUP BY DATE(create_time)")
    List<Map<String, Object>> selectDailyStatistics();

    @Select("SELECT d.dept_name,COUNT(*) AS total_orders,SUM(o.is_overdue = 1 ) / COUNT(*) AS overdue_rate\n" +
            "FROM  work_order o JOIN dept d ON o.dept_id = d.dept_id\n" +
            "WHERE  MONTH(o.create_time) = 7\n" +
            "GROUP BY d.dept_name")
    List<Map<String,Object>> selectDepartmentStatistics();

    @Select("SELECT o.order_type,COUNT(*) AS total_orders,SUM(o.is_overdue = 1 ) / COUNT(o.id) AS overdue_rate FROM work_order o\n" +
            "WHERE MONTH(create_time) = 7 GROUP BY o.order_type;")
    List<Map<String,Object>> selectOrderTypeStatistics();
}
