package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.CustomException;
import com.reggie.common.Result;
import com.reggie.pojo.Category;
import com.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author NewAdmin
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     */
    @PostMapping
    public Result<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return Result.success("新增分类成功");
    }

    /**
     * 分页功能的实现
     *
     * @param page     当前页
     * @param pageSize 页面显示条目
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize) {
        //构建分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
//        构建条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

//        排序 根据sort
        queryWrapper.orderByAsc(Category::getSort);
//        执行查询
        categoryService.page(pageInfo, queryWrapper);
//         返回查询的结果
        return Result.success(pageInfo);
    }

    /**
     * 根据id删除分类
     *
     * @param id 获取前端来的id
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Long id) throws CustomException {
        categoryService.remove(id);
        return Result.success("删除分类信息成功");

    }

    /**
     * 修分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success("更新分类信息成功");

    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */

    @GetMapping("/list")
    public Result<List> list(Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category.getType() != null, Category::getType, category.getType());
        /* 排序  顺序   时间 */
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(wrapper);
        return Result.success(list);
    }


}
