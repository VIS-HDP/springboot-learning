package com.jwt.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jwt.demo.domain.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {
    public static final String TOKEN_KEY = "_key-o2plus2018";

    //生成token
    public String getToken(User user) {
        String token="";
        token= JWT.create().withAudience(user.getId()+"")// 将 user id 保存到 token 里面
                .withClaim("u_r","r_vip,r_buyer")//增加自定义信息
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*24))//设置过期时间
                .sign(Algorithm.HMAC256(user.getPassword()+TOKEN_KEY));// 以 password 作为 token 的密钥
        return token;
    }
}
