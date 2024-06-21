package com.example.miaosha.MQ;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.miaosha.Configuration.TopicExchangeConfig;
import com.example.miaosha.Entity.Product;
import com.example.miaosha.Mapper.ProductMapper;
import com.example.miaosha.MyResponse.MyResponse;
import com.example.miaosha.Service.Interface.OrderService;
import com.example.miaosha.Utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class MQReceiver {
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ProductMapper productMapper;


    @Transactional
    @RabbitHandler
    @RabbitListener(queues = TopicExchangeConfig.MIAOSHA_QUEUE)
    public void rabbitReceive(String mqMessage){
        JSONObject jsonObject = JSON.parseObject(mqMessage);
        int user_id = jsonObject.getInteger("user_id");
        int product_id = jsonObject.getInteger("product_id");
        log.info("============消息接收成功============");
        //Integer product = redisUtil.getMapValue("product", product_id);
        int productNum = productMapper.getProductNum(product_id);
        productNum--;
        productMapper.updateProductNum(product_id,productNum);
        orderService.orderCreate(user_id,product_id,"用户"+user_id+"秒杀成功:商品"+product_id);
    }


}
