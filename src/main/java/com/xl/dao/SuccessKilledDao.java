package com.xl.dao;

import com.xl.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 秒杀成功Dao
 * @Auther: X-Dragon
 * @Date: 2018/11/28 16:12
 * @Version: 1.0
 */
public interface SuccessKilledDao {

    /*
     * @description: TODO 插入购买明细
     * @date 2018/11/17 22:17
     * @param [seckillId, userPhone]
     * @return int 插入行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone")long userPhone);

    /*
     * @description: TODO 根据ID查询SuccessKilled并携带秒杀产品对象实体
     * @date 2018/11/17 22:20
     * @param [seckillId]
     * @return org.seckill.entity.SuccessKilled
     */
    SuccessKilled getSeckillByIdUserphone(@Param("seckillId") long seckillId, @Param("userPhone")long userPhone);
}
