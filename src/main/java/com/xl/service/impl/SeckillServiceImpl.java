package com.xl.service.impl;

import com.xl.dao.SeckillDao;
import com.xl.dao.SuccessKilledDao;
import com.xl.dto.Exposer;
import com.xl.dto.SeckillExecution;
import com.xl.entity.Seckill;
import com.xl.entity.SuccessKilled;
import com.xl.exception.RepeatKillException;
import com.xl.exception.SeckillCloseException;
import com.xl.exception.SeckillException;
import com.xl.service.SeckillService;
import com.xl.util.Md5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description: 秒杀接口实现类
 * @Auther: X-Dragon
 * @Date: 2018/11/29 16:23
 * @Version: 1.0
 */

@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
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

    @Override
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {

        try {
            if (null == md5 || !md5.equals(Md5Utils.encrypt(Long.toString(seckillId)))) {
                throw new SeckillException("秒杀异常");
            }
            Date now = new Date();
            // 先执行减库存操作
            int reduceNumber = seckillDao.reduceNumber(seckillId, now);
            if(reduceNumber<=0){
                throw new SeckillCloseException("秒杀未开始或者结束");
            }
            // 判断减库存结果
            int insertSuccessKilled = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertSuccessKilled<=0){//重复秒杀
                throw new RepeatKillException("重复秒杀");
            }else{ //插入数据
                SuccessKilled successKilled = successKilledDao.getSeckillByIdUserphone(seckillId, userPhone);
                SeckillExecution seckillException = new SeckillExecution(seckillId, 1, "插入成功", successKilled);
                return seckillException;
            }
        }catch(SeckillCloseException e){
            e.printStackTrace();
        }catch(RepeatKillException e){
            e.printStackTrace();
        } catch (SeckillException e) {
            e.printStackTrace();
        }
        return null;
    }


}
