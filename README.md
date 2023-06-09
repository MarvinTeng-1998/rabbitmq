# Self-Learning RabbitMQ

主要是记录用来自己一步一步学习RabbitMQ的过程。

## MQ的基本概念
  - 流量削峰
  - 应用解耦
  - 异步处理

## MQ的种类
  - ActiveMQ
  - Kafka
  - RocketMQ
  - RabbitMQ

## RabbitMQ的四大核心概念
  - 生产者：产生消息
  - 交换机：可以和多个队列绑定
  - 队列：绑定的不同队列对应着不同的消费者或消费者组
  - 消费者：消费消息

## 其他概念
  - Broker：接受和分法消息实体，里面包含交换机和队列
  - Virtual Host：多租户和安全的场景下，把AMQP的基本组件划分到一个虚拟的分组中，类似于网络中的namespace的概念。
  - Connection：publisher/consumer和Broker之间创建的TCP连接。
  - Channel：每一个连接内部会创建一个逻辑连接，每一个进程的线程跟Channel进行连接
  - Exchange：message到底Broker的第一站，一个Exchange中对应着多个Queue。

