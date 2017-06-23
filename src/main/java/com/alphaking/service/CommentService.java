package com.alphaking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alphaking.constant.CommentConstant;
import com.alphaking.model.Comment;
import com.alphaking.model.CommentAt;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.CommentDto;
import com.alphaking.pojo.weibo.ParsedText;
import com.alphaking.util.HtmlTextUtil;

@Service
public class CommentService extends ImportDaoService {

	/**
	 * 获取评论列表
	 */
	public PagerResult<CommentDto> getCommentListByCondition(SearchCondition searchCondition) {
		PagerResult<Comment> commentPagerResult = commentDao.list(true, searchCondition);
		PagerResult<CommentDto> commentDtoPagerResult = new PagerResult<>();
		if (commentPagerResult.getResultSet() != null) {
			List<CommentDto> commentDtos = new ArrayList<>();
			for (Comment comment : commentPagerResult.getResultSet()) {
				CommentDto commentDto = new CommentDto();
				commentDto.setComment(comment);
				commentDto.setFromUser(userDao.getById(comment.getFromUserId()));
				commentDto.setToUser(userDao.getById(comment.getToUserId()));
				commentDtos.add(commentDto);
			}
			commentDtoPagerResult.setResultSet(commentDtos);
			PagerResult.changePagerResult(commentDtoPagerResult, commentPagerResult);
		}
		return commentDtoPagerResult;
	}

	/**
	 * 保存评论
	 */
	public void saveComment(Comment comment) {
		ParsedText parsedText=HtmlTextUtil.parseText(comment.getContent());
		comment.setContent(parsedText.getParsedContent());
		commentDao.save(comment);
	}

	/**
	 * 获取新评论数量
	 * 
	 * @param userId
	 * @return
	 */
	public Long countNewComments(Integer userId) {
		SearchCondition searchCondition = new SearchCondition();
		searchCondition.addCondition("toUserId", SearchBean.OPERATOR_EQ, userId, 0);
		searchCondition.addCondition("isRead", SearchBean.OPERATOR_EQ, CommentConstant.IS_READ_FALSE, 0);
		return commentDao.count(searchCondition);
	}

	/**
	 * 删除评论
	 */
	public void deleteComment(Integer commentId) {
		commentAtDao.deleteByCommentId(commentId);
		commentDao.delete(commentId);
	}
	/**
	 * 已阅用户收到的所有评论
	 */
	public void readCommentOfUser(Integer userId){
		commentDao.readCommentOfUser(userId);
	}
	/**
	 * 获取某微博的评论数
	 */
	public Long getCommentAmountOfTwitter(Integer twitterId){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("twitterId", SearchBean.OPERATOR_EQ, twitterId, 0);
		return commentDao.count(searchCondition);
	}
	/**
	 * 已阅用户某的微博的所有评论
	 */
	public void readCommentOfTwitter(Integer twitterId){
		commentDao.readCommentOfTwitter(twitterId);
	}
	/**
	 * 获取带微博内容的回复
	 */
	public PagerResult<CommentDto> getCommentDtoWithTwitter(SearchCondition searchCondition){
		PagerResult<Comment> commentPagerResult = commentDao.list(true, searchCondition);
		PagerResult<CommentDto> commentDtoPagerResult = new PagerResult<>();
		if (commentPagerResult.getResultSet() != null) {
			List<CommentDto> commentDtos = new ArrayList<>();
			for (Comment comment : commentPagerResult.getResultSet()) {
				CommentDto commentDto = new CommentDto();
				commentDto.setComment(comment);
				commentDto.setFromUser(userDao.getById(comment.getFromUserId()));
				commentDto.setToUser(userDao.getById(comment.getToUserId()));
				commentDto.setTwitter(twitterDao.getById(comment.getTwitterId()));
				commentDtos.add(commentDto);
			}
			commentDtoPagerResult.setResultSet(commentDtos);
			PagerResult.changePagerResult(commentDtoPagerResult, commentPagerResult);
		}
		return commentDtoPagerResult;
	}
}
