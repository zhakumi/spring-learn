package com.wangcan.spring.mybatis.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @author: wangcan
 * @date: 2021/9/6 19:36
 */
public interface OrderMapper {

    @Select("select contact from OMS_ORDER limit 1")
    String select();
}
