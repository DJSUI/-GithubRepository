package com.sdj.wu_back.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Dict;
import  cn.hutool.core.util.StrUtil;
import com.sdj.wu_back.common.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileController {

    //上传的文件不会传到数据库中，而是被保留在项目根目录下 ，会根据时间生成时间戳，命名图片为这个时间戳
    //存储到本地，时间戳也会被存储在数据库当中，
    private static final  String filePath = System.getProperty("user.dir") + "/file/";

    //文件上传
    @PostMapping("/upload")
    public Result upload(MultipartFile file){
        synchronized (FileController.class){
            //获取当前时间戳
            String flag = System.currentTimeMillis()+"";
            //获取上传的文件的名字
            String fileName = file.getOriginalFilename();
            try{
                if(!FileUtil.isDirectory(filePath)){
                    FileUtil.mkdir(filePath);
                }
                //文件存储形式 时间戳+文件名
                FileUtil.writeBytes(file.getBytes(),filePath+flag+"-"+fileName);
                System.out.println(fileName + "--上传成功");
                Thread.sleep(1L);
            }catch (Exception e){
                System.err.println(fileName+"--文件上传失败");
            }
            return  Result.success(flag);
        }
    }


    // 获取文件
    @GetMapping("/{flag}")
    public void avatarPath(@PathVariable String flag, HttpServletResponse response){
        if(!FileUtil.isDirectory(filePath)){
            FileUtil.mkdir(filePath);
        }
        OutputStream os;
        List<String> fileNames = FileUtil.listFileNames(filePath);
        String  avatar = fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");
        try{
            if(StrUtil.isNotEmpty(avatar)){
                response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(avatar,"UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(filePath+avatar);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch (Exception e ){
            System.out.println("文件下载失败");
        }
    }
//    Wang富文本传输文件
    @PostMapping("/wang/upload")
    public Map<String,Object> wangEditorUpload(MultipartFile file){
        String flag  = System.currentTimeMillis() + "";
        String fileName  = file.getOriginalFilename();
        try{
            FileUtil.writeBytes(file.getBytes(),filePath+flag+"-"+fileName);
            System.out.println(fileName + "--上传成功");
            Thread.sleep(1L);
        }catch (Exception e){
            System.err.println(fileName + "-- 文件上传失败");
        }
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("errno",0);
        resMap.put("data", CollUtil.newArrayList(Dict.create().set("url","http://localhost:8080/api/files/"+flag)));
        return resMap;
    }

}
