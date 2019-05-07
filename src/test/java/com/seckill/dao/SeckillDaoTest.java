package com.seckill.dao;

import com.seckill.entity.Seckill;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
/**
 * 配置spring 和junit整合，spring启动时加载springIOC容器
 * @RunWith(SpringJUnit4ClassRunner.class):自动加载springIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
        //注入dao依赖
            @Autowired
        private SeckillDao seckillDao;
        @Test
    public   void testQueryById() throws Exception{
            long id = 1000;
            Seckill seckill = seckillDao.queryById(id);
             System.out.println(seckill);
        }
    @Test
    public   void testQueryAll() throws Exception{
        List<Seckill> seckillList=seckillDao.queryAll(0,10);
        for (Seckill s:seckillList
             ) {
            System.out.println(s);
        }
    }
    @Test
    public   void testReduceNumber() throws Exception{
        Date date = new Date();
        int i = seckillDao.reduceNumber(1000L, date);
        System.out.println(i);
    }
}