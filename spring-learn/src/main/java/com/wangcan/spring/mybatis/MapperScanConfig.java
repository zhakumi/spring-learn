package com.wangcan.spring.mybatis;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @author: wangcan
 * @date: 2021/9/6 17:05
 */
@ComponentScan("com.wangcan.spring.mybatis")
@Import(MapperBeanDefinitionRegistry.class)
public class MapperScanConfig {


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        InputStream inputStream= Resources.getResourceAsStream("mybatis.xml");
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(MapperScanConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        userService.show();
    }

}
