package com.alphaking.dao;

import org.springframework.stereotype.Repository;

import com.alphaking.model.User;
@Repository
public class UserDao extends BaseDaoImpl<User>{
	/**
	 * 通过用户账号获取用户
	 * @param userOpenId
	 * @return
	 */
 public User getByUserOpenId(String userOpenId){
	 String hql="from User where userOpenId =:userOpenId";	 
	 User user=(User)getSession().createQuery(hql).setParameter("userOpenId", userOpenId).uniqueResult();
	 return user;
 }
 /**
  *通过昵称获取用户
  */
 public User getByNickname(String nickname){
	 String hql="from User where nickname = '"+nickname+"'";
	 return (User) getSession().createQuery(hql).uniqueResult();
 }
}
