package com.wangcan.spring.learn.post.processor;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

//bean工厂后置处理器
//扫描注册成功完成后，spring自动调用后置处理器MyBeanFactoryPostProcessor的postProcessBeanFactory方法
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("interService");
        System.out.println("BeanFactoryPostProcessor 扫描注册成功完成后，spring自动调用次方法");
//        System.out.println("开始偷梁换柱成User");
//        beanDefinition.setBeanClass(User.class);
    }
}
