package com.alphaking.pojo.common;

import java.util.ArrayList;
import java.util.List;

import com.alphaking.model.Twitter;

/**
 * 分页查询的结果
 */
public class PagerResult<T> {
	
	private Long count; //总记录数量
	private List<T> resultSet; //结果集
	private Long firstPage; //首页
	private Long previousPage; //上一页
	private Long currentPage; //当前页
	private Long nextPage; //下一页
	private Long lastPage; //尾页
	private Long totalPage; //总页数
	private List<Long> pageList; //页码数组
	public PagerResult(){
		this.count=(long) 0;
	}
	
	public Long getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(Long firstPage) {
		this.firstPage = firstPage;
	}
	public Long getLastPage() {
		return lastPage;
	}
	public void setLastPage(Long lastPage) {
		this.lastPage = lastPage;
	}
	public Long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Long currentPage) {
		this.currentPage = currentPage;
	}
	public Long getPreviousPage() {
		return previousPage;
	}
	public void setPreviousPage(Long previousPage) {
		this.previousPage = previousPage;
	}
	public Long getNextPage() {
		return nextPage;
	}
	public void setNextPage(Long nextPage) {
		this.nextPage = nextPage;
	}
	public Long getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Long totalPage) {
		this.totalPage = totalPage;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public List<T> getResultSet() {
		return resultSet;
	}
	public void setResultSet(List<T> resultSet) {
		this.resultSet = resultSet;
	}
	public List<Long> getPageList() {
		return pageList;
	}
	public void setPageList(List<Long> pageList) {
		this.pageList = pageList;
	}
	public void init(Integer pageNum,Integer size){
		Long firstPage = new Long(1);
		Long currentPage = new Long(pageNum);
		Long lastPage = count/size;
		Long totalPage = count/size;
		
		if(count%size!=0){
			lastPage = count/size + 1;
			totalPage = count/size + 1;
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
		
		setFirstPage(firstPage);
		setPreviousPage(previousPage);
		setCurrentPage(currentPage);
		setNextPage(nextPage);
		setLastPage(lastPage);
		setTotalPage(totalPage);
		setPageList(pageList);
	}
	@Override
	public String toString() {
		return "PagerResult [count=" + count + ", resultSet=" + resultSet
				+ ", firstPage=" + firstPage + ", previousPage=" + previousPage
				+ ", currentPage=" + currentPage + ", nextPage=" + nextPage
				+ ", lastPage=" + lastPage + ", totalPage=" + totalPage
				+ ", pageList=" + pageList + "]";
	}
	
	
	public static void changePagerResult(PagerResult<?> newPagerResult,PagerResult<?> oldPagerResult){
		if(newPagerResult==null){
			newPagerResult = new PagerResult<>();
		}
		if(oldPagerResult!=null){
			newPagerResult.setCount(oldPagerResult.getCount());
			newPagerResult.setCurrentPage(oldPagerResult.getCurrentPage());
			newPagerResult.setFirstPage(oldPagerResult.getFirstPage());
			newPagerResult.setLastPage(oldPagerResult.getLastPage());
			newPagerResult.setNextPage(oldPagerResult.getNextPage());
			newPagerResult.setPageList(oldPagerResult.getPageList());
			newPagerResult.setPreviousPage(oldPagerResult.getPreviousPage());
			newPagerResult.setTotalPage(oldPagerResult.getTotalPage());
		}
	}
	public static <T> PagerResult<T> getPagerResultByData(Long count,Integer pageNum,Integer size,List<T> resultSet){
		PagerResult<T> pagerResult=new PagerResult<>();
		pagerResult.setResultSet(resultSet);
		pagerResult.setCount(count);
		Long firstPage = new Long(1);
		Long currentPage = new Long(pageNum);
		Long lastPage = count/size;
		Long totalPage = count/size;
		
		if(count%size!=0){
			lastPage = count/size + 1;
			totalPage = count/size + 1;
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
		return pagerResult;
	}
}
