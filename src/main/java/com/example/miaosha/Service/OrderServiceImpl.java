package com.example.miaosha.Service;

import com.example.miaosha.Entity.MQMessage;
import com.example.miaosha.Entity.Order;
import com.example.miaosha.MQ.MQSender;
import com.example.miaosha.Mapper.OrderMapper;
import com.example.miaosha.Service.Interface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int orderCreate(int user_id, int product_id,String msg) {

        return orderMapper.addOrder(user_id, product_id, msg);
    }

    @Override
    public boolean orderNotExist(int product_id, int user_id) {
        List<Order> orders = orderMapper.getOrderById(user_id, product_id);
        if (orders.size()==0) return true;
        else return false;
    }
}
