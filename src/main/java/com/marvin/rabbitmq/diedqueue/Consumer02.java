package com.marvin.rabbitmq.diedqueue;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-12 18:09
 **/
public class Consumer02 {
    public static final String DIED_QUEUE = "died_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.basicConsume(DIED_QUEUE,true,(consumerTag, message) -> {
            System.out.println("consumer02 接受的消息是：" + new String(message.getBody(),"UTF-8"));
        },consumerTag -> {
            System.out.println("消费失败");
        });
    }
}
