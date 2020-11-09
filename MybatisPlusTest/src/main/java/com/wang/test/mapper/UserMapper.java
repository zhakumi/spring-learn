package com.wang.test.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wang.test.domain.User;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: wangcan
 * @Date: 2018/12/20 15:10
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 注解形式
     *
     * @param name
     * @return
     */
    @Select("SELECT * FROM OMS_USER where name = #{name}")
    List<User> selectByName(@Param("name") String name);


    /**
     * 如果自定义的方法还希望能够使用MP提供的Wrapper条件构造器，则需要如下写法
     *
     * @param userWrapper
     * @return
     */
    @Select("SELECT * FROM OMS_USER ${ew.customSqlSegment}")
    List<User> selectByMyWrapper(@Param(Constants.WRAPPER) Wrapper<User> userWrapper);


    /**
     * xml 和Mybatis使用方法一致
     *
     * @param id
     * @return
     */
    User selectById(@Param("id") Long id);


    /**
     * 全表更新
     */
    @Update("update OMS_USER set user_name=1")
    void updateAll();
}
