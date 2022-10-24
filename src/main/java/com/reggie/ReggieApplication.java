package com.reggie;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringBootApplication 标明为springboot启动类
 * Slf4j    打印log日志
 * ServletComponentScan 用来注册带有servlet、filter、litter注解的类
 * EnableTransactionManagement  开启事务管理
 *
 * @author NewAdmin
 */
@Slf4j
@SpringBootApplication
@MapperScan("com.reggie.mapper")
@EnableTransactionManagement
@ServletComponentScan
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
        log.info("项目启动成功");
    }

}
