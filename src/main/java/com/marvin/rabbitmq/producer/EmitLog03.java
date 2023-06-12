package com.marvin.rabbitmq.producer;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * @TODO:
 * @author: dengbin
 * @create: 2023-06-12 16:25
 **/
public class EmitLog03 {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        Scanner scanner = new Scanner(System.in);
        // int i = 0;
        while(scanner.hasNext()){
            String next = scanner.next();
            // if(i % 3 == 0){
            //     channel.basicPublish(EXCHANGE_NAME,"quick.orange.rabbit",null,next.getBytes("UTF-8"));
            // }else if(i % 3 == 1){
            //     channel.basicPublish(EXCHANGE_NAME,"lazy.orange.elephant",null,next.getBytes("UTF-8"));
            // }else if(i % 3 == 2){
            //
            // }
            channel.basicPublish(EXCHANGE_NAME,"lazy.abc.123.sad",null,next.getBytes("UTF-8"));
            // i++;
        }
    }
}
