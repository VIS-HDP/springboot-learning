package com.robin.baiduapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordToAudioController {

    @GetMapping("/test")
    public String hello(){
        Audio audio = new Audio();
        audio.setTitle("你好！");
        audio.setUrl("https://www.baidu.com");
        System.out.println("======hello audio=======");
        return "Hello,Audio";
    }
}
