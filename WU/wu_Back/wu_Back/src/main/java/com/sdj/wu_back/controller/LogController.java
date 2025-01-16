package com.sdj.wu_back.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.Result;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.Log;
import com.sdj.wu_back.exception.CustomException;
import com.sdj.wu_back.service.LogService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/log")
public class LogController {
    @Resource
    private LogService logService;

//
//    @GetMapping
//    public Result findAll(){
//        return Result.success(logService.findAll());
//    }
//
//

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Log> info = logService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        logService.delete(id);
        return Result.success();
    }









}
