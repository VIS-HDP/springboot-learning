package com.jwt.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置Token验证拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    //排除拦截的路由
    private final String [] excludePath = {"/user/**","/test/**"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("=====注册拦截器=======");
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**").excludePathPatterns(excludePath);
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }

}
