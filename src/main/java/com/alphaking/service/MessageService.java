package com.alphaking.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.alphaking.constant.MessageConstant;
import com.alphaking.model.Conversation;
import com.alphaking.model.Message;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;

@Service
public class MessageService extends ImportDaoService{
/**
 * 保存消息
 */
	public void saveMessage(Message message){
		messageDao.save(message);
		Integer fromUserId=message.getFromUserId();
		Integer toUserId=message.getToUserId();
		SearchCondition searchCondition=new SearchCondition();
		//System.out.println(fromUserId+"      "+toUserId);
		searchCondition.addCondition("userId",SearchBean.OPERATOR_EQ,toUserId,0);
		searchCondition.addCondition("friendUserId", SearchBean.OPERATOR_EQ,fromUserId, 0);	
		if (conversationDao.getUniqueResult(searchCondition)==null) {
			Conversation conversation=new Conversation();
			conversation.setFriendUserId(fromUserId);
			conversation.setUserId(toUserId);
			conversationDao.save(conversation);
		}
		SearchCondition searchCondition2=new SearchCondition();
		searchCondition2.addCondition("userId",SearchBean.OPERATOR_EQ,fromUserId,0);
		searchCondition2.addCondition("friendUserId", SearchBean.OPERATOR_EQ,toUserId, 0);	
		if (conversationDao.getUniqueResult(searchCondition2)==null) {
			Conversation conversation=new Conversation();
			conversation.setFriendUserId(toUserId);
			conversation.setUserId(fromUserId);
			conversationDao.save(conversation);
		}
	}
/**
 * 获取与某个朋友的所有消息
 */
	public List<Message> getMessagesOfFriend(Integer userId,Integer friendUserId){
           return messageDao.getMessagesOfFriend(userId, friendUserId);
	}
/**
 * 获取某个朋友的未读消息
 */
	public List<Message> getUnReadMessagesOfFriend(Integer userId,Integer friendUserId){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("fromUserId", SearchBean.OPERATOR_EQ, friendUserId, 0);
		searchCondition.addCondition("toUserId", SearchBean.OPERATOR_EQ, userId, 0);
		searchCondition.addCondition("isRead", SearchBean.OPERATOR_EQ, MessageConstant.UNREAD,0);
		searchCondition.addCondition("isDeleted", SearchBean.OPERATOR_NE, userId, 0);
		searchCondition.addCondition("sendTime", SearchBean.OPERATOR_SORT,"asc",0);
		List<Message> messages=messageDao.list(false, searchCondition).getResultSet();
		return messages;
	}
/**
 * 获取未读消息数量
 */
	public Long countNewMessages(Integer userId){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("toUserId",SearchBean.OPERATOR_EQ,userId,0);
		searchCondition.addCondition("isRead", SearchBean.OPERATOR_EQ,MessageConstant.UNKOWN, 0);
		searchCondition.addCondition("isDeleted", SearchBean.OPERATOR_NE, userId, 0);
		return messageDao.count(searchCondition);
	}
/**
 * 将消息设置为已读
 */
	public void hasReadMessages(Integer userId,Integer friendUserId){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("toUserId", SearchBean.OPERATOR_EQ,userId,0);
		searchCondition.addCondition("fromUserId",SearchBean.OPERATOR_EQ,friendUserId,0);
		searchCondition.addCondition("isRead",SearchBean.OPERATOR_EQ,MessageConstant.UNREAD,0);
		searchCondition.addCondition("isDeleted", SearchBean.OPERATOR_NE, userId, 0);
		List<Message> messages=messageDao.list(false, searchCondition).getResultSet();
		for(Message message:messages){
			message.setIsRead(MessageConstant.READ);
			messageDao.update(message);
		}
	}
   /**
    * 将朋友的未读消息设置为已读
    * @param userId
    * @param friendUserId
    */
	public void setUnReadToRead(Integer userId,Integer friendUserId){
		messageDao.setUnReadMessageToRead(userId,friendUserId);
	}
    /**
     * 测试flush
     */
    public void testFlush(Integer toUserId,Integer fromUserId){
    	messageDao.testFlush(toUserId,fromUserId);
    	Scanner scanner=new Scanner(System.in);
    	String line=scanner.nextLine();
    	scanner.close();
    	System.out.println(line);
    }
}