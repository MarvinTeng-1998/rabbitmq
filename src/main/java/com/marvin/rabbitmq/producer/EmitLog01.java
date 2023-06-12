package com.marvin.rabbitmq.producer;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @TODO: 发消息给交换机
 * @author: dengbin
 * @create: 2023-06-12 15:24
 **/
public class EmitLog01 {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String next =  scanner.next();
            channel.basicPublish(EXCHANGE_NAME,"error",null,next.getBytes("UTF-8"));
            System.out.println("生产者发出消息" + next);
        }
    }
}
