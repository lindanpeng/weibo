package com.alphaking.service;

import java.util.List;

import org.apache.coyote.http11.filters.VoidInputFilter;
import org.hibernate.id.IntegralDataTypeHolder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alphaking.model.Message;

public class MessageSeviceTest {
	private MessageService messageService;
	@Before
	public void init(){
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
		messageService=(MessageService) applicationContext.getBean("messageService");
	}
	@Test
   public void testGetUnReadMessagesOfFriend(){
		Integer userId=1;
		Integer friendUserId=5;
		List<Message> messages=messageService.getUnReadMessagesOfFriend(userId, friendUserId);
		System.out.println(messages.size());
	}
  @Test
  public void testSetUnReadToRead(){
	  Integer userId=1;
	  Integer friendUserId=5;
	  messageService.setUnReadToRead(userId, friendUserId);
  }
  @Test
  public void testSaveMessage(){
	Message message=new Message();
	message.setContent("test");
	message.setFromUserId(1);
	message.setToUserId(2);
	message.setIsRead(0);
	messageService.saveMessage(message);
	Message message2=new Message();
	message2.setContent("test2");
	message2.setFromUserId(2);
	message2.setToUserId(1);
	message2.setIsRead(0);
	messageService.saveMessage(message2);
	messageService.saveMessage(message);
	messageService.saveMessage(message2);
	messageService.saveMessage(message2);
	messageService.saveMessage(message);
	messageService.saveMessage(message);
	messageService.saveMessage(message);
  }
@Test
 public void testFlush(){
	Integer toUserId=2;
	Integer fromUserId=1;
	messageService.testFlush(toUserId, fromUserId);
}
}
