package com.alphaking.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alphaking.constant.ThumbsupConstant;
import com.alphaking.model.Thumbsup;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.ThumbsupDto;
import com.alphaking.pojo.weibo.TwitterDto;

@Service
public class ThumbsupService extends ImportDaoService{
	@Resource
	private CommonService commonService;
	/**
	 * 通过条件获取点赞列表
	 */
	public PagerResult<ThumbsupDto> getThumbsupListByCondition(SearchCondition searchCondition){
		PagerResult<Thumbsup> pagerResult=thumbsupDao.list(true, searchCondition);
		PagerResult<ThumbsupDto> pagerResult2=new PagerResult<>();
		if (pagerResult.getResultSet()!=null) {
			List<ThumbsupDto> thumbsupDtos=new ArrayList<>(pagerResult.getResultSet().size());
			for(Thumbsup thumbsup:pagerResult.getResultSet()){
				ThumbsupDto thumbsupDto=new ThumbsupDto();
				thumbsupDto.setTwitter(twitterDao.getById(thumbsup.getTwitterId()));
				thumbsupDto.setUser(userDao.getById(thumbsup.getUserId()));
				thumbsupDto.setThumbsup(thumbsup);
				thumbsupDtos.add(thumbsupDto);
			}
			pagerResult2.setResultSet(thumbsupDtos);
			PagerResult.changePagerResult(pagerResult2, pagerResult);
		}
		return pagerResult2;
	}
	/**
	 * 保存赞
	 */
	public void saveThumbsup(Thumbsup thumbsup){
		thumbsupDao.save(thumbsup);
	}
	/**
	 * 取消赞
	 */
	public void cancelThumbsup(Integer userId,Integer twitterId){
		thumbsupDao.deleteByTwitterIdAndUserId(userId, twitterId);
	}
	/**
	 * 已阅用户收到的所有赞
	 */
	public void readThumbsupOfUser(Integer userId){
		thumbsupDao.readThumbsupOfUser(userId);
	}
	/**
	 * 获取某微博的点赞数
	 */
	public Long getThumbsupAmountOfTwitter(Integer twitterId){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("twitterId", SearchBean.OPERATOR_EQ, twitterId,0);
		return thumbsupDao.count(searchCondition);
	}
	/**
	 * 获取未读赞数量
	 */
	public Long getUnReadThumbsupAmount(Integer userId){
		SearchCondition searchCondition=new SearchCondition();
		searchCondition.addCondition("thumbsupedUserId", SearchBean.OPERATOR_EQ,userId, 0);
		searchCondition.addCondition("isRead", SearchBean.OPERATOR_EQ, ThumbsupConstant.THUMBSUP_READ_FALSE,0);
		return thumbsupDao.count(searchCondition);
	}
}
