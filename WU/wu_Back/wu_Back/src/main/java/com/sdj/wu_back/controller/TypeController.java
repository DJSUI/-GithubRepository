package com.sdj.wu_back.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.Result;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.Type;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.exception.CustomException;
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
@RequestMapping("/type")
public class TypeController {
    @Resource
    private TypeService typeService;


    @GetMapping
    public Result findAll(){
        return Result.success(typeService.findAll());
    }

    @PostMapping()
    public Result save(@RequestBody Type type ){

        if(type.getId() == null){
            typeService.add(type);
        }else{
            typeService.update(type);
        }
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Type> info = typeService.findBySearch(params);
        return Result.success(info);

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        typeService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Type> list){
        for(Type type: list){
             typeService.delete(type.getId() );
        }
        return Result.success();
    }

    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws IOException {

        // 1 从数据库中查询所有数据
        List<Type> all =   typeService.findAll();
        if(CollectionUtil.isEmpty(all)){
            throw  new CustomException("未找到数据");
        }
        // 2 定义一个List和Map<key,value>，存储处理之后的数据，用于塞到List里面
        List<Map<String,Object>> list = new ArrayList<>(all.size());
        // 3 遍历每条数据，然后封装Map<key,value>， 把整个map塞到List里面
        for(Type type : all){
            Map<String,Object> row = new HashMap<>();
            row.put("分类名称",type.getName());
            row.put("分类描述",type.getDescription());

            list.add(row);
        }
        // 4 创建一个excelWriter  把list 数据用writer 写出来
        ExcelWriter wr  = ExcelUtil.getWriter(true);
        wr.write(list);
        // 5.把excel 下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=sdj.xlsx");
        ServletOutputStream out = response.getOutputStream();
        wr.flush(out,true);
        wr.close();
        IoUtil.close(System.out);
        return  Result.success();
    }

    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        List<Type> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Type.class);
        if(!CollectionUtil.isEmpty(infoList)){
            for(Type type: infoList){
                try{
                    typeService.add(type);
                }catch (Exception e ){
                    e.printStackTrace();
                }
            }
        }
        return Result.success();
    }





}
