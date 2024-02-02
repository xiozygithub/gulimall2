package com.xunqi.gulimall.ssoclient.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordeOperate {

    String desc() default "";

    //类必须是convert的子类
    Class<? extends Convert> convert();
}
