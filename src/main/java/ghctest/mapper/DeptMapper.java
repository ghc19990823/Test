package ghctest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ghctest.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {
}
