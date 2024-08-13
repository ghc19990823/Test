package ghctest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "dept")
@Data
public class Dept {
    @TableId(type = IdType.AUTO)
    private Long deptId;
    private String deptName;
}
