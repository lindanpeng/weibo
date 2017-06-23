package com.alphaking.dao;

import org.springframework.stereotype.Repository;

import com.alphaking.constant.CommentConstant;
import com.alphaking.model.Comment;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
@Repository
public class CommentDao extends BaseDaoImpl<Comment>{
	/**
	 * 获取某微博的评论数量
	 */
	public Long getCommentsAmountOfTwitter(Integer twitterId){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("twitterId", SearchBean.OPERATOR_EQ,twitterId,0);
		return count(searchCondition);
	}

	public void deleteByTwitterId(Integer twitterId) {
		String hql="delete from Comment where twitterId=:twitterId";
		getSession().createQuery(hql).setParameter("twitterId", twitterId).executeUpdate();
		
	}
/**
 * 已阅用户收到的所有评论
 * @param userId
 */
	public void readCommentOfUser(Integer userId) {
		String hql="update Comment set isRead=:isRead where toUserId=:userId and isRead="+CommentConstant.IS_READ_FALSE;
		getSession().createQuery(hql).setParameter("isRead", CommentConstant.IS_READ_TRUE).
		setParameter("userId", userId).executeUpdate();
		
	}
/**
 * 已阅用户某微博的所有的评论
 */
	public void readCommentOfTwitter(Integer twitterId){
		String hql="update Comment set isRead=:isRead where twitterId=:twitterId and isRead="+CommentConstant.IS_READ_FALSE;
		getSession().createQuery(hql).setParameter("isRead", CommentConstant.IS_READ_TRUE).setParameter("twitterId", twitterId).
		executeUpdate();
	}
}
