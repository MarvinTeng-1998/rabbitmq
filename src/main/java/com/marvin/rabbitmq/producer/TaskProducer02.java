package com.marvin.rabbitmq.producer;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @TODO:消息在手动应答不消失，放回队列中重新进行消费
 * @author: dengbin
 * @create: 2023-06-11 18:21
 **/
public class TaskProducer02 {
    // 创建一个新的队列名称
    public static final String TASK_NAME = "ack_queue";
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(TASK_NAME,false,false,false,null);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",TASK_NAME,null,message.getBytes("UTF-8"));
            System.out.println("生产者发送消息：" + message);
        }

    }
}
