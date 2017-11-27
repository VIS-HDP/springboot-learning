package com.vis.learn;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/22.
 */
@RestController
public class HelloController {

    @CrossOrigin
    @GetMapping("/users")
    public String sayHello(){
        System.out.println(" enter sayHello ...111 ");
        return "Springboot1233";
    }
}
