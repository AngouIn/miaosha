package com.jingege.mapper;

import com.jingege.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ISeckillMapper {
    @Select("select goods_id,goods_stock from goods")
    List<Goods> queryAllGoodStock();
}
