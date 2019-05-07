package com.seckill.service;

import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExcution;
import com.seckill.entity.Seckill;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @Test
    public void getSeckillListTest()throws Exception{
        List<Seckill> list= seckillService.getSeckillList();
        logger.info("list={}",list);
    }
    @Test
    public void getByIdTest()throws Exception{
        long id=1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}",seckill);
    }
    @Test
    public void exportSeckillUrlTest()throws Exception{
        long id=1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}",exposer);
        //exposer=Exposer{exposed=true, md5='ef9d21ab7ff6f0cd7ff3e9abc69e5751', seckillId=1000, now=0, start=0, end=0}
    }
    @Test
    public void executeSeckillTest()throws Exception{
        long id=1000L;
        long phone=12314553L;
        String md5="ef9d21ab7ff6f0cd7ff3e9abc69e5751";
        try{

            SeckillExcution seckillExcution = seckillService.executeSeckill(id, phone, md5);
            logger.info("seckillExcution={}",seckillExcution);
        }catch (SeckillCloseException e1){
            logger.error(e1.getMessage());
        }catch (RepeatKillException e2){
            logger.error(e2.getMessage());
        }catch (Exception e ){
            logger.error(e.getMessage(),e);
            //所有编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }


    }

}