# java-RabbitMQ
java-RabbitMQ学习  
官网教程：https://www.rabbitmq.com/getstarted.html

MQ作用：异步、解耦、削峰  

# RabbitMQ的几种形式  
1.最简单的:  
生产者--队列--消费者  
  
2.工作队列:  
生产者--队列--多个消费者
消费者可以竞争获取消息，能者多劳，处理消息花费的时间更短的消费者可以获取更多的消息  
  
3.发布订阅模式：  
                队列--消费者
生产者--交换机--
                队列--消费者
