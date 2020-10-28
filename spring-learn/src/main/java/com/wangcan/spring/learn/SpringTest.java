package com.wangcan.spring.learn;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringTest {
    public static void main(String[] args) throws InterruptedException {
        // 以下三行 完成spring 的启动
        //
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(Config.class);
//        加载或者刷新当前的配置信息
        context.refresh();;
//        获取InterService对应的BeanDefinition ，默认名称为interService，关于名字的更改以后讲。

        BeanDefinition interServiceBeanDefinition = context.getBeanDefinition("interService");

        System.out.println("——————InterService的附加属性如下：");
        System.out.println("父类："+interServiceBeanDefinition.getParentName());
        System.out.println("描述："+interServiceBeanDefinition.getDescription());
        System.out.println("InterService在spring的名称："+interServiceBeanDefinition.getBeanClassName());
        System.out.println("实例范围"+interServiceBeanDefinition.getScope());
        System.out.println("是否是懒加载"+interServiceBeanDefinition.isLazyInit());
        System.out.println("是否是抽象类"+interServiceBeanDefinition.isAbstract());
        System.out.println("——————等等等等，读者自行编写");
        System.out.println("获取interService.class的源："+interServiceBeanDefinition.getSource());

        //在更改业务类后 此时会报错 已经没有InterService的实例了
        context.getBean(InterService.class);
    }
}
