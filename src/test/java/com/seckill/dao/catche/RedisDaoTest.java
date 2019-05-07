package com.seckill.dao.catche;

import com.seckill.dao.SeckillDao;
import com.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
private long id=1000L;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SeckillDao seckillDao;
    @Test
    public void testRedisDao(){
        //测试get put方法
        Seckill seckill = redisDao.getSeckill(id);
        System.out.println("第一次"+seckill);
        if(seckill==null){
                 seckill=seckillDao.queryById(id);
                 if(seckill!=null){
                     String result = redisDao.putSeckill(seckill);
                     Seckill seckill2 = redisDao.getSeckill(id);
                     System.out.println("第二次"+seckill2);
                 }
        }

    }
}