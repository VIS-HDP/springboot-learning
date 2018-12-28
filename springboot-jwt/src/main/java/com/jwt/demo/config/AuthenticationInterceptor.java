package com.jwt.demo.config;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jwt.demo.domain.User;
import com.jwt.demo.service.TokenService;
import com.jwt.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *拦截器验证token
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        System.out.println("=====拦截器验证token开始=======");
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }

        if (token == null) {
            throw new RuntimeException("无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        String u_r;
        try {
            userId = JWT.decode(token).getAudience().get(0);
            u_r = JWT.decode(token).getClaim("u_r").asString();
            httpServletRequest.setAttribute("user_id",userId);
            httpServletRequest.setAttribute("u_r",u_r);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401");
        }
        User user = userService.findById(Long.valueOf(userId));
        if (user == null) {
            throw new RuntimeException("用户不存在，请重新登录");
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword()+ TokenService.TOKEN_KEY)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("401");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
