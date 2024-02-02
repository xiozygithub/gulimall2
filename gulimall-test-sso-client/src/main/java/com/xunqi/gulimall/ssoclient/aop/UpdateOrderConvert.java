package com.xunqi.gulimall.ssoclient.aop;

import com.xunqi.gulimall.ssoclient.dto.UpdateOrder;

/**
 * Created by foreknow on 2023/2/23.
 */
public class UpdateOrderConvert implements Convert<UpdateOrder>{
    @Override
    public OperateLogDo convert(UpdateOrder updateOrder) {

        OperateLogDo operateLogDo = new OperateLogDo();
        operateLogDo.setOrderId(updateOrder.getOrderId());

        return operateLogDo;
    }
}
