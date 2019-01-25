package com.tian.common.handler;

import com.tian.common.validation.Validate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 基于AOP切面实现统一的日志管理
 * Created by Administrator on 2016/11/16 0016.
 */
@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);



    @Before(value = "execution(* com.tian.controller..*Controller.*(..))")
    public void before(JoinPoint jp) throws Throwable {
        // 参数有效性验证
        Validate.validate(jp);
        // 接口频率校验
//        CheckLimit.checkLimit(jp);

        logger.info("====> process in : " + jp.getSignature());
        StringBuffer params = new StringBuffer();
        String oneParam;
        for (Object o : jp.getArgs()) {
            if (o == null) {
                continue;
            }
            if (o instanceof Integer || o instanceof Long || o instanceof Double || o instanceof String || o instanceof Boolean) {
                oneParam = o + "";
            } else {
                oneParam = o.toString();
            }
            params.append(oneParam + " ; ");
        }
        logger.info("====> param : " + params.toString());

    }


    @AfterReturning(returning = "result", value = "execution(* com.tian.controller..*.*(..))")
    public void after(Object result) throws Throwable {
        logger.info("====> result : " + (result == null ? "null" : result.toString()));
    }

}
