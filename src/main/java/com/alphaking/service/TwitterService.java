package com.alphaking.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Service;

import com.alphaking.constant.TwitterAtConstant;
import com.alphaking.model.Twitter;
import com.alphaking.model.TwitterAt;
import com.alphaking.model.TwitterPicture;
import com.alphaking.model.User;
import com.alphaking.model.UserRelation;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.ParsedText;
import com.alphaking.pojo.weibo.TwitterDto;
import com.alphaking.util.HtmlTextUtil;

@Service
public class TwitterService extends ImportDaoService {
	@Resource
	private CommonService commonService;
/**
 * 通过Id获取微博
 */
	public TwitterDto getTwitterById(Integer userId,Integer twitterId){
         TwitterDto twitterDto=commonService.wragTwitter(userId,twitterId);
         return twitterDto;
         
	}
/**
 * 添加微博
 */
	public void addTwitterDto(TwitterDto twitterDto){
		ParsedText parsedText=HtmlTextUtil.parseText(twitterDto.getTwitter().getTextContent());

		twitterDto.getTwitter().setTextContent(parsedText.getParsedContent());
		
		twitterDao.save(twitterDto.getTwitter());
		if(twitterDto.getTwitterPictures()!=null){
			for(TwitterPicture twitterPicture:twitterDto.getTwitterPictures())
			{   
				twitterPicture.setTwitterId(twitterDto.getTwitter().getTwitterId());
				twitterPictureDao.save(twitterPicture);
			}
				
		}
		
		for(String nickname:parsedText.getPeople()){
			User user=userDao.getByNickname(nickname);
	
			TwitterAt twitterAt=new TwitterAt();
			twitterAt.setIsRead(TwitterAtConstant.IS_READ_FALSE);
			twitterAt.setTwitterId(twitterDto.getTwitter().getTwitterId());
			twitterAt.setTwitterAtTime(twitterDto.getTwitter().getPublicTime());
			if(user!=null){
			twitterAt.setUserId(user.getUserId());
			}
			twitterAtDao.save(twitterAt);
		}
	}
/**
 * 删除微博动态
 */
	public void deleteTwitter(Integer userId,Integer twitterId){
		twitterDao.delete(twitterId);
		twitterPictureDao.deletePicturesByTwitterId(twitterId);
		thumbsupDao.deleteByTwitterId(twitterId);
		commentDao.deleteByTwitterId(twitterId);
		commentAtDao.deleteByTwitterId(twitterId);
		twitterAtDao.deleteByTwitterId(twitterId);
		twitterPictureDao.deletePicturesByTwitterId(twitterId);
		collectionDao.deleteCollectionByUserIdByTwitterId(userId, twitterId);
		
	}
/**
 * 获取微博列表
 */
	public PagerResult<TwitterDto> getTwittersByCondition(Integer myUserId,SearchCondition searchCondition){
		 PagerResult<Twitter> pagerResultOftwitters=twitterDao.list(true, searchCondition);
		 PagerResult<TwitterDto> pagerResultOftwitterDtos=new PagerResult<>();	 
		 if(pagerResultOftwitters.getResultSet()!=null){
					 List<TwitterDto> twitterDtos=new ArrayList<>();
		 for (Twitter twitter : pagerResultOftwitters.getResultSet()) {
			TwitterDto twitterDto=commonService.wragTwitter(myUserId,twitter.getTwitterId());
			twitterDtos.add(twitterDto);
		}
		pagerResultOftwitterDtos.setResultSet(twitterDtos);
        PagerResult.changePagerResult(pagerResultOftwitterDtos, pagerResultOftwitters);
		 }
        return pagerResultOftwitterDtos;
	}
/**
 * 获取关注人微博
 */
	public PagerResult<TwitterDto> getConcernedUserTwittersByCondition(Integer userId,Integer pageNum,Integer size){

		 PagerResult<Twitter> pagerResultOftwitters=twitterDao.getConcernedUserTwitters(userId, pageNum, size);
		 PagerResult<TwitterDto> pagerResultOftwitterDtos=new PagerResult<>();	 
		 if(pagerResultOftwitters.getResultSet()!=null){
					 List<TwitterDto> twitterDtos=new ArrayList<>();
		 for (Twitter twitter : pagerResultOftwitters.getResultSet()) {
			TwitterDto twitterDto=commonService.wragTwitter(userId, twitter.getTwitterId());
			twitterDtos.add(twitterDto);
		}
		pagerResultOftwitterDtos.setResultSet(twitterDtos);
       PagerResult.changePagerResult(pagerResultOftwitterDtos, pagerResultOftwitters);
		 }
       return pagerResultOftwitterDtos;
	}
 
}
