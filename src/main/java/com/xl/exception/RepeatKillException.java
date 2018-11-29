package com.xl.exception;

/**
 * @Description: 重复秒杀异常
 * @Auther: X-Dragon
 * @Date: 2018/11/29 16:04
 * @Version: 1.0
 */
public class RepeatKillException extends SeckillException {
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
