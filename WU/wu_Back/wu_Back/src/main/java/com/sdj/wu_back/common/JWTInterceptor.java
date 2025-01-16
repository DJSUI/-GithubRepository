package com.sdj.wu_back.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.exception.CustomException;
import com.sdj.wu_back.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    private static  final Logger log = LoggerFactory.getLogger(JWTInterceptor.class);
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request , HttpServletResponse response ,Object handler){
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            token = request.getParameter("token");
        }
        if(StrUtil.isBlank(token)){
            throw new CustomException("无效的token 请重新登陆");
        }

        String userId;
        User user = new User();

        try {
            userId = JWT.decode(token).getAudience().get(0);
             user =  userService.findById(Integer.parseInt(userId));

        }catch (Exception e ){
            String errMsg = "token  验证失败， 请重新登陆地";
            log.error(errMsg + ", token=  " + token,e );
            throw new CustomException(errMsg);
        }

        if(user == null){
            throw   new CustomException("用户不存在");
        }

        try{
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            jwtVerifier.verify(token);
        }catch(JWTVerificationException e ){
            throw  new CustomException("token 验证失败，请重新登陆");
        }
        log.info("token 验证成功 允许放行");
        return  true ;
    }
}
