package com.xl.service.impl;

import com.xl.dao.SeckillDao;
import com.xl.dao.SuccessKilledDao;
import com.xl.dto.Exposer;
import com.xl.dto.SeckillExecution;
import com.xl.entity.Seckill;
import com.xl.entity.SuccessKilled;
import com.xl.enums.SeckillStatEnum;
import com.xl.exception.RepeatKillException;
import com.xl.exception.SeckillCloseException;
import com.xl.exception.SeckillException;
import com.xl.service.SeckillService;
import com.xl.util.Md5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Description: 秒杀接口实现类
 * @Auther: X-Dragon
 * @Date: 2018/11/29 16:23
 * @Version: 1.0
 */

// @Component可以实现 @Responsity @Dao @Controller
@Service
public class SeckillServiceImpl implements SeckillService {

    static  final Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

    @Autowired // @Resource也可以实现
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Override
    public List<Seckill> listAllSeckill() {
        List<Seckill> seckills = seckillDao.listAllSeckill(0, 4);
        return seckills;
    }

    @Override
    public Seckill getById(long seckillId) {
        Seckill seckill = seckillDao.getSeckillById(seckillId);
        return seckill;
    }

    /*
     * @Auther: XDragon
     * @Description: 暴露秒杀接口
     * @Date: 2018/11/30 12:44 
     * @Param [seckillId]
     * @Return com.xl.dto.Exposer
     * @Exception 
     */
    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        // 根据Id获取秒杀信息
        Seckill seckill = seckillDao.getSeckillById(seckillId);
        // 判断是否为空
        if (null == seckill) { // 如果没找到
            return new Exposer(false, seckillId);
        }
        // 判断当前时间 和开始时间 结束时间
        Date now = new Date();
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        if (now.before(startTime) || now.after(endTime)) {// 如果现在还没开始
            return new Exposer(false, seckillId, now.getTime(), startTime.getTime(), endTime.getTime());
        } else { // 查询到秒杀信息并在秒杀时间段内
            String stringId = Long.toString(seckill.getseckillId());
            String md5 = Md5Utils.encrypt(stringId);
            return new Exposer(true, md5, seckillId);
        }
    }

    /*
     * @Auther: XDragon
     * @Description: 执行秒杀过程
     * @Date: 2018/11/30 12:44 
     * @Param [seckillId, userPhone, md5]
     * @Return com.xl.dto.SeckillExecution
     * @Exception 
     */
    @Override
    @Transactional
    /**
     * 使用注解控制事务的优点:
     * 1.开发团队达成一致约定,明确标注事务方法的编程风格.
     * 2.保证事务方法的执行时间尽可能短,不要穿插其他网络操作RPC/HTTP请求或者剥离到事务方法外部.
     * 3.不是所有的方法都需要事务.如一些查询的service.只有一条修改操作的service.
     */
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws RepeatKillException, SeckillCloseException, SeckillException{

        try {
            if (null == md5 || !md5.equals(Md5Utils.encrypt(Long.toString(seckillId)))) {
                throw new SeckillException(SeckillStatEnum.INNER_ERROR.getStateInfo());
            }
            Date now = new Date();
            // 先执行减库存操作
            int reduceNumber = seckillDao.reduceNumber(seckillId, now);
            if(reduceNumber<=0){
                throw new SeckillCloseException(SeckillStatEnum.TIME_ERROR.getStateInfo());
            }
            // 判断减库存结果
            int insertSuccessKilled = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertSuccessKilled<=0){//重复秒杀
                throw new RepeatKillException(SeckillStatEnum.REPEAT_KILL.getStateInfo());
            }else{ //插入数据
                SuccessKilled successKilled = successKilledDao.getSeckillByIdUserphone(seckillId, userPhone);
                SeckillExecution seckillException = new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS.getState(), SeckillStatEnum.SUCCESS.getStateInfo(), successKilled);
                return seckillException;
            }
        }catch(SeckillCloseException e){
            throw e;
        }catch(RepeatKillException e){
            throw e;
        } catch (SeckillException e) {
            logger.error("秒杀系统内部错误", e.getMessage());
            throw  new SeckillException("seckill inner error: " + e.getMessage());
        }
    }


}
