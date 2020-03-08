package com.goblet.rabbitmq.ps;

import com.goblet.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 *                     --queue1--消费者1
 * 生产者----交换机----
 *                     --queue2--消费者2
 *
 *
 */
public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机.交换机没有存储的能力，没有队列绑定到交换机，则数据丢失
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");//分发
        String msg = "hello ps";
        channel.basicPublish(EXCHANGE_NAME,"",null,msg.getBytes());
        System.out.println("send:"+msg);
        channel.close();
        connection.close();
    }
}
