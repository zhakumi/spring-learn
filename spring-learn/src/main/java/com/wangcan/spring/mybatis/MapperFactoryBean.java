package com.wangcan.spring.mybatis;

import java.lang.reflect.Method;
import lombok.val;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

/**
 * @author: wangcan
 * @date: 2021/9/6 19:19
 */
public class MapperFactoryBean implements FactoryBean {


    private Class mapperClass;
    private SqlSession sqlSession;

    public MapperFactoryBean(Class mapperClass) {
        this.mapperClass = mapperClass;
    }

    public Object getObject() throws Exception {
        return sqlSession.getMapper(mapperClass);
    }

    public Class<?> getObjectType() {
        return mapperClass;
    }

    @Autowired
    public void setSqlSession(SqlSessionFactory sessionFactory) {
        sessionFactory.getConfiguration().addMapper(mapperClass);
        this.sqlSession = sessionFactory.openSession();
    }
}
