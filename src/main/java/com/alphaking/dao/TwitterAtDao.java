package com.alphaking.dao;

import org.springframework.stereotype.Repository;

import com.alphaking.constant.TwitterAtConstant;
import com.alphaking.model.TwitterAt;

@Repository
public class TwitterAtDao extends BaseDaoImpl<TwitterAt> {
	/**
	 * 通过微博Id删除所有twitterAt
	 * 
	 * @param twitterId
	 */
	public void deleteByTwitterId(Integer twitterId) {
		String hql = "delete from TwitterAt where twitterId=:twitterId";
		getSession().createQuery(hql).setParameter("twitterId", twitterId).executeUpdate();

	}

	/**
	 * 已阅所有@我的微博
	 */
	public void readTwitterAtOfUser(Integer userId) {
		String hql = "update TwitterAt set isRead=:isRead where userId=:userId";
		getSession().createQuery(hql).setParameter("isRead", TwitterAtConstant.IS_READ_TRUE)
				.setParameter("userId", userId).executeUpdate();

	}

}
