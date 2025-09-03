package com.iurac.recruit.exception;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.iurac.recruit.util.Result;
import org.apache.shiro.ShiroException;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ModelAndView handle401(ShiroException e) {
        return errorModelAndView(401,"您没有权限");
    }

    //业务服务异常
    @ResponseStatus()
    @ExceptionHandler(value = ServiceException.class)
    public Object handlerServiceException(HttpServletRequest request, ServiceException e){
        e.printStackTrace();
        if(isAjax(request)){
            return Result.fail(e.getMessage());
        }else {
            return errorModelAndView(500,"请求超时");
        }
    }

    //后台管理异常
    @ResponseStatus()
    @ExceptionHandler(value = ManageException.class)
    public Object handlerManageException(HttpServletRequest request, ManageException e) {
        e.printStackTrace();
        if(isAjax(request)){
            return Result.fail(e.getMessage());
        }else {
            return errorModelAndView(500,"请求超时");
        }
    }

    //其它异常
    @ResponseStatus()
    @ExceptionHandler(value = Exception.class)
    public Object handlerRuntimeException(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        if(isAjax(request)){
            return Result.fail(e.getMessage());
        }else {
            return errorModelAndView(500,"请求超时");
        }
    }

    private ModelAndView errorModelAndView(Integer code,String message) {
        ModelAndView mv = new ModelAndView();
        mv.getModel().put("timestamp", DateUtil.now());
        mv.getModel().put("status", code);
        mv.getModel().put("error","系统错误");
        mv.getModel().put("message",message);
        mv.setViewName("error/4xx");
        return mv;
    }

    public static boolean isAjax(HttpServletRequest httpRequest) {
        return (ObjectUtil.isNotNull(httpRequest.getHeader("X-Requested-With"))
                && StrUtil.equals("XMLHttpRequest", httpRequest.getHeader("X-Requested-With")));
    }
}