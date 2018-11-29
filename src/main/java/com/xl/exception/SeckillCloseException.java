package com.xl.exception;

/**
 * @Description: 秒杀关闭异常
 * @Auther: X-Dragon
 * @Date: 2018/11/29 16:04
 * @Version: 1.0
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
