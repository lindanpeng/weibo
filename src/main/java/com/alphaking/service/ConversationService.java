package com.alphaking.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alphaking.constant.MessageConstant;
import com.alphaking.dao.UserDao;
import com.alphaking.model.Conversation;
import com.alphaking.model.Message;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.ConversationDto;
import com.alphaking.pojo.weibo.UserDto;

@Service
public class ConversationService extends ImportDaoService{
@Resource
private CommonService commonService;
/**
 * 获取用户的所有对话
 */
 public List<ConversationDto> getConversations(Integer userId){
	 
	 SearchCondition searchCondition=new SearchCondition();
	 searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ,userId,0);
	 PagerResult<Conversation> pagerResult=conversationDao.list(false, searchCondition);
	 List<ConversationDto> conversationDtos=null;
	 if (pagerResult.getResultSet()!=null) {
            conversationDtos=new ArrayList<>(pagerResult.getResultSet().size());
		for(Conversation conversation:pagerResult.getResultSet()){
			ConversationDto conversationDto=new ConversationDto();
			 conversationDto.setFriend(commonService.wragUser(userId, conversation.getFriendUserId()));
			conversationDto.setConversation(conversation);
			conversationDto.setLatestMessage(messageDao.getLatestMessage(userId, conversation.getFriendUserId()));
			Long unReadMessageAmount=messageDao.getUnReadMessageAmount(userId,conversation.getFriendUserId(),MessageConstant.UNREAD)+
					messageDao.getUnReadMessageAmount(userId,conversation.getFriendUserId(),MessageConstant.UNKOWN);			
			conversationDto.setUnReadMessageAmount(unReadMessageAmount);
			conversationDtos.add(conversationDto);
			
		}
	}
	 messageDao.setUnkownMessageToUnRead(userId);
	 Collections.sort(conversationDtos);
	 return conversationDtos;
 }
 /**
  * 删除对话
  */
 public void deleteConversation(Integer conversationId){
	 Conversation conversation=conversationDao.getById(conversationId);
	 messageDao.deleteByUserIdAndFriendUserId(conversation.getUserId(), conversation.getFriendUserId());
	 conversationDao.delete(conversationId);
 }
 /**
  * 添加对话
  */
  public void addConversation(Integer userId,Integer friendUserId){
	  //先判断是否存在咯
	  SearchCondition searchCondition=new SearchCondition();
	  searchCondition.addCondition("friendUserId", SearchBean.OPERATOR_EQ,friendUserId,0);
	  searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ, userId, 0);
	  if (conversationDao.getUniqueResult(searchCondition)==null) {
		  Conversation conversation=new Conversation();
		  conversation.setFriendUserId(friendUserId);
		  conversation.setUserId(userId);
		  conversationDao.save(conversation);
	}
	
  }
  /**
   * 获取新对话
   */
  public List<ConversationDto> newConversations(Integer userId){
	  SearchCondition searchCondition=new SearchCondition();
	  searchCondition.addCondition("toUserId", SearchBean.OPERATOR_EQ,userId, 0);
	  searchCondition.addCondition("isRead", SearchBean.OPERATOR_EQ, MessageConstant.UNKOWN,0);
	  List<Message> messages=messageDao.list(false, searchCondition).getResultSet();
	  messageDao.setUnkownMessageToUnRead(userId);
	  HashMap<Integer,Integer> map=new HashMap<>();
	  for(Message message:messages){
		  map.put(message.getFromUserId(),message.getToUserId());
	  }
	  List<ConversationDto> conversationDtos=new ArrayList<>(map.size());
	  for(Map.Entry<Integer,Integer> entry:map.entrySet()){
		
		  ConversationDto conversationDto=new ConversationDto();
		  Conversation conversation=conversationDao.getByUserIdAndFriendUserId(userId, entry.getKey());
		  conversationDto.setConversation(conversation);
		  conversationDto.setFriend(commonService.wragUser(userId, conversation.getFriendUserId()));
		  conversationDto.setLatestMessage(messageDao.getLatestMessage(userId, conversation.getFriendUserId()));
		  conversationDto.setUnReadMessageAmount(messageDao.getUnReadMessageAmount(userId,conversation.getFriendUserId(),MessageConstant.UNREAD));
		  conversationDtos.add(conversationDto);
	  }
	  Collections.sort(conversationDtos);
	  return conversationDtos;
  }
  
}
