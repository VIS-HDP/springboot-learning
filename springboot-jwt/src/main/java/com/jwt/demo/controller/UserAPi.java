package com.jwt.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.jwt.demo.domain.User;
import com.jwt.demo.service.TokenService;
import com.jwt.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class UserAPi {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    //登录
    @PostMapping("user/login")
    public Object login(@RequestBody User user){
        System.out.println("=====登录=======");
        JSONObject jsonObject=new JSONObject();
        User userForBase=userService.findByUsername(user.getUsername());
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getPassword().equals(user.getPassword())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                String token = tokenService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }
    //登录
    @PostMapping("user/register")
    public Object register(@RequestBody User user){
        System.out.println("=====注册用户=======");
        User retUser=userService.save(user);
        JSONObject jsonObject=new JSONObject();
        Object o = JSONObject.toJSON(retUser);
        System.out.println("====注册成功=="+o.toString()+"=======");
        return  retUser;
    }
    //@UserLoginToken
    @GetMapping("/api/getMessage")
    public String getMessage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("=====进入getMessage=======");
        String user_id = (String) httpServletRequest.getAttribute("user_id");
        String user_role = (String) httpServletRequest.getAttribute("u_r");
        System.out.println("======api====user_id="+user_id+",user_role="+user_role);


        return "user_id="+user_id+",你已通过验证,user_role="+user_role;
    }

}
