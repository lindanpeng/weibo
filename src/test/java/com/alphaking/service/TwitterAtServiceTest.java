package com.alphaking.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.TwitterDto;

public class TwitterAtServiceTest {
	private TwitterAtService 	twitterAtService;
	@Before
	public void init(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		twitterAtService=(TwitterAtService) applicationContext.getBean("twitterAtService");
	}
	@Test
	public void testGetTwitter(){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ,111 , 0);
		PagerResult<TwitterDto> pagerResult=twitterAtService.getTwitterAtList(searchCondition);
		for(TwitterDto twitterDto:pagerResult.getResultSet()){
			System.out.println(twitterDto.getTwitter().getTextContent());
		}
	}
}
