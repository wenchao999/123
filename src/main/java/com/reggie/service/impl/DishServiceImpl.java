package com.reggie.service.impl;

import com.alibaba.druid.filter.AutoLoad;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.dto.DishDto;
import com.reggie.mapper.DishMapper;
import com.reggie.pojo.Dish;
import com.reggie.pojo.DishFlavor;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Transactional 事务注解  因为涉及到两张表的操作
 * @author NewAdmin
 */
@Service

public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 新增菜品，同时保存对应的口味数据
     */
    @Override
    @Transactional
    public void saveWithFlavors(DishDto dishDto) {
//        保存菜品的基本信息到菜品表
        this.save(dishDto);
        //菜品id
        Long dishId = dishDto.getId();
        //口味表
        List<DishFlavor> flavors = dishDto.getFlavors();
//        将每个元素都附上dishId的值
        flavors = flavors.stream().peek((item)-> item.setDishId(dishId)).collect(Collectors.toList());
//        保存口味表到dish_flavor saveBatch 批量插入
        dishFlavorService.saveBatch(flavors);
    }
}
