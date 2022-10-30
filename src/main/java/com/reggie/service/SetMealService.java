package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.common.CustomException;
import com.reggie.dto.SetMealDto;
import com.reggie.pojo.SetMeal;

import java.util.List;

/**
 * @author NewAdmin
 */
public interface SetMealService extends IService<SetMeal> {
    /**
     * 新增套餐，同时需要保存餐品和菜品的关系
     */
    void saveWithDish(SetMealDto setmealDto);

    /**
     *     /**
     *      * 删除套餐和菜品的关联信息
     *      * @param ids
     *      */
    void removeWithDish(List<Long> ids) throws CustomException;
}
