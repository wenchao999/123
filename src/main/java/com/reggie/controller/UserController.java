package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.reggie.common.Result;
import com.reggie.pojo.User;
import com.reggie.service.UserService;
import com.reggie.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author NewAdmin
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {
//        获取前端输入的手机号码
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
//            生成四位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
//            发送验证码,且行且珍惜
//            SMSUtils.send(phone,code);
//            保存验证码和手机信息
            log.info("........" + code + "........");
            session.setAttribute(phone, code);
            return Result.success("发送短信成功");
        }
        return Result.error("发送短信失败");
    }

    /**
     * 移动端登录
     *
     * @param
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody Map map, HttpSession session) {
//        1.获取手机号码
        String phone = map.get("phone").toString();

//        2.获取验证码
        String code = map.get("code").toString();
//        3.从session取出保存的验证码
        String sessionCode = session.getAttribute(phone).toString();
//        4.验证码的比对
        if (sessionCode != null && sessionCode.equals(code)) {
//            5. 如果比对成功, 说明登录成功
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone, phone);
            User user = userService.getOne(wrapper);
            if (user == null) {
//         6.判断当前的手机号码是否为新用户，如果是新用户完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
//            将用户的id的存入session中
            session.setAttribute("user", user.getId());
            return Result.success(user);
        }
        return Result.error("登录失败");

    }
}
