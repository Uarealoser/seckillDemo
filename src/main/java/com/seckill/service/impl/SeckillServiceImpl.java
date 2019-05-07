package com.seckill.service.impl;

import com.seckill.dao.SeckillDao;
import com.seckill.dao.catche.RedisDao;
import com.seckill.dao.successKilledDao;
import com.seckill.dto.Exposer;
import com.seckill.dto.SeckillExcution;
import com.seckill.entity.Seckill;
import com.seckill.entity.SuccessKilled;
import com.seckill.enums.seckillStateEnum;
import com.seckill.exception.RepeatKillException;
import com.seckill.exception.SeckillCloseException;
import com.seckill.exception.SeckillException;
import com.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @Conponent @Service @Dao @Controller
 *
 */
@Service
@Transactional
public class SeckillServiceImpl implements SeckillService {
    //日志
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    //注入service依赖
    @Autowired
    private SeckillDao seckillDao;
    @Autowired
    private successKilledDao successKilledDao;
    //用于混淆md5
     private final String slat="afadgdsaf21312@#￥@#asgawer";
     //注入redisDao
    @Autowired
    private RedisDao redisDao;


    /**
     * 查询所有秒杀记录
     */
    @Override
    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    /**
     * 查询单个记录
     *
     * @param seckilled
     * @return
     */
    @Override
    public Seckill getById(long seckilled) {
        return seckillDao.queryById(seckilled);
    }

    /**
     * 秒杀开启时，输出秒杀接口地址，
     * 否则输出系统时间和秒杀时间
     * 秒杀还没有开始的时候，不暴露秒杀接口地址
     *
     * @param seckillId
     */
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        //缓存优化 :建立在超时的基础上
        //1.访问redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if(seckill==null){
            //2.访问数据库
            seckill = seckillDao.queryById(seckillId);
            if(seckill==null){
                return new Exposer(false,seckillId);
            }else {
                //3.放入redis
                redisDao.putSeckill(seckill);
            }
        }
        Date startTime=seckill.getStartTime();
        Date endTime=seckill.getEndTime();
        //系统当前时间
        Date nowTime=new Date();
        if(nowTime.getTime()<startTime.getTime()|| nowTime.getTime()>endTime.getTime()){
            return new Exposer(false,seckillId,nowTime.getTime(),startTime.getTime(),endTime.getTime());
        }
        //转化特定字符串的过程，不可逆
         String md5=getMD5(seckillId);
        return new Exposer(true,md5,seckillId);
    }

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    @Override
    public SeckillExcution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if(md5==null||!md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data dewrite");
        }
        //执行秒杀操作：减库存+记录秒杀信息 先插入购买行为再减库存，减少行级锁持续时间
        Date nowTime = new Date();
        try{
            //记录购买行为
            int insertCount=successKilledDao.insertSuccessKilled(seckillId,userPhone);
            if(insertCount<=0){
                //重复秒杀
                throw new RepeatKillException("seckill repeated");
            }else {
                int updateCount=seckillDao.reduceNumber(seckillId,nowTime);
                if(updateCount<=0){
                    //没有更新到操作 rollback
                    throw new SeckillCloseException("seckill is closed");
                }else {
                    //秒杀成功，commit
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExcution(seckillId, seckillStateEnum.SUCCESS,successKilled);
                }
            }
        }catch (SeckillCloseException e1){
            throw e1;
        }catch (RepeatKillException e2){
            throw e2;
        }catch (Exception e ){
            logger.error(e.getMessage(),e);
            //所有编译期异常转化为运行期异常
            throw new SeckillException("seckill inner error:"+e.getMessage());
        }
    }

    private String getMD5(long seckillId){
        String base=seckillId+"/"+slat;
        //通过工具类
        String md5= DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
