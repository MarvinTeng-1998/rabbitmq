package com.marvin.rabbitmq.consumer;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-12 16:01
 **/
public class Direct01 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("console", false, false, false, null);
        channel.queueBind("console", EXCHANGE_NAME, "info");
        channel.queueBind("console",EXCHANGE_NAME,"warnings");
        channel.basicConsume("console", (consumerTag, message) -> {
                    System.out.println("Direct01消息消费成功" + new String(message.getBody(),"UTF-8"));
                    channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
                },
                (consumerTag -> {
                    System.out.println("Direct01消息不被消费");
                }));
    }
}
