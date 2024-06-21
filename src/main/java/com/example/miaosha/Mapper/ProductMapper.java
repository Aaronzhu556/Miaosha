package com.example.miaosha.Mapper;

import com.example.miaosha.Entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> getAllProduct();
    int updateProductNum(int product_id, int product_num);

    int getProductNum(int product_id);
}
