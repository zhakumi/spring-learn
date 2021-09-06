package com.wangcan.spring.mybatis;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author: wangcan
 * @date: 2021/9/6 18:59
 */
@Component
public class AFactoryBean implements FactoryBean {

    public Object getObject() throws Exception {
        return new A();
    }

    public Class<?> getObjectType() {
        return A.class;
    }
}
