package com.jingege.controller;

import com.jingege.pojo.Goods;
import com.jingege.pojo.Order;
import com.jingege.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    private ISeckillService seckillService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static Logger log = LoggerFactory.getLogger(SeckillController.class);

    //redis中商品库存key的前缀
    private static final String  GOODS_PREFIX = "GOODS_PREFIX:STOCK:";



    @Override
    public void afterPropertiesSet() throws Exception {
        List<Goods> goodsList = seckillService.queryAllGoodsStock();
        if(goodsList.size()<=0){
            throw new RuntimeException("初始化库存失败！");
        }
        for (Goods goods : goodsList) {
            ValueOperations<String, String> ops = redisTemplate.opsForValue();
            ops.set(GOODS_PREFIX+goods.getGoods_id(),goods.getGoods_stock().toString());
        }
    }


    @RequestMapping("go")
    @ResponseBody
    public ResponseEntity<String> miaosha(@Valid Order order){

        if(isOver(order.getGoods_id())){
            log.info("ID为 {} 的用户，在秒杀商品时库存没有剩余，结束此次秒杀！",order.getUser_id());
            return ResponseEntity.ok("您来慢了,秒杀结束！");
        }
        Long stock = redisIncrement(order);
        //System.out.println("库存："+order.getGoods_id()+": "+stock);
        seckillService.sendMQ(order);  //发送秒杀消息到队列
        return ResponseEntity.ok("正在秒杀中......");
    }




    private synchronized boolean isOver(Long goods_id){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        int stock =Integer.parseInt(Objects.requireNonNull(ops.get(GOODS_PREFIX+goods_id)));
        return stock<=0;
    }

    /**
     * redis库存-1
     * @param order
     * @return
     */
    private synchronized Long redisIncrement(Order order){
        //return redisTemplate.opsForValue().increment(GOODS_PREFIX+order.getGoods_id(), -order.getNum());
        return redisTemplate.opsForValue().increment(GOODS_PREFIX+order.getGoods_id(), -1);
    }


}
