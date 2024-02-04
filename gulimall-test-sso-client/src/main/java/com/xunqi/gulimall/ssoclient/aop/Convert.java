package com.xunqi.gulimall.ssoclient.aop;

public interface Convert<PARAM> {
    OperateLogDo convert(PARAM param);
}
