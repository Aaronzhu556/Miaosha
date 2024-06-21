package com.example.miaosha.Service.Interface;

import com.example.miaosha.Entity.MQMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    int orderCreate(int user_id ,int product_id,String msg);
    boolean orderNotExist(int product_id, int user_id);
}
