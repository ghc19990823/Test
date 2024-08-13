package ghctest.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ghctest.entity.WorkOrder;
import ghctest.mapper.DeptMapper;
import ghctest.mapper.WorkOrderMapper;
import ghctest.service.WorkOrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements WorkOrderService {

    int[] array = {0,1,3};

    @Resource
    WorkOrderMapper workOrderMapper;

    @Resource
    DeptMapper deptMapper;

    @Override
    public IPage<WorkOrder> searchOrders(int pageNum, int pageSize) {
        return this.page(new Page<>(pageNum, pageSize));
    }

   @Override
   public void assignOrder(Long orderNo, Long deptId, String deptName) {
       WorkOrder workOrder = this.getById(orderNo);
       if (workOrder != null && deptMapper.selectById(deptId) != null && Objects.equals(deptMapper.selectById(deptId).getDeptName(), deptName)) {
           workOrder.setDeptId(deptId);
           workOrder.setFenpaiTime(LocalDateTime.now());
           workOrderMapper.updateById(workOrder);
       } else {
           throw new RuntimeException("工单号无效或部门ID不存在或者部门名称和ID不一致。");
       }
   }

    @Override
    public boolean removeById(Long id) {
        LambdaQueryWrapper<WorkOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkOrder::getId, id);
        Long aLong = workOrderMapper.selectCount(wrapper);
        log.info(String.valueOf(aLong));
        if (aLong != 0){
            workOrderMapper.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean save(WorkOrder workOrder) {
        LambdaQueryWrapper<WorkOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkOrder::getOrderNo,workOrder.getOrderNo());
        Long aLong = workOrderMapper.selectCount(wrapper);
        log.info(String.valueOf(aLong) +"==="+String.valueOf(workOrder.getOrderType()+"==="+workOrder.getId()));
        if (aLong == 0 &&  (workOrder.getId() == null || workOrder.getId() ==0)  && (workOrder.getOrderType() == 0 || workOrder.getOrderType() == 1 || workOrder.getOrderType() == 3)
                && workOrder.getOrderType() != null && !Objects.equals(workOrder.getTitle(), "") && !Objects.equals(workOrder.getContent(), "")){
            workOrder.setCreateTime(LocalDateTime.now());
            workOrder.setId(0L);
            return super.save(workOrder);
        }else {
            return false;
        }
    }

    @Override
    public boolean updateById(WorkOrder workOrder) {
        LambdaQueryWrapper<WorkOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WorkOrder::getOrderNo,workOrder.getOrderNo());
        Long aLong = workOrderMapper.selectCount(wrapper);
        if (aLong != 0 && workOrder.getId() == null  && (workOrder.getOrderType() == 0 || workOrder.getOrderType() == 1 || workOrder.getOrderType() == 3)
                && workOrder.getOrderNo() != null
                && workOrder.getOrderType() != null
                && !Objects.equals(workOrder.getTitle(), "")
                && !Objects.equals(workOrder.getContent(), "")
                && workOrder.getCreateTime() != null){
            workOrderMapper.updateFenPai(workOrder.getOrderNo(),workOrder.getOrderType(),
                    workOrder.getTitle(),workOrder.getContent(),
                    workOrder.getDeptId(),workOrder.getCreateTime(),
                    workOrder.getFenpaiTime(),workOrder.getIsOverdue());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> getDailyStatistics() {
        return workOrderMapper.selectDailyStatistics();
    }

    @Override
    public List<Map<String, Object>> getDepartmentStatistics() {
        return workOrderMapper.selectDepartmentStatistics();
    }

    @Override
    public List<Map<String, Object>> getOrderTypeStatistics() {
        return workOrderMapper.selectOrderTypeStatistics();
    }
}
