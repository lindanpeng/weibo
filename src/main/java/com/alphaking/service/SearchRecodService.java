package com.alphaking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alphaking.model.SearchRecord;
import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchCondition;

@Service
public class SearchRecodService extends ImportDaoService{
/**
 * 保存记录
 */
	public void saveSearchRecord(SearchRecord searchRecord){
		if (searchRecordDao.searchKeyExists(searchRecord.getSearchKey())) {
			return;
		}
		searchRecordDao.save(searchRecord);
	}
/**
 * 删除某人的搜索记录
 */
	public void deleteByUserId(Integer userId){
		searchRecordDao.deleteByUserId(userId);
	}
/**
 * 通过条件获取搜索记录
 */
	public List<SearchRecord> getByCondition(SearchCondition searchCondition){
		return searchRecordDao.list(false, searchCondition).getResultSet();
	}
}
