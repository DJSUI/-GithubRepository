package com.sdj.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sdj.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;

public class TokenUtils {
    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);
    private static  StudentService staticStudentService;

    @Resource
    StudentService studentService;

    @PostConstruct
    public void setUserService() {
        staticStudentService = studentService;
    }
//    生成Token
    public static String createToken(String id, String pwd) {
        return JWT.create().withAudience(id) // 将 userId-role 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(pwd)); // 以 password 作为 token 的密钥
    }







}
