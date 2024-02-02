package com.xunqi.gulimall.ssoclient.service;

import com.xunqi.gulimall.ssoclient.aop.RecordeOperate;
import com.xunqi.gulimall.ssoclient.aop.SaveOrderConvert;
import com.xunqi.gulimall.ssoclient.aop.UpdateOrderConvert;
import com.xunqi.gulimall.ssoclient.dto.UpdateOrder;
import com.xunqi.gulimall.ssoclient.dto.SaveOrder;
import org.springframework.stereotype.Service;

/**
 * Created by foreknow on 2023/2/22.
 */
@Service
public class OrderService {


    @RecordeOperate(desc = "保存订单",convert = SaveOrderConvert.class)
    public Boolean saveOrder(SaveOrder saveOrder){
        System.out.println("save order,order id:"+saveOrder.getId().toString());
        return true;
    }


    @RecordeOperate(desc = "更新订单",convert = UpdateOrderConvert.class)
    public Boolean updateOrder(UpdateOrder updateOrder){
        System.out.println("update order,order id:"+updateOrder.getOrderId().toString());
        return true;
    }

}
