package com.sdj.wu_back.service;


import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.dao.NoticeDao;
import com.sdj.wu_back.dao.TypeDao;
import com.sdj.wu_back.entity.Book;
import com.sdj.wu_back.entity.Notice;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.Type;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeService {

        @Resource
        private NoticeDao noticeDao;

        public void add(Notice notice){
                notice.setTime(DateUtil.now());
                noticeDao.addNotice(notice);
        }
        public void update(Notice notice){
                notice.setTime(DateUtil.now());
                noticeDao.updateNotice(notice);
        }


        public PageInfo<Notice> findBySearch(Params params) {
                PageHelper.startPage(params.getPageNum() ,params.getPageSize());
                List<Notice> list = noticeDao.findBySearch(params);
                return new PageInfo<>(list);
        }

        public void delete(Integer id){
                noticeDao.deleteNotice(id);
        }

        public List<Notice> findTop(){
                return noticeDao.findTop();
        }



}
