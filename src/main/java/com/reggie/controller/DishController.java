package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.Result;
import com.reggie.dto.DishDto;
import com.reggie.pojo.Category;
import com.reggie.pojo.Dish;
import com.reggie.service.CategoryService;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author NewAdmin
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    /**
     * 菜品、口味
     */
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavors(dishDto);
        return Result.success("新增菜品成功");
    }

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
//        构造分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
//        构造条件构造器
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
//        添加过滤条件
        wrapper.like(name != null, Dish::getName, name);
        wrapper.orderByDesc(Dish::getUpdateTime);
//        执行分页查询
        dishService.page(pageInfo, wrapper);
//        对象拷贝 ignoreProperties参数意思是忽略某个参数，使其不被复制 records对于数据集合
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
//            将item的普通属性拷贝到dishDto
            BeanUtils.copyProperties(item, dishDto);
//             查询id

            Long categoryId = item.getCategoryId();
//            根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return Result.success(dishDtoPage);

    }

    /**
     * 根据id查询菜品信息和口味信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavors(id);
        return Result.success(dishDto);
    }

    /**
     * 新增菜品
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavors(dishDto);
        return Result.success("修改菜品成功");
    }

    /**
     * 删除/批量信息
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Long[] ids) {
        for (Long id : ids) {
            dishService.removeById(id);
        }
        return Result.success("删除菜品信息成功");
    }

    /**
     * 批量停/启售
     * @param state 状态
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/{state}")
    public Result<String> status(@PathVariable int state, Long[] ids){
        System.out.println(Arrays.toString(ids) +"...........");
        Dish dish = new Dish();
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId,  ids);
        dish.setStatus(state);
        dishService.update(dish,queryWrapper);
        return Result.success("修改成功");
    }


}
