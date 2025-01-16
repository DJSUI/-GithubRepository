package com.sdj.wu_back.common;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 *
 */
//如果前端和后端都使用同一个IP， 前端访问后端时候会被阻拦
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 1 设置访问源地址  允许所有来源地址的请求
        corsConfiguration.addAllowedHeader("*"); // 2 设置访问源请求头 允许所有请求头
        corsConfiguration.addAllowedMethod("*"); // 3 设置访问源请求方法  允许所有请求方法
        // 4 对接口配置跨域设置   contoller 下的所有接口适用上方规则
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}