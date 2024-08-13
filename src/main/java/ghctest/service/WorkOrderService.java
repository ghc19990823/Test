package ghctest.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ghctest.entity.WorkOrder;

import java.util.List;
import java.util.Map;

public interface WorkOrderService {
    /*分页查询*/
    IPage<WorkOrder> searchOrders(int pageNum, int pageSize);

    /*分派功能*/
    void assignOrder(Long orderNo, Long deptId,String deptName);

    /*添加工单信息*/
    boolean save(WorkOrder workOrder);

    /*删除工单信息*/
    boolean removeById(Long id);

    /*更新工单信息*/
    boolean updateById(WorkOrder workOrder);

    /*7月每天的工单总量、超期率*/
    List<Map<String, Object>> getDailyStatistics();

    /*查询7月每个部门的工单总量和超期率*/
    List<Map<String,Object>> getDepartmentStatistics();

    /*查询7月每个工单类型的工单总量和超期率*/
    List<Map<String,Object>> getOrderTypeStatistics();
}
