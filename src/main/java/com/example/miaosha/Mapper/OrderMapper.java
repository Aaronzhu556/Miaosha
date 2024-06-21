package com.example.miaosha.Mapper;

import com.example.miaosha.Entity.Order;
import com.example.miaosha.Entity.Product;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int addOrder(int user_id,int product_id,String msg);
    List<Order> getOrderById(int user_id,int product_id);

}
