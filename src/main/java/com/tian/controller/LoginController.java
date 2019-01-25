package com.tian.controller;

import com.tian.dao.entity.User;
import com.tian.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 与登录相关的控制层(其它不需要做登录校验的请求也放这里,方便统一处理)
 * Created by Administrator on 2016/12/13 0013.
 */
@Controller
@RequestMapping("login")
public class LoginController{
    @Autowired
    private IUserService userService;


    /**
     * 用户登录接口(用户名,密码)
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public String login(String userName, String password, HttpServletRequest request){
        User user = userService.queryUserByUserNameAndPassword(userName,password);
        if(user != null){
            // 用户登录成功,把当前登录用户信息放入Session中管理
            request.getSession().setAttribute("user",user);
            return "success";
        }
        return "failed";
    }

    /**
     * 根据id更新
     * @param user
     * @return
     */
    @RequestMapping("update")
    @ResponseBody
    public String update(User user){
        userService.updateByUserId(user);
        return "success";
    }

}
