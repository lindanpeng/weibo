package com.alphaking.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.alphaking.model.Twitter;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
@Repository
public class TwitterDao extends BaseDaoImpl<Twitter>{
/**
 * 获取关注人微博
 */
	public PagerResult<Twitter> getConcernedUserTwitters(Integer userId,Integer pageNum,Integer size){
		//TODO 排序报错
		String sql="select t.twitterId,t.publicTime ,t.textContent,t.twitterVideoUrl,t.userId,t.userNickName from UserRelation as u inner join Twitter as t on u.concernedUserId =t.userId  where u.fanId=:userId "+
	                " order by t.publicTime desc limit "+(pageNum-1)*size+","+size;
		String sql2="select count(*) from UserRelation as u inner join Twitter as t on u.concernedUserId =t.userId where u.fanId=:userId ";
		
		BigInteger bigInteger=(BigInteger) (getSession().createSQLQuery(sql2).setParameter("userId", userId).uniqueResult());
		Long count=bigInteger.longValue();
		List<Twitter> twitters=getSession().createSQLQuery(sql).addEntity(Twitter.class).setParameter("userId",userId).list();
	    return PagerResult.getPagerResultByData(count, pageNum, size, twitters);
	}
/**
 * 获取用户的微博数量
 */
	public Long getTwitterAmountOfUser(Integer userId){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ,userId,0);
		return count(searchCondition);
	}
}
