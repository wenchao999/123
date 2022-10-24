package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.dto.DishDto;
import com.reggie.pojo.Dish;

/**
 * @author NewAdmin
 */
public interface DishService extends IService<Dish> {

    /**
     * 新增菜品，同时插入菜品对于的口味数据、需要操作两张表 dish dish flavor
     * @param dishDto
     */
    void saveWithFlavors(DishDto dishDto);

}

