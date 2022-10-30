package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.CustomException;
import com.reggie.common.Result;
import com.reggie.dto.SetMealDto;
import com.reggie.pojo.Category;
import com.reggie.pojo.Dish;
import com.reggie.pojo.SetMeal;
import com.reggie.service.CategoryService;
import com.reggie.service.SetMealDishService;
import com.reggie.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 套餐管理
 */

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetMealController {

    @Autowired
    private SetMealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetMealDishService setmealDishService;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody SetMealDto setmealDto) {
        log.info("套餐信息：{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return Result.success("新增套餐成功");
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        Page<SetMeal> pageInfo = new Page<>(page, pageSize);
        Page<SetMealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, SetMeal::getName, name);
        queryWrapper.orderByDesc(SetMeal::getUpdateTime);
        setmealService.page(pageInfo, queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dtoPage, "records");
        List<SetMeal> records = pageInfo.getRecords();
        List<SetMealDto> list = records.stream().map((item) -> {
            SetMealDto setMealDto = new SetMealDto();
//            拷贝普通的属性
            BeanUtils.copyProperties(item, setMealDto);
//            分类id
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setMealDto.setCategoryName(categoryName);
            }
            return setMealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return Result.success(dtoPage);

    }

    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids) throws CustomException {
        log.info(ids.toString()+"......");
        setmealService.removeWithDish(ids);
        return Result.success("删除成功");
    }
    @PostMapping("/status/{state}")
    public Result<String> status(@PathVariable int state, @RequestParam List<Long> ids) {
        SetMeal setMeal = new SetMeal();
        LambdaQueryWrapper<SetMeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetMeal::getId, ids);
        setMeal.setStatus(state);
        setmealService.update(setMeal, queryWrapper);
        return Result.success("修改成功");
    }

    /**
     * 根据条件查询套餐信息
     * @param setMeal
     * @return
     */
    @GetMapping("/list")
    public Result<List<SetMeal>> list(SetMeal setMeal){
        LambdaQueryWrapper<SetMeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setMeal.getCategoryId()!=null,SetMeal::getCategoryId,setMeal.getCategoryId());
        wrapper.eq(setMeal.getStatus()!=null,SetMeal::getStatus,setMeal.getStatus());
        wrapper.orderByDesc(SetMeal::getUpdateTime);
        List<SetMeal> list = setmealService.list(wrapper);
        return  Result.success(list);
    }

}
