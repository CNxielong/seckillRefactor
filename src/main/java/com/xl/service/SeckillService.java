package com.xl.service;

import com.xl.dto.Exposer;
import com.xl.dto.SeckillExecution;
import com.xl.entity.Seckill;
import com.xl.exception.RepeatKillException;
import com.xl.exception.SeckillCloseException;
import com.xl.exception.SeckillException;

import java.util.List;

/**
 * @Description: 秒杀功能业务接口
 * 设计原则:站在"使用者"的角度设计接口
 * 三个方面:方法定义粒度，参数，返回类型(Return 类型 异常)
 * @Auther: X-Dragon
 * @Date: 2018/11/29 14:35
 * @Version: 1.0
 */
public interface SeckillService {

    /*
     * @Auther: XDragon
     * @Description: 查询所有可以秒杀的商品信息
     * @Date: 2018/11/29 14:52
     * @Param []
     * @Return java.util.List<com.xl.entity.Seckill>
     * @Exception 
     */
    List<Seckill> listAllSeckill();

    /**
     * @Description: 根据ID查询
     * @Auther: XDragon
     * @Date: 2018/11/29 15:10
     * @Description:
     * @Version: 1.0
     */
    Seckill getById(long seckillId);

    /*
     * @Auther: XDragon
     * @Description: 秒杀开启时输入秒杀接口地址,
     * 否则输出系统时间和秒杀时间
     * @Date: 2018/11/29 15:18
     * @Param [seckillId]
     * @Return void
     * @Exception
     */
    Exposer exportSeckillUrl(long seckillId);

    /*
     * @Auther: XDragon
     * @Description: 执行秒杀操作
     * @Date: 2018/11/29 15:53
     * @Param [seckillId, userPhone, md5]
     * @Return com.xl.dto.SeckillExecution
     * @Exception
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;

    /*
     * @Auther: XDragon
     * @Description:执行秒杀操作存储过程优化
     * @Date: 2018/12/20 22:43
     * @Param [seckillId, userPhone, md5]
     * @Return com.xl.dto.SeckillExecution
     * @Exception
     */
    SeckillExecution executeSeckillByProducer(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;
}
