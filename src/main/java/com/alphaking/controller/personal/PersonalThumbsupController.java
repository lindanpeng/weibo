package com.alphaking.controller.personal;

import java.sql.Timestamp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.model.Thumbsup;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.ThumbsupDto;
import com.alphaking.pojo.weibo.UserDto;
import com.alphaking.service.CommonService;
import com.alphaking.service.ThumbsupService;
/**
 * 登录用户点赞请求处理器
 * @author lindanpeng
 *
 */
@Controller
@RequestMapping("/personal")
public class PersonalThumbsupController {
@Resource
private ThumbsupService thumbsupService;
@Resource
private CommonService commonService;
/**
 * 点赞
 */
	@RequestMapping("/thumbsup")
	@ResponseBody
	public void thumbsup(Integer  twitterId,Integer thumbsupedUserId,HttpSession session){
		Integer userId=(Integer) session.getAttribute("loginedId");
		Thumbsup thumbsup=new Thumbsup();
		thumbsup.setTwitterId(twitterId);
		thumbsup.setThumbsupTime(new Timestamp(System.currentTimeMillis()));
		thumbsup.setUserId(userId);
		thumbsup.setThumbsupedUserId(thumbsupedUserId);
		thumbsupService.saveThumbsup(thumbsup);
	}
/**
 * 取消赞
 */
	@RequestMapping("/cancelThumbsup")
	@ResponseBody
	public void cancelThumbsup(Integer twitterId,HttpSession session){	
		Integer userId=(Integer) session.getAttribute("loginedId");
		thumbsupService.cancelThumbsup(userId, twitterId);
	
	}
/**
 * 获取我收到的赞
 */
@RequestMapping("/thumbsupList")
public String thumbsupList(Integer pageNum,HttpServletRequest request){
	SearchCondition searchCondition=new SearchCondition();
	Integer userId=(Integer) request.getSession().getAttribute("loginedId");
	UserDto userDto=commonService.wragUser(null, userId);
	if (pageNum==null) {
		pageNum=1;
	}
	searchCondition.setPageNum(pageNum);
	searchCondition.addCondition("thumbsupedUserId",SearchBean.OPERATOR_EQ,userId,0);
	searchCondition.addCondition("thumbsupTime", SearchBean.OPERATOR_SORT, "desc", 0);
	PagerResult<ThumbsupDto> pagerResult=thumbsupService.getThumbsupListByCondition(searchCondition);
	thumbsupService.readThumbsupOfUser(userId);
	request.setAttribute("pagerResult", pagerResult);
	request.setAttribute("userDto", userDto);
	return "personal/thumbsup";
}
/**
 * 获取某个微博的点赞数
 */
@RequestMapping("/getThumbsupAmountOfTwitter")
@ResponseBody
public Long getThumbsupAmountOfTwitter(Integer twitterId){
	return thumbsupService.getThumbsupAmountOfTwitter(twitterId);
}
}
