package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/qq_36510261/article/details/78962081#
 *
 *
 */
@Component
public class RedisLock2 {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     *  获取锁
     * @param lockKey
     * @param requestId
     * @param expireTime 失效时间单位为秒
     * @return
     */
    public boolean setNx(String lockKey, String requestId, int expireTime) {
        boolean success = stringRedisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.set(lockKey.getBytes(), requestId.getBytes(), Expiration.from(expireTime, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT));
        return success;
    }
    public String setNx(String key,int expireTime){
        String temp = stringRedisTemplate.execute(new RedisCallback<String>() {
            @Nullable
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
/*                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.set(key, "锁定的资源", "NX", "PX", expire);*/
                return null;
            }
        });
        System.out.println();
        return temp;
    }

    public boolean releaseDistributedLock(String lockKey, String requestId) {
        String scriptStr = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> script = new DefaultRedisScript(scriptStr, Long.class);
        List<String> keys = new ArrayList<>();
        keys.add(lockKey);
        Long result = stringRedisTemplate.execute(script, new StringRedisSerializer(), new RedisSerializer<Long>() {
            private final Charset charset = Charset.forName("UTF8");

            @Override
            public byte[] serialize(Long aLong) throws SerializationException {
                return (aLong == null ? null : (aLong.toString()).getBytes(charset));
            }

            @Override
            public Long deserialize(byte[] bytes) throws SerializationException {
                return (bytes == null ? null : Long.parseLong(new String(bytes, charset)));
            }
        }, keys, requestId);
        if ("1".equals(result+"")) {
            return true;
        }
        return false;
    }


}
