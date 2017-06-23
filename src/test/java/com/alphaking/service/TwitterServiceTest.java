package com.alphaking.service;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alphaking.model.Twitter;
import com.alphaking.model.TwitterAt;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.TwitterDto;

public class TwitterServiceTest {
	private TwitterService 	twitterService;
	@Before
	public void init(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		twitterService=(TwitterService) applicationContext.getBean("twitterService");
	}
	@Test
	public void testaddTwitter(){
		Twitter twitter=new Twitter();
		twitter.setPublicTime(new Timestamp(System.currentTimeMillis()));
		twitter.setTextContent("你好啊");
		twitter.setUserId(1);
		TwitterDto twitterDto=new TwitterDto();
		twitterDto.setTwitter(twitter);
		twitterService.addTwitterDto(twitterDto);
	}
	
}
