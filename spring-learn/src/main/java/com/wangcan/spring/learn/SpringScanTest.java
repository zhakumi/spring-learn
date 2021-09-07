package com.wangcan.spring.learn;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring scan package test
 */
@Configuration
public class SpringScanTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(Config.class);
        context.scan("com.wangcan.spring.learn");
        context.refresh();
    }


}
