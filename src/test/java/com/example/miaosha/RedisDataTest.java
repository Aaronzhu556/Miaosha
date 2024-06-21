package com.example.miaosha;

import com.example.miaosha.Entity.Product;
import com.example.miaosha.Mapper.ProductMapper;
import com.example.miaosha.Utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class RedisDataTest {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ProductMapper productMapper;
    @Test
    public void redisData(){
        HashMap<Integer,Integer> map = new HashMap<>();
        List<Product> allProduct = productMapper.getAllProduct();
        for (Product product:allProduct){
            map.put(product.getProduct_id(),product.getProduct_num());
        }
        redisUtil.add("product",map);
        log.info("============redis库存预加载成功==============");
    }
}
