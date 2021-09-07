package com.wangcan.spring.learn.test;

import com.wangcan.spring.learn.mapper.UserMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangcan
 * @date 2021/9/4 9:23
 */
public class UserService {

    @Autowired
    private UserMapper userMapper;

}
