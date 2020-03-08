package com.goblet.rabbitmq.confirm;

import com.goblet.rabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Send_confirm_sync {

    private static String QUEUE_NAME = "test_queue_confirm_sync";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.confirmSelect();
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //未确认的消息
        channel.addConfirmListener(new ConfirmListener() {
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(deliveryTag);
                if (multiple){
                    System.out.println("handleAck----multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    System.out.println("handleAck----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }

            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println(deliveryTag);
                if (multiple){
                    System.out.println("handleNAck----multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    System.out.println("handleNAck----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msg = "sss";
        int i = 100;
        while (i--!=0){
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,(msg+seqNo).getBytes());
            confirmSet.add(seqNo);
        }
    }
}
