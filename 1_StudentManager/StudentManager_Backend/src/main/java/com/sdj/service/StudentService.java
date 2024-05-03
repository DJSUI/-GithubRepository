package com.sdj.service;

import cn.hutool.core.util.ObjectUtil;
import com.sdj.common.enums.ResultCodeEnum;
import com.sdj.entity.LoginUser;
import com.sdj.entity.Student;
import com.sdj.exception.CustomException;
import com.sdj.mapper.StudentMapper;
import com.sdj.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentService {
//    2 studentMapper 调用mapper方法，并指向=》StudentMapper

//     学生信息查询功能
    @Resource
    private StudentMapper studentMapper;

    public List<Student> getAllStudents() {
        return studentMapper.getAllStudents();
    }

//     学生信息删除功能
    public boolean deleteStudent(String id) {
        int result = studentMapper.softDeleteStudent(id);
        if (result == 0 ){
            return false ;
        }else{
            return true;
        }


}
//   学生信息修改功能
public boolean editStudent(String id ,Student updatedStudent) {
    int result = studentMapper.editStudent(id,updatedStudent);
    if (result == 0) {
        System.out.println("修改时候，数据库里没找到！！！  ");

        return false;


    } else {
        return true;
    }
}
//   学生信息添加功能
public boolean addStudent(String id ,Student addStudent) {
    int result = studentMapper.addStudent(id,addStudent);
    if (result == 0) {
        System.out.println("添加时候，数据库里出错！！！  ");

        return false;


    } else {
        return true;
    }
}

//    学生信息查询功能
    public List<Student> searchStudents(String keywords) {
    return studentMapper.resualtStudents(keywords);

}


// 用于token 验证功能 主要验证在JWT 中
    public  LoginUser  selectById(Integer id) {
        System.out.println("执行了Token验证service  且 解析token中的id为   "+id);
        return studentMapper.selectById(id);
    }


// 无token 用户登陆

    public LoginUser authenticate(LoginUser loginUser){
//        更具用户登陆账号， 获取数据库真实实体
        LoginUser  dbLoginUser = studentMapper.resualtAuthentic(loginUser.getId());
        if (ObjectUtil.isNull(dbLoginUser)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!loginUser.getPwd().equals(dbLoginUser.getPwd())) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token  将ID和password 作为token的一部分生成出来
        String token = TokenUtils.createToken(dbLoginUser.getId(), dbLoginUser.getPwd());
        dbLoginUser.setToken(token);
        return dbLoginUser;


    }


}
