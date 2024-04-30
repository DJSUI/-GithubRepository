package com.sdj;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//api的一层命名 /api
@RequestMapping("/api")
public class StudentController {
    //1 当请求过来先访问这里controller 层 ， 其次指向=》Service层
    @Autowired
    private StudentService studentService;
    //遍历所有学生信息******************************
    @GetMapping("/Students")
    // 实际后端程序端口为8085， 这里则是模拟从8080
    //因为浏览器跨源资源共享（CORS）策略限制，访问同一IP的不同端口，会被浏览器限制
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    //查询学生信息************************************
    @GetMapping("/Students/search")
    @CrossOrigin(origins = "http://localhost:8080")
    public List<Student> searchStudents(@RequestParam("keywords") String keywords) {
        // 调用Service层的方法查询学生信息
        return studentService.searchStudents(keywords);
    }

    //删除学生信息**********************************
    @DeleteMapping("/Students/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        // 调用Service层的方法删除学生信息
        boolean deleted = studentService.deleteStudent(id);
        if (deleted) {
            return ResponseEntity.ok("学生信息删除成功");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //修改学生****************
    @PutMapping("/editStudent/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<String> editStudent(@PathVariable String id,@RequestBody String jsonData) {

        // 使用 Jackson 库将 JSON 字符串反序列化为 Student 对象
        ObjectMapper objectMapper = new ObjectMapper();
        Student updatedStudent = null;
        try {
            updatedStudent = objectMapper.readValue(jsonData, Student.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        // 调用Service层的方法删除学生信息
        boolean updated = studentService.editStudent(id,updatedStudent);
        if (updated) {
            return ResponseEntity.ok("学生信息修改成功");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //增加学生****************
    @PutMapping("/addStudent/{id}")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<String> addStudent(@PathVariable String id,@RequestBody String jsonData) {

        // 使用 Jackson 库将 JSON 字符串反序列化为 Student 对象
        ObjectMapper objectMapper = new ObjectMapper();
        Student addStudent = null;
        try {
            addStudent = objectMapper.readValue(jsonData, Student.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 在
        // 调用Service层的方法删除学生信息
        boolean add = studentService.addStudent(id,addStudent);
        if (add) {
            return ResponseEntity.ok("学生信息添加成功");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    用户登录逻辑
@PostMapping("/login")
@CrossOrigin(origins = "http://localhost:8080")
public ResponseEntity<LoginUser> login(@RequestBody LoginUser loginUser) {
    // 从请求中获取用户名和密码
    String id = loginUser.getId();
    String pwd = loginUser.getPwd();

    // 使用 UserService 进行验证
    LoginUser user = studentService.authenticate(id, pwd);

    if (user != null) {
        // 如果验证成功，返回用户信息
        return ResponseEntity.ok(user);
    } else {
        // 如果验证失败，返回未授权状态码
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}



}
