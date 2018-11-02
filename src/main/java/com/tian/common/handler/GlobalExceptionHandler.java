package com.tian.common.handler;

import com.tian.common.other.BusinessException;
import com.tian.common.other.ResponseData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * 通过@ControllerAdvice结合@ExceptionHandler 也可以实现全局异常处理. 而且可以区分不同类型的异常, 相对于之前的DefaultExceptionHandler更直观. 且优先
 * 于DefaultExceptionHandler. 也就是两个都配置的时候,DefaultExceptionHandler无效.
 * Created by tianxiong on 2018/11/2.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseData handleBizExp(HttpServletRequest request, BusinessException ex){
        System.out.println("handleBizExp");
        ex.printStackTrace();
        ResponseData responseData = new ResponseData(ex.getErrorCode(),"failed" ,ex.getErrorMessage());
        return responseData;
    }

    @ExceptionHandler(SQLException.class)
    public ModelAndView handSql(Exception ex){
        ModelAndView mv = new ModelAndView();
        mv.addObject("message", ex.getMessage());
        mv.setViewName("sql_error");
        return mv;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseData handleSysExp(Exception e){
        e.printStackTrace();
        System.out.println("handleSysExp");
        return ResponseData.failedData;
    }

}
