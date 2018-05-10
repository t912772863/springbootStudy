package com.tian.controller;

import com.alibaba.fastjson.JSONObject;
import com.tian.common.mq.MessageProducer;
import com.tian.common.other.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Queue;

/**
 * @RestController 注解整合了@Controller和@ResponseBody, 这样就不用每个返回方法标识返回json了
 * Created by Administrator on 2018/5/9 0009.
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private MessageProducer messageProducer;
    @Resource(name = "testQueue1")
    private Queue queue;
    @Resource(name = "queue2")
    private Queue queue2;

    @RequestMapping("test1")
    public JSONObject test1(String content){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 200);
        jsonObject.put("data", "this is test method. content="+content);
        return jsonObject;
    }

    /**
     * 测试整合spring boot, activemq
     * @param message
     * @return
     */
    @RequestMapping("testActiveMQ")
    public ResponseData testActiveMQ(String message){

        messageProducer.sendMessage(queue, message);
        return ResponseData.successData.setData("create message success.");
    }

    @RequestMapping("testActiveMQ2")
    public ResponseData testActiveMQ2(String message){

        messageProducer.sendMessage(queue2, message);
        return ResponseData.successData.setData("create message success.");
    }
}
