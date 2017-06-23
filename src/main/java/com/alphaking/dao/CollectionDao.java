package com.alphaking.dao;

import org.springframework.stereotype.Repository;

import com.alphaking.model.Collection;
@Repository
public class CollectionDao extends BaseDaoImpl<Collection> {
/**
 * 通过用户id和微博id删除收藏
 */
	public void deleteCollectionByUserIdByTwitterId(Integer userId,Integer twitterId){
		String hql="delete from Collection where userId=:userId and collectedTwitterId=:twitterId";
		getSession().createQuery(hql).setParameter("userId", userId).setParameter("twitterId", twitterId).executeUpdate();
	}
/**
 * 通过用户id和微博id获取收藏
 */
	public Collection getByUserIdAndTwitterId(Integer userId,Integer collectedTwitterId){
		String hql="from Collection where userId =:userId and collectedTwitterId=:twitterId";
		return (Collection) getSession().createQuery(hql).setParameter("userId", userId).setParameter("twitterId", collectedTwitterId).uniqueResult();
	}
}
