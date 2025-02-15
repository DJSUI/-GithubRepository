package com.sdj.wu_back.dao;

import com.sdj.wu_back.entity.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface NoticeDao extends Mapper<Notice> {

    List<Notice> findBySearch(@Param("params")Params params);

    void  addNotice(Notice notice);

    void  updateNotice(Notice notice);

    void deleteNotice(Integer id);

    @Select("select * from notice order by time desc limit 3")
    List<Notice> findTop();

}
