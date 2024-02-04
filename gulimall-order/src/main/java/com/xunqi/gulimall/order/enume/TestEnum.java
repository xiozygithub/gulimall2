package com.xunqi.gulimall.order.enume;

public enum TestEnum {

    CREAME_NEW(01,"你干嘛！"),
    pay(02,"哎哟")
    ;


    private Integer code;
    private String msg;

    TestEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


}
