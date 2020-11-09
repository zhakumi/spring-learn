package com.wang.test.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: wangcan 自动填充
 * @Date: 2018/12/20 16:56
 */
@Component
public class MyInitObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
        this.setFieldValByName("version",0,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}
