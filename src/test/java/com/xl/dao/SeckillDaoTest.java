package com.xl.dao;

import com.xl.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Description: com.xl.dao.SeckillDao测试类
 * @Auther: XDragon
 * @Date: 2018/11/29 1:09
 * @Description:
 * @Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junitSpring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Autowired
    private SeckillDao seckillDao;

    long seckillId = 1000L;

    @Test
    public void reduceNumber() {
        int reduceNumber = seckillDao.reduceNumber(seckillId, new Date());
        System.out.println("减少了："+reduceNumber);
    }

    @Test
    public void queryById() {
        Seckill seckill = seckillDao.getSeckillById(seckillId);
        System.out.println("查询到的结果是:"+seckill);
    }

    @Test
    public void queryAll() {
        List<Seckill> seckills = seckillDao.listAllSeckill(0, 6);//查出来只有四个 数据库一共四个
        for(Seckill seckill : seckills){
            System.out.println("seckill: "+seckill);
        }
    }
}