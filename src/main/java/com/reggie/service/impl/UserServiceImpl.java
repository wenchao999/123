package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.mapper.UserMapper;
import com.reggie.pojo.User;
import com.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author NewAdmin
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

