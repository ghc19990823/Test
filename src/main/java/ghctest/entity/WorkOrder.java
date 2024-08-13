package ghctest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@TableName(value = "work_order")
@Data
public class WorkOrder {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orderNo;
    private Integer orderType;
    private String title;
    private String content;
    private Long deptId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fenpaiTime;
    private Integer isOverdue;


}
