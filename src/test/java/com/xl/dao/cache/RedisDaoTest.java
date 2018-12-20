package com.xl.dao.cache;

import com.xl.dao.SeckillDao;
import com.xl.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junitSpring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    Long seckillId = 1000L;
    @Test
    public void getSeckill() {
        Seckill seckill = redisDao.getSeckill(seckillId);
        System.out.println(seckill);
    }

    @Test
    public void setSeckill() {
        Seckill seckill = seckillDao.getSeckillById(seckillId);
        String s = redisDao.setSeckill(seckill);
        System.out.println("set结果:"+s);
    }
}