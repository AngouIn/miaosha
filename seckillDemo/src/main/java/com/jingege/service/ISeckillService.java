package com.jingege.service;

import com.jingege.pojo.Goods;
import com.jingege.pojo.Order;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ISeckillService {

    List<Goods> queryAllGoodsStock();

    Goods queryGoodsStockById(Long goods_id);

    Integer updateGoodsStock(Long goods_id);

    Integer insertOrder(Order order);

    /**
     * 下单
     * @param order
     */
    @Transactional
    void placeOrder(Order order);

    void sendMQ(Order order);
}
