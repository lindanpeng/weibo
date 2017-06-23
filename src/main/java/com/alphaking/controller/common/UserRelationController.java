package com.alphaking.controller.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alphaking.model.User;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.UserDto;
import com.alphaking.service.CommonService;
import com.alphaking.service.UserRelationService;
import com.alphaking.service.UserService;
/**
 * 游客的与用户关系相关的请求处理器
 * @author lindanpeng
 *
 */
@Controller
@RequestMapping("/common")
public class UserRelationController {
	@Resource
	private UserRelationService userRelationService;
    @Resource
    private UserService userService;
    @Resource
    private CommonService commonService;
	/**
	 * 前往某用户的关注用户页面
	 */
	@RequestMapping("/hisConcernedUserList")
	public String hisConcernedUserList(Integer userId,Integer pageNum,HttpServletRequest request){
		UserDto checkedUser=commonService.wragUser(null, userId);
		SearchCondition searchCondition=new SearchCondition();
		if (pageNum==null) {
			pageNum=1;
		}
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(15);
		searchCondition.addCondition("fanId", SearchBean.OPERATOR_EQ, userId, 0);
        PagerResult<UserDto> pagerResult=userRelationService.getHisConcernedUsers(null,searchCondition);
		request.setAttribute("checkedUser", checkedUser);
        request.setAttribute("pagerResult", pagerResult);
        return "common/hisConcernedUserList";
	}
	/**
	 * 前往某用户的粉丝页面
	 */
	@RequestMapping("/hisFanList")
	public String hisFanList(Integer pageNum, Integer userId,HttpServletRequest request) {
		UserDto checkedUser=commonService.wragUser(null, userId);
		if (pageNum == null)
			pageNum = 1;
		SearchCondition searchCondition = new SearchCondition();
		searchCondition.setPageNum(pageNum);
		searchCondition.setSize(15);
		searchCondition.addCondition("concernedUserId", SearchBean.OPERATOR_EQ, userId, 0);
		PagerResult<UserDto> pagerResult = userRelationService.getHisFans(null,searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("checkedUser", checkedUser);
		return "common/hisFanList";
	}
}
