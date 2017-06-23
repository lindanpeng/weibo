package com.alphaking.controller.common;

import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.ClearCommand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.model.User;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.DataWrapper;
import com.alphaking.pojo.weibo.TwitterDto;
import com.alphaking.pojo.weibo.VerificationCode;
import com.alphaking.service.CommentAtService;
import com.alphaking.service.CommentService;
import com.alphaking.service.MessageService;
import com.alphaking.service.SearchRecodService;
import com.alphaking.service.ThumbsupService;
import com.alphaking.service.TwitterAtService;
import com.alphaking.service.TwitterService;
import com.alphaking.service.UserRelationService;
import com.alphaking.service.UserService;
import com.alphaking.util.QiNiuyunUtil;
import com.alphaking.util.VerificationCodeUtil;

/**
 * 公用处理器，负责处理转发、数据更新等等
 * @author lindanpeng
 *
 */
@Controller
public class CommonController {
@Resource
private UserRelationService userRelationService;
@Resource
private MessageService messageService;
@Resource
private CommentService commentService;
@Resource
private TwitterService twitterService;
@Resource
private UserService userService;
@Resource
private CommentAtService commentAtService;
@Resource
private TwitterAtService twitterAtService;
@Resource
private SearchRecodService searchRecodService;
@Resource
private ThumbsupService thumbsupService;
	/**
	 * 检查是否有私信、评论、@我的
	 * 
	 * @throws InterruptedException
	 */
	@RequestMapping("/personal/refreshData")
	@ResponseBody
	public DataWrapper refleshData(HttpServletRequest request) throws InterruptedException {
		Integer userId =  (Integer) request.getSession().getAttribute("loginedId");
		DataWrapper dataWrapper = new DataWrapper();
		Long	unReadMessageAmount = messageService.countNewMessages(userId);
		Long	unReadCommentAmount = commentService.countNewComments(userId);
		Long	unReadCommentAtAmount=commentAtService.getNewCommentAts(userId);
		Long	unReadTwitterAtAmount=twitterAtService.getNewTwitterAts(userId);
		Long    unReadThumbsupAmount=thumbsupService.getUnReadThumbsupAmount(userId);
		Long	unReadAtmeAmount=unReadCommentAtAmount+unReadTwitterAtAmount;
		//System.out.println(unReadThumbsupAmount);
		dataWrapper.setUnReadCommentAmount(unReadCommentAmount);
		dataWrapper.setUnReadMessageAmount(unReadMessageAmount);
		dataWrapper.setUnReadAtmeAmount(unReadAtmeAmount);	
		dataWrapper.setUnReadThumbsupAmount(unReadThumbsupAmount);
		dataWrapper.setTotalAmount(unReadAtmeAmount+unReadCommentAmount+unReadMessageAmount+unReadThumbsupAmount);
		return dataWrapper;
}
	/**
	 * 获取验证码
	 */
	@RequestMapping("/common/getVerificationCode")
	public void getVerificationCode(HttpSession session,HttpServletResponse response){
		VerificationCode code=VerificationCodeUtil.getCode(80,35);
		session.setAttribute("verificationCode",code.getCodeText());
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 
		try {
			ImageIO.write(code.getCodeImage(),"JPEG",response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 前往注册页面
	 * @return
	 */
	@RequestMapping("/common/register")
	public String register(){
		return "common/register";
	}

	/**
	 * 前往登录页面
	 */
	@RequestMapping("/common/login")
	public String login(HttpServletRequest request) {
		return "common/login";

	}
	
	/**
	 * 前往登录提醒页面
	 */
	@RequestMapping("/common/toLogin")
	public String toLogin(){
		return "common/toLogin";
	}



/**
 * 获取上传令牌
 */
	@RequestMapping("/common/getUpToken")
	@ResponseBody
	public String getUpToken(){
		return QiNiuyunUtil.getUpToken();
	}
/**
 * 前往上传头像页面
 */
	@RequestMapping("/personal/toUploadPortrait")
	public String toUploadPortrait(){
		return "personal/uploadPortrait";
	}
/**
 * 搜索
 */
	@RequestMapping("/personal/search")
	public String search(Integer searchType,String searchKey){
		if (searchType.equals(0)) {
			return "redirect:/personal/searchUser?searchKey="+searchKey;
		}
		return "redirect:/personal/searchTwitter?searchKey="+searchKey;
	}
	/**
	 * 搜索
	 */
		@RequestMapping("/common/search")
		public String commonSearch(Integer searchType,String searchKey){
			if (searchType.equals(0)) {
				return "redirect:/common/searchUser?searchKey="+searchKey;
			}
			return "redirect:/common/searchTwitter?searchKey="+searchKey;
		}
/**
 * 清空搜索记录
 */
	@RequestMapping("/personal/clearSearchRecord")
	@ResponseBody
	public void clearSearchRecord(HttpServletRequest request){
		Integer userId =  (Integer) request.getSession().getAttribute("loginedId");
		searchRecodService.deleteByUserId(userId);
	}
/**
 * test
 */
	@RequestMapping("common/test")
	@ResponseBody
	public void  test(){
         System.out.println("test");
	}
}
