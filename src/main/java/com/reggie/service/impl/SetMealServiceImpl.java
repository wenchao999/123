package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.common.CustomException;
import com.reggie.dto.SetMealDto;
import com.reggie.mapper.SetMealMapper;
import com.reggie.pojo.SetMeal;
import com.reggie.pojo.SetMealDish;
import com.reggie.service.SetMealDishService;
import com.reggie.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author NewAdmin
 */
@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal> implements SetMealService {
    @Autowired
    private SetMealDishService setMealDishService;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param setmealDto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveWithDish(SetMealDto setmealDto) {
        super.save(setmealDto);
        //保存套餐的基本信息，操作setmeal，执行insert操作

        List<SetMealDish> setmealDishes = setmealDto.getSetMealDishes();
        setmealDishes.stream().peek((item) -> item.setSetmealId(setmealDto.getId())).collect(Collectors.toList());


        //保存套餐和菜品的关联信息，操作setmeal_dish,执行insert操作
        setMealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐和菜品的关联信息
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeWithDish(List<Long> ids) throws CustomException {
        //查询套餐的状态，确实是否可以删除
        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SetMeal::getId,ids);
        wrapper.eq(SetMeal::getStatus,1);
        //统计状态为1的数目
        long count = this.count(wrapper);
        //如果不能删除，抛出业务异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖中，不能被删除");
        }
        //如果可以删除，先删除套餐表中的数据  setmeal
        this.removeByIds(ids);
        //删除关联表中的数据 setmeal-dish
        LambdaQueryWrapper<SetMealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetMealDish::getSetmealId,ids);
        setMealDishService.remove(queryWrapper);


    }

}

