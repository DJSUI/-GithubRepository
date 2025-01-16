package com.sdj.wu_back.controller;







import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.sdj.wu_back.common.CaptureConfig;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping
public class CaptureController {

    @RequestMapping("/captcha")
    public void generateCaptcha(@RequestParam String  key,HttpServletResponse response,HttpServletRequest request) throws Exception {
        // 创建一个带干扰线的字符验证码
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 20);
        //将验证吗内容存放在session当中
//        session.setAttribute("captcha",captcha.getCode());
        CaptureConfig.CAPTURE_MAP.put(key,captcha.getCode().toLowerCase());
        // 设置响应内容类型为图像
        response.setContentType("image/png");

//        System.out.println("session before"+session);

        // 输出验证码图像到浏览器
        try (ServletOutputStream out = response.getOutputStream()) {
            captcha.write(out);
        }
    }


}
