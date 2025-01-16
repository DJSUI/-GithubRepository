package com.sdj.wu_back.dao;

import com.sdj.wu_back.entity.Hotel;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


@Repository
public interface HotelDao extends Mapper<Hotel> {
    List<Hotel> findBySearch(@Param("params")Params params);


    @Select("select * from hotel where id = #{id}")
    Hotel searchByID(@Param("id") Integer id);
    void updateHotel(Hotel hotel);

    void deleteHotel(Integer id );

    void  addHotel(Hotel hotel);


}
