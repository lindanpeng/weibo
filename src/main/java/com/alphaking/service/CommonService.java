package com.alphaking.service;

import org.springframework.stereotype.Service;

import com.alphaking.constant.UserRelationConstant;
import com.alphaking.model.Twitter;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.TwitterDto;
import com.alphaking.pojo.weibo.UserDto;

@Service
public class CommonService extends ImportDaoService{
/**
 * 将用户对象包装为UserDto对象
 */
	public UserDto wragUser(Integer myUserId,Integer otherUserId){
		UserDto userDto=new UserDto();
		userDto.setIsMyConcernedUser(isMyConcernedUser(myUserId,otherUserId));
		userDto.setIsMyFan(isMyFan(myUserId,otherUserId));
		userDto.setConcernedUserAmount(userRelationDao.getConcernedUserAmountOfUser(otherUserId));
		userDto.setFanAmount(userRelationDao.getFanAmountOfUser(otherUserId));
	    userDto.setTwitterAmount(twitterDao.getTwitterAmountOfUser(otherUserId));
	    userDto.setUser(userDao.getById(otherUserId));
	    return userDto;
	}
/**
 * 将微博对象包装为TwitterDto对象
 */
	public TwitterDto wragTwitter(Integer myUserId,Integer twitterId){
		TwitterDto twitterDto=new TwitterDto();
		Twitter twitter=twitterDao.getById(twitterId);
		twitterDto.setTwitter(twitter);
		twitterDto.setUserDto(wragUser(myUserId,twitter.getUserId()));
		twitterDto.setTwitterPictures(twitterPictureDao.geTwitterPicturesByTwitterId(twitterId));
		twitterDto.setCommentsAmount(commentDao.getCommentsAmountOfTwitter(twitterId ));
		twitterDto.setThumbsupAmount(thumbsupDao.getThumbsUpAmountById(twitterId));
		twitterDto.setCollected(collectionDao.getByUserIdAndTwitterId(myUserId, twitterId)==null?false:true);
		twitterDto.setThumbsuped(thumbsupDao.getByUserIdAndTwitterId(myUserId, twitterId)==null?false:true);
        twitterDto.setTwitterPictures(twitterPictureDao.geTwitterPicturesByTwitterId(twitterId));
		return twitterDto;
	}
	/**
	 * 判断某粉丝是否为关注用户
	 * @param userId
	 * @param fanUserId
	 * @return
	 */
	private Integer isMyConcernedUser(Integer userId,Integer fanUserId){
		if (userId==null||userId.equals(fanUserId)) {
			return UserRelationConstant.IS_MY_CONCERNED_USER_FALSE;
		}
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("fanId", SearchBean.OPERATOR_EQ,userId, 0);
		searchCondition.addCondition("concernedUserId", SearchBean.OPERATOR_EQ, fanUserId, 0);
		if (userRelationDao.getUniqueResult(searchCondition)!=null) {
			return UserRelationConstant.IS_MY_CONCERNED_USER_TRUE;
		}
		else{
			return UserRelationConstant.IS_MY_CONCERNED_USER_FALSE;
		}
	}
	/**
	 * 判断某关注用户是否为粉丝
	 */
	private Integer isMyFan(Integer userId,Integer concernedUserId){
		if (userId==null||userId.equals(concernedUserId)) {
			return UserRelationConstant.IS_MY_FAN_FALSE;
		}
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("fanId", SearchBean.OPERATOR_EQ,concernedUserId, 0);
		searchCondition.addCondition("concernedUserId", SearchBean.OPERATOR_EQ, userId, 0);
		if (userRelationDao.getUniqueResult(searchCondition)!=null) {
			return UserRelationConstant.IS_MY_FAN_TRUE;
		}
		else{
			return UserRelationConstant.IS_MY_FAN_FALSE;
		}
	}
}
