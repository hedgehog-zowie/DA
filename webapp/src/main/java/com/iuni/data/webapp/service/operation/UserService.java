package com.iuni.data.webapp.service.operation;

import com.iuni.data.persist.model.operation.*;

import java.util.List;

/**
 * @author zowie
 *         Email:   hedgehog.zowie@gmail.com
 */
public interface UserService {

    /**
     * 查询用户注册数据
     * @param registerQueryDto
     * @return
     */
    List<RegisterTableDto> selectRegister(RegisterQueryDto registerQueryDto);

    /**
     * 查询用户留存数据
     * @param userQueryDto
     * @return
     */
    List<UserTableDto> selectUser(UserQueryDto userQueryDto);

    /**
     * 查询用户登录行为数据
     * @param userBehaviorQueryDto
     * @return
     */
    List<UserBehaviorTableDto> selectUserBehavior(UserBehaviorQueryDto userBehaviorQueryDto);

}
