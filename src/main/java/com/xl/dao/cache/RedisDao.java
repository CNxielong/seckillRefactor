package com.xl.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.xl.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Description: 集成Redis高并发优化
 * @Auther: X-Dragon
 * @Date: 2018/12/15 0:39
 * @Version: 1.0
 */
public class RedisDao {

    // Logger日志
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDao.class);

    // jedis连接池
    private JedisPool jedisPool;

    // 序列化工具
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    // 构造方法
    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    // get
    public Seckill getSeckill(long seckillId) {

        try {
            Jedis jedis = jedisPool.getResource();
            String key = "seckill:" + seckillId;
            // 并没有实现内部序列化操作
            // get:byte[]->反序列化->Object(Seckill)
            // 采用自定义序列化
            // protostuff:pojo
            try {
                byte[] bytes = jedis.get(key.getBytes());
                // 缓存中获取到
                if (null != bytes) {
                    // 空对象
                    Seckill seckill = schema.newMessage();
                    ProtobufIOUtil.mergeFrom(bytes, seckill, schema);
                    // seckill被反序列化
                    return seckill;
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage(), e);
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }
        return null;
    }

    // set
    public String setSeckill(Seckill seckill) {
        // set:Object(Seckill)->序列化->byte[]->发送给redis

        try {
            Jedis jedis = jedisPool.getResource();
            String key = "seckill:" + seckill.getseckillId();
            try {
                byte[] bytes = ProtobufIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                // 超时缓存
                int timeOut = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeOut, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
        }

        return null;
    }

}
