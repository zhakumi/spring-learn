package com.wang.test.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.test.domain.User;
import com.wang.test.mapper.UserMapper;
import com.wang.test.service.IOmsUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangcan
 * @since 2020-11-03
 */
@Service
public class OmsUserServiceImpl extends ServiceImpl<UserMapper, User> implements IOmsUserService {

}
