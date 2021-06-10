package com.jingege.pojo;


/**
 * 商品库存信息
 */
public class Goods {

    private Long goods_id;
    private String goods_name;
    private Integer goods_stock;

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public String getGood_name() {
        return goods_name;
    }

    public void setGood_name(String good_name) {
        this.goods_name = good_name;
    }

    public Integer getGoods_stock() {
        return goods_stock;
    }

    public void setGoods_stock(Integer goods_stock) {
        this.goods_stock = goods_stock;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goods_id=" + goods_id +
                ", goods_name='" + goods_name + '\'' +
                ", goods_stock=" + goods_stock +
                '}';
    }
}
