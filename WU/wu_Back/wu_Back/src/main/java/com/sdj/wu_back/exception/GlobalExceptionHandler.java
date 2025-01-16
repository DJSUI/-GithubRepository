package com.sdj.wu_back.exception;


import com.sdj.wu_back.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = "com.sdj.wu_back.controller")
public class GlobalExceptionHandler {
    
        private static final Logger log  = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(Exception.class)
        @ResponseBody
        public Result error(HttpServletRequest request , Exception e ){
            log.error("异常信息",e);  // 用于展示给后台
            return Result.error("系统异常");    //用于展示给前台
        }
        
        @ExceptionHandler(CustomException.class)
        @ResponseBody
        public Result customError(HttpServletRequest request , CustomException e){
            return Result.error(e.getMsg());
        }
        
        
        
}
