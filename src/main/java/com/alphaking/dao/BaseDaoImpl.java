package com.alphaking.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.alphaking.pojo.common.PagerResult;
import com.alphaking.pojo.common.SearchCondition;

/**
 * 所有dao的原型
 * 
 * @author lindanpeng
 *
 */
public class BaseDaoImpl<T> {
	@Resource
	private SessionFactory sessionFactory;
	protected Class<T> clazz;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();// 获得直接超类
		clazz = (Class) type.getActualTypeArguments()[0];// 获得超类的第一个泛型参数的类型
		/*
		 * 如：UserDao extends BaseDaoImpl<User> 超类为BaseDaoImpl，泛型参数为User
		 */
		System.out.println("BasicDao ----- clazz = " + this.clazz);
	}

	/**
	 * 保存记录
	 */
	public T save(T entity) {
		T proxyEntity = (T) getSession().save(entity);
		getSession().flush();
		return proxyEntity;
	}

	/**
	 * 更新记录
	 */
	public void update(T entity) {
		getSession().update(entity);
		getSession().flush();
	}

	/**
	 * 保存或者更新记录
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
		getSession().flush();
	}

	/**
	 * 通过id删除记录
	 */
	public void delete(Serializable id) {
		Object obj = getById(id);
		if (obj != null) {
			getSession().delete(obj);
		}
		getSession().flush();
	}

	/**
	 * 通过id找记录
	 */
	public T getById(Serializable id) {
		return (T) getSession().get(clazz, id);
	}
	/**
	 * 根据条件获取数量
	 */
    public Long count(SearchCondition searchCondition){
    	String hql = SearchCondition.getHql(clazz, searchCondition);
		String[] tempHQL = hql.split(" order by");
		hql = "select count(*) " + tempHQL[0];
	    return	(Long) getSession().createQuery(hql).uniqueResult();
    
    }
    /**
     * 根据条件获取列表
     */
    public PagerResult<T> list(boolean isPager,SearchCondition searchCondition){
    	String hql=searchCondition.getHql(clazz, searchCondition);
		Long count=count(searchCondition);
		Query query=getSession().createQuery(hql);
		List<T> resultSet;
		if(isPager){
			resultSet = query.setFirstResult((searchCondition.getPageNum()-1)*searchCondition.getSize()).setMaxResults(searchCondition.getSize()).list();
		}else{
			resultSet = query.list();
		}
		PagerResult<T> pagerResult = new PagerResult<T>();
		pagerResult.setResultSet(resultSet);
		pagerResult.setCount(count);
		if(resultSet==null || resultSet.size()==0){
			return pagerResult;
		}
		
		if(isPager){
			Long firstPage = new Long(1);
			Long currentPage = new Long(searchCondition.getPageNum());
			Long lastPage = count/searchCondition.getSize();
			Long totalPage = count/searchCondition.getSize();
			
			if(count%searchCondition.getSize()!=0){
				lastPage = count/searchCondition.getSize() + 1;
				totalPage = count/searchCondition.getSize() + 1;
			}
			
			Long previousPage = firstPage;
			Long nextPage = lastPage;
			
			if(firstPage<currentPage){
				previousPage = currentPage - 1;
			}
			
			if(lastPage>currentPage){
				nextPage = currentPage + 1;
			}
			
			List<Long> pageList = new ArrayList<Long>();
			for(int i=0;i<totalPage;i++){
				pageList.add(new Long(i+1));
			}
			
			pagerResult.setFirstPage(firstPage);
			pagerResult.setPreviousPage(previousPage);
			pagerResult.setCurrentPage(currentPage);
			pagerResult.setNextPage(nextPage);
			pagerResult.setLastPage(lastPage);
			pagerResult.setTotalPage(totalPage);
			pagerResult.setPageList(pageList);
			
		}
		
		return pagerResult;
    }
    /**
     * 根据条件获取单一对象
     */
    public T getUniqueResult(SearchCondition searchCondition){
    	String hql=SearchCondition.getHql(clazz, searchCondition);
    	return (T) getSession().createQuery(hql).uniqueResult();
    }
}
