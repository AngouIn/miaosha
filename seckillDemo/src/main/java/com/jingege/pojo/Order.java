package com.jingege.pojo;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单信息
 */
public class Order implements Serializable {

    @NotNull(message = "参数有误！")
    private Long user_id;
    @NotNull(message = "参数有误！")
    private Long goods_id;
    @Max(value = 1,message = "参数有误！")
    private Integer num;   //每个订单购买商品的数量  这里限制了最大为1
    private Date create_time;
    private Integer pay_status;  //支付状态 0-超时未支付  1-已支付  2-待支付

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getPay_status() {
        return pay_status;
    }

    public void setPay_status(Integer pay_status) {
        this.pay_status = pay_status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "user_id=" + user_id +
                ", goods_id=" + goods_id +
                ", num=" + num +
                ", create_time=" + create_time +
                ", pay_status=" + pay_status +
                '}';
    }
}
