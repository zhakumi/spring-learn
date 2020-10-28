package com.wangcan.spring.learn;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

//后置处理器
//扫描注册成功完成后，spring自动调用后置处理器MyBeanFactoryPostProcessor的postProcessBeanFactory方法
//@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("interService");
        System.out.println("扫描注册成功完成后，spring自动调用次方法");
        System.out.println(beanDefinition.getDescription());
        System.out.println("开始偷梁换柱成User");
        beanDefinition.setBeanClass(User.class);

//        spring 插件就是基于其开放性扩展出来的
        //print InterService在spring的名称：com.wangcan.spring.learn.User
    }
}
