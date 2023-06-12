package com.marvin.rabbitmq.consumer;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-12 16:19
 **/
public class Topic01 {

    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare("q1", false, false, false, null);
        channel.queueBind("q1", EXCHANGE_NAME, "*.orange.*");

        channel.basicConsume("q1",false, (consumerTag, message) -> {
            System.out.println("Topic01消费者消费消息: " + new String(message.getBody(),"UTF-8"));
            System.out.print("/t" + "键名" + message.getEnvelope().getRoutingKey());
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        }, consumerTag -> {
            System.out.println("Topic01消费者消费消息失败");
        });
    }
}
