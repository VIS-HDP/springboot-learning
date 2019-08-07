package com.robin.baiduapi;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class BaiduapiTest {

    //设置APPID/AK/SK
    public static final String APP_ID = "";
    public static final String API_KEY = "";
    public static final String SECRET_KEY = "";

    public static void main(String[] args) {

        String content = "藕夹新闻精选\n" +
                "2019-7-05。\n" +
                "\n" +
                "藕夹事业号，让你赚钱快人一步，\n" +
                "祝愿大家在7月里生意兴隆，财源广进！\n" +
                "\n" +
                "【国内】\n" +
                "1）李克强主持召开国务院常务会议，支持自贸试验区在改革开放方面更多先行先试，部署完善跨境电商等新业态促进政策。\n" +
                "2）[法治] 受贿864万余元，中船重工原总经理孙波一审被判12年，并处罚金80万元。湖南省政府驻上海办事处原主任王华平受贿近5000万元，一审被判14年6个月。云南省工信厅原副厅长王祥被逮捕。安徽检察机关依法对新华发行集团原工会主席桑坤涉嫌受贿案提起公诉。\n" +
                "3）辽宁开原突发龙卷风已致6死190余伤9900余人受灾。四川宜宾市珙县5.6级地震致9人伤，应急管理部调度救援。水利部：黄河今年第2号洪水形成，预计本月上中旬防汛形势严峻。\n" +
                "\n" +
                "【国际】\n" +
                "1）欧盟下届领导人人选出炉，德国防长被提名为欧盟委员会主席。\n" +
                "2）拖欠欧洲委员会5500万欧元会费，俄罗斯：不准备给。普京访问意大利，就贸易和双边关系发展等问题与意大利总统马塔雷拉、总理孔特举行会谈。\n" +
                "3）韩国拟反制日本“经济报复”，或向世贸组织申诉。\n" +
                "4）利比亚一难民收容所遭空袭造成170余人死伤，联合国强烈谴责。\n" +
                "5）伊朗总统鲁哈尼表示伊有可能突破浓缩铀丰度限制。\n" +
                "\n" +
                "【财经】\n" +
                "工信部：2019年1-5月互联网和相关服务业完成业务收入4282亿元。\n" +
                "\n" +
                "【文体】\n" +
                "1）教育部会同公安部约谈有关搜索引擎网站，规范整治“志愿填报指导”信息服务。\n" +
                "2）中国工程院院士李兆申学术不端续：中国工程院、海军军医大学：已启动调查工作。\n" +
                "3）美洲杯：巴西2:0战胜阿根廷，秘鲁3:0大胜智利进决赛，将和巴西争冠。\n" +
                "4）世界女排联赛总决赛：中国女排二队1:3告负土耳其，出线形势岌岌可危。\n" +
                "\n" +
                "【生活】\n" +
                "1）公安部交通管理局：全国机动车保有量达3.4亿辆，驾驶人数4.2亿。\n" +
                "2）穗莞深城际铁路计划9月底开通。世界航标日，宁波市民见证花鸟灯塔启明。\n" +
                "3）海南将逐步给予免签入境人员30日以上停留期限。";

        System.out.println("content.length="+(content).length());
        content = content.replaceAll("】","。").replaceAll("【",",").replaceAll("A.","。A.")+"。欢迎下次收听。";
        System.out.println("content="+content);
        AipSpeech client = BaiduapiTest.getAipSpeech();
        try {
            BaiduapiTest.synthesis(client,content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("====end =======baidu===========");
    }

    public static void synthesis(AipSpeech client,String content) throws JSONException {
        //TtsResponse res = client.synthesis("你好百度", "zh", 1, null);
        //System.out.println(res.getResult().toString());
        String name = "藕夹新闻精选";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        name = name +sdf.format(new Date());
        System.out.println(sdf.format(new Date()));
        /**
         * tex	String	合成的文本，使用UTF-8编码，
         * 请注意文本长度必须小于1024字节	是
         * cuid	String	用户唯一标识，用来区分用户，
         * 填写机器 MAC 地址或 IMEI 码，长度为60以内	否
         * spd	String	语速，取值0-9，默认为5中语速	否
         * pit	String	音调，取值0-9，默认为5中语调	否
         * vol	String	音量，取值0-15，默认为5中音量	否
         * per	String	发音人选择, 0为女声，1为男声，
         * 3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女
         */
        // 设置可选参数
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "6");
        options.put("pit", "5");
        options.put("per", "0");
        TtsResponse res = client.synthesis(content, "zh", 1, options);
        //System.out.println(res.);
        JSONObject result = res.getResult();    //服务器返回的内容，合成成功时为null,失败时包含error_no等信息
        byte[] data = res.getData();            //生成的音频数据

        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, name+".mp3");
                System.out.println("output.mp3 successful ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("data is null ");
        }
        if (result != null) {
            System.out.println(result.toString(2));
        }else{
            System.out.println("result is null ");
        }
    }

    public static AipSpeech getAipSpeech(){
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        // client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");
        return client;
    }
}
