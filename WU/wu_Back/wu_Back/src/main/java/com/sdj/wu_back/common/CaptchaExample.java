package com.sdj.wu_back.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;

import java.io.File;

public class CaptchaExample {
    public static void main(String[] args) throws Exception {
        // 创建一个带干扰线的验证码
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 20); // 宽200px，高100px，4个字符，20条干扰线

        // 获取验证码的文本
        String code = captcha.getCode();
        System.out.println("验证码: " + code);  // 输出验证码文本

        // 将验证码图像保存为文件
        captcha.write(new File("captcha.png"));  // 保存验证码图像为captcha.png
    }
}
