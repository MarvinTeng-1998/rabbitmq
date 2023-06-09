package com.marvin.rabbitmq.consumer;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @TODO:这是一个工作线程，相当于消费者01
 * @author: dengbin
 * @create: 2023-06-09 17:32
 **/
public class WorkConsumer01 {
    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception {
        DeliverCallback deliverCallback = (consumerTag,message) -> {
            System.out.println(new String(message.getBody()));
        };

        CancelCallback cancelCallback = (message) -> {
            System.out.println("消费取消，原因是" + message);
        };

        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C2等待接受消息.....");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        
    }
}
