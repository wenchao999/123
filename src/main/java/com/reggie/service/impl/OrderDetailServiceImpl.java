package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.mapper.OrderDetailMapper;
import com.reggie.pojo.OrderDetail;
import com.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author NewAdmin
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
