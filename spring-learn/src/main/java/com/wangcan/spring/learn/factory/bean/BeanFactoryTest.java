package com.wangcan.spring.learn.factory.bean;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanFactoryTest {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        AbstractBeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        definition.setBeanClass(AFactoryBean.class);
        ctx.registerBeanDefinition("a", definition);
        ctx.refresh();
        // factoryBean 获取bean
        A a = (A) ctx.getBean("a");
        a.show();

        // factoryBean 获取工厂bean
        AFactoryBean aFactoryBean = (AFactoryBean) ctx.getBean("&a");
        System.out.println(aFactoryBean.getObject());
        System.out.println(aFactoryBean);

    }
}
