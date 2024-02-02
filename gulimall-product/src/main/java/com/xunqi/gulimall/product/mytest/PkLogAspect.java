package com.xunqi.gulimall.product.mytest;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by foreknow on 2023/3/27.
 */

@Component
@Aspect
@Slf4j
public class PkLogAspect {

    @Pointcut("@annotation(com.xunqi.gulimall.product.mytest.PkLog)")
    public void pkLogAspect() {
    }

    @Before("pkLogAspect()")
    public void beforePkLog(JoinPoint joinPoint){

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //拿到注解方法的名字
        String mathodName = joinPoint.getSignature().getName();
        System.out.println("=======method"+mathodName+"begin==========");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date);

        //打印执行时间
        System.out.println("time:"+time);
        //打印请求的url
        System.out.println("URL:"+request.getRequestURL());
        //打印请求方法(get,post等)
        System.out.println("httpmethod:"+request.getMethod());
        //打印controller请求全路径及执行方法
        System.out.println("classmethod:"+joinPoint.getSignature().getDeclaringTypeName()+"."+mathodName);
        //打印请求的ip
        System.out.println("IP"+request.getRemoteHost());
        //打印请求入参
        System.out.println("request afgs:"+ JSON.toJSONString(joinPoint.getArgs()));
        System.out.println("==============");
    }

    @After("pkLogAspect()")
    public void afterPklog(JoinPoint joinPoint){
        //拿到注解方法的名字
        String mathodName = joinPoint.getSignature().getName();
        System.out.println("=======method"+mathodName+"end==========");

    }
}
