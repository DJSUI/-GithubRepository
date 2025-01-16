package com.sdj.wu_back.dao;

import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface LogDao extends Mapper<Log> {

    List<Log> findBySearch(@Param("params")Params params);

    void deleteLog(Integer id );

    void add(Log log);

}
