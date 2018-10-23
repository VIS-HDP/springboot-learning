package com.example.controller;

import com.example.entity.User;
import com.example.utils.RedisUtil;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * nohup java -jar springbooredis-0.0.1-SNAPSHOT.jar > log.file 2>&1 &
 * java -jar  lqyspringboot-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
 *
 */
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

    /**init=132492,rangebyScore=46
     *
     *  init=7044,rangebyScore=50
     *init=9366,rangebyScore=56
     **/
    @RequestMapping("/sort")
    String helloSort(){
        logger.info("=======start======");
        String name = "mysort";
        long start = System.currentTimeMillis();
        if(redisTemplate.opsForZSet().size(name)<=0){
            Random r = new Random();
            for(int i=0;i<10000;i++){
                int score = r.nextInt(10000);
                User user = new User(new Long(i),score, "测试zset",new Date());
                redisTemplate.opsForZSet().add(name,user,score);
            }
        }
        long middle = System.currentTimeMillis();
        //rangeByScore  Get elements in range from start to end where score is between min and max from sorted set.
        //Set<Object> ret = redisTemplate.opsForZSet().rangeByScore(name,2,3);

        Set<Object> ret = redisTemplate.opsForZSet().range(name,0,19);
        long end =  System.currentTimeMillis();
        System.out.println("init="+(middle-start)+",rangebyScore="+(end-middle));
        logger.info("init="+(middle-start)+",rangebyScore="+(end-middle));
        System.out.println(ret.toString());

        return "Hello,successful ! sort ";
    }


}
