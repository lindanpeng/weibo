package com.alphaking.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alphaking.constant.MessageConstant;
import com.alphaking.model.Message;
@Repository
public class MessageDao extends BaseDaoImpl<Message>{
	/**
	 * 获取与某个朋友的所有消息
	 */
		public List<Message> getMessagesOfFriend(Integer userId,Integer friendUserId){
           String hql="from Message where (isDeleted !=:userId) and ((fromUserId=:userId and toUserId=:friendUserId) or "
           		+ "(fromUserId=:friendUserId and toUserId=:userId)) order by sendTime asc";
           
           return getSession().createQuery(hql).setParameter("userId", userId).setParameter("friendUserId", friendUserId).list();
           
		}
	/**
	 * 获取用户和指定好友的某种类型的消息数量
	 */
		public Long getUnReadMessageAmount(Integer userId,Integer friendUserId,Integer type){
			String hql="select count(*) from Message where fromUserId=:friendUserId and toUserId=:userId and isRead = "+type;
			return (Long) getSession().createQuery(hql).setParameter("userId", userId).setParameter("friendUserId", friendUserId).uniqueResult();
		}
	/**
	 * 获取与指定好友的最新一条消息
	 */
		public Message getLatestMessage(Integer userId,Integer friendUserId){
			 String hql="from Message where (isDeleted !=:userId) and ((fromUserId=:userId and toUserId=:friendUserId) or "
		           		+ "(fromUserId=:friendUserId and toUserId=:userId)) order by sendTime desc";
			 Message message= (Message) getSession().createQuery(hql).setParameter("userId", userId).setParameter("friendUserId", friendUserId)
			        .setFirstResult(0).setMaxResults(1).uniqueResult();
			 return message;
		}
	/**
	 * 将未知消息设置为未读
	 */
		public void setUnkownMessageToUnRead(Integer userId){
			String hql="update Message set isRead =:newIsRead where isDeleted!=:userId and toUserId=:userId and isRead =:isRead";		
			getSession().createQuery(hql).setParameter("userId", userId).setParameter("isRead", MessageConstant.UNKOWN)
			.setParameter("newIsRead", MessageConstant.UNREAD).executeUpdate();
			getSession().flush();
		}
	public void setUnReadMessageToRead(Integer userId,Integer friendUserId) {
		String hql="update Message set isRead =:newIsRead where isDeleted!=:userId and toUserId=:userId and isRead =:isRead and fromUserId =:friendUserId";		
		getSession().createQuery(hql).setParameter("userId", userId).setParameter("isRead", MessageConstant.UNREAD)
		.setParameter("newIsRead", MessageConstant.READ).setParameter("friendUserId", friendUserId).executeUpdate();
		getSession().flush();
		
	}
	/**
	 * 删除用户与朋友的记录
	 */
	public void deleteByUserIdAndFriendUserId(Integer userId,Integer friendUserId){
		String hql1="delete from Message where isDeleted =:friendUserId and ((fromUserId=:userId and toUserId=:friendUserId) or "
           		+ "(fromUserId=:friendUserId and toUserId=:userId)) ";
		String hql2="update Message set isDeleted =:userId where isDeleted =0 and ((fromUserId=:userId and toUserId=:friendUserId) or "
           		+ "(fromUserId=:friendUserId and toUserId=:userId))";
		getSession().createQuery(hql1).setParameter("userId", userId).setParameter("friendUserId", friendUserId).executeUpdate();
		getSession().createQuery(hql2).setParameter("userId", userId).setParameter("friendUserId", friendUserId).executeUpdate();
	}
	/**
	 * 测试flush,并没有立即提交更新？
	 */
	public void testFlush(Integer toUserId,Integer fromUserId){
		String hql=" delete from Message where toUserId =:toUserId and fromUserId =:fromUserId";
		getSession().createQuery(hql).setParameter("toUserId", toUserId).setParameter("fromUserId",fromUserId)
		.executeUpdate();
		getSession().flush();
	}
}
