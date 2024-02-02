package com.xunqi.gulimall.ssoclient.aop;

import lombok.Data;

import javax.xml.bind.PrintConversionEvent;

/**
 * Created by foreknow on 2023/2/23.
 * 切面注解类
 */

@Data
public class OperateLogDo {

    private Long orderId;

    private String desc;

    private String result;

}
