package com.goblet.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    /**
     * 获取MQ连接
     * @return
     * @throws IOException
     * @throws TimeoutException
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址、端口、vhost、username、password
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/my_vhost");
        factory.setUsername("goblet");
        factory.setPassword("goblet");
        return factory.newConnection();
    }
}
