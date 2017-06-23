package com.alphaking.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alphaking.model.UserRelation;

public class UserRelationServiceTest {
	private UserRelationService userRelationService;
	@Before
	public void init(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		userRelationService=(UserRelationService) applicationContext.getBean("userRelationService");
	}
	
	@Test
	public void testConcernUser(){
		for(int i=3;i<=30;i++){
			UserRelation userRelation=new UserRelation();
			userRelation.setConcernedUserId(1);
			userRelation.setFanId(i);
			userRelationService.concernUser(userRelation);
		}
		
	}
   @Test
   public void testIsFriend(){
	   
	   System.out.println(userRelationService.isFriend(1, 107));
   }

}
