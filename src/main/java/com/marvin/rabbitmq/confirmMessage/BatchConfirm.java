package com.marvin.rabbitmq.confirmMessage;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.UUID;

/**
 * @TODO: 发布确认模式之 批量确认消息
*                --> 测试结果为37ms，大幅度变快
 * @author: dengbin
 * @create: 2023-06-12 00:33
 **/
public class BatchConfirm {

    public static final int MESSAGE_COUNT = 1000;

    public static final String QUEUE_NAME = UUID.randomUUID().toString();

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        // 开启发布确认
        channel.confirmSelect();
        long begin = System.currentTimeMillis();

        // 批量确认消息大小
        int batchSize = 100;

        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + " ";
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes("UTF-8"));

            // 判断达到100条消息的时候 批量确认一次
            if(i % batchSize == 0){
                boolean flag = channel.waitForConfirms();
                if(flag){
                    System.out.println("批量确认成功");
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MESSAGE_COUNT + "个单独确认消息，耗时" + (end - begin) + "ms");
    }
}
