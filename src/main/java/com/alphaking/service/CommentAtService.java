package com.alphaking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alphaking.constant.CommentAtConstant;
import com.alphaking.model.Comment;
import com.alphaking.model.CommentAt;
import com.alphaking.model.User;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.CommentDto;

@Service
public class CommentAtService extends ImportDaoService{
/**
 * 保存@我的
 */
	public void saveCommentAtUser(CommentAt commentAt){
		commentAtDao.save(commentAt);
	}
/**
 * 获取新@我的评论的数量
 */
	public Long getNewCommentAts(Integer userId){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("isRead", SearchBean.OPERATOR_EQ,CommentAtConstant.IS_READ_FALSE,0);
		searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ,userId,0);
		return commentAtDao.count(searchCondition);
	}
/**
 * 获取@我的评论列表
 */
  public PagerResult<CommentDto> getCommentAtList(SearchCondition searchCondition){
	  PagerResult<CommentAt> commentAtPagerResult=commentAtDao.list(true, searchCondition);
	  PagerResult<CommentDto> commentDtoPagerResult=new PagerResult<>();
	  if (commentAtPagerResult.getResultSet()!=null) {
		  List<CommentDto> commentDtos=new ArrayList<>();
		for(CommentAt commentAt:commentAtPagerResult.getResultSet()){
			CommentDto commentDto=new CommentDto();
			Comment comment=commentDao.getById(commentAt.getCommentId());
			User user=userDao.getById(comment.getFromUserId());
			commentDto.setComment(comment);
			commentDto.setFromUser(user);
			commentDtos.add(commentDto);
		}
		commentDtoPagerResult.setResultSet(commentDtos);
		PagerResult.changePagerResult(commentDtoPagerResult, commentAtPagerResult);
	}
	  return commentDtoPagerResult;
  }
  
}
