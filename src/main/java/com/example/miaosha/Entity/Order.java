package com.example.miaosha.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    int order_id;
    int order_user_id;
    int order_product_id;
    String order_msg;
}
