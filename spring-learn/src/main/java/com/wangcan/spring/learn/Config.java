package com.wangcan.spring.learn;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@ComponentScan("com.wangcan.spring.learn")
//@ImportResource("applicationContext.xml")//使用xml文件而非注解的方式进行启动
public class Config {
}
