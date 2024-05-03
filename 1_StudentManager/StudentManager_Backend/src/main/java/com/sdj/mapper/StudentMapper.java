package com.sdj.mapper;

import com.sdj.entity.LoginUser;
import com.sdj.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

    @Mapper
    public interface StudentMapper {
//        3 执行slect 语句
//        使用 MyBatis Spring Boot Starter 时，可以省略编写 Mapper.xml 文件
//        以@Select这样形式的注解，积习难改sql编写
//        @Select、@Insert、@Update 和 @Delete 注解分别对应 SQL 查询语句
//        只需在方法上添加适当的注解，以及对应的 SQL 语句，就可以完成数据访问操作。

//        @Select是告诉mybatis框架实现一个sql语句
//        查询学生信息逻辑
        @Select("SELECT * FROM Students WHERE is_deleted != 1")
        List<Student>  getAllStudents();
    //    void addStudent(Student student);
    //    void updateStudent(Student student);

//        关键字查询学生信息逻辑
        @Select("SELECT * FROM Students WHERE is_deleted != 1 AND (studentId LIKE CONCAT('%', #{keywords}, '%') OR name LIKE CONCAT('%', #{keywords}, '%') OR gender LIKE CONCAT('%', #{keywords}, '%') OR age LIKE CONCAT('%', #{keywords}, '%'))")
        List<Student>  resualtStudents(String keywords);

//        删除学生逻辑逻辑
        @Update("UPDATE Students SET is_deleted = 1 WHERE studentId = #{id}")
        int softDeleteStudent(String id);
//        编辑学生信息逻辑
//        mybatis 的查找顺序是根据参数位置来判断 所以不能将 #{param2.name} 写成 #{updatedStudent.name} ，
//        param2 会寻找方法函数中的第二个参数的位置
        @Update("UPDATE Students SET name = #{param2.name}, gender = #{param2.gender}, age = #{param2.age} WHERE studentId = #{param1}")
        int editStudent(String id, Student updatedStudent);
//        增加学生逻辑
        @Insert("INSERT INTO Students (studentId, name, gender, age) VALUES(#{param2.studentId}, #{param2.name}, #{param2.gender}, #{param2.age})")
        int addStudent(String id, Student addStudent);

//        存在token时候 ，解析token 验证密码是否正确,返回整个学生实体
//            mybatis写法不需要 写成sql语句
        LoginUser selectById(Integer id);

        //    首次登陆的时候，用于验证账号和密码
        @Select("SELECT * FROM Login_user WHERE id = #{id}")
        LoginUser resualtAuthentic(String id);




    }
