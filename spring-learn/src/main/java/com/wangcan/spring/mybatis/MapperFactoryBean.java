package com.wangcan.spring.mybatis;

import java.lang.reflect.Method;
import lombok.val;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

/**
 * @author: wangcan
 * @date: 2021/9/6 19:19
 */
//@Component
public class MapperFactoryBean implements FactoryBean {


    private Class mapperObjectClass;

    public MapperFactoryBean(Class mapperObjectClass) {
        this.mapperObjectClass = mapperObjectClass;
    }

    public Object getObject() throws Exception {
        val object = Proxy.newProxyInstance(MapperFactoryBean.class.getClassLoader(),
            new Class[]{mapperObjectClass},
            new InvocationHandler() {

                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    System.out.println(method.getName());
                    return null;
                }
            });
        return object;
    }

    public Class<?> getObjectType() {
        return mapperObjectClass;
    }
}
