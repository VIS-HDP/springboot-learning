package com.vis.demo.jta.controller;

import com.vis.demo.jta.service.JtaTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JtaTestController {

    @Autowired
    public JtaTestService  jtaTestService;
    @GetMapping("/jta/test")
    public String jtaTest(){
        int rows = jtaTestService.jtaTest();
        return  " hello jta test true ! rows = "+rows ;
    }

}
