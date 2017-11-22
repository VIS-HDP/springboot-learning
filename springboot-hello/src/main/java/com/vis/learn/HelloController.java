package com.vis.learn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/22.
 */
@RestController
public class HelloController {

    @GetMapping("/api/say")
    public String sayHello(){
        return "Hello Springboot123!";
    }
}
