package com.wangcan.spring.learn;

import com.wangcan.spring.learn.bean.warpper.Student;
import org.springframework.beans.factory.DisposableBean;

/**
 * @Author: wangcan
 * @Date: 2020/10/28 16:07
 */
public class PerformerImpl implements Performer
    , DisposableBean {


    private Student student;
    private String name;


    public void perform() throws PerformanceException {
        System.out.println(name);
    }

    public void init() {
        System.out.println("init");
    }

    public void destroy() throws Exception {
        System.out.println("destory");
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
