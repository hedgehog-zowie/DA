package com.iuni.data.webapp.service.operation.impl;

import com.iuni.data.persist.mapper.operation.UserMapper;
import com.iuni.data.persist.model.operation.*;
import com.iuni.data.webapp.service.operation.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zowie
 *         Email:   hedgehog.zowie@gmail.com
 */
@Service("userServiceOfOperation")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<RegisterTableDto> selectRegister(RegisterQueryDto registerQueryDto) {
        return userMapper.selectRegister(registerQueryDto);
    }

    @Override
    public List<UserTableDto> selectUser(UserQueryDto userQueryDto) {
        return userMapper.selectUser(userQueryDto);
    }

    @Override
    public List<UserBehaviorTableDto> selectUserBehavior(UserBehaviorQueryDto userBehaviorQueryDto) {
        return userMapper.selectUserBehavior(userBehaviorQueryDto);
    }

}
