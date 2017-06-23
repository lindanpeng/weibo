package com.alphaking.controller.personal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.model.UserRelation;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.UserDto;
import com.alphaking.service.CommonService;
import com.alphaking.service.UserRelationService;
import com.alphaking.service.UserService;
/**
 * 登录用户的与用户关系相关的请求处理器
 * @author lindanpeng
 *
 */
@Controller
@RequestMapping("/personal")
public class PersonalUserRelationController {
	@Resource
	private UserRelationService userRelationService;
    @Resource
    private UserService userService;
    @Resource
    private CommonService commonService;
	/**
	 * 关注用户
	 */
	@RequestMapping("/concernUser")
	@ResponseBody
	public void concernUser(Integer concernedUserId,HttpServletRequest request) {	
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
			UserRelation userRelation=new UserRelation();
			userRelation.setFanId(userId);
			userRelation.setConcernedUserId(concernedUserId);
			userRelationService.concernUser(userRelation);
	}
    /**
     * 取消关注
     */
	@RequestMapping("/cancelConcern")
	@ResponseBody
	public void cancelConcern(Integer concernedUserId,HttpServletRequest request){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
			userRelationService.cancelConcern(userId,concernedUserId);
	
	}
	/**
	 * 前往我的粉丝页面
	 */
	@RequestMapping("/myFanList")
	public String getFanList(Integer pageNum, HttpServletRequest request) {
		if (pageNum == null)
			pageNum = 1;
		SearchCondition searchCondition = new SearchCondition();
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(null, userId);
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(15);
		searchCondition.addCondition("concernedUserId", SearchBean.OPERATOR_EQ, userId, 0);
		PagerResult<UserDto> pagerResult = userRelationService.getFans(searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("userDto", userDto);
		return "personal/fanList";
	}
   /**
    * 前往我的关注页面
    */
	@RequestMapping("/myConcernedUserList")
	public String getConcernedUserList(Integer pageNum, HttpServletRequest request) {
		if (pageNum == null)
			pageNum = 1;
		SearchCondition searchCondition = new SearchCondition();
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(null, userId);
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(15);
		searchCondition.addCondition("fanId", SearchBean.OPERATOR_EQ,userId, 0);
		PagerResult<UserDto> pagerResult = userRelationService.getConcernedUsers(searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("userDto", userDto);
		return "personal/concernedUserList";
	}
	/**
	 * 前往某用户的关注用户页面
	 */
	@RequestMapping("/hisConcernedUserList")
	public String hisConcernedUserList(Integer userId,Integer pageNum,HttpServletRequest request){
		Integer myUserId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(null, myUserId); 
		UserDto checkedUser=commonService.wragUser(myUserId, userId);
		SearchCondition searchCondition=new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(15);
		searchCondition.addCondition("fanId", SearchBean.OPERATOR_EQ,userId, 0);
        PagerResult<UserDto> pagerResult=userRelationService.getHisConcernedUsers(myUserId,searchCondition);
		request.setAttribute("checkedUser", checkedUser);
        request.setAttribute("pagerResult", pagerResult);
        request.setAttribute("userDto", userDto);
        return "personal/hisConcernedUserList";
	}
	/**
	 * 前往某用户的粉丝页面
	 */
	@RequestMapping("/hisFanList")
	public String hisFanList(Integer pageNum, Integer userId,HttpServletRequest request) {
		Integer myUserId=(Integer) request.getSession().getAttribute("loginedId");
		if (pageNum == null)
			pageNum = 1;
		SearchCondition searchCondition = new SearchCondition();
		UserDto checkedUser=commonService.wragUser(myUserId, userId);
		UserDto userDto=commonService.wragUser(null, myUserId);
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(15);
		searchCondition.addCondition("concernedUserId", SearchBean.OPERATOR_EQ, userId, 0);
		PagerResult<UserDto> pagerResult = userRelationService.getHisFans(myUserId,searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("checkedUser", checkedUser);
		request.setAttribute("userDto", userDto);	
		return "personal/hisFanList";
	}
	/**
	 * 判断是否为好友
	 */
	@RequestMapping("/isFriend")
	@ResponseBody
	public boolean isFriend(Integer userId,HttpServletRequest request){
		Integer myUserId=(Integer) request.getSession().getAttribute("loginedId");
		return userRelationService.isFriend(myUserId,userId);
	}
}
