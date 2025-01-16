package com.sdj.wu_back.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.JwtTokenUtils;
import com.sdj.wu_back.dao.AuditDao;
import com.sdj.wu_back.dao.UserDao;
import com.sdj.wu_back.entity.Audit;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class AuditService {

    @Resource
    private AuditDao auditDao;

    @Resource
    private UserDao userDao;

    public void add(Audit audit){

//        auditDao.insertSelective(audit);
        auditDao.addAudit(audit);
    }

    public void  update(Audit audit){
        auditDao.updateAudit(audit);

    }

    public PageInfo<Audit> findBySearch(Params params){
        User user  = JwtTokenUtils.getCurrentUser();
       if(ObjectUtils.isEmpty(user)){
           throw new CustomException("token中没有用户信息");
       }
       if("ROLE_STUDENT".equals(user.getRole())){
            params.setUserId(user.getId());
       }
        PageHelper.startPage(params.getPageNum(),params.getPageSize());
        List<Audit> list = auditDao.findBySearch(params);

        for(Audit audit : list ){
            if(!ObjectUtils.isEmpty(audit.getUserId())){
            User user2 =  userDao.searchByID(audit.getUserId());
                if(!ObjectUtils.isEmpty(user2.getName())){
                    audit.setUserName(user2.getName());
                }
            }
        }
        return PageInfo.of(list);
    }

    public void  delete(Integer id){
        auditDao.deleteAudit(id);

    }


}
