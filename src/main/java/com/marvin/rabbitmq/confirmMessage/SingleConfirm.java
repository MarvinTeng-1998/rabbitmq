package com.marvin.rabbitmq.confirmMessage;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.UUID;

/**
 * @TODO: 发布确认模式之 单个确认
 *
 * @author: dengbin
 * @create: 2023-06-11 23:55
 **/
public class SingleConfirm {
    // 批量发消息的个数
    //
    public static final int MESSAGE_COUNT = 1000;
    private static final String QUEUE_NAME = UUID.randomUUID().toString();
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 把消息持久化...
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        // 开启发布确认
        channel.confirmSelect();
        // 开始时间
        long begin = System.currentTimeMillis();

        // 批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + " ";
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));
            // 单个消息就马上进行发布确认
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("消息发送成功");
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("发布" +  MESSAGE_COUNT + "个单独确认消息，耗时" + (end - begin) + "ms");

    }
}
