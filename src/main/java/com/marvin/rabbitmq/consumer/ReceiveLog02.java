package com.marvin.rabbitmq.consumer;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

/**
 * @TODO:消息接收方
 * @author: dengbin
 * @create: 2023-06-12 15:24
 **/
public class ReceiveLog02 {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queue = channel.queueDeclare().getQueue();
        // 绑定队列
        channel.queueBind(queue, EXCHANGE_NAME, "");
        System.out.println("ReceiveLog02等待接受消息...把接受到的消息打印在屏幕上...");

        channel.basicConsume(queue, false,
                (consumerTag, message) -> {
                    System.out.println("ReceiveLog02消息接受成功:" + new String(message.getBody(), "UTF-8"));
                    channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                },
                (consumerTag -> {
                    System.out.println("ReceiveLog02消息消费失败");
                }));

    }
}
