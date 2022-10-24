package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.common.CustomException;
import com.reggie.pojo.Category;

/**
 * @author NewAdmin
 */
public interface CategoryService extends IService<Category> {
    /**
     * 删除分类，判断分类有无关联套餐、菜品
     *
     * @param id 前端获取的id
     * @throws CustomException
     */
    void remove(Long id) throws CustomException;
}
