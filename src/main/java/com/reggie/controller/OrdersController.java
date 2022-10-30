package com.reggie.controller;

import com.reggie.common.CustomException;
import com.reggie.common.Result;
import com.reggie.pojo.Orders;
import com.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     *
     * @param orders 订单实体
     * @return
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders) throws CustomException {
        ordersService.submit(orders);
        return Result.success("下单成功");
    }
}
