package com.jingege.mq;

import com.jingege.pojo.Goods;
import com.jingege.pojo.Order;
import com.jingege.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiverListener {

    @Autowired
    private ISeckillService seckillService;
    private static Logger log = LoggerFactory.getLogger(MQReceiverListener.class);


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "miaosha",durable = "true"),
            exchange = @Exchange(
                    value = "miaosha.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key ={"miaosha"}))
    public void receive(Order order){
        //查询库存  确保有库存
        Goods goods = seckillService.queryGoodsStockById(order.getGoods_id());
        if(goods==null){
            log.error("处理消息时，查询库存失败！");
            throw new RuntimeException("查询库存失败！");
        }

        if(goods.getGoods_stock()<=0){
            //测试是否
            log.info("ID为 {} 的用户，在秒杀商品时库存没有剩余，结束此次秒杀！(此为漏网之鱼，Redis没拦截到)",order.getUser_id());
            return;
        }

        //又库存， 开始秒杀
        seckillService.placeOrder(order);
        log.info("ID为{}的用户：成功抢到{}，库存剩余：{}",order.getUser_id(),goods.getGood_name(),goods.getGoods_stock()-1);
    }
}
