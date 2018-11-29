package com.xl.dao;

import com.xl.entity.Seckill;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Auther: XDragon
 * @Date: 2018/11/28
 * @Description: 秒杀接口
 * @Version: 1.0
 */
public interface SeckillDao {

    /*
     * @author X-Dragon
     * @description:  减库存
     * @date 2018/11/17 22:05
     * @param [seckillId, killTime]
     * @return int 如果行数>1,表示更新的记录行数
     */
    int reduceNumber(@Param(value = "seckillId") long seckillId, @Param(value = "killTime") Date killTime);

    /*
     * @author X-Dragon
     * @description:  根据秒杀ID查询对象
     * @date 2018/11/17 22:07
     * @param [seckillId] 一个参数可以不加@Param
     * @return org.seckill.entity.Seckill
     */
//    Seckill queryById(long seckillId);
    Seckill getSeckillById(@Param(value = "seckillId") long seckillId);

    /*
     * @Auther: XDragon
     * @Description:  根据偏移量查询秒杀集合信息
     * @Date: 2018/11/28 23:48
     * @Param [offset, limit]
     * @Return java.util.List<com.xl.entity.Seckill>
     * @Exception
     */
    List<Seckill> listAllSeckill(@Param(value = "offset") int offset, @Param(value = "limit") int limit);
}
