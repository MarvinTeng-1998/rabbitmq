package com.marvin.rabbitmq.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @TODO:生产者 发送消息给队列
 * @author: dengbin
 * @create: 2023-06-09 16:45
 **/
public class HelloProducer {


    // 创建一个队列
    public static final String QUEUE_NAME = "mirrior_hello";

    public static void main(String[] args) {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接工厂的IP，连接rabbitmq的队列
        factory.setHost("127.0.0.1");
        // 设置用户名
        factory.setUsername("root");
        // 设置密码
        factory.setPassword("123456");
        factory.setPort(5672);

        try {
            // 创建连接
            Connection connection = factory.newConnection();
            // 获取信道 可以使用默认交换机，直接跟队列进行沟通
            Channel channel = connection.createChannel();
            // 创建一个队列
            // 参数1 队列名称
            // 参数2 是否是需要持久化消息，默认情况下消息存储在内存中，不持久化。
            // 参数3 该队列是否只提供一个消费者进行消费，是否进行消息的共享。如果是true表示可以多个消费者消费，如果是false是不允许多个消费者消费
            // 参数4 是否自动删除 最后一个消费者断开连接后，队列是否自动删除，如果是true自动删除，如果是false不自动删除
            // 参数5 其他参数：延迟消息、死信消息

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "hello world";
            // 参数1 填写交换机
            // 参数2 路由key --> 队列的名称
            // 参数3 其他参数信息
            // 参数4 消息体
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("消息发送完毕");

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }


}
