package com.xunqi.gulimall.ssoclient.aop;

import com.xunqi.gulimall.ssoclient.dto.SaveOrder;

/**
 * Created by foreknow on 2023/2/23.
 */
public class SaveOrderConvert implements Convert<SaveOrder> {


    @Override
    public OperateLogDo convert(SaveOrder saveOrder) {
        OperateLogDo operateLogDo = new OperateLogDo();
        operateLogDo.setOrderId(saveOrder.getId());

        return operateLogDo;
    }
}
