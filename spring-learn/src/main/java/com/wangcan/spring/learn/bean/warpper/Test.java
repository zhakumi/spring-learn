package com.wangcan.spring.learn.bean.warpper;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author wangcan
 * @date 2022/3/12 9:44
 */
public class Test {

    //BeanWrapper 相当于是Spring中的一个包装类，对Bean 进行包装，具有（单独或批量）获取和设置属性值，获取属性描述符以及查询属性的可读性/可写性的能力。
    public static void main(String[] args) {
        Student student = new Student();
        BeanWrapper wrapper = new BeanWrapperImpl(student);
        wrapper.setPropertyValue("name", "wangan");
        wrapper.setPropertyValue("age", 1);
        Student student1 = (Student) wrapper.getWrappedInstance();
        System.out.println(student1.getName());
    }
}
