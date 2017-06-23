package com.alphaking.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.alphaking.model.TwitterPicture;
@Repository
public class TwitterPictureDao extends BaseDaoImpl<TwitterPicture>{
/**
 * 通过微博id获取图片列表
 */
public List<TwitterPicture> geTwitterPicturesByTwitterId(Integer twitterId){
	String hql="from TwitterPicture where twitterId=:twitterId order by twitterPictureId";
	return getSession().createQuery(hql).setParameter("twitterId", twitterId).list();
}
/**
 * 通过微博Id删除图片
 */
public void deletePicturesByTwitterId(Integer twitterId){
	String hql="delete from TwitterPicture where twitterId ="+twitterId;
	getSession().createQuery(hql).executeUpdate();
}
}
