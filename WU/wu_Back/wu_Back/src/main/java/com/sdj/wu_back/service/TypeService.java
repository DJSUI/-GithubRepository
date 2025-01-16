package com.sdj.wu_back.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.JwtTokenUtils;
import com.sdj.wu_back.common.Result;
import com.sdj.wu_back.dao.TypeDao;
import com.sdj.wu_back.dao.UserDao;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.Type;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class TypeService {

    @Resource
    private TypeDao typeDao;

    public void add(Type type){

        typeDao.insertSelective(type);

    }

    public void  update(Type type){
        typeDao.updateType(type);

    }

    public PageInfo<Type> findBySearch(Params params){
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Type> list = typeDao.findBySearch(params);
        return PageInfo.of(list);
    }

    public void  delete(Integer id){
        typeDao.deleteType(id);

    }

    public List<Type> findAll(){

        return typeDao.selectAll();
    }


}
