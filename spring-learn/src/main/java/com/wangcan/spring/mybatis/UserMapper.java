package com.wangcan.spring.mybatis;

import org.apache.ibatis.annotations.Select;

/**
 * @author: wangcan
 * @date: 2021/9/6 17:07
 */
public interface UserMapper {

    @Select("select 'test'")
    String select();
}
