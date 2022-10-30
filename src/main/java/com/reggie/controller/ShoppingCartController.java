package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.common.BaseContext;
import com.reggie.common.Result;
import com.reggie.pojo.ShoppingCart;
import com.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author NewAdmin
 */
@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public Result<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info(shoppingCart.toString() + ".........");
//        设置用户id
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
//        查询菜品或者套餐是否在购物车
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
//        查询当前用户
        wrapper.eq(ShoppingCart::getUserId, currentId);
        if (dishId != null) {
            //添加到购物车上的是菜品
            wrapper.eq(ShoppingCart::getDishId, dishId);

        } else {
            ////添加到购物车上的是套餐
            wrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart cartServiceOne = shoppingCartService.getOne(wrapper);
        if (cartServiceOne != null) {
//            如果已经存在，在原来的数据基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceOne);
        } else {
//            如果不存在，则添加到购物车上数量默认为一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
//
        return Result.success(cartServiceOne);
    }

    /**
     * 查看购物车
     *
     * @return
     */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        Long currentId = BaseContext.getCurrentId();
        wrapper.eq(ShoppingCart::getUserId, currentId);
        wrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(wrapper);
        return Result.success(list);
    }

    /**
     * 清空购物车
     *
     * @return
     */
    @DeleteMapping("/clean")
    public Result<String> clean() {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        Long currentId = BaseContext.getCurrentId();
        wrapper.eq(ShoppingCart::getUserId, currentId);
        shoppingCartService.remove(wrapper);
        return Result.success("清空购物车成功");
    }
}
