package com.alphaking.dao;

import org.springframework.stereotype.Repository;

import com.alphaking.model.CommentAt;
@Repository
public class CommentAtDao extends BaseDaoImpl<CommentAt>{
/**
 * 通过评论Id删除评论@我的
 * @param commentId
 */
	public void deleteByCommentId(Integer commentId) {
		String hql="delete from Comment where commentId =:commentId";
		getSession().createQuery(hql).setParameter("commentId", commentId).executeUpdate();
		
	}

public void deleteByTwitterId(Integer twitterId) {
	String hql="delete from Comment where twitterId =:twitterId";
	getSession().createQuery(hql).setParameter("twitterId", twitterId).executeUpdate();
	
}

}
