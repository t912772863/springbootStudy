package com.tian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/5/9 0009.
 */
@Controller
@RequestMapping("test")
public class TestController {
    @RequestMapping("test1")
    @ResponseBody
    public String test1(){
        return "this is test method.";
    }
}
