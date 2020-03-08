package com.goblet.rabbitmq.workfair;

import com.goblet.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//能者多劳，recv1处理时间更短，recv1接受的数据就多，send,recv1,recv2都要设置channel.basicQos(1);
public class SendWork {

    private static final String QUEUE_NAME = "test_work_fair_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //获取channel
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);
        for (int i=0;i<50;i++){
            String msg = "hello " + i;
            System.out.println("SendWork:"+msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(5*i);
        }
        channel.close();
        connection.close();
    }
}
