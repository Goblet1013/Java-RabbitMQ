package com.goblet.rabbitmq.routing;

import com.goblet.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String msg = "hello direct";
        String routingkey = "info";
        channel.basicPublish(EXCHANGE_NAME,routingkey,null,msg.getBytes());

        channel.close();
        connection.close();
    }
}
