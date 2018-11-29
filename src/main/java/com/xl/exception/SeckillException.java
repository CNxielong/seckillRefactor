package com.xl.exception;

/**
 * @Description: 公共的秒杀异常
 * @Auther: X-Dragon
 * @Date: 2018/11/29 16:02
 * @Version: 1.0
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
