package com.wangcan.spring.mybatis;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author: wangcan
 * @date: 2021/9/6 17:05
 */
public class MapperScanConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.scan("com.wangcan.spring.mybatis");

        // 注册userMapper
        AbstractBeanDefinition beanDefinition= BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(MapperFactoryBean.class);
        beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(UserMapper.class);
        context.registerBeanDefinition("userMapper",beanDefinition);

        // 注册orderMapper
        AbstractBeanDefinition orderDefinition= BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        orderDefinition.setBeanClass(MapperFactoryBean.class);
        orderDefinition.getConstructorArgumentValues().addGenericArgumentValue(OrderMapper.class);
        context.registerBeanDefinition("orderMapper",orderDefinition);

//        AbstractBeanDefinition beanDefinition= BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        beanDefinition.setBeanClass(UserService.class);
//        context.registerBeanDefinition("userService",beanDefinition);

//        AbstractBeanDefinition beanDefinition= BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
//        beanDefinition.setBeanClass(AFactoryBean.class);
//        context.registerBeanDefinition("a",beanDefinition);
        context.refresh();

        //        System.out.println(context.getBean("a"));
//        System.out.println(context.getBean("&a"));

        UserService userService = context.getBean("userService", UserService.class);
        userService.show();

    }

}
