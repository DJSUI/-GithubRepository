package com.sdj.wu_back.dao;

import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.Type;
import com.sdj.wu_back.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface TypeDao extends Mapper<Type> {
    void updateType(Type type);

    List<Type> findBySearch(@Param("params")Params params);

    void deleteType(Integer id );

    @Select("select * from  type ")
    List<Type> selectAll();
}
