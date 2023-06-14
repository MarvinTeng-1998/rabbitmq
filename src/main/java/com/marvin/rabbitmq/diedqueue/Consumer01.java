package com.marvin.rabbitmq.diedqueue;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @TODO: 消费者1
 * @author: dengbin
 * @create: 2023-06-12 17:44
 **/
public class Consumer01 {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DIED_EXCHANGE = "died_exchange";
    public static final String DIED_QUEUE = "died_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DIED_EXCHANGE, BuiltinExchangeType.DIRECT);

        Map<String,Object> arguments = new HashMap<>();
        // 过期时间 10s
        arguments.put("x-message-ttl",10000);
        // 正常队列设置死信交换机是谁
        arguments.put("x-dead-letter-exchange",DIED_EXCHANGE);
        // 设置死信routingKey
        arguments.put("x-dead-letter-routing-key","lisi");
        // 设置队列的最大长度
        arguments.put("x-max-length",6);
        // 普通队列
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);
        // 死信队列
        channel.queueDeclare(DIED_QUEUE,false,false,false,null);

        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        channel.queueBind(DIED_QUEUE,DIED_EXCHANGE,"lisi");

        System.out.println("等待接受消息...");

        channel.basicConsume(NORMAL_QUEUE,true,(consumerTag, message) -> {
            System.out.println("consumer01 接受的消息是：" + new String(message.getBody(),"UTF-8"));
        },consumerTag -> {
            System.out.println("消息消费失败");
        });
    }
}
