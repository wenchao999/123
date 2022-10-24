package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.common.Result;
import com.reggie.pojo.Employee;
import com.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author NewAdmin
 */
@RequestMapping("/employee")
@RestController
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
//1.将页面获取到的密码进行md5加密
        String password = employee.getPassword();

        password = DigestUtils.md5DigestAsHex(password.getBytes());
//2.根据用户名查询数据库
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);
// 3.如果没有查询到返回登录失败的结果
        if (emp == null) {
            return Result.error("登录失败");

        }
//4.密码比对，如果不一致返回登录失败
        if (!emp.getPassword().equals(password)) {
            return Result.error("登录失败");
        }
//5.查看状态
        if (emp.getStatus() == 0) {
            return Result.error("账号已禁用");


        }
// 6.登录成功，将员工的id存入session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    /***
     * 退出登录
     * @param request 获取session来删除存储在本地
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
//        清除session
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
//        设置默认密码，并进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        /***
         * 注释部分在MyMetaObjectHandler中被实现
         */
//        设置创建时间、更新时间
        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());
//        设置创建人的创建id和更新人的id
        // Long empId = (Long) request.getSession().getAttribute("employee");
        //   employee.setCreateUser(empId);
        // employee.setUpdateUser(empId);
//        保存员工信息
        employeeService.save(employee);
        return Result.success("新增员工成功");
    }

    /**
     * 分页功能的实现
     *
     * @param page     当前页
     * @param pageSize 页面显示条目
     * @param name     用户输入搜索
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String name) {
        //构建分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
//        构建条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //判断用户是否输入了name，没有的话不执行该语句
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
//        排序 根据更新时间
        queryWrapper.orderByDesc(Employee::getUpdateTime);
//        执行查询
        employeeService.page(pageInfo, queryWrapper);
//         返回查询的结果
        return Result.success(pageInfo);
    }

    /**
     * 更新数据
     *
     * @param request
     * @param employee
     * @return
     */
    @PutMapping()
    public Result<String> update(HttpServletRequest request, @RequestBody Employee employee) {
//        设置更新的人时间
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return Result.success("员工信息修改成功");

    }

    /**
     * 根据id查询用户信息
     *
     * @param id PathVariable 获取地址栏的id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> get(@PathVariable Long id) {
        log.info("根据id查询用户信息:{}", id);
        if (id != null) {
            return Result.success(employeeService.getById(id));
        }
        return Result.error("没有查询到用户信息");

    }


}
