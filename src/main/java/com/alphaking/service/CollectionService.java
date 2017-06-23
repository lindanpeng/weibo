package com.alphaking.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alphaking.model.Collection;
import com.alphaking.model.Twitter;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchBean;
import com.alphaking.pojo.common.SearchCondition;
import com.alphaking.pojo.weibo.TwitterDto;

@Service
public class CollectionService extends ImportDaoService{
	@Resource
	private CommonService commonService;
/**
 * 添加收藏
 */
	public void addCollection(Collection collection){
		collectionDao.save(collection);
	}
/**
 * 取消收藏
 */
	public void removeCollection(Integer userId,Integer twitterId){
		collectionDao.deleteCollectionByUserIdByTwitterId(userId, twitterId);
	}
/**
 * 获取收藏列表
 */
	public PagerResult<TwitterDto> getCollectionList(SearchCondition searchCondition){
		List<SearchBean> searchBeans=searchCondition.getConditions();
		Integer userId=null;
		for(SearchBean searchBean:searchBeans){
			if(searchBean.getKey().equals("userId"))
				userId=(Integer) searchBean.getValue();
		}
		PagerResult<Collection> collectionPagerResult=collectionDao.list(true, searchCondition);
		PagerResult<TwitterDto> twitterDtoPagerResult=new PagerResult<>();
		List<Collection> collections=collectionPagerResult.getResultSet();
		if(collections!=null){
			List<TwitterDto> twitterDtos=new ArrayList<>(collections.size());
			for(Collection collection:collections){
				TwitterDto twitterDto=commonService.wragTwitter(userId, collection.getCollectedTwitterId());			
				twitterDtos.add(twitterDto);
			}
			twitterDtoPagerResult.setResultSet(twitterDtos);
			PagerResult.changePagerResult(twitterDtoPagerResult, collectionPagerResult);
		}
		
		return twitterDtoPagerResult;
	}
}
