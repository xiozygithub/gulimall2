package com.xunqi.gulimall.ssoclient;

import com.xunqi.gulimall.ssoclient.dto.SaveOrder;
import com.xunqi.gulimall.ssoclient.dto.UpdateOrder;
import com.xunqi.gulimall.ssoclient.service.OrderService;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GulimallTestSsoClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
       // SpringApplication.run(GulimallTestSsoClientApplication.class, args);

        new SpringApplication(GulimallTestSsoClientApplication.class).run(args);
    }

    @Autowired
    private OrderService orderService;

    @Override
    public  void run(String... args) throws Exception {

        SaveOrder saveOrder = new SaveOrder();
        saveOrder.setId(1L);
        orderService.saveOrder(saveOrder);

        UpdateOrder updateOrder = new UpdateOrder();
        updateOrder.setOrderId(2L);
        orderService.updateOrder(updateOrder);


    }

}
