package com.robingao.springbootrocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

@Component
public class RocketProduce {

    public  static final  String name_ser_addr = "172.17.119.67:9876";
    public static void main(String[] args) throws Exception {

    }

    public void send() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("Robin-ProducerGroup");
        producer.setNamesrvAddr(name_ser_addr);
        // Launch producer
        producer.start();
        int totalMessagesToSend = 3;
        for (int i = 0; i < totalMessagesToSend; i++) {
            String tempStr = "Hello scheduled message " + i;
            Message message = new Message("Robin-Topic", tempStr.getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(3);
            // Send the message
            producer.send(message);
            System.out.println("send scheduled message = "+tempStr);
        }
        // Shutdown producer after use.
        producer.shutdown();
    }

    public void sendSimpleMessage() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("Robin-ProducerGroup");
        producer.setNamesrvAddr(name_ser_addr);
        // Launch producer
        producer.start();
        int totalMessagesToSend = 3;
        for (int i = 0; i < totalMessagesToSend; i++) {
            String tempStr = "Hello  message " + i;
            Message message = new Message("Robin-Topic", tempStr.getBytes());
            // This message will be delivered to consumer 10 seconds later.
            //message.setDelayTimeLevel(3);
            // Send the message
            producer.send(message);
            System.out.println("send message = "+tempStr);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }
}
