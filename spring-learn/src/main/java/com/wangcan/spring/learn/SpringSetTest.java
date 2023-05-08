package com.wangcan.spring.learn;


import com.wangcan.spring.learn.scan.Config;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 手工 初始化beanDefinition
 */
public class SpringSetTest {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //手工初始化Bean
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName("com.wangcan.spring.learn.post.processor.InterService");
        beanDefinition.setScope("singleton");
        beanDefinition.setDescription("手工注入");
        beanDefinition.setAbstract(false);
        //对应bean的 属性set方法
        beanDefinition.getPropertyValues().add("name","wangcan");
        beanDefinition.getPropertyValues().add("age","1");
        // 设置一个不存在的属性就会报错
//        beanDefinition.getPropertyValues().add("age1","1");
        // 设置属性值
        beanDefinition.setAttribute("name", "wangcan");
        // 将bean 注册到spring 容器，底层是保存到ConcurrentHashMap中  spring ioc 对应底层就是一堆Map集合
        context.registerBeanDefinition("interService", beanDefinition);
        // 完成spring 启动扫描解析 实例化等一系列过程
        context.refresh();

//        获取InterService对应的BeanDefinition ，默认名称为interService，关于名字的更改以后讲
        BeanDefinition interServiceBeanDefinition = context.getBeanDefinition("interService");
        //获取属性值
        String[] atts = interServiceBeanDefinition.attributeNames();
        for (int i = 0; i <atts.length ; i++) {
            System.out.println(atts[i]);
        }

        System.out.println("——————InterService的附加属性如下：");
        System.out.println("父类：" + interServiceBeanDefinition.getParentName());
        System.out.println("描述：" + interServiceBeanDefinition.getDescription());
        System.out.println("InterService在spring的名称：" + interServiceBeanDefinition.getBeanClassName());
        System.out.println("实例范围" + interServiceBeanDefinition.getScope());
        System.out.println("是否是懒加载" + interServiceBeanDefinition.isLazyInit());
        System.out.println("是否是抽象类" + interServiceBeanDefinition.isAbstract());
    }
}
