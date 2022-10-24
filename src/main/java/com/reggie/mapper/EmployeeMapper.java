package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NewAdmin
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
