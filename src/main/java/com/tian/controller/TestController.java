package com.tian.controller;

import com.alibaba.fastjson.JSONObject;
import com.tian.common.mq.MessageProducer;
import com.tian.common.other.BusinessException;
import com.tian.common.other.ResponseData;
import com.tian.common.util.DocumentUtil;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @RestController 注解整合了@Controller和@ResponseBody, 这样就不用每个返回方法标识返回json了
 * Created by Administrator on 2018/5/9 0009.
 */
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private MessageProducer messageProducer;

    /** 当有多个同一类型的bean时, 通过名字注入*/
    @Resource(name = "testQueue1")
    private Queue queue;
    /** 像这种与业务直接相关的, 也可以不用注解, 直接在这new*/
    private Queue queue2 = new ActiveMQQueue("queue2");
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private RedisTemplate redisTemplate;


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

    /**
     * 查看直接返回中文String是否乱码.
     * 没有乱码, 后来版本spring修复了这个问题
     *
     * @return
     */
    @RequestMapping("testReturnString")
    public String testReturnString(){
        return "这是中文";
    }

    /**
     * 测试返回一个业务异常
     * @return
     */
    @RequestMapping("testBizExp")
    public ResponseData testBizExp(){
        if(1==1){
            throw new BusinessException(520,"手动拋出异常");
        }
        return ResponseData.failedData;
    }

    /**
     * 测试异常拦截器
     * @return
     */
    @RequestMapping("testException")
    public ResponseData testException(){
        throw new RuntimeException();
    }

    /**
     * 测试下载文件, 同时测试没有返回值时,这里默认的@ResponseBody注解是否会有异常.
     * 没有问题
     * @param request
     * @param response
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @RequestMapping("testNoReturn")
    public void testNoReturn(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List list = new ArrayList();
        OutputStream out = response.getOutputStream();

        String fileName = System.currentTimeMillis()+".xlsx";
        // 区分是否为ie浏览器,解决中文乱码问题
        String header = request.getHeader("User-Agent").toUpperCase();
        if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
            fileName = URLEncoder.encode(fileName, "utf-8");
            fileName = fileName.replace("+", "%20");    //IE下载文件名空格变+号问题
        } else {
            fileName = new String(fileName.getBytes("utf-8"), "ISO8859-1");
        }

        // 设置响应头,
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream");
        String[] strArr = {"手机号", "内容", "时间", "通道编号", "下发端口号"};
        DocumentUtil.exportExcel(fileName,strArr,list,out,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 发送普通邮件.
     * 模版邮件, 其实所谓的模版邮件,就是先把邮件内容存入文件中, 然后读取文件内容,替换掉变量部分
     * 然后把这个包含html文本的内容当成一个字符串set进Text中.
     * @return
     */
    @RequestMapping("sendSimpleMail")
    public ResponseData sendSimpleMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo("13510272496@139.com");
        message.setSubject("标题：测试标题");
        // 这里的文本内容是可以包含html标签及样式的, 邮箱会解析加载
        message.setText("测试内容部份");
        javaMailSender.send(message);
        return ResponseData.successData;
    }

    /**
     * 发送带有附件的邮件
     * @return
     */
    @RequestMapping("sendMultipartMail")
    public ResponseData sendMultipartMail() throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(username);
        helper.setTo("13510272496@139.com");
        helper.setSubject("标题：测试标题");
        helper.setText("有附件的邮件");
        FileSystemResource fileSystemResource = new FileSystemResource(new File("C:\\Users\\Administrator\\Desktop\\临时文件夹\\a.jpg"));
        helper.addAttachment("a.jpg", fileSystemResource);
        javaMailSender.send(message);
        return ResponseData.successData;
    }

    /**
     * 测试springboog带的redis工具类的使用
     * @return
     */
    @RequestMapping("testRedis")
    public ResponseData testRedis(){
        redisTemplate.opsForValue().set("key1", "value1",5,TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("key1"));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(redisTemplate.opsForValue().get("key1"));
        return ResponseData.successData;
    }

}
