package com.example.miaosha.Entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MQMessage {
    int user_id;
    int product_id;

}
