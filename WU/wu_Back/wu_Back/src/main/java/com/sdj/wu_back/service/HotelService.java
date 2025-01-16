package com.sdj.wu_back.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.dao.BookDao;
import com.sdj.wu_back.dao.HotelDao;
import com.sdj.wu_back.dao.TypeDao;
import com.sdj.wu_back.entity.Book;
import com.sdj.wu_back.entity.Hotel;
import com.sdj.wu_back.entity.Params;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Resource
    private HotelDao hotelDao;




    public PageInfo<Hotel> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum() ,params.getPageSize());
        List<Hotel> list = hotelDao.findBySearch(params);
        return new PageInfo<>(list);
    }

    public void add(Hotel hotel) {
//        hotelDao.insertSelective(hotel);
        hotelDao.addHotel(hotel);

    }
    public void  update(Hotel hotel ){
        hotelDao.updateHotel(hotel);


    }
    public void delete(Integer id){
        hotelDao.deleteHotel(id);
    }

}
