package com.alphaking.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.alphaking.model.UserRelation;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
@Repository
public class UserRelationDao extends BaseDaoImpl<UserRelation>{
/**
 * 获取有相似昵称的所有朋友Id
 */
	public List<Integer> getFriendUserIds(Integer userId){
		String sql="select t2.concernedUserId from UserRelation as t1 inner join UserRelation as t2 on"
				+ " t1.concernedUserId = t2.fanId where t1.fanId=t2.concernedUserId and t1.concernedUserId=:userId";
		
		 List<Integer> friends=getSession().createSQLQuery(sql).setParameter("userId", userId).list();
		 return friends;
	}

public void delete(Integer fanId, Integer concernedUserId) {
	String hql="delete from UserRelation where fanId=:fanId and concernedUserId=:concernedUserId";
	getSession().createQuery(hql).setParameter("fanId", fanId).setParameter("concernedUserId", concernedUserId).executeUpdate();
	
}
/**
 * 判断是否互相关注
 */
public boolean isFriend(Integer userId1,Integer userId2){
	String sql="select count(*) from UserRelation where (concernedUserId=:userId1 and fanId=:userId2) or (concernedUserId=:userId2 and fanId=:userId1)";
	BigInteger count=(BigInteger) getSession().createSQLQuery(sql).setParameter("userId1", userId1).setParameter("userId2", userId2).uniqueResult();
	return count.intValue()==2;
}
/**
 * 获取用户的关注用户数量
 */
public Long getConcernedUserAmountOfUser(Integer userId){
	SearchCondition searchCondition=new SearchCondition();
	searchCondition.addCondition("fanId", SearchBean.OPERATOR_EQ,userId,0);
	return count(searchCondition);
}
/**
 * 获取用户的粉丝数量
 */
public Long getFanAmountOfUser(Integer userId){
	SearchCondition searchCondition=new SearchCondition();
	searchCondition.addCondition("concernedUserId", SearchBean.OPERATOR_EQ,userId,0);
	return count(searchCondition);
}
}
