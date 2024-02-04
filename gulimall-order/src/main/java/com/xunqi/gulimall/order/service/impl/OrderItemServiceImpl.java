package com.xunqi.gulimall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import com.xunqi.common.utils.PageUtils;
import com.xunqi.common.utils.Query;
import com.xunqi.gulimall.order.dao.OrderItemDao;
import com.xunqi.gulimall.order.entity.OrderEntity;
import com.xunqi.gulimall.order.entity.OrderItemEntity;
import com.xunqi.gulimall.order.entity.OrderReturnReasonEntity;
import com.xunqi.gulimall.order.service.OrderItemService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@RabbitListener(queues = {"hello-java-queue"})
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }


    /**
     * queues：声明需要监听的队列
     * channel：当前传输数据的通道
     * message是消息的完整内容，有消息体和属性。
     * 后面的是消息体的内容，我们发送的消息是什么对象，这里接受就可以填该对象，省去了将json转为对象的过程
     * @RabbitListener 监听消息的方法可以有三种参数（不分数量，顺序）
     * Object content, Message message, Channel channel
     */
    //@RabbitListener(queues = {"hello-java-queue"})
    @RabbitHandler
    public void receiveMessage(Message message,
                               OrderReturnReasonEntity content,
                               Channel channel) throws IOException {
        //拿到主体内容
        byte[] body = message.getBody();
        //拿到的消息头属性信息
        MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("接受到的消息...内容" + message + "===内容：" + content);
        //通道内按顺序自增的数字，代表了服务的顺序
        long deliveryTag = messageProperties.getDeliveryTag();

        try {
            //手动签收货物,false代表不批量签收消息，做一个签收一个。
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            //一定是网络中断
            //退货
            /**
             * long deliveryTag,通道内按顺序自增的数字，代表了服务的顺序
             * boolean multiple,是否批量拒绝
             * boolean requeue  是否重新入队。true为重新入队
             * basicNack()与basicReject()的差别是前者能批量拒绝，
             */
            channel.basicNack(deliveryTag,false,false);
            //long deliveryTag, boolean requeue
            //channel.basicReject();
        }

    }

    @RabbitHandler
    public void receiveMessage2( OrderEntity content) {

        System.out.println("接受到的消息...内容"  + content);

    }

}