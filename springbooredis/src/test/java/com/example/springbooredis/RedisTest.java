package com.example.springbooredis;

import com.example.entity.User;
import com.example.utils.RedisLock;
import com.example.utils.RedisLock2;
import com.example.utils.RedisLockInfo;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    StringRedisTemplate template;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RedisLock redisLock;
    @Autowired
    RedisLock2 redisLock2;

    //@Test
    public void RedisLockTest(){
        RedisLockInfo redisLockInfo = redisLock.tryLock("lockKey",50000,1000);
        System.out.println("redesLock="+redisLockInfo.getRedisKey()+",,,"+redisLockInfo.getTryCount());
        System.out.println("释放锁="+redisLock.releaseLock(redisLockInfo));
    }

    @Test
    public void RedisLock2Test(){
        boolean result = redisLock2.setNx("lockkey2","requestid",30);
        System.out.println("RedisLock2Test="+result);
        System.out.println("RedisLock2Test释放锁="+redisLock2.releaseDistributedLock("lockkey2","requestid"));
    }
    //@Test
    public void test(){

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
        System.out.println(ret.toString());




        //redisTemplate.expire("mytest",30,TimeUnit.SECONDS);

        //https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ZSetOperations.html
        //Get elements where score is between min and max from sorted set ordered from high to low.
        //Get elements in range from start to end where score is between min and max from sorted set ordered high -> low.
        //redisTemplate.opsForZSet().reverseRangeByScore();

        //Intersect sorted sets at key and otherKey and store result in destination destKey.
        //redisTemplate.opsForZSet().intersectAndStore();

        // reverseRange Get elements in range from start to end from sorted set ordered from high to low.
    }



    //@Test
    public void testutil(){

        System.out.println("template=="+redisUtil.toString());
        System.out.println(redisUtil.set("a","set-a"));
        System.out.println(redisUtil.get("a").toString());
        JSONArray a = new JSONArray();
        a.put("robin");
        a.put("测试");
        a.put("596902726@qq.com");
        redisUtil.set("testUtil",a.toString(),30);
        System.out.println(redisUtil.get("testUtil"));
        User user = new User();
        user.setCreateTime(new Date());
        user.setId(100L);
        user.setUser_name("测试哦");
        user.setType(1);
        redisUtil.set("testUser",user,30);
        System.out.println(((User)redisUtil.get("testUser")).toString());
    }

    //@Test
    public void testRedisTemplate(){
        System.out.println("template=="+redisTemplate.toString());
        System.out.println(template.hasKey("foo"));
        System.out.println(redisTemplate.hasKey("foo"));
        redisTemplate.opsForValue().set("a","test-a");
        System.out.println(redisTemplate.opsForValue().get("a"));
        JSONArray a = new JSONArray();
        a.put("robin");
        a.put("测试RedisTemplate-test");
        a.put("596902726@qq.com");
        redisTemplate.opsForValue().set("test",a.toString(),30, TimeUnit.SECONDS);
        System.out.println(redisTemplate.opsForValue().get("test"));
    }
}
