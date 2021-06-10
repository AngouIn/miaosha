package com.jingege.service.impl;

import com.jingege.mapper.ISeckillMapper;
import com.jingege.pojo.Goods;
import com.jingege.pojo.Order;
import com.jingege.service.ISeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeckillServiceImpl implements ISeckillService {

    private static Logger log = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Autowired
    private ISeckillMapper seckillMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<Goods> queryAllGoodsStock() {
        return this.seckillMapper.queryAllGoodsStock();
    }

    @Override
    public Goods queryGoodsStockById(Long goods_id) {
        return this.seckillMapper.queryGoodsStockById(goods_id);
    }

    @Override
    public Integer updateGoodsStock(Long goods_id) {
        return this.seckillMapper.updateGoodsStock(goods_id);
    }

    @Override
    public Integer insertOrder(Order order) {
        return this.seckillMapper.insertOrder(order);
    }


    @Override
    @Transactional
    public void placeOrder(Order order) {
        //减少一个库存
        Integer tag = this.updateGoodsStock(order.getGoods_id());
        if(tag<=0){
            //更新失败   说明没库存了
            return;
        }

        //生成订单
        this.insertOrder(order);
    }


    public void sendMQ(Order order){
        try {
            this.amqpTemplate.convertAndSend("miaosha",order);
        } catch (AmqpException e) {
            log.error("添加消息到队列失败！");
            e.printStackTrace();
        }
    }

}
