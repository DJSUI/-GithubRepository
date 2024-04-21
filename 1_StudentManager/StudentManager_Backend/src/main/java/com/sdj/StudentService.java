package com.sdj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class StudentService {
//    2 studentMapper 调用mapper方法，并指向=》StudentMapper

//     学生信息查询功能
    @Autowired
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


//   用户登陆功能
    public LoginUser authenticate(String id , String pwd){
        LoginUser  loginUser = studentMapper.resualtAuthentic(id);
        if (loginUser != null && loginUser.getPwd().equals(pwd)) {
            return loginUser;
        } else {
            return null; // 用户名或密码不匹配，返回null
        }

    }


}
