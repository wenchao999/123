package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NewAdmin
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
