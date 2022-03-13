package com.wangcan.spring.learn.post.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: wangcan
 * @date: 2021/9/3 21:21
 */
@Component
public class MyBeanPostProcess implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName)
        throws BeansException {
        if(bean instanceof InterService) {
            System.out.println("BeanPostProcessor bean befor");
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName)
        throws BeansException {
        if(bean instanceof InterService) {
            System.out.println("BeanPostProcessor bean after");
        }
        return bean;
    }
}
