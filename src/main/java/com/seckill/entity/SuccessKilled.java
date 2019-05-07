package com.seckill.entity;

import java.util.Date;

public class SuccessKilled {
    private long seckillId;
    private long userPhone;
    private short state;
    private Date createTime;


    //多对一
    private Seckill seckill;

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    public long getSeckill_id() {
        return seckillId;
    }

    public void setSeckill_id(long seckill_id) {
        this.seckillId = seckill_id;
    }

    public long getUser_phone() {
        return userPhone;
    }

    public void setUser_phone(long user_phone) {
        this.userPhone = user_phone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreate_time() {
        return createTime;
    }

    public void setCreate_time(Date create_time) {
        this.createTime = create_time;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckill_id=" + seckillId +
                ", user_phone=" + userPhone +
                ", state=" + state +
                ", create_time=" + createTime +
                '}';
    }
}
