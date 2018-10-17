package com.example.controller;

import com.example.utils.RedisUtil;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
public class MyController {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    RedisUtil redisUtil;
    @RequestMapping("/log")
    String log(){

        logger.info("this is info ");
        logger.debug("this is debug=================");
        logger.error("this is error");
        logger.warn("this is warn ");
        return  "hello" ;
    }


    @RequestMapping("/hello")
    String hello(){
        System.out.println("foo="+stringRedisTemplate.opsForValue().get("foo"));
        System.out.println("foo="+redisTemplate.hasKey("foo"));

        redisTemplate.opsForValue().set("a","test-a");
        System.out.println(redisTemplate.opsForValue().get("a"));
        JSONArray a = new JSONArray();
        a.put("高建华");
        a.put("全景腾飞RedisTemplate-test");
        a.put("596902726@qq.com");
        redisTemplate.opsForValue().set("test",a.toString(),30, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("test").toString());
        System.out.println("redisUtil="+redisUtil.get("test"));
        try {
            System.out.println(redisUtil.hincr("intemp","a",1));
            System.out.println("intemp.a="+redisUtil.hget("intemp","a").toString());
            System.out.println("intemp="+redisUtil.hmget("intemp").toString());
            redisUtil.expire("intemp",30);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "Hello";
    }
    @RequestMapping("/sort")
    String helloSort(){

        //redisTemplate.opsForZSet().remove("mysort");
        redisTemplate.expire("mytest",30,TimeUnit.SECONDS);

/*        redisTemplate.opsForZSet().add("mysort","j",4);
        redisTemplate.opsForZSet().add("mysort","o",3);
        redisTemplate.opsForZSet().add("mysort","a",6);
        redisTemplate.opsForZSet().add("mysort","g",1);
        redisTemplate.opsForZSet().add("mysort","a",2);
        redisTemplate.opsForZSet().add("mysort","i",5);
        redisTemplate.opsForZSet().add("mysort","n",7);*/


        //https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html
        //Get elements where score is between min and max from sorted set ordered from high to low.
        //Get elements in range from start to end where score is between min and max from sorted set ordered high -> low.
        //redisTemplate.opsForZSet().reverseRangeByScore();

        //Intersect sorted sets at key and otherKey and store result in destination destKey.
        //redisTemplate.opsForZSet().intersectAndStore();
        //rangeByScore  Get elements in range from start to end where score is between min and max from sorted set.
        Set<Object> ret = redisTemplate.opsForZSet().rangeByScore("mysort",2,3);
        System.out.println(ret.toString());

        // reverseRange Get elements in range from start to end from sorted set ordered from high to low.

        return "Hello";
    }


}
