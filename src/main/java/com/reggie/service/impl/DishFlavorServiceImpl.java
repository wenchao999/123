package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.mapper.DishFlavorMapper;
import com.reggie.pojo.DishFlavor;
import com.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author NewAdmin
 */
@Service
public class DishFlavorServiceImpl  extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
