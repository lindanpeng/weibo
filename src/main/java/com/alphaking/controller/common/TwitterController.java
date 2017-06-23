package com.alphaking.controller.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.CommentDto;
import com.alphaking.pojo.weibo.TwitterDto;
import com.alphaking.pojo.weibo.UserDto;
import com.alphaking.service.CommentService;
import com.alphaking.service.CommonService;
import com.alphaking.service.TwitterService;
/**
 * 游客的与微博相关的请求处理器
 * @author lindanpeng
 *
 */
@RequestMapping("/common")
@Controller
public class TwitterController {
	@Resource
	private TwitterService twitterService;
	   @Resource
	    private CommonService commonService;
	   @Resource
	   private CommentService commentService;
	/**
   * 搜索微博
   */
	@RequestMapping("/searchTwitter")
	public String searchTwitter(String searchKey,Integer pageNum,HttpServletRequest request){
		if (pageNum==null) {
			pageNum=1;
		}
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.setPageNum(pageNum);
		searchCondition.addCondition("textContent", SearchBean.OPERATOR_LIKE,searchKey,0);
		searchCondition.addCondition("publicTime", SearchBean.OPERATOR_SORT, "desc",0);
		PagerResult<TwitterDto> pagerResult=twitterService.getTwittersByCondition(null,searchCondition); 
	    request.setAttribute("pagerResult", pagerResult);
	    request.setAttribute("searchKey", searchKey);
		return "common/searchTwitter";
	}
	/**
	 * 进入首页
	 */
	@RequestMapping("/index")
	public String index(Integer pageNum,HttpServletRequest request){
		SearchCondition searchCondition = new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		searchCondition.setPageNum(pageNum);
		searchCondition.addCondition("publicTime",SearchBean.OPERATOR_SORT,"desc",0);
		PagerResult<TwitterDto> pagerResult = twitterService.getTwittersByCondition(null,searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		return "common/index";
	}
	/**
	 * 获取用户发布的微博
	 */
	@RequestMapping("/hisTwitterList")
	public String getHisTwitterList(Integer userId,Integer pageNum,HttpServletRequest request){

		UserDto checkedUser=commonService.wragUser(null, userId);
		SearchCondition searchCondition=new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(10);
		searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ, userId,0);
		searchCondition.addCondition("publicTime",SearchBean.OPERATOR_SORT,"desc", 0);
		PagerResult<TwitterDto> pagerResult=twitterService.getTwittersByCondition(userId, searchCondition);
		request.setAttribute("pagerResult", pagerResult);
        request.setAttribute("checkedUser", checkedUser);
		return "common/hisTwitterList";
	}
	/**
	 * 查看单个微博
	 */
	@RequestMapping("/singleTwitter")
	public String singleTwitter(Integer twitterId,Integer pageNum,HttpServletRequest request){

		TwitterDto twitterDto=twitterService.getTwitterById(null, twitterId);
		SearchCondition searchCondition=new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(10);
		searchCondition.addCondition("twitterId", SearchBean.OPERATOR_EQ, twitterId,0);
		searchCondition.addCondition("commentTime", SearchBean.OPERATOR_SORT,"desc", 0);
		PagerResult<CommentDto> pagerResult=commentService.getCommentListByCondition(searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("twitterDto", twitterDto);
		return "common/singleTwitter";
	}
}
