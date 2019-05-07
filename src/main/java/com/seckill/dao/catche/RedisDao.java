package com.seckill.dao.catche;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class RedisDao {
    private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;
    private RuntimeSchema<Seckill> schema=RuntimeSchema.createFrom(Seckill.class);
    public RedisDao(String ip,int port){
        jedisPool=new JedisPool(ip,port);
    }
    //通过redis拿到seckill
    public Seckill getSeckill(long seckillId){
        //redis操作
        try{
            Jedis jedis=jedisPool.getResource();
            try{
                String key="seckill:"+seckillId;
                //并没有实现内部序列化，get拿到byte[] 反序列化 得到Seckill对象
                //采用自定义序列化方式
                //protostuff :pojo
                byte[] bytes = jedis.get(key.getBytes());
                if(bytes!=null){
                    //空对象
                    Seckill seckill=schema.newMessage();
                    //从缓存中获取到了对象
                    ProtostuffIOUtil.mergeFrom(bytes,seckill,schema);
                    //seckill被反序列
                    return seckill;
                }
            }finally {
                if(jedis!=null){
                    jedis.close();
                }
            }

        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return  null;
    }
    public String putSeckill(Seckill seckill){
        //set Object(Seckill)->byte[]->发送给redis 序列化的过程
        try{
            Jedis jedis=jedisPool.getResource();
            try{
                String key="seckill:"+seckill.getSeckillId();
                //第三个参数为缓存器，对象特别大时用于缓存
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout=60*60;// one hour
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }


}
