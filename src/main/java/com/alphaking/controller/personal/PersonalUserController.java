package com.alphaking.controller.personal;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.model.SearchRecord;
import com.alphaking.model.User;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.ReturnResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.UserDto;
import com.alphaking.service.CommonService;
import com.alphaking.service.SearchRecodService;
import com.alphaking.service.UserRelationService;
import com.alphaking.service.UserService;
/**
 * 登录用户的与用户功能相关的请求处理器
 * @author lindanpeng
 *
 */
@RequestMapping("/personal")
@Controller
public class PersonalUserController {

	@Resource
	private UserService userService;
	@Resource
	private UserRelationService userRelationService;
	@Resource
	private CommonService commonService;
	@Resource
	private SearchRecodService searchRecodService;
	/**
	 * 注销
	 */
	@RequestMapping("/logout")
	public String Logout(HttpSession session){
		session.removeAttribute("loginedId");
		return "common/logout";
	}
	/**
	 * 修改个人资料
	 */
	@RequestMapping("/savePersonalInfo")
	public String savePersonalInfo(String introduction,String email,String birthday,Integer gender,HttpServletRequest request){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(null, userId);
		User user=userService.getById(userId);
		user.setBirthday(Timestamp.valueOf(birthday+" 00:00:00"));
		user.setEmail(email);
		user.setGender(gender);
		user.setIntroduction(introduction);
		userService.amendPersonalInfo(user);
		request.setAttribute("userDto", userDto);
		return "personal/toPersonalInformation";
	}
	/**
	 * 上传头像
	 */
	@RequestMapping("/uploadPortrait")
	@ResponseBody
	public ReturnResult<Object> uploadPortrait(String imgStr,HttpServletRequest request){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
		User user=userService.getById(userId);
		user.setPortraitUrl(imgStr);
		userService.amendPersonalInfo(user);
	   ReturnResult<Object> returnResult=new ReturnResult<>();
	   returnResult.setCode(500);
	   return returnResult;
	}
	/**
	 * 查看用户信息
	 */
	@RequestMapping("/checkUserInformation")
	public String checkUserInformation(Integer userId,HttpServletRequest request){
		Integer myUserId=(Integer) request.getSession().getAttribute("loginedId");
		if(myUserId.equals(userId))
			{
						
			UserDto userDto=commonService.wragUser(null, myUserId);
			request.setAttribute("userDto", userDto);
			return "personal/personalInformation";
			
			}
		UserDto checkedUser=commonService.wragUser(myUserId, userId);
		UserDto userDto=commonService.wragUser(null, myUserId);
		request.setAttribute("checkedUser", checkedUser);
		request.setAttribute("userDto", userDto);
		return "personal/hisInformation";
	}
	
	/**
	 * 查看用户信息
	 */
	@RequestMapping("/checkUserInformationByNickname")
	public String checkUserInformationByNickname(String nickname,HttpServletRequest request){
		Integer myUserId=(Integer) request.getSession().getAttribute("loginedId");
		User user=userService.getByNickname(nickname);
		if(myUserId.equals(user.getUserId()))
			{
						
			UserDto userDto=commonService.wragUser(null, myUserId);
			request.setAttribute("userDto", userDto);
			return "personal/personalInformation";
			
			}
		UserDto checkedUser=commonService.wragUser(myUserId, user.getUserId());
		UserDto userDto=commonService.wragUser(null, myUserId);
		request.setAttribute("checkedUser", checkedUser);
		request.setAttribute("userDto", userDto);
		return "personal/hisInformation";
	}
	/**
	 * 异步搜索用户,返回前5个匹配用户
	 */
	@RequestMapping("/asySearchUsers")
	@ResponseBody
	public List<User> asySearchUsers(String nickname){
		return null;
	}
	/**
	 * 搜索用户
	 */
	@RequestMapping("/searchUser")
	public String searchUser(String searchKey,Integer pageNum,HttpServletRequest request){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(null, userId);
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("nickname",SearchBean.OPERATOR_LIKE, searchKey,0);
		if(pageNum==null)
			pageNum=1;
		searchCondition.setPageNum(pageNum);
		if (searchKey!=null&&!searchKey.equals("")) {
			SearchRecord searchRecord=new SearchRecord();
			searchRecord.setSearchKey(searchKey);
			searchRecord.setUserId(userId);
			searchRecodService.saveSearchRecord(searchRecord);
		}
	
		PagerResult<UserDto> pagerResult=userService.getUsersByCondition(userId, searchCondition);
		SearchCondition searchCondition2=new SearchCondition();
		searchCondition2.addCondition("userId", SearchBean.OPERATOR_EQ, userId, 0);
		List<SearchRecord> searchRecords=searchRecodService.getByCondition(searchCondition2);
		request.setAttribute("pagerResult", pagerResult);	
		request.setAttribute("userDto", userDto);
		request.setAttribute("searchKey", searchKey);
		request.setAttribute("searchRecords", searchRecords);
		return "personal/searchUser";
	}
	/**
	 * 搜索朋友
	 */
    @RequestMapping("/searchFriend")
    @ResponseBody
    public List<User> searchFriend(String nickname,HttpServletRequest request){
		Integer userId=(Integer) request.getSession().getAttribute("loginedId");
    	List<User> friends=userRelationService.getFriends(userId, nickname);
    	return friends;
    }
    /**
	 * 前往个人信息中心
	 */
		@RequestMapping("/personalInformation")
		public String personalInformation(HttpServletRequest request){
			Integer myUserId=(Integer) request.getSession().getAttribute("loginedId");
			UserDto userDto=commonService.wragUser(null, myUserId);
			request.setAttribute("userDto", userDto);
			 return "personal/personalInformation";
		}
 /**
 * 前往编辑个人资料页面
 */
	@RequestMapping("/toAmendPersonalInfo")
	public String toAmendPersonalInfo(HttpServletRequest request){
		Integer myUserId=(Integer) request.getSession().getAttribute("loginedId");
		UserDto userDto=commonService.wragUser(null, myUserId);
		request.setAttribute("userDto", userDto);
		return "personal/amendPersonalInfo";
	}
	}
