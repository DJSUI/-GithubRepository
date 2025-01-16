package com.sdj.wu_back.controller;

import cn.hutool.captcha.CaptchaUtil;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.WuBackApplication;
import com.sdj.wu_back.common.AutoLog;
import com.sdj.wu_back.common.CaptureConfig;
import com.sdj.wu_back.common.Result;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
//    @Resource 功能是引入javaBean
    @Resource
    private UserService userService;

    @GetMapping("/start")
    public Result test(){
        return Result.success("good job");
    }

    @GetMapping("/getUser")
    public Result getUser(){
        List<User> list = userService.getUser();
        return Result.success(list);
    }

    @PostMapping("/register")
    public Result register (@RequestBody User user ){
        userService.add(user);
        return Result.success() ;
    }

    @CrossOrigin
    @PostMapping("/login")
    @AutoLog("登陆系统")
    public Result login (@RequestBody User user ,@RequestParam String key){
        // 第一步  验证码验证

        if(ObjectUtils.isEmpty(user.getVerCode())){
            return Result.error("验证码空");
        }
        if( ! user.getVerCode().toLowerCase().equals(CaptureConfig.CAPTURE_MAP.get(key))){
            return Result.error("验证码错误");
        }
        //第二步  验证用户信息
        User  loginUser = userService.login(user);
        CaptureConfig.CAPTURE_MAP.remove(key);
        return Result.success(loginUser,"login successfully!!!");
    }




    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<User>  info =   userService.findBySearch(params);
        return Result.success(info);

    }

    @PostMapping()
    public Result save(@RequestBody User user ){



        if(user.getId() == null){
            userService.add(user);
        }else{
            userService.update(user);
        }
         return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        userService.delete(id);
        return Result.success();
    }

}
