package com.xl.dto;

import com.xl.entity.SuccessKilled;

/**
 * （Data Transfer Object） ：数据传输对象， Service 或 Manager 向外传输的对象
 * @Description: 封装秒杀的结果对象
 * @Auther: X-Dragon
 * @Date: 2018/11/29 15:46
 * @Version: 1.0
 */
public class SeckillExecution {

    //秒杀ID
    private long seckillId;

    //秒杀执行结果状态
    private int state;

    //状态信息
    private String stateInfo;

    //秒杀成功结果返回信息
    private SuccessKilled successKilled;

    //成功状态
    public SeckillExecution(long seckillId, int state, String stateInfo, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.successKilled = successKilled;
    }

    //失败状态
    public SeckillExecution(long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

}
