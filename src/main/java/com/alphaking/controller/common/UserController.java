package com.alphaking.controller.common;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alphaking.constant.CommonConstant;
import com.alphaking.constant.HintConstant;
import com.alphaking.constant.SecurityConstant;
import com.alphaking.model.User;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.ReturnResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.LoginUser;
import com.alphaking.pojo.weibo.UserDto;
import com.alphaking.service.CommonService;
import com.alphaking.service.UserRelationService;
import com.alphaking.service.UserService;
import com.alphaking.util.DataConvertorUtil;
import com.alphaking.util.EmailUtil;
import com.alphaking.util.MD5Util;
/**
 * 游客的与用户相关的请求处理器
 * @author lindanpeng
 *
 */
@Controller
@RequestMapping("/common")
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private UserRelationService userRelationService;
    @Resource
    private CommonService commonService;
	/**
	 * 同步验证用户
	 * @param loginUser
	 * @return
	 */
	@RequestMapping("/validateUser")
	public String validateUser(HttpServletRequest request, HttpServletResponse response,LoginUser loginUser) {
		HttpSession session=request.getSession();
		 ReturnResult<Object> returnResult=new ReturnResult<>();
		int wrongLoginTimes=0;
		if (session.getAttribute("wrongLoginTimes")!=null) {
			wrongLoginTimes= (Integer) session.getAttribute("wrongLoginTimes");
		}	 
		session.setAttribute("loginTimes", wrongLoginTimes + 1);
		
		if (wrongLoginTimes > SecurityConstant.WRONG_PASSWORD_TIMES) {
			String code = (String) session.getAttribute("verificationCode");
			if (!code.equals(loginUser.getCode()))
				{

				 returnResult.setRetMsg(HintConstant.WRONG_CODE);
				 request.setAttribute("returnResult", returnResult);
				  return "common/login";
				}
		}
		  User user=userService.getByUserOpenId(loginUser.getAccountId());
		if(user!=null&&!user.isActivationStatus()){
			session.setAttribute("wrongLoginTimes",++wrongLoginTimes);
			 returnResult.setRetMsg(HintConstant.WRONG_ACTIVATION_CODE);
			 request.setAttribute("returnResult", returnResult);
		}
		else if (userService.validateUser(loginUser))
			{

			   session.setAttribute("loginedId", user.getUserId());
			   return "redirect:/personal/home";
			}
		else{
			session.setAttribute("wrongLoginTimes",++wrongLoginTimes);
			 returnResult.setRetMsg(HintConstant.WRONG_PASSWORD);
			 request.setAttribute("returnResult", returnResult);
		}
		return "common/login";
	}
 /**
  * 异步验证用户
  */
@RequestMapping("/asyValidateUser")
@ResponseBody
public ReturnResult<Object> asyValidateUser(String jsonStr,HttpServletRequest request){
	LoginUser loginUser=DataConvertorUtil.json2object(jsonStr, LoginUser.class);
	ReturnResult<Object> returnResult=new ReturnResult<>();
	String verificationCode=(String) request.getSession().getAttribute("verificationCode");
	if (!loginUser.getCode().equals(verificationCode)) {
		returnResult.setCode(404);
		returnResult.setRetMsg(HintConstant.WRONG_CODE);
	}
	else if(userService.validateUser(loginUser)){
		returnResult.setCode(500);
		User user=userService.getByUserOpenId(loginUser.getAccountId());
		request.getSession().setAttribute("loginedId", user.getUserId());
	}
	else{
		returnResult.setCode(404);
		returnResult.setRetMsg(HintConstant.WRONG_PASSWORD);
	}
	return returnResult;
}
	/**
	 * 异步判断用户是否存在
	 */
	@ResponseBody
	@RequestMapping("/isExistent")
	public ReturnResult<Object> isExistent(String userOpenId){
		ReturnResult<Object> returnResult=new ReturnResult<>();
		if(userService.existsSameId(userOpenId))
			{
			  returnResult.setCode(404);
			  returnResult.setRetMsg(HintConstant.EXIST_SAME_ID);
			}
		else{
			returnResult.setCode(500);
			returnResult.setRetMsg(HintConstant.APPROVED_ID);
		}
		return returnResult;
	}
	/**
	 * 注册用户
	 */
	@RequestMapping("/registerUser")
	public String registerUser(User user,String code,HttpServletRequest request,HttpServletResponse response){
		String verificationCode=(String) request.getSession().getAttribute("verificationCode");
		 if(!code.equals(verificationCode)){
			 ReturnResult<Object> returnResult=new ReturnResult<>();
				returnResult.setCode(404);
				returnResult.setRetMsg(HintConstant.WRONG_CODE);
				request.setAttribute("returnResult",returnResult);
				return "common/register";
			}
		else if(userService.existsSameId(user.getUserOpenId())){
			ReturnResult<Object> returnResult=new ReturnResult<>();
			returnResult.setCode(404);
			returnResult.setRetMsg(HintConstant.EXIST_SAME_ID);
			request.setAttribute("registerInfo", user);
			request.setAttribute("returnResult",returnResult);
		    return "common/register";
		}
		else if (userService.existsSameName(user.getNickname())) {
			ReturnResult<Object> returnResult=new ReturnResult<>();
			returnResult.setCode(404);
			returnResult.setRetMsg(HintConstant.EXIST_SAME_NAME);
			request.setAttribute("registerInfo", user);
			request.setAttribute("returnResult",returnResult);
		    return "common/register";
		}
		else
	        {
			user.setPortraitUrl(CommonConstant.DEFAULT_PORTRAIT_URL);
			user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
			user.setActivationCode(MD5Util.encode2hex(user.getUserOpenId()));
			user.setEmail(user.getUserOpenId());
		 	user.setActivationStatus(true);
			try {
				EmailUtil.sendMail(user);
			} catch (Exception e) {
				
				return "error";
			}
			userService.addUser(user);
			return "common/waitActivation";
	        }
	}
	/**
	 * 查看用户信息
	 */
	@RequestMapping("/checkUserInformation")
	public String checkUserInformation(Integer userId,HttpServletRequest request){
	    UserDto checkedUser=commonService.wragUser(null, userId);
		request.setAttribute("checkedUser", checkedUser);
		return "common/hisInformation";
	}
	/**
	 * 查看用户信息
	 */
	@RequestMapping("/checkUserInformationByNickname")
	public String checkUserInformationByNickname(String nickname,HttpServletRequest request){

		User user=userService.getByNickname(nickname);
		UserDto checkedUser=commonService.wragUser(null, user.getUserId());
		request.setAttribute("checkedUser", checkedUser);
		return "common/hisInformation";
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
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("nickname",SearchBean.OPERATOR_LIKE, searchKey,0);
		if(pageNum==null)
			pageNum=1;
		searchCondition.setPageNum(pageNum);
		PagerResult<UserDto> pagerResult=userService.getUsersByCondition(null,searchCondition);
		request.setAttribute("pagerResult", pagerResult);
		request.setAttribute("searchKey", searchKey);
		return "common/searchUser";
	}
	/**
	 * 完成激活
	 */
	@RequestMapping("/finishActivation")
	public String finishActivation(String activationCode,String email){
		User user=userService.getByUserOpenId(email);
		if(activationCode.equals(user.getActivationCode())){
			user.setActivationStatus(true);
			userService.amendPersonalInfo(user);
			return "common/successful";
		}
		else{
			return "common/wrongActivationCode";
		}
		
	}
}
