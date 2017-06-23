package com.alphaking.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alphaking.constant.ThumbsupConstant;
import com.alphaking.model.Thumbsup;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
@Repository
public class ThumbsupDao extends BaseDaoImpl<Thumbsup>{
/**
 * 根据微博Id删除赞
 * @param twitterId
 */
public void deleteByTwitterId(Integer twitterId){
	String hql="delete from Thumbsup where twitterId = "+twitterId;
	getSession().createQuery(hql).executeUpdate();
}
/**
 * 根据微博Id获取点赞数
 */
public Long getThumbsUpAmountById(Integer twitterId){
	SearchCondition searchCondition=new SearchCondition();
	searchCondition.addCondition("twitterId", SearchBean.OPERATOR_EQ,twitterId,0);
	
	return count(searchCondition);
}
/**
 * 根据微博Id获取所有点赞
 */
public List<Thumbsup> getThumbsupList(Integer twitterId){
	String hql="from Thumbsup where twitterId = "+twitterId;
	return getSession().createQuery(hql).list();
}
/**
 * 根据微博id和用户Id删除赞
 */
public void deleteByTwitterIdAndUserId(Integer userId,Integer twitterId){
	String hql="delete from Thumbsup where userId=:userId and twitterId =:twitterId";
	getSession().createQuery(hql).setParameter("userId", userId).setParameter("twitterId", twitterId).executeUpdate();
}
/**
 * 根据微博id和用户id获取赞
 */
public Thumbsup getByUserIdAndTwitterId(Integer userId,Integer twitterId){
	String hql="from Thumbsup where userId =:userId and twitterId =:twitterId";
	return (Thumbsup) getSession().createQuery(hql).setParameter("userId", userId).setParameter("twitterId", twitterId).uniqueResult();
}
/**
 * 已阅用户的所有赞
 * @param userId
 */
public void readThumbsupOfUser(Integer userId) {
	String hql=" update Thumbsup set isRead=:isRead where thumbsupedUserId=:userId";
    getSession().createQuery(hql).setParameter("isRead", ThumbsupConstant.THUMBSUP_READ_TRUE).
    setParameter("userId", userId).executeUpdate();
	
}
}
