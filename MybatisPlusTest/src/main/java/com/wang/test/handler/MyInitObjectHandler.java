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
        Object createTime = getFieldValByName("createTime", metaObject);
        if (createTime == null) {
            this.setFieldValByName("createTime", now, metaObject);
        }
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (updateTime == null) {
            this.setFieldValByName("updateTime", now, metaObject);
        }
        Object version = getFieldValByName("version", metaObject);
        if (version == null) {
            this.setFieldValByName("version", 0, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (updateTime == null) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
    }
}
