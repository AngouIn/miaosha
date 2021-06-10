package com.jingege.mapper;

import com.jingege.pojo.Goods;
import com.jingege.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ISeckillMapper {

    /**
     * 查询所有商品的库存
     * @return
     */
    @Select("select goods_id,goods_name,goods_stock from goods")
    List<Goods> queryAllGoodsStock();


    /**
     * 根据商品id查询商品库存信息
     * @param goods_id
     * @return
     */
    @Select("select goods_id,goods_name,goods_stock from goods where goods_id = #{goods_id}")
    Goods queryGoodsStockById(@Param("goods_id") Long goods_id);


    /**
     * 根据id修改库存
     * 这里直接定死了每次请求（订单）只能购买一件商品  -1
     * @param goods_id
     * @return
     */
    @Update("update goods set goods_stock = (goods_stock-1) where goods_stock > 0 and goods_id = #{goods_id}")
    Integer updateGoodsStock(@Param("goods_id") Long goods_id);


    /**
     * 插入订单
     * @param order
     * @return
     */
    @Insert("insert into order_record(user_id,goods_id,num,create_time) values (#{user_id},#{goods_id},#{num},now())")
    Integer insertOrder(Order order);

}
