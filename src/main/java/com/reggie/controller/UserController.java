package com.reggie.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.reggie.common.Result;
import com.reggie.pojo.User;
import com.reggie.service.UserService;
import com.reggie.util.SMSUtils;
import com.reggie.util.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author NewAdmin
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {
//        获取前端输入的手机号码
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone))
        {
//            生成四位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
//            发送验证码
            SMSUtils.send(phone,code);
//            保存验证码和手机信息
            session.setAttribute(phone,code);
            return Result.success("发送短信成功");
        }
        return Result.error("发送短信失败");
    }

}
