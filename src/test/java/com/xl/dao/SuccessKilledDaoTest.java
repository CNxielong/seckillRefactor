package com.xl.dao;

import com.xl.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private  SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        int insertSuccessKilled = successKilledDao.insertSuccessKilled(1001L, 17744494563L);
        System.out.println("insertSuccessKilled："+insertSuccessKilled);
    }

    @Test
    public void queryByIdSeckill() {
        SuccessKilled successKilled = successKilledDao.getSeckillByIdUserphone(1001L, 17744494563L);
        System.out.println("successKilled: "+successKilled);
    }
}