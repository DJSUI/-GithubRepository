package com.sdj.wu_back.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.AutoLog;
import com.sdj.wu_back.common.Result;
import com.sdj.wu_back.entity.Book;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.service.BookService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/book")
public class BookController {
    private static  final Logger log = LoggerFactory.getLogger(BookController.class);
    @Resource
    private BookService bookService;

    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Book> info =   bookService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping()
    public Result save(@RequestBody Book book ){
        if(book.getId() == null){
            bookService.add(book);
        }else{
            bookService.update(book);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @AutoLog("删除图书信息")
    public Result delete(@PathVariable Integer id){
        log.info("已经开始执行方法");
        bookService.delete(id);
        return Result.success();
    }
    @GetMapping("/echarts/bie")
    public Result bie(){
        //查询出所有数据
        List <Book> list = bookService.findAll();
        Map<String,Long> collect = list.stream().filter(x -> !ObjectUtils.isEmpty(x.getTypeName()))
                                                .collect(Collectors.groupingBy(Book::getTypeName,Collectors.counting()));
        //最后返回前端数据结构
        List <Map<String,Object>> mapList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(collect)){
            for(String key : collect.keySet()){
                Map<String,Object> map = new HashMap<>();
                map.put("name",key);
                map.put("value",collect.get(key));
                mapList.add(map);
            }
        }
        return Result.success(mapList);
    }

}
