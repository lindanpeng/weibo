package com.alphaking.service;

import java.sql.Timestamp;

import javax.faces.webapp.UIComponentBodyTag;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.ServletContextAwareProcessor;

import com.alphaking.constant.CommonConstant;
import com.alphaking.model.User;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;

public class UserServiceTest {
	private UserService userService;
	@Before
	public void init(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		userService=(UserService) applicationContext.getBean("userService");
	}
	@Test
   public void testAddUser(){
		for(int i=100;i<=200;i++){
			User user=new User();
			user.setNickname("僵尸"+i);
			user.setPassword("123");
			user.setUserOpenId(i+"@qq.com");
			user.setPortraitUrl(CommonConstant.DEFAULT_PORTRAIT_URL);
			user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
			user.setGender(0);
			user.setActivationStatus(true);
			userService.addUser(user);
		}
	   
   }
}
