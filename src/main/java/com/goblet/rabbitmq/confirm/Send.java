package com.goblet.rabbitmq.confirm;

import com.goblet.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();
        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明,在控制台中可以看到新增的test_simple_queue
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //将channel设置为confirm模式
        channel.confirmSelect();
        String msg = "hello confirm";
        //单条确认
//        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        //多条确认
        for (int i=0;i<10;i++){
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }
        if (!channel.waitForConfirms()){
            System.out.println("msg send failed");
        }else{
            System.out.println("msg send ok");
        }
        System.out.println("--send msg:"+msg);
        channel.close();
        connection.close();
    }
}
