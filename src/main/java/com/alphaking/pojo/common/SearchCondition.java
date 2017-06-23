package com.alphaking.pojo.common;

import java.util.ArrayList;
import java.util.List;

public class SearchCondition {
public static final Integer DEFAULT_SIZE=15;
private Integer pageNum;
private Integer size;
private List<SearchBean> conditions;
public SearchCondition(){
	pageNum=1;
	size=DEFAULT_SIZE;
	conditions=new ArrayList<>();
}
public void addCondition(SearchBean searchBean){
	conditions.add(searchBean);
}
public void addCondition(String key,String operator,Object value,Integer priority){
	SearchBean searchBean=new SearchBean();
	searchBean.setKey(key);
	searchBean.setOperator(operator);
	searchBean.setPriority(priority);
	searchBean.setValue(value);
	conditions.add(searchBean);
}
public static String getHql(Class clazz,SearchCondition  searchCondition){
	String hql="from "+clazz.getSimpleName()+" where 1=1 ";
	if(searchCondition==null)return hql;
	List<SearchBean> sortBeans = new ArrayList<SearchBean>();
	List<SearchBean> searchBeans=searchCondition.getConditions();
	for(SearchBean searchBean:searchBeans){
		if(searchBean!=null && searchBean.getOperator()!=null && !searchBean.getOperator().equals("") && searchBean.getKey()!=null && !searchBean.getKey().equals("") && searchBean.getValue()!=null && !searchBean.getValue().equals("")){
			String operator = searchBean.getOperator();
			String key = searchBean.getKey();
			Object value = searchBean.getValue();
			String formatedValue=null;
			if(value instanceof String)
				formatedValue="'"+value+"'";
			else
				formatedValue=String.valueOf(value);
			Integer priority = searchBean.getPriority();				
			String condition = "";
			switch(operator){
			case SearchBean.OPERATOR_LIKE :
				condition = " and "+key+" like '%"+value+"%'";
				hql = hql + condition;
				break;
			case SearchBean.OPERATOR_SORT :
				if(priority!=null){
					sortBeans.add(searchBean);
				}
				break;
			case SearchBean.OPERATOR_IS_NULL:
				condition = " and "+key+" is null";
				hql = hql + condition;
				break;
			case SearchBean.OPERATOR_EQ :
				condition = " and "+key+"="+formatedValue;
				hql = hql + condition;
				break;
			case SearchBean.OPERATOR_NE :
				condition = " and "+key+"!="+formatedValue;
				hql = hql + condition;
				break;
			case SearchBean.OPERATOR_LT :
				condition = " and "+key+"<"+formatedValue;
				hql = hql + condition;
				break;
			case SearchBean.OPERATOR_GT :
				condition = " and "+key+">"+formatedValue;
				hql = hql + condition;
				break;
			case SearchBean.OPERATOR_LE :
				condition = " and "+key+"<="+formatedValue;
				hql = hql + condition;
				break;
			case SearchBean.OPERATOR_GE :
				condition = " and "+key+">="+formatedValue;
				hql = hql + condition;
				break;
			default :
				break;
			}
		}
	}
	/*
	 * 排序查询单独组合
	 */
	if(sortBeans!=null && sortBeans.size()>0){
		List<SearchBean> sortedBeans = new ArrayList<SearchBean>(sortBeans.size());
		for(SearchBean searchBean : sortBeans){
			sortedBeans.add(new SearchBean());
		}
		
		for(SearchBean searchBean : sortBeans){
			sortedBeans.set(searchBean.getPriority(),searchBean);
		}
		
		for (int i = 0; i < sortedBeans.size(); i++) {
			if (sortedBeans.get(i) != null) {
				String condition = null;
				if (i == 0) {
					condition = " order by " + sortedBeans.get(i).getKey()
							+ " " + sortedBeans.get(i).getValue();
				} else {
					condition = "," + sortedBeans.get(i).getKey() + " "
							+ sortedBeans.get(i).getValue();
				}
				hql = hql + condition;
			}
		}

	}
	return hql;
}
public Integer getPageNum() {
	return pageNum;
}
public void setPageNum(Integer pageNum) {
	this.pageNum = pageNum;
}
public Integer getSize() {
	return size;
}
public void setSize(Integer size) {
	this.size = size;
}
public List<SearchBean> getConditions() {
	return conditions;
}
public void setConditions(List<SearchBean> conditions) {
	this.conditions = conditions;
}


}
