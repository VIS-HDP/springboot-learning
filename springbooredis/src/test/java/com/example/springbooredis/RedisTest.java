package com.example.springbooredis;

import com.example.utils.RedisUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    StringRedisTemplate template;
    @Resource
    //@Autowired
    //@Qualifier("redisTemplate")
    RedisTemplate<String,Object> redisTemplate;
    //@Autowired
    RedisUtil redisUtil;

    //@Test
    public void testRedisTemplate(){
        System.out.println("template=="+redisTemplate.toString());
        System.out.println(template.hasKey("foo"));
        System.out.println(redisTemplate.hasKey("foo"));
        redisTemplate.opsForValue().set("a","test-a");
        System.out.println(redisTemplate.opsForValue().get("a"));
        JSONArray a = new JSONArray();
        a.put("高建华");
        a.put("全景腾飞RedisTemplate-test");
        a.put("596902726@qq.com");
        redisTemplate.opsForValue().set("test",a.toString(),30, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("test"));
    }
    //@Test
    public void test(){
        System.out.println("template=="+template.toString());
        System.out.println(template.opsForValue().get("foo"));
        JSONArray a = new JSONArray();
        a.put("高建华");
        a.put("全景腾飞");
        a.put("596902726@qq.com");
        template.opsForValue().set("test",a.toString(),30, TimeUnit.SECONDS);
        System.out.println(template.opsForValue().get("test"));
    }



    //@Test
    public void testutil(){
        //RedisUtil redisUtil = new RedisUtil(template);
        System.out.println("template=="+redisUtil.toString());
        System.out.println(redisUtil.get("foo"));
        JSONArray a = new JSONArray();
        a.put("高建华");
        a.put("全景腾飞");
        a.put("596902726@qq.com");
        redisUtil.set("testUtil",a.toString(),30);
        System.out.println(redisUtil.get("testUtil"));
    }
}
