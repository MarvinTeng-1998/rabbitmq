package com.marvin.rabbitmq.consumer;

import com.marvin.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @TODO:消息在手动应答时是不丢失的、放回队列中重新消费
 * @author: dengbin
 * @create: 2023-06-11 18:24
 **/
public class WorkConsumer03 {

    public static final String QUEUE_NAME = "ack_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C2等待消息处理，时间较长");

        DeliverCallback deliverCallback = ((consumerTag, message) -> {
            // 消息沉睡一秒
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("接受到的消息" + new String(message.getBody(),"UTF-8"));

            /*
            手动应答：
            参数1 -> 消息的标记tag
            参数2 -> 是否批量应答信道中的消息，处理一个应答一个，如果批量则会出现消息的丢失
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        });

        CancelCallback cancelCallback = (consumerTag -> {
            System.out.println("消费者取消消费");
        });

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,deliverCallback,cancelCallback);

    }
}
