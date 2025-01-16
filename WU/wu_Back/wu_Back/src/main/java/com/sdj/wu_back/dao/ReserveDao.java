package com.sdj.wu_back.dao;

import com.sdj.wu_back.entity.Reserve;
import com.sdj.wu_back.entity.Reserve ;
import com.sdj.wu_back.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface ReserveDao extends Mapper<Reserve > {
    List<Reserve > findBySearch(@Param("params")Params params);

     Reserve findById( Integer id );
    void updateReserve(Reserve reserve);

    void deleteReserve(Integer id );

    void  addReserve(Reserve reserve);

}
