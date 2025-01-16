package com.sdj.wu_back.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sdj.wu_back.common.JwtTokenUtils;
import com.sdj.wu_back.dao.UserDao;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    //test
    public String junitTest (String name  ){
        String cSts = "";

        return cSts;
    }

    //test
    public User getUserTest(User user){
        User resUser =  userDao.UserTest( user);
        return resUser;
    }

//    用户登陆逻辑
    public User login (User user ){

        if(user.getName() == null || "".equals(user.getName())){
            throw  new CustomException("username can not be null");
        }
        if(user.getPassword() == null || "".equals(user.getPassword())){
            throw  new CustomException("password can not be null");
        }
        User loginUser = userDao.findByNameAndPassword(user.getName(),user.getPassword());
        if(loginUser == null){
            throw  new CustomException("username or password is incorrect ");
        }

        String token = JwtTokenUtils.genToken(loginUser.getId().toString(),loginUser.getPassword());
        loginUser.setToken(token);
        return  loginUser;
    }

    public List<User> getUser(){
//        return userDao.getUser();
        return userDao.selectAll();
    }

    public PageInfo<User> findBySearch(Params params) {
        PageHelper.startPage(params.getPageNum() ,params.getPageSize());
        List<User> list = userDao.findBySearch(params);
        return new PageInfo<>(list);
    }

//    添加用户逻辑
    public void add(User user ){
        //非空检查
        if(user.getName() == null || "".equals(user.getName())){
            throw new CustomException("用户名不为空");
        }
        //进行姓名重复性检查
        User overlapuser  = userDao.findByName(user.getName());
        if(overlapuser != null){
            // ！！此处希望返回失败的信息，但是因为是void 无返回数值
            // 所以用到全局异常处理
            throw new CustomException("user already exist ");
        }
        //初始化用户密码
        if(StringUtil.isEmpty(user.getPassword())){
            user.setPassword("123");
        }
        userDao.insertSelective(user);


    }

    public void  update(User user ){
        userDao.updateUser(user);

    }
    public void  delete(Integer id){
        userDao.deleteUser(id);

    }

    public User findById(Integer id){
//        return userDao.selectByPrimaryKey(id);   无法使用ybatisdocker
         return userDao.searchByID(id);
    }

}
