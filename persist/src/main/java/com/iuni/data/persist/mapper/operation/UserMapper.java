package com.iuni.data.persist.mapper.operation;

import com.iuni.data.persist.model.operation.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zowie
 *         Email:   hedgehog.zowie@gmail.com
 */
@Service("userMapperOfOperation")
public interface UserMapper {

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
