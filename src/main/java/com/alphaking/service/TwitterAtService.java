package com.alphaking.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alphaking.constant.CommentAtConstant;
import com.alphaking.dao.UserRelationDao;
import com.alphaking.model.Twitter;
import com.alphaking.model.TwitterAt;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.TwitterDto;

@Service
public class TwitterAtService extends ImportDaoService{
	@Resource
	private CommonService commonService;
	/**
	 * 保存@
	 */
	public void  saveTwitterAtUser(TwitterAt twitterAt ){
	twitterAtDao.save(twitterAt);	
	}
	/**
	 * 获取新@我的微博的数量
	 */
		public Long getNewTwitterAts(Integer userId){
			SearchCondition searchCondition=new SearchCondition();
			searchCondition.addCondition("isRead", SearchBean.OPERATOR_EQ,CommentAtConstant.IS_READ_FALSE,0);
			searchCondition.addCondition("userId", SearchBean.OPERATOR_EQ,userId,0);
			return twitterAtDao.count(searchCondition);
		}
		/**
		 * 获取@我的微博列表
		 */
			public PagerResult<TwitterDto> getTwitterAtList(SearchCondition searchCondition){
				List<SearchBean> searchBeans=searchCondition.getConditions();
				Integer userId=null;
				for(SearchBean searchBean:searchBeans){
					if (searchBean.getKey().equals("userId")) {
						userId=(Integer) searchBean.getValue();
						break;
					}
				}
				readTwitterAtOfUser(userId);
				 PagerResult<TwitterAt> pagerResultOftwitterAts=twitterAtDao.list(true, searchCondition);
				 PagerResult<TwitterDto> pagerResultOftwitterDtos=new PagerResult<>();	 
				 if(pagerResultOftwitterAts.getResultSet()!=null){
							 List<TwitterDto> twitterDtos=new ArrayList<>();
				 for (TwitterAt twitterAt : pagerResultOftwitterAts.getResultSet()) {
					TwitterDto twitterDto=commonService.wragTwitter(twitterAt.getUserId(),twitterAt.getTwitterId());
					twitterDtos.add(twitterDto);
				}
				pagerResultOftwitterDtos.setResultSet(twitterDtos);
		        PagerResult.changePagerResult(pagerResultOftwitterDtos, pagerResultOftwitterAts);
				 }
		        return pagerResultOftwitterDtos;
			}
		/**
		 * 已阅所有@我的微博
		 */
		public void	readTwitterAtOfUser(Integer userId){
		     twitterAtDao.readTwitterAtOfUser(userId);		
		}
}
