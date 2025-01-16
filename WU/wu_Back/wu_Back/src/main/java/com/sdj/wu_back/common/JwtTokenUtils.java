package com.sdj.wu_back.common;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Date;


@Component
public class JwtTokenUtils {
    private static UserService staticUserService;
    private  static final  Logger log = LoggerFactory.getLogger(JwtTokenUtils.class);

    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService(){
        staticUserService =  userService;
    }


    // 生成token
    public static String  genToken(String userId, String password){
        return JWT.create().withAudience(userId)
                .withExpiresAt(DateUtil.offsetHour(new Date() , 2))
                .sign(Algorithm.HMAC256(password));
    }


    public static User getCurrentUser(){
        String token = null ;
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            token = request.getHeader("token");
            if(StrUtil.isBlank(token)){
                token = request.getParameter("token");
            }
            if(StrUtil.isBlank(token)){
                log.error("获取当前登陆的token失败，token：{}",token);
                return null;
            }

            String adminId = JWT.decode(token).getAudience().get(0);
            return staticUserService.findById(Integer.valueOf(adminId));

        } catch (Exception e){
            log.error("获取当前登陆的管理员信息失败，token={}",token,e);
            return null;
        }


    }





}
