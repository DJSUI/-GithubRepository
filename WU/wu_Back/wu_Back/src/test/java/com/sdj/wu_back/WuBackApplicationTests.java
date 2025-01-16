package com.sdj.wu_back;

import com.sdj.wu_back.dao.UserDao;
import com.sdj.wu_back.entity.User;
import com.sdj.wu_back.service.UserService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tk.mybatis.mapper.util.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class WuBackApplicationTests {
	@InjectMocks
	private UserService userService;
	@Mock
	private UserDao userDao;




	@BeforeAll
	public static void init(){
		System.out.println("ni hao a ");
	}

	@BeforeEach
	public void start(){
		System.out.println("start test ");
	}

	@DisplayName("这是一个parameterTest")
	@Test
	void test1() {

		Mockito.when(this. userDao.UserTest(Mockito.any()))
				.thenAnswer(new Answer<User>() {
					@Override
					public User answer(InvocationOnMock invocation){
						//创建对比对象
						User userTest = new User();
						User actualPara = invocation.getArgument(0);
						//填充数据
						userTest.setName("testUser");
						userTest.setAge(10);
						userTest.setId(123);
						userTest.setSex("man");
						userTest.setPhone("23123123");
						userTest.setPassword("null");
						// 断言对比
						assertEquals(userTest.getName(),actualPara.getName());
						//返回对比结果
						return  userTest;
					}
				});

		User userPara =  new User();
		userPara.setName("testUser");
		User resUser = userService.getUserTest(userPara);






	}


	@ValueSource(strings = {"cat","dog"})
	@ParameterizedTest(name = "{0}")
	void test2(String name ){
		System.out.println(name);
	}




	@AfterEach
	public void end(){
		System.out.println("single test over ");
	}

	@AfterAll
	public static  void close(){
		System.out.println("ALl test over ");
	}







}
