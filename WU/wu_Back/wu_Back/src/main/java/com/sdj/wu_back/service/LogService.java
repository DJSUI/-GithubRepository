package com.sdj.wu_back.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.dao.LogDao;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.Log;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Resource
    private LogDao logDao;

    public PageInfo<Log> findBySearch(Params params){
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Log> list = logDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void add(Log log ){
        logDao.add(log);

    }

    public void  delete(Integer id){
        logDao.deleteLog(id);

    }
    

}
