package com.xunqi.gulimall.ssoclient;

import com.xunqi.gulimall.ssoclient.dto.SaveOrder;
import com.xunqi.gulimall.ssoclient.dto.UpdateOrder;
import com.xunqi.gulimall.ssoclient.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallTestSsoClientApplicationTests {

    @Autowired
    OrderService orderService;

    @Test
    public void contextLoads() {

    }


    @Test
    public  void test( ) {

        SaveOrder saveOrder = new SaveOrder();
        saveOrder.setId(3L);
        orderService.saveOrder(saveOrder);

        UpdateOrder updateOrder = new UpdateOrder();
        updateOrder.setOrderId(2L);
        orderService.updateOrder(updateOrder);


    }
}
