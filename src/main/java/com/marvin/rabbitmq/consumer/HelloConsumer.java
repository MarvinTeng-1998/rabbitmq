package com.marvin.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-09 16:56
 **/
public class HelloConsumer {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息消费被中断");
        };
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("admin");
        factory.setPassword("123");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            /*
            消费者消费消息 直接调取队列
            参数1 消费哪个队列
            参数2 消费成功之后是否要自动应答，true --> 代表的是自动应答 false --> 代表的是手动应答
            参数3 消费者未成功消费的回调
            参数4 消费者取消消费的回调
             */
            channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}
