package com.xl.service.impl;

import com.mchange.v2.util.ResourceClosedException;
import com.xl.dto.Exposer;
import com.xl.dto.SeckillExecution;
import com.xl.entity.Seckill;
import com.xl.enums.SeckillStatEnum;
import com.xl.exception.RepeatKillException;
import com.xl.exception.SeckillCloseException;
import com.xl.exception.SeckillException;
import com.xl.service.SeckillService;
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
//告诉junitSpring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
//@ContextConfiguration({"classpath:spring/*"}) // 也行
public class SeckillServiceImplTest {

    //Logger日志
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SeckillService seckillService;

    @Test
    public void listAllSeckill() {
        List<Seckill> seckills = seckillService.listAllSeckill();
//        for (Seckill seckill: seckills) {
//            System.out.println(seckill);
//        }
        logger.info("list={}", seckills);
    }

    @Test
    public void getById() {
        Seckill seckill = seckillService.getById(1000L);
//        System.out.println(seckill);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() {
        long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("暴露的秒杀信息#{}", exposer);// #Exposer{exposed=true, md5='E2FD9012A6CEB49620304A4C0B4086A9', seckillId=1000, now=0, start=0, end=0}
    }

    @Test
    public void executeSeckill() {
        long seckillId = 1000L;
        long userPhone = 17712341234L;
        String md5 = "E2FD9012A6CEB49620304A4C0B4086A9";
        SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
        logger.info("执行结果#{}", seckillExecution);
    }

    @Test
    public void testSeckillLogic() {
        long seckillId = 1000L;
        Seckill seckill = seckillService.getById(seckillId);
        if (null == seckill) {
            logger.info("没有秒杀信息");
        } else {//找到秒杀信息
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            long id = exposer.getSeckillId();
            long phone = 17744494563L;
            String userPhone = exposer.getMd5();
            if (!exposer.isExposed()) {
                logger.info("秒杀未开启或者已关闭");
            } else {//可以执行秒杀
                try {
                    SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, userPhone);
                    logger.info("暴露的秒杀信息#{}", seckillExecution);
                } catch (SeckillCloseException e) {
//                    logger.info(SeckillStatEnum.TIME_ERROR.getStateInfo(), e);
                    logger.info("暴露的秒杀信息#{}", e.getMessage());
                } catch (RepeatKillException e) {
//                    logger.info(SeckillStatEnum.REPEAT_KILL.getStateInfo(), e);
                    logger.info("暴露的秒杀信息#{}", e.getMessage());
                } catch (SeckillException e) {
//                    logger.info(SeckillStatEnum.INNER_ERROR.getStateInfo(), e);
                    logger.info("暴露的秒杀信息#{}", e.getMessage());
                }

            }
        }
    }
}