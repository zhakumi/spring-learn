package com.wangcan.spring.learn;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * spring scan package test
 */
public class SpringScanTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(Config.class);
        context.scan("com.wangcan.spring.learn");
        context.refresh();
    }
}
