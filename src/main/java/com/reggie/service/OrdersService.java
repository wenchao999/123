package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.common.CustomException;
import com.reggie.pojo.Orders;
import org.springframework.core.annotation.Order;

/**
 * @author NewAdmin
 */
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders) throws CustomException;
}
