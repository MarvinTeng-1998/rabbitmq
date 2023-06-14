package com.marvin.rabbitmq.diedqueue;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-12 17:54
 **/
public class Producer01 {
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 延迟消息 或者是死信消息 设置TTL消息
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 0; i < 10; i++) {
            String message = "info:" + i;
            System.out.println("生产者发送消息：" + message);
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", properties, message.getBytes("UTF-8"));
        }
    }
}
