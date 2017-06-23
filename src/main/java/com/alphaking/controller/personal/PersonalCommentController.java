package com.alphaking.controller.personal;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.model.Comment;
import com.alphaking.model.CommentAt;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.CommentDto;
import com.alphaking.pojo.weibo.UserDto;
import com.alphaking.service.CommentAtService;
import com.alphaking.service.CommentService;
import com.alphaking.service.CommonService;
import com.alphaking.service.TwitterService;
import com.alphaking.service.UserService;
import com.alphaking.util.DataConvertorUtil;
/**
 * 登录用户评论请求处理器
 * @author lindanpeng
 *
 */
@Controller
@RequestMapping("/personal")
public class PersonalCommentController {
@Resource
private CommentService commentService;
@Resource
private TwitterService twitterService;
@Resource
private CommentAtService commentAtService;
@Resource
private CommonService commonService;
@Resource
private UserService userService;
/**
 * 在个人主页发表评论
 */
@RequestMapping("/comment")
@ResponseBody
public List<CommentDto> comment(String jsonStr){
	Comment comment=DataConvertorUtil.json2object(jsonStr, Comment.class);
	commentService.saveComment(comment);
	SearchCondition searchCondition=new SearchCondition();
	searchCondition.setPageNum(1);
	searchCondition.setSize(5);
	searchCondition.addCondition("commentTime", SearchBean.OPERATOR_SORT, "desc", 0);
	searchCondition.addCondition("twitterId",SearchBean.OPERATOR_EQ,comment.getTwitterId(),0);
	PagerResult<CommentDto> pagerResult=commentService.getCommentListByCondition(searchCondition);	
    return pagerResult.getResultSet();
}
/**
 * 在微博页面发表评论
 */
@RequestMapping("/publishComment")
public String publishComment(String jsonStr,HttpServletRequest request){
  Comment comment=DataConvertorUtil.json2object(jsonStr, Comment.class);
	commentService.saveComment(comment);
	return "redirect:/personal/singleTwitter?twitterId="+comment.getTwitterId();
}
/**
 * 删除评论
 */
@RequestMapping("/deleteComment")
@ResponseBody
public List<CommentDto> deleteComment(Integer commentId,Integer twitterId){
	commentService.deleteComment(commentId);
	SearchCondition searchCondition=new SearchCondition();
	searchCondition.setPageNum(1);
	searchCondition.setSize(5);
	searchCondition.addCondition("commentTime", SearchBean.OPERATOR_SORT, "desc", 0);	
	searchCondition.addCondition("twitterId",SearchBean.OPERATOR_EQ,twitterId,0);
	PagerResult<CommentDto> pagerResult=commentService.getCommentListByCondition(searchCondition);	
    return pagerResult.getResultSet();
}
/**
 * 获取回复我的所有评论列表
 */
@RequestMapping("/commentToMe")
public String commentToMe(Integer pageNum,HttpServletRequest request){
	Integer userId=(Integer) request.getSession().getAttribute("loginedId");
	UserDto userDto=commonService.wragUser(null, userId);
	SearchCondition searchCondition = new SearchCondition();
	if (pageNum==null) {
		pageNum=1;		
	}
	searchCondition.setPageNum(pageNum);
	searchCondition.addCondition("toUserId", SearchBean.OPERATOR_EQ,userId,0);
	searchCondition.addCondition("commentTime", SearchBean.OPERATOR_SORT,"desc",0);
	searchCondition.addCondition("fromUserId", SearchBean.OPERATOR_NE, userId, 0);
	PagerResult<CommentDto> pagerResult=commentService.getCommentDtoWithTwitter(searchCondition);
	commentService.readCommentOfUser(userId);
	request.setAttribute("pagerResult", pagerResult);
	request.setAttribute("userDto", userDto);	
	return "personal/commentToMe";
}
/**
 * 获取与指定微博有关的评论列表
 */
@RequestMapping("/getCommentListOfTwitter")
public String getCommentListOfTwitter(Integer pageNum,Integer twitterId,HttpServletRequest request){
	Integer userId=(Integer) request.getSession().getAttribute("loginedId");
	UserDto userDto=commonService.wragUser(null, userId);
	SearchCondition searchCondition = new SearchCondition();
	if (pageNum==null) {
		pageNum=1;		
	}
	searchCondition.setPageNum(pageNum);
	searchCondition.addCondition("twitterId", SearchBean.OPERATOR_EQ,twitterId,0);
	searchCondition.addCondition("commentTime", SearchBean.OPERATOR_SORT,"desc",0);
	PagerResult<CommentDto> pagerResult=commentService.getCommentListByCondition(searchCondition);
	request.setAttribute("pagerResult", pagerResult);
	request.setAttribute("userDto", userDto);	
	return "personal/commentListOfTwitter";
}
/**
 * 评论里@用户
 */
@RequestMapping("/commentAtUser")
@ResponseBody
public void commentAtUser(CommentAt commentAt){
	commentAtService.saveCommentAtUser(commentAt);
}
/**
 * 获取@我的评论
 */
@RequestMapping("/atMeComments")
@ResponseBody
public String atMeTwitters(Integer pageNum,HttpServletRequest request ){
	Integer userId=(Integer) request.getSession().getAttribute("loginedId");
	if (pageNum==null) {
		pageNum=1;
	}
	SearchCondition searchCondition=new SearchCondition();
	searchCondition.setPageNum(pageNum);
	searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ,userId, 0);
	PagerResult<CommentDto> pagerResult=commentAtService.getCommentAtList(searchCondition);
	request.setAttribute("pagerResult", pagerResult);
	return "personal/atMeComments";
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
/**
 * 已读我的某微博的评论
 */
@RequestMapping("/readCommentOfTwitter")
@ResponseBody
public void readCommentOfTwitter(Integer twitterId,HttpServletRequest request){
	commentService.readCommentOfTwitter(twitterId);
}
/**
 * 我发出的评论
 */
@RequestMapping("/commentFromMe")
public String commentFromMe(Integer pageNum,HttpServletRequest request){
	Integer userId=(Integer) request.getSession().getAttribute("loginedId");
	SearchCondition searchCondition=new SearchCondition();
	if (pageNum==null) {
		pageNum=1;
	}
	searchCondition.setPageNum(pageNum);
	searchCondition.addCondition("fromUserId", SearchBean.OPERATOR_EQ,userId,0);
	searchCondition.addCondition("toUserId", SearchBean.OPERATOR_NE,userId,0);
	searchCondition.addCondition("commentTime", SearchBean.OPERATOR_SORT,"desc",0);
	PagerResult<CommentDto> pagerResult=commentService.getCommentDtoWithTwitter(searchCondition);
	UserDto userDto=commonService.wragUser(userId, userId);
	request.setAttribute("userDto", userDto);
	request.setAttribute("pagerResult", pagerResult);
	return "personal/commentFromMe";
}
}