package com.wangcan.spring.learn.scan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * @Author: wangcan
 * @Date: 2020/10/29 20:54
 *
 * 模拟mybatis 扫描接口
 */
public class ScanTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        CustomScanner customScanner = new CustomScanner(context);
        customScanner.addIncludeFilter(new AnnotationTypeFilter(MyAnnotation.class));
//        将带有MyAnnotation注解的类扫描到springIOC容器中，并返回扫描的个数
        int num = customScanner.scan("com.wangcan.spring.learn.scan");
        System.out.println("扫描的接口个数:"+num);
        context.refresh();

        MyClass myClass=context.getBean(MyClass.class);
        System.out.println(myClass);
    }

}
