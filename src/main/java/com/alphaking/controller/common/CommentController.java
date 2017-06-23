package com.alphaking.controller.common;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.CommentDto;
import com.alphaking.pojo.weibo.TwitterDto;
import com.alphaking.service.CommentService;
import com.alphaking.service.TwitterService;
/**
 * 游客的与评论相关的请求处理器
 * @author lindanpeng
 *
 */
@Controller
@RequestMapping("/common")
public class CommentController {
@Resource
private CommentService commentService;
@Resource
private TwitterService twitterService;
/**
 * 获取更多评论
 */
@RequestMapping("/moveComments")
public String moveComments(Integer twitterId,HttpServletRequest request){
	TwitterDto twitterDto=twitterService.getTwitterById(null,twitterId);
	request.setAttribute("twitterDto", twitterDto);
	return "common/singleTwitter";
}
/**
 * 获取部分评论
 */
@RequestMapping("/getSomeComments")
@ResponseBody
public List<CommentDto> getSomeComments(Integer twitterId){
	SearchCondition searchCondition=new SearchCondition();
	searchCondition.setPageNum(1);
	searchCondition.setSize(5);
	searchCondition.addCondition("commentTime", SearchBean.OPERATOR_SORT, "desc", 0);	
	searchCondition.addCondition("twitterId",SearchBean.OPERATOR_EQ,twitterId,0);
	PagerResult<CommentDto> pagerResult=commentService.getCommentListByCondition(searchCondition);	
    return pagerResult.getResultSet();
} 
/**
 * 获取微博的评论数
 */
@RequestMapping("/getCommentAmountOfTwitter")
@ResponseBody
public Long getCommentAmountOfTwitter(Integer twitterId){
	return commentService.getCommentAmountOfTwitter(twitterId);
}
}
