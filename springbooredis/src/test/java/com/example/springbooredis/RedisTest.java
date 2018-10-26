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
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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

    @Test
    // redis geohash test 附近的服务。
    public void geoHashTest(){
        ArrayList<RedisGeoCommands.GeoLocation<Object>> list = new ArrayList<>();
        RedisGeoCommands.GeoLocation<Object> geo = null ;
        for(String[] loation : testData()){
            Double lng = Double.parseDouble(loation[4].trim());
            Double lat = Double.parseDouble(loation[3].trim());
            User user = new User(Long.parseLong(loation[0]),
                    loation[1]+loation[2],lng,lat);
            Point point = new Point(lng,lat);
            geo = new RedisGeoCommands.GeoLocation(user,point);
            list.add(geo);
        }
        // ---初始化数据-----
        //redisTemplate.opsForGeo().add("test_geo",list);

        //longitude,latitude
        Circle circle = new Circle(116.51706,39.91763, Metrics.KILOMETERS.getMultiplier());
        // 参数，返回结果包含距离，坐标，距离正序排序，查询限制5个
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                                             .includeDistance().includeCoordinates().sortAscending().limit(5);
        //获取附近的地理位置
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo()
                .radius("test_geo",circle,args);
        System.out.println(results);
        //redisTemplate.opsForGeo().r
    }

    //@Test
    public void redisLockTest(){
        RedisLockInfo redisLockInfo = redisLock.tryLock("lockKey",50000,1000);
        System.out.println("redesLock="+redisLockInfo.getRedisKey()+",,,"+redisLockInfo.getTryCount());
        System.out.println("释放锁="+redisLock.releaseLock(redisLockInfo));
    }

    //@Test
    public void redisLock2Test(){
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
    private String[][] testData(){
        String[][] temp = new String[][]{
        {"100" , "北京市", "北京市", "39.91681", "116.51475"},
        {"102" , "北京市", "北京市", "39.91992", "116.50289"},
        {"105" , "北京市", "北京市", "39.96681", "116.50269"},
        {"108" , "北京市", "北京市", "39.91763", "116.51876"},
        {"125" , "北京市", "北京市", "39.96684", "116.50315"},
        {"126" , "北京市", "北京市", "39.96707", "116.50376"},
        {"130" , "北京市", "北京市", "39.91704", "116.51739"},
        {"135" , "北京市", "北京市", "39.96695", "116.50334"},
        {"136" , "北京市", "北京市", "39.96669", "116.50306"},
        {"143" , "北京市", "北京市", "39.91978", "116.51685"},
        {"144" , "北京市", "北京市", "39.91725", "116.51711"},
        {"161" , "北京市", "北京市", "40.13005", "116.66362"},
        {"162" , "北京市", "北京市", "39.90464", "116.52124"},
        {"168" , "北京市", "北京市", "39.91722", "116.51677"},
        {"169" , "北京市", "北京市", "39.91788", "116.51781"},
        {"171" , "北京市", "北京市", "39.91739", "116.51706"},
        {"173" , "北京市", "北京市", "39.91741", "116.52055"},
        {"213" , "北京市", "北京市", "39.91718", "116.52011"},
        {"218" , "北京市", "北京市", "39.97614", "116.45107"},
        {"243" , "北京市", "北京市", "39.58792", "116.70916"},
        {"247" , "北京市", "北京市", "39.79135", "116.5028 "},
        {"249" , "北京市", "北京市", "39.81126", "116.49993"},
        {"272" , "北京市", "北京市", "39.91731", "116.51693"},
        {"276" , "北京市", "北京市", "39.97157", "116.39463"},
        {"277" , "北京市", "北京市", "39.91492", "116.40774"},
        {"287" , "北京市", "北京市", "39.91682", "116.51728"},
        {"288" , "北京市", "北京市", "39.78139", "116.51309"},
        {"293" , "北京市", "北京市", "40.12187", "116.66388"},
        {"294" , "北京市", "北京市", "39.91682", "116.51723"},
        {"296" , "北京市", "北京市", "39.90482", "116.53354"},
        {"299" , "北京市", "北京市", "39.90354", "116.51489"},
        {"300" , "北京市", "北京市", "39.93567", "116.37439"},
        {"325" , "北京市", "北京市", "39.91702", "116.51734"},
        {"326" , "北京市", "北京市", "39.91203", "116.51112"},
        {"557" , "河北省", "唐山市", "39.99428", "118.71694"},
        {"655" , "北京市", "北京市", "40.12933", "116.6601 "},
        {"701" , "北京市", "北京市", "39.92085", "116.50452"},
        {"849" , "江苏省", "苏州市", "31.91003", "120.59302"},
        {"909" , "河北省", "保定市", "38.55681", "114.93643"},
        {"920" , "江西省", "宜春市", "28.21471", "115.78478"},
        {"990" , "四川省", "南充市", "30.97208", "105.98494"},
        {"1363",	"贵州省",	"贵阳市",	"26.63658",	"106.77757"},
        {"1384",	"辽宁省",	"沈阳市",	"41.702",   "123.1594"},
        {"1443",	"湖南省",	"郴州市",	"25.81951",	"113.02006"},
        {"1724",	"四川省",	"广安市",	"30.17466",	"106.98932"},
        {"1781",	"辽宁省",	"抚顺市",	"41.85268",	"123.73212"},
        {"1811",	"广东省",	"河源市",	"23.79989",	"114.71113"},
        {"2229",	"河南省",	"南阳市",	"32.84501",	"111.92347"},
        {"2230",	"河南省",	"南阳市",	"33.01142",	"112.54284"},
        {"2255",	"北京市",	"北京市",	"39.94813",	"116.31012"},
        {"2333",	"河北省",	"保定市",	"38.49539",	"115.60142"},
        {"2343",	"河北省",	"保定市",	"38.4957",  "115.6011"},
        {"2368",	"安徽省",	"阜阳市",	"32.64824",	"116.28162"},
        {"2921",	"湖南省",	"衡阳市",	"26.42193",	"112.85216"},
        {"3344",	"安徽省",	"阜阳市",	"32.64817",	"116.28163"},
        {"3818",	"广东省",	"深圳市",	"22.54605",	"114.02597"},
        {"3948",	"江苏省",	"苏州市",	"31.21564",	"120.63848"},
        {"3975",	"北京市",	"北京市",	"39.91682",	"116.51729"},
        {"3976",	"北京市",	"北京市",	"39.91734",	"116.51707"},
        {"4339",	"天津市",	"天津市",	"39.14393",	"117.21081"},
        {"4567",	"广东省",	"汕头市",	"23.40185",	"116.71532"},
        {"4798",	"江西省",	"上饶市",	"28.71418",	"116.6645"},
        {"5065",	"甘肃省",	"武威市",	"37.83978",	"102.62925"},
        {"5971",	"江西省",	"赣州市",	"26.50255",	"116.02763"},
        {"6182",	"江苏省",	"苏州市",	"31.31799",	"120.61991"},
        {"6196",	"河南省",	"开封市",	"34.82955",	"114.24925"},
        {"6255",	"黑龙江",	"哈尔滨",	"45.66558",	"126.63055"},
        {"6369",	"江西省",	"南昌市",	"28.85651",	"115.58849"},
        {"6584",	"安徽省",	"亳州市",	"33.58828",	"116.13102"},
        {"6787",	"湖南省",	"郴州市",	"25.27244",	"112.5588"},
        {"6924",	"广东省",	"深圳市",	"22.67894",	"114.00209"},
        {"7142",	"广东省",	"韶关市",	"24.31474",	"114.06894"},
        {"9108",	"湖南省",	"郴州市",	"26.71854",	"113.27806"},
        {"9550",	"福建省",	"龙岩市",	"25.09994",	"117.02989"},
        {"9607",	"浙江省",	"嘉兴市",	"30.48727",	"120.83597"},
        {"9682",	"北京市",	"北京市",	"39.91682",	"116.51728"},
        {"9715",	"北京市",	"北京市",	"39.92999",	"116.39565"},
        {"9754",	"北京市",	"北京市",	"39.88925",	"116.67521"} };
        return temp ;
    }
}
