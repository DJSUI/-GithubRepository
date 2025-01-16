package com.sdj.wu_back.service;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.dao.HotelDao;
import com.sdj.wu_back.dao.ReserveDao;
import com.sdj.wu_back.dao.UserDao;
import com.sdj.wu_back.entity.Hotel;
import com.sdj.wu_back.entity.Reserve;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import jakarta.annotation.Resource;
import org.springframework.beans.support.ResourceEditorRegistrar;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReserveService {

    @Resource
    private ReserveDao reserveDao;
    @Resource
    private HotelDao  hotelDao ;
    @Resource
    private UserDao userDao ;




    public PageInfo<Reserve> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum() ,params.getPageSize());
        List<Reserve> list = reserveDao.findBySearch(params);
        if(CollectionUtil.isEmpty(list)){
            return PageInfo.of(new ArrayList<>());
        }
        for(Reserve reserve: list){
            if(ObjectUtil.isNotEmpty(reserve.getHotelId())){
                Hotel hotel = hotelDao.searchByID(reserve.getHotelId());
                if(ObjectUtil.isNotEmpty(hotel)){
                    reserve.setHotelName(hotel.getName());
                };
            }
            if(ObjectUtil.isNotEmpty(reserve.getUserId())){
                User user  = userDao.searchByID(reserve.getUserId());
                if(ObjectUtil.isNotEmpty(user)){
                    reserve.setUserName(user.getName());
                }
            }
        }
        return new PageInfo<>(list);
    }

    public void add(Reserve reserve) {
        reserveDao.addReserve(reserve);

    }
    public void  update(Reserve reserve ){
        reserveDao.updateReserve(reserve);


    }

    public  Reserve findById(Integer id ) {
        Reserve reserve = reserveDao.findById(id);
        return  reserve ;

    }

    public void delete(Integer id){
        reserveDao.deleteReserve(id);
    }

}
