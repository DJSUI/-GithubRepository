package com.sdj.wu_back;

//import org.mybatis.spring.annotation.MapperScan;
import com.sdj.wu_back.entity.Params;
import com.sdj.wu_back.entity.User;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
//如果写了dao层 这里必须添加
@MapperScan("com.sdj.wu_back.dao")
public class WuBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(WuBackApplication.class, args);
	}


}
