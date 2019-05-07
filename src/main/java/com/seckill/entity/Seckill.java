package com.seckill.entity;

import java.util.Date;
/*秒杀商品表对应的实体类*/
public class Seckill {
    private long seckillId;
    private String seckillName;
    private int seckillNumber;
    private Date startTime;
    private Date endTime;
    private Date createTime;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public String getSeckillName() {
        return seckillName;
    }

    public void setSeckillName(String seckillName) {
        this.seckillName = seckillName;
    }

    public int getSeckillNumber() {
        return seckillNumber;
    }

    public void setSeckillNumber(int seckillNumber) {
        this.seckillNumber = seckillNumber;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "seckillId=" + seckillId +
                ", seckillName='" + seckillName + '\'' +
                ", seckillNumber=" + seckillNumber +
                ", start_time=" + startTime +
                ", end_time=" + endTime +
                ", create_time=" + createTime +
                '}';
    }
}
