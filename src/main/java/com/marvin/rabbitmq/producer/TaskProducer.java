package com.marvin.rabbitmq.producer;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-09 17:42
 **/
public class TaskProducer {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("消息发送完成：" + message);
        }
    }
}
