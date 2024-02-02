package com.xunqi.gulimall.ssoclient.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by foreknow on 2023/2/23.
 * 切入点
 * 切面逻辑
 * 织入
 */
@Aspect
@Component
public class OperateAspect {

    //切点是注解
    @Pointcut("@annotation(com.xunqi.gulimall.ssoclient.aop.RecordeOperate)")
    public void pointcut(){

    }

    //定义一个线程池，使用异步来记录流水，避免干扰主业务性能
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            2,2,1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(100)
    );

    //环绕增强切面方法
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();

        threadPoolExecutor.execute(()->{

            try {

                MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
                RecordeOperate annotation = methodSignature.getMethod().getAnnotation(RecordeOperate.class);//拿到方法上的注解

                //通过反射拿到类convert的真实对象(实现)
                Class<? extends Convert> convert = annotation.convert();
                Convert logConvert = convert.newInstance();

                OperateLogDo operateLogDo = logConvert.convert(pjp.getArgs()[0]);//将反射的入参放进去

                //构造记录流水的模型
                operateLogDo.setDesc(annotation.desc());
                operateLogDo.setResult(result.toString());

                System.out.println("intsert operateLog:" + JSON.toJSONString(operateLogDo));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        });

        //异步方法记录流水

        return result;
    }
}
