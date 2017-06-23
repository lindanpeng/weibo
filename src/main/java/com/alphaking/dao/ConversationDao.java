package com.alphaking.dao;

import org.springframework.stereotype.Repository;

import com.alphaking.model.Conversation;
@Repository
public class ConversationDao extends BaseDaoImpl<Conversation>{
/**
 * 通过用户id和朋友id获取对话
 */
	public Conversation getByUserIdAndFriendUserId(Integer userId,Integer friendUserId){
		String hql="from Conversation where userId=:userId and friendUserId=:friendUserId";
		return (Conversation) getSession().createQuery(hql).setParameter("userId", userId).setParameter("friendUserId", friendUserId).uniqueResult();
	}
}
