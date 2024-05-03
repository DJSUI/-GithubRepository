package com.sdj;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//后端程序的入口
// 测试api ，浏览器无法进行，最好使用postman
@SpringBootApplication
@MapperScan("com.sdj.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);

    }
}
