package com.robingao.springbootrocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RocketController {
    @Autowired
    RocketConsume rocketConsume;
    @Autowired
    RocketProduce rocketProduce;
    @GetMapping("/send")
    public  String sendMsg() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        System.out.println(" send start");
        rocketProduce.send();
        return "send ok";
    }

    @GetMapping("/simple")
    public  String sendSimpleMsg() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        System.out.println(" send start");
        rocketProduce.sendSimpleMessage();
        return "send simple ok";
    }

    @GetMapping("/consume")
    public  String consumeMsg() throws MQClientException {
        System.out.println(" consume start");
        rocketConsume.consume();
        return "consume start ok";
    }


}
