package com.sdj.wu_back.controller;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.Result;
import com.sdj.wu_back.dao.HotelDao;
import com.sdj.wu_back.entity.Hotel;
import com.sdj.wu_back.entity.Reserve;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.service.HotelService;
import com.sdj.wu_back.service.ReserveService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/reserve")
public class ReserveController {

    @Resource
    private ReserveService reserveService;
    @Resource
    private HotelDao hotelDao;


    @GetMapping("/search")
    public Result findBySearch(Params params){
        PageInfo<Reserve> info =   reserveService.findBySearch(params);
        return Result.success(info);
    }

    @PostMapping()
    public Result save(@RequestBody Reserve reserve ){

        Hotel  hotel = hotelDao.searchByID(reserve.getHotelId());
        if(hotel.getNum() == 0 ){
            return Result.error("房间已完成预定");
        }
        reserve.setTime(DateUtil.now());
        reserveService.add(reserve);

        hotel.setNum(hotel.getNum() -  1 );
        hotelDao.updateHotel(hotel);

        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        Reserve reserve =   reserveService.findById(id);
        Hotel  hotel = hotelDao.searchByID(reserve.getHotelId());
        hotel.setNum(hotel.getNum() +  1 );
        hotelDao.updateHotel(hotel);
        reserveService.delete(id);
        return Result.success();
    }

}
