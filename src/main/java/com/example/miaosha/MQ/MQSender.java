package com.example.miaosha.MQ;


import com.alibaba.fastjson.JSON;
import com.example.miaosha.Configuration.TopicExchangeConfig;
import com.example.miaosha.Entity.MQMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate;
    public void messageSender(MQMessage mqMessage,String routingKey){
        String message = JSON.toJSONString(mqMessage);
        amqpTemplate.convertAndSend(TopicExchangeConfig.TOPIC_EXCHANGE,routingKey,message);
        log.info("====消息发送成功======");

    }

}
