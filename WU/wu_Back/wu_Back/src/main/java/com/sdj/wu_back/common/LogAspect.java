package com.sdj.wu_back.common;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sdj.wu_back.entity.Log;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.service.LogService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component
@Aspect
public class LogAspect {

    @Resource
    private LogService logService;

    @Around("@annotation(autoLog)")
    public Object doAround(ProceedingJoinPoint joinPoint, AutoLog autoLog) throws Throwable {

        //操作内容
        String name = autoLog.value();
        //操作时间
        String time  = DateUtil.now();
        String username = "" ;
        //从token中获取用户
        User user = JwtTokenUtils.getCurrentUser();

        if(ObjectUtil.isNotNull(user)){
            username = user.getName();
        }
        // 从request中获取IP地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        //执行具体接口
        Result result  = (Result) joinPoint.proceed();
        Object data = result.getData();
        if(data  instanceof User){
            User user1  = (User)data ;
            username = user1.getName();
        }
        //生成日志实体，记录到数据库
        Log log = new Log(null,name ,time ,username,ip);
        logService.add(log);
        return result ;


    }

}

