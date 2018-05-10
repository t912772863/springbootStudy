package com.tian.service;


import com.tian.dao.entity.User;

/**
 * Created by tian on 2016/10/12.
 */
public interface IUserService {
    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    public User queryUserById(Long id);

    /**
     * 新增用户
     * @param user
     * @return
     */
    public boolean insertUser(User user);


    /**
     * 根据用户名和密码查询用户
     * @param userName
     * @param password
     * @return
     */
    User queryUserByUserNameAndPassword(String userName, String password);

    /**
     * 根据手机号查询用户信息
     * @param mobile
     * @return
     */
    User queryUserByMobile(String mobile);
}
