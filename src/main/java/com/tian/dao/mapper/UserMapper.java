package com.tian.dao.mapper;

import com.tian.dao.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    /**
     * 根据用户名和密码查询用户
     * @param userName
     * @param password
     * @return
     */
    User queryByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

    /**
     * 根据手机号查询用户信息
     * @param mobile
     * @return
     */
    User queryByMobile(@Param("mobile") String mobile);
}