package cn.itrip.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisAPI {
    public JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    /**
     * 依据key 获取
     * @param key
     * @return
     */
    public String get(String key){
        Jedis jedis=jedisPool.getResource();//获取实例
        String value=jedis.get(key);
        jedisPool.returnResource(jedis);//释放掉资源
        return value;
}
    //set

    public String set(String key,String value){
        Jedis jedis=jedisPool.getResource();
        String result=jedis.set(key,value);
        jedisPool.returnResource(jedis);
        return result;
    }

    public String set(String key,int  seconds,String value){
        Jedis jedis=jedisPool.getResource();
        String result=jedis.setex(key,seconds,value);
        jedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 查询是否存在
     * @param key
     * @return
     */
    public boolean exists(String key){
        Jedis jedis=jedisPool.getResource();
        boolean result=jedis.exists(key);
        jedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 查询剩余时间
     * @param key
     * @return
     */
    public Long ttl(String key){
        Jedis jedis=jedisPool.getResource();
        Long result=jedis.ttl(key);
        jedisPool.returnResource(jedis);
        return result;
    }

    /**
     * 依据key删除
     * @param key
     * @return
     */
    public Long del(String key){
        Jedis jedis=jedisPool.getResource();
        Long result=jedis.del(key);
        jedisPool.returnResource(jedis);
        return result;
    }
}
