package ghctest.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ghctest.entity.WorkOrder;
import ghctest.service.WorkOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/post/order")
@Tag(name = "接口",description = "接口 API")
public class FenPaiController {

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping("/save")
    @Operation(summary = "添加工单")
    public String saveOrder(@RequestBody WorkOrder workOrder) {
        if (workOrderService.save(workOrder)) {
            return "工单保存成功";
        }
        return "工单保存失败,请检查必填项,请不要输入ID值，或者删除id字段！";
    }

    @PostMapping("/delete")
    @Operation(summary = "删除工单")
    public String deleteOrder(@RequestParam Long id) {
        if (workOrderService.removeById(id)) {
            return "工单删除成功";
        }
        return "工单删除失败，请细心填写删除的id。";
    }

    @PostMapping("/update")
    @Operation(summary = "更新工单")
    public String updateOrder(@RequestBody WorkOrder workOrder) {
        if (workOrderService.updateById(workOrder)) {
            return "工单更新成功";
        }
        return "工单更新失败,请检查必填项或检查工单类型是否在指定数值中选取！id请不要填写！";
    }

    @PostMapping("/search")
    @Operation(summary = "分页查询")
    public IPage<WorkOrder> searchOrders(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "3") int pageSize) {
        return workOrderService.searchOrders(pageNum, pageSize);
    }

    @PostMapping("/fenpai")
    @Operation(summary = "工单分派")
    public String assignOrder(@RequestParam Long orderNo, @RequestParam Long deptId, @RequestParam String deptName) {
        try {
            workOrderService.assignOrder(orderNo, deptId, deptName);
            return "工单分派成功";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Operation(summary = "7月每天的工单总量、超期率")
    @GetMapping("/daily")
    public List<Map<String, Object>> getDailyStatistics() {
        return workOrderService.getDailyStatistics();
    }

    @Operation(summary = "查询7月每个部门的工单总量和超期率")
    @GetMapping("/Department")
    public List<Map<String,Object>> getDepartmentStatistics(){
        return workOrderService.getDepartmentStatistics();
    }

    @Operation(summary = "查询7月每个工单类型的工单总量和超期率")
    @GetMapping("/orderType")
    public List<Map<String,Object>> getOrderTypeStatistics(){
        return workOrderService.getOrderTypeStatistics();
    }
}
