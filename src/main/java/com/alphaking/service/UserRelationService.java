package com.alphaking.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alphaking.model.User;
import com.alphaking.model.UserRelation;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.UserDto;

@Service
public class UserRelationService extends ImportDaoService{
	@Resource
	private CommonService commonService;
/**
 * 获取粉丝列表
 */
	public PagerResult<UserDto> getFans(SearchCondition searchCondition){
		PagerResult<UserRelation> pagerResult=userRelationDao.list(true, searchCondition);
		PagerResult<UserDto> fansPagerResult=new PagerResult<>();
		List<UserRelation> userRelations=pagerResult.getResultSet();
		if(userRelations!=null){
			List<UserDto> userDtos=new ArrayList<>(userRelations.size());
		for(UserRelation userRelation:userRelations){
			UserDto userDto=commonService.wragUser(userRelation.getConcernedUserId(),userRelation.getFanId());
		    userDtos.add(userDto);
		}
		fansPagerResult.setResultSet(userDtos);
		PagerResult.changePagerResult(fansPagerResult, pagerResult);
		}
		return fansPagerResult;
	}
/**
 * 获取关注用户列表
 */
	public PagerResult<UserDto> getConcernedUsers(SearchCondition searchCondition){
		PagerResult<UserRelation> pagerResult=userRelationDao.list(true, searchCondition);
		PagerResult<UserDto> concernedUsersPagerResult=new PagerResult<>();
		List<UserRelation> userRelations=pagerResult.getResultSet();
		if(userRelations!=null){
			List<UserDto> userDtos=new ArrayList<>(userRelations.size());
		for(UserRelation userRelation:userRelations){
			UserDto userDto=commonService.wragUser(userRelation.getFanId(),userRelation.getConcernedUserId());
		    userDtos.add(userDto);
		}
		concernedUsersPagerResult.setResultSet(userDtos);
		PagerResult.changePagerResult(concernedUsersPagerResult, pagerResult);
		}
		return concernedUsersPagerResult;
	}
/**
 * 获取某用户的粉丝列表
 */
	public PagerResult<UserDto> getHisFans(Integer userId,SearchCondition searchCondition){
		PagerResult<UserRelation> pagerResult=userRelationDao.list(true, searchCondition);
		PagerResult<UserDto> fansPagerResult=new PagerResult<>();
		List<UserRelation> userRelations=pagerResult.getResultSet();
		if(userRelations!=null){
			List<UserDto> userDtos=new ArrayList<>(userRelations.size());
		for(UserRelation userRelation:userRelations){
                UserDto userDto=commonService.wragUser(null, userRelation.getFanId());	 
                userDtos.add(userDto);
		}
		fansPagerResult.setResultSet(userDtos);
		PagerResult.changePagerResult(fansPagerResult, pagerResult);
		}
		return fansPagerResult;
	}
	/**
	 * 获取某用户关注用户列表
	 */
		public PagerResult<UserDto> getHisConcernedUsers(Integer userId,SearchCondition searchCondition){
			PagerResult<UserRelation> pagerResult=userRelationDao.list(true, searchCondition);
			PagerResult<UserDto> concernedUsersPagerResult=new PagerResult<>();
			List<UserRelation> userRelations=pagerResult.getResultSet();
			if(userRelations!=null){
				List<UserDto> userDtos=new ArrayList<>(userRelations.size());
			for(UserRelation userRelation:userRelations){
	                UserDto userDto=commonService.wragUser(null, userRelation.getConcernedUserId());	 
	                userDtos.add(userDto);
			}
			concernedUsersPagerResult.setResultSet(userDtos);
			PagerResult.changePagerResult(concernedUsersPagerResult, pagerResult);
			}
			return concernedUsersPagerResult;
		}
	/**
	 * 关注用户
	 */
	public void concernUser(UserRelation userRelation){
		Integer fanId=userRelation.getFanId();
		Integer concernedUserId=userRelation.getConcernedUserId();
		User fan=userDao.getById(fanId);
		User concernedUser=userDao.getById(concernedUserId);
		userDao.update(fan);
		userDao.update(concernedUser);
		userRelationDao.save(userRelation);
	}
  /**
   * 
   * @param fanId
   * @param concernedUserId
   */
	public void cancelConcern(Integer fanId,Integer concernedUserId) {
		User fan=userDao.getById(fanId);
		User concernedUser=userDao.getById(concernedUserId);
		userDao.update(fan);
		userDao.update(concernedUser);
		userRelationDao.delete(fanId,concernedUserId);
	}
	/**
	 * 获取好友
	 */
	public List<User> getFriends(Integer userId,String nickname){
		List<Integer> friendIds=userRelationDao.getFriendUserIds(userId);
		List<User> friends=null;
		if (friendIds!=null) {
	         friends=new ArrayList<>();
	         for(Integer friendId:friendIds){
	        	 User user=userDao.getById(friendId);
	        	 if (user.getNickname().indexOf(nickname)!=-1) {
					friends.add(user);
				}
	         }
		}
		return friends;
	}
	/**
	 * 判断是否为好友
	 */
	public boolean isFriend(Integer userId1,Integer userId2){
		return userRelationDao.isFriend(userId1, userId2);
	}
}
