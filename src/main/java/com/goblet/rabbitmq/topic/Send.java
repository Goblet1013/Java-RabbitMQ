package com.goblet.rabbitmq.topic;

import com.goblet.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        String msg = "hello topic";
        channel.basicPublish(EXCHANGE_NAME,"goods.delete",null,msg.getBytes());

        channel.close();
        connection.close();
    }
}
