package com.sdj.wu_back.common;


import jakarta.annotation.Resource;
import org.aopalliance.intercept.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private JWTInterceptor jwtInterceptor;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer){
        configurer.addPathPrefix("/api",clazz -> clazz.isAnnotationPresent(RestController.class));
        
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/captcha")
                .excludePathPatterns("/api/user/login")
                .excludePathPatterns("/api/user/register")
                .excludePathPatterns("/api/files/**")
                .excludePathPatterns("/api/type/upload");
    }
}
