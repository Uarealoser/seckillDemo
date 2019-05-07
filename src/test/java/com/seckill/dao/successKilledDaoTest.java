package com.seckill.dao;

import com.seckill.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class successKilledDaoTest {
    @Autowired
    private successKilledDao successKilledDao;
    @Test
    public void testinsertSuccessKilled(){
        System.out.println(successKilledDao);
       long id=1000L;
       long phone=12345667L;
        int i = successKilledDao.insertSuccessKilled(id,phone);
        System.out.println(i);
    }
    @Test
    public void testqueryByIdWithSeckill(){
        long id=1000L;
        long phone=12345667L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}