package com.sdj.wu_back.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.Result;
import com.sdj.wu_back.entity.Book;
import com.sdj.wu_back.entity.Notice;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.Type;
import com.sdj.wu_back.exception.CustomException;
import com.sdj.wu_back.service.NoticeService;
import com.sdj.wu_back.service.TypeService;
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
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @PostMapping
    public Result update(@RequestBody Notice notice){
        if(ObjectUtil.isEmpty(notice.getId())){
            noticeService.add(notice);
        }else{
            noticeService.update(notice);
        }
        return Result.success();
    }

    @GetMapping
    public Result findTop(){
       List<Notice> list = noticeService.findTop();
        return Result.success(list);
    }



    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Notice> info =   noticeService.findBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        noticeService.delete(id);
        return Result.success();
    }






}
