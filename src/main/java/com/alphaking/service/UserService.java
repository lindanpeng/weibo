package com.alphaking.service;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alphaking.model.User;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.LoginUser;
import com.alphaking.pojo.weibo.UserDto;
@Service
public class UserService extends ImportDaoService {
@Resource
private CommonService  commonService;
/**
 * 添加用户
 * @param user
 */
public void addUser(User user){
   userDao.save(user);
}
/**
 * 验证用户账号密码
 */
public boolean validateUser(LoginUser loginUser){
    User user=userDao.getByUserOpenId(loginUser.getAccountId());
    if(user!=null&&user.getUserOpenId().equals(loginUser.getAccountId()))
    	return true;
	return false;
}
/**
 * 判断用户ID是否存在
 */
public boolean existsSameId(String userOpenId){
	User user=userDao.getByUserOpenId(userOpenId);
	if(user!=null)
		return true;
	return false;
			
}
/**
 * 判断用户昵称是否存在
 */
public boolean existsSameName(String nickname){
	User user=userDao.getByNickname(nickname);
	if(user!=null)
		return true;
	return false;
}
/**
 * 通过昵称获取用户数据
 */
public User getByNickname(String nickname){
	SearchCondition searchCondition=new SearchCondition();
	searchCondition.addCondition("nickname", SearchBean.OPERATOR_EQ,nickname,0);
	return userDao.getUniqueResult(searchCondition);
}
/**
 * 通过账号获取用户数据
 */
public User getByUserOpenId(String userOpenId){
	SearchCondition searchCondition=new SearchCondition();
	searchCondition.addCondition("userOpenId", SearchBean.OPERATOR_EQ,userOpenId,0);
	return userDao.getUniqueResult(searchCondition);
}
/**
 * 通过id获取用户数据
 */
public User getById(Integer userId){
	return userDao.getById(userId);
}
/**
 * 通过条件获取用户
 */
public PagerResult<UserDto> getUsersByCondition(Integer myUserId,SearchCondition searchCondition){
	PagerResult<User> pagerResult=userDao.list(true, searchCondition);
	PagerResult<UserDto> pagerResult2=new PagerResult<>();
	if (pagerResult.getResultSet()!=null) {
		List<UserDto> userDtos=new ArrayList<>(pagerResult.getResultSet().size());
		for(User user:pagerResult.getResultSet()){
			UserDto userDto=commonService.wragUser(myUserId, user.getUserId());
			userDtos.add(userDto);
		}
		pagerResult2.setResultSet(userDtos);
		PagerResult.changePagerResult(pagerResult2, pagerResult);
	}
	return pagerResult2;
}
public void amendPersonalInfo(User user) {
	userDao.update(user);
	
}
}


