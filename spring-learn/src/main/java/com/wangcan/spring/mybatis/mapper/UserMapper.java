package com.wangcan.spring.mybatis.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author: wangcan
 * @date: 2021/9/6 17:07
 */
public interface UserMapper {

    @Select("select user_name from OMS_USER limit 1")
    String select();
}
