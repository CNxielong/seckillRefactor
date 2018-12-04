package com.xl.dto;

/**
 * @Description: 封装秒杀VO信息 封装JSON信息
 * @Auther: X-Dragon
 * @Date: 2018/12/4 0:13
 * @Version: 1.0
 */
public class SeckillResult<T> {

    // 确定是否成功
    private boolean success;

    // 封装返回的数据信息
    private T data;

    // 封装信息
    private String msg;

    // 未用到
    public SeckillResult(boolean success, T data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    // 成功的情况
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    // 失败的情况
    public SeckillResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SeckillResult{" +
                "success=" + success +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
