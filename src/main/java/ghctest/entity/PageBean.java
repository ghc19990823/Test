package ghctest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean {
    private String title;         // 标题查询条件
    private Integer orderType ;    // 工单类型查询条件
    private Integer pageNumber = 1;   // 当前页码
    private Integer pageSize = 3;     // 每页条数
}
