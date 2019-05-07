package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExcution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在使用者角度去设计
 *
 */
public interface SeckillService {
/**
 * 查询所有秒杀记录
 */
        List<Seckill> getSeckillList();

    /**
     * 查询单个记录
     * @param seckilled
     * @return
     */
    Seckill getById(long seckilled);

    /**
     * 秒杀开启时，输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * 秒杀还没有开始的时候，不暴露秒杀接口地址
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExcution executeSeckill(long seckillId, long userPhone, String md5)
    throws SeckillException, RepeatKillException, SeckillCloseException;
}
