package com.sdj.wu_back.dao;

import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;



@Repository
public interface UserDao  extends Mapper<User> {
//    方式一 基于注解的方式
//    @Select("select * from user")
//    List<User> getUser();

//    方式二  基于mapper的方式
//      List<User> getUser();

//    方式三  基于tk-mybatis自带包的方式  上方加入extends Mapper<User>

    //for Parameter Test
    User UserTest(User user);

    List<User> findBySearch(@Param("params")Params params);

    void updateUser(User user);

    void deleteUser(Integer id );

    @Select("select * from user where name = #{name} limit 1 ")
    User findByName(@Param("name")  String name );

    @Select("select * from user where name = #{name} and password = #{password} limit 1 ")
    User findByNameAndPassword(@Param("name")  String name,@Param("password")  String password);

    @Select("select * from user where id = #{id}")
    User searchByID(@Param("id") Integer id);
}
