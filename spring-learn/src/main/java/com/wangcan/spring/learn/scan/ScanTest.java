package com.wangcan.spring.learn.scan;

import com.wangcan.spring.learn.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * @Author: wangcan
 * @Date: 2020/10/29 20:54
 */
public class ScanTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(Config.class);
        CustomScanner customScanner = new CustomScanner(context);
        customScanner.addIncludeFilter(new AnnotationTypeFilter(MyAnnotation.class));
        //将带有MyAnnotation注解的类扫描到springIOC容器中，并返回扫描的个数
        int num = customScanner.scan("com.wangcan.spring.learn.scan");
        System.out.println("扫描的个数:"+num);
        context.refresh();

        System.out.println(context.getBean(MyClass.class));

    }

}
