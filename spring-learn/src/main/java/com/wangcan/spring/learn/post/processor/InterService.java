package com.wangcan.spring.learn.post.processor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Description("业务类")
@Scope("singleton")
public class InterService implements InitializingBean, DisposableBean {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet do");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("InterService postConstruct");
    }


    public InterService() {
        System.out.println("实例化：InterService 构造");
    }

    public void destroy() throws Exception {
        System.out.println("destory");
    }


    @PreDestroy
    public void destroy1() {
        System.out.println("PreDestroy");
    }
}