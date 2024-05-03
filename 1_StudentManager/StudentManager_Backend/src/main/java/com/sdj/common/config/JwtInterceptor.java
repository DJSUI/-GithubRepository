package com.sdj.common.config;

import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.sdj.common.Constants;
import com.sdj.common.enums.ResultCodeEnum;
import com.sdj.entity.LoginUser;
import com.sdj.exception.CustomException;
import com.sdj.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor  implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(JwtInterceptor.class);

    @Resource
    private StudentService studentService;

    @Override
    public boolean preHandle(HttpServletRequest request , HttpServletResponse response , Object handler) {
        // 1. 从http请求的header中获取token
        String token = request.getHeader(Constants.TOKEN);

        if (ObjectUtil.isEmpty(token)) {
            // 如果没拿到，从参数里再拿一次
            token = request.getParameter(Constants.TOKEN);
        }
        // 2. 开始执行认证（此时已确定存在token）
        if (ObjectUtil.isEmpty(token)) {
            throw new CustomException(ResultCodeEnum.TOKEN_INVALID_ERROR);
        }
        LoginUser loginUser = null ;
        try {
            // 解析token获取存储的数据(用户ID  和 用户角色)
            String id = JWT.decode(token).getAudience().get(0);
            System.out.println("id"+id);
            System.out.println("code before executed");
            // 根据userId查询数据库
//            bug 下方语句
            loginUser = studentService.selectById(Integer.valueOf(id));
            System.out.println("code is been executed");

        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.TOKEN_CHECK_ERROR);
        }
//          如果上方从数据查数据，获取loginUser信息，如果查不到，则抱下方错误
        if (ObjectUtil.isNull(loginUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        return true ;
    }

}