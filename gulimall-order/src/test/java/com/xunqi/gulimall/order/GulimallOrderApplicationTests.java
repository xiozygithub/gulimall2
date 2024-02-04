package com.xunqi.gulimall.order;

import com.xunqi.gulimall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallOrderApplicationTests {

    //创建交换机等的组件
    @Autowired
    private AmqpAdmin amqpAdmin;

    //收发消息的组件
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送消息
     * 参数：交换机，路由键，消息内容
     */
    @Test
    public void sendMessageTest() {

        //这里是随便拿的一个业务对象OrderReturnReasonEntity，退货原因对象
        OrderReturnReasonEntity reasonEntity = new OrderReturnReasonEntity();
        reasonEntity.setId(1L);
        reasonEntity.setCreateTime(new Date());
        reasonEntity.setName("reason");
        reasonEntity.setStatus(1);
        reasonEntity.setSort(2);
        //1、发送消息,如果发送的消息是个对象，会使用序列化机制，将对象写出去，对象必须实现Serializable接口

        //2、发送的对象类型的消息，可以是一个json
        //convertAndSend转换并发送，将对象转换为字节流发送出去
        //new CorrelationData(UUID.randomUUID().toString())消息的唯一id
        // 即correlationData：当前消息的唯一关联数据(这个是消息的唯一id)，在MyRabbitConfig的设置确认回调中会用到
        rabbitTemplate.convertAndSend("hello-java-exchange","hello2.java",
                reasonEntity,new CorrelationData(UUID.randomUUID().toString()));
        log.info("消息发送完成:{}",reasonEntity);
    }

    /**
     * 1、如何创建Exchange、Queue、Binding
     *      1）、使用AmqpAdmin进行创建
     * 2、如何收发消息
     */
    @Test
    public void createExchange() {

        Exchange directExchange = new DirectExchange("hello-java-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange[{}]创建成功：","hello-java-exchange");
    }



    @Test
    public void testCreateQueue() {
        //参数依次为：名字，持久化，不排他，不自动删除
        Queue queue = new Queue("hello-java-queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        log.info("Queue[{}]创建成功：","hello-java-queue");
    }


    @Test
    public void createBinding() {
        /**
         * 参数
         * String destination,【目的地】
         * Binding.DestinationType destinationType,【目的地类型】：交换机可以绑定的类型有交换机、消息队列
         * String exchange,【交换机】
         * String routingKey,【路由键】
         * Map<String, Object> arguments【参数】
         */
        Binding binding = new Binding(
                "hello-java-queue",
                Binding.DestinationType.QUEUE,
                "hello-java-exchange",
                "hello.java",
                null);
        amqpAdmin.declareBinding(binding);
        log.info("Binding[{}]创建成功：","hello-java-binding");

    }

    @Test
    public void create() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        arguments.put("x-message-ttl", 60000); // 消息过期时间 1分钟
        Queue queue = new Queue("order.delay.queue", true, false, false, arguments);
        amqpAdmin.declareQueue(queue);
        log.info("Queue[{}]创建成功：","order.delay.queue");
    }

}
