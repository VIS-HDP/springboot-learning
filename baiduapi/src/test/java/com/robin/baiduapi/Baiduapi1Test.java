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

public class Baiduapi1Test {

    //设置APPID/AK/SK
    public static final String APP_ID = "";
    public static final String API_KEY = "";
    public static final String SECRET_KEY = "";

    public static void main(String[] args) {

            // 初始化一个AipSpeech
            AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);

            // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
            //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
            //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

            // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
            // 也可以直接通过jvm启动参数设置此环境变量
            //System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        //String path = "D:\\code\\java-sdk\\speech_sdk\\src\\test\\resources\\16k_test.pcm";
        String path = "D:\\aaa.wma";
            // 调用接口
            JSONObject res = client.asr(path, "pcm", 16000, null);
            System.out.println(res.toString());

/*        // 对本地语音文件进行识别
        String path = "D:\\code\\java-sdk\\speech_sdk\\src\\test\\resources\\16k_test.pcm";
        JSONObject asrRes = client.asr(path, "pcm", 16000, null);
        System.out.println(asrRes);

        // 对语音二进制数据进行识别
        byte[] data = Util.readFileByBytes(path);     //readFileByBytes仅为获取二进制数据示例
        JSONObject asrRes2 = client.asr(data, "pcm", 16000, null);
        System.out.println(asrRes2);*/



/*        String content = "藕夹新闻精选\n";

        System.out.println("content.length="+(content).length());
        content = content.replaceAll("】","。").replaceAll("【",",").replaceAll("A.","。A.")+"。欢迎下次收听。";
        System.out.println("content="+content);
        AipSpeech client = Baiduapi1Test.getAipSpeech();
        try {
            Baiduapi1Test.synthesis(client,content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("====end =======baidu===========");*/
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
