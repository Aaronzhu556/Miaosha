package com.example.miaosha.Controller;

import com.example.miaosha.Entity.MQMessage;
import com.example.miaosha.MQ.MQSender;
import com.example.miaosha.MyResponse.MyResponse;
import com.example.miaosha.Service.Interface.OrderService;
import com.example.miaosha.Utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/order")
@Slf4j
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MQSender mqSender;
    @RequestMapping("/create")
    @ResponseBody
    public MyResponse createOrder(@RequestParam int user_id, @RequestParam int product_id){
        String product_list = "product";
        String code = "",msg="";

        if (redisUtil.getMapValue(product_list, product_id)>0){
            if (!orderService.orderNotExist(product_id, user_id))
                return MyResponse.builder()
                        .res_code("202")
                        .res_msg("重复秒杀")
                        .res_object(null).build();
            Integer product_num = redisUtil.getMapValue(product_list, product_id);
            product_num--;  //预减库存，当商品被秒杀完后，剩下的请求不会到达服务器
            redisUtil.hashSet(product_list,product_id,product_num);
            try{
                mqSender.messageSender(MQMessage.builder().user_id(user_id).product_id(product_id).build(),"miaosha.order" );
                code = "200";
                msg = "秒杀成功，正在排队等待订单创建,请耐心等待";
            } catch (Exception e){
                log.info(e.toString());
                code = "203";
                msg = "系统出错,请稍后重试";
                product_num = redisUtil.getMapValue(product_list, product_id);
                product_num++;
                redisUtil.hashSet(product_list,product_id,product_num);//把预扣的库存加上去
            }
            return MyResponse.builder()
                    .res_code(code)
                    .res_msg(msg)
                    .res_object(null).build();

        }
        else return MyResponse.builder()
                    .res_code("201")
                    .res_msg("秒杀失败，库存不足")
                    .res_object(null).build();
        

    }
    @RequestMapping("/test")
    @ResponseBody
    public void test(){
        log.info("==============测试成功=========");

    }
}
