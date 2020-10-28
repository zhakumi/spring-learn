package com.wangcan.spring.learn;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

/**
 * @Author: wangcan
 * @Date: 2020/10/28 16:09
 * xml 定义Bean
 */
public class XmlTest {

    public static void main(String[] args) throws PerformanceException {
        //利用Resource抽象实现类，来包装这个包含了BeanDefinition的定义信息
        ClassPathResource res=new ClassPathResource("applicationContext.xml");
        //创建一个BeanFactory，DefaultListableBeanFactory
        DefaultListableBeanFactory factory=new DefaultListableBeanFactory();
        // 创建一个载入BeanDefinition的读取器，通过回调配置给BeanFactory
        XmlBeanDefinitionReader reader=new XmlBeanDefinitionReader(factory);
        // 从定义好好的Resource中，读取配置信息。由XmlBeanDefinitionReader完成解析，完成整个载入和注册Bean定义的过程
        reader.loadBeanDefinitions(res);
        // 通过BeanFactory的getBean()方法，获取对应的Bean。这里涉及到了Bean的实例化以及依赖注入
        Performer performer=(Performer) factory.getBean("performer");
        // 调用bean的方法
        performer.perform();
    }
}
