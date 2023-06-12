package com.marvin.rabbitmq.confirmMessage;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @TODO: 发布确认模式之 异步确认发布模式
 *                      --> 耗时25ms --> 25ms
 * @author: dengbin
 * @create: 2023-06-12 00:44
 **/
public class AsynchronousConfirm {

    public static final int MAX_COUNT = 1000;

    public static final String QUEUE_NAME = UUID.randomUUID().toString();

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 开启发布确认
        channel.confirmSelect();

        /*
        线程安全有序的表，适用于高并发的场景 --> 跳表
        1. 轻松的将序号与消息进行关联
        2. 轻松的批量删除已经确认的消息
        3. 支持高并发
         */
        ConcurrentSkipListMap<Long,String> outstandingConfirms = new ConcurrentSkipListMap<>();

        // 准备消息的监听器，监听哪些消息成功了，哪些消息失败了

        // 消息确认成功 回调函数
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {
            if(multiple){
                // 删除已经确认的消息，剩下的就是未确认的消息
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag);
                confirmed.clear();
            }else{
                // 如果不是批量的，那就删除当前这个tag的消息
                outstandingConfirms.remove(deliveryTag);
            }
            System.out.println("消息" + deliveryTag + "发送成功");
        };
        // 消息确认失败 回调函数
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            String nackMessage = outstandingConfirms.get(deliveryTag);
            System.out.println("未确认的消息是:" + nackMessage);
            // 打印一下未确认的消息都有哪些
            System.out.println("消息" + deliveryTag + "发送失败");
        };
        /*
        参数1:监听哪些消息成功了
        参数2:监听哪些消息失败了
         */
        channel.addConfirmListener(ackCallback, nackCallback);


        long begin = System.currentTimeMillis();
        // 批量发布，异步发布。
        for (int i = 0; i < MAX_COUNT; i++) {
            String message = "消息" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            // 1.这里记录所有要发送的消息
            outstandingConfirms.put(channel.getNextPublishSeqNo(),message);
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + MAX_COUNT + "个消息，耗时" + (end - begin) + "ms");
    }
}
