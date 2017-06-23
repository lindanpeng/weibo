package com.alphaking.dao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.alphaking.model.SearchRecord;
@Repository
public class SearchRecordDao extends BaseDaoImpl<SearchRecord>{
/**
 * 删除某人的搜索记录
 * @param userId
 */
	public void deleteByUserId(Integer userId) {
		String hql="delete SearchRecord where userId="+userId;
		getSession().createQuery(hql).executeUpdate();
	}
/**
 * 判断是否存在某搜索记录
 */
	public boolean searchKeyExists(String searchKey){
		String hql="from SearchRecord where searchKey =:searchKey";
		SearchRecord searchRecord=(SearchRecord) getSession().createQuery(hql).setParameter("searchKey", searchKey).uniqueResult();
		if (searchRecord!=null) {
			return true;
		}
		return false;
	}

}
