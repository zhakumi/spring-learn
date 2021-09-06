package com.wangcan.spring.mybatis;

import org.apache.ibatis.annotations.Select;

/**
 * @author: wangcan
 * @date: 2021/9/6 19:36
 */
public interface OrderMapper {

    @Select("select 'order'")
    String select();
}
