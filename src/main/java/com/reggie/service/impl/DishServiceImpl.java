package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.dto.DishDto;
import com.reggie.mapper.DishMapper;
import com.reggie.pojo.Dish;
import com.reggie.pojo.DishFlavor;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Transactional 事务注解  因为涉及到两张表的操作
 *
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
    @Transactional(rollbackFor = Exception.class)
    public void saveWithFlavors(DishDto dishDto) {
//        保存菜品的基本信息到菜品表
        this.save(dishDto);
        //菜品id
        Long dishId = dishDto.getId();
        //口味表
        List<DishFlavor> flavors = dishDto.getFlavors();
//        将每个元素都附上dishId的值
        flavors = flavors.stream().peek((item) -> item.setDishId(dishId)).collect(Collectors.toList());
//        保存口味表到dish_flavor saveBatch 批量插入
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdWithFlavors(Long id) {
//        查询菜品基本信息，从dish
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
//        从口味表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
//       获取相应的口味表
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWithFlavors(DishDto dishDto) {
        //更新dish表的基本信息
        this.updateById(dishDto);
        // 清理当前表的口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        //添加当前提交的口味
        List<DishFlavor> flavors = dishDto.getFlavors();
//        将dish id添加到
        flavors = flavors.stream().peek((item) -> item.setDishId(dishDto.getId())).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);

    }
}
