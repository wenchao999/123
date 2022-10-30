package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NewAdmin
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{
}
