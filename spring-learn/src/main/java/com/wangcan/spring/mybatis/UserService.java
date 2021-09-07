package com.wangcan.spring.mybatis;

import com.wangcan.spring.mybatis.mapper.OrderMapper;
import com.wangcan.spring.mybatis.mapper.UserMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * @author: wangcan
 * @date: 2021/9/6 17:07
 */
@Component
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;

    public void show(){
        System.out.println(userMapper.select());
        System.out.println(orderMapper.select());
    }
}
