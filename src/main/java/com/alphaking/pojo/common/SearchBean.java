package com.alphaking.pojo.common;

public class SearchBean {
private String key;
private Object value;
private String operator;
private Integer priority;
private String conjunction;
public String getKey() {
	return key;
}
public void setKey(String key) {
	this.key = key;
}
public String getOperator() {
	return operator;
}
public void setOperator(String operator) {
	this.operator = operator;
}


public Object getValue() {
	return value;
}
public void setValue(Object value) {
	this.value = value;
}
public Integer getPriority() {
	return priority;
}
public void setPriority(Integer priority) {
	this.priority = priority;
}


public String getConjunction() {
	return conjunction;
}
public void setConjunction(String conjunction) {
	this.conjunction = conjunction;
}


public final static String OPERATOR_IN = "in";
public final static String OPERATOR_LIKE = "like";
public final static String OPERATOR_SORT = "sort";
public final static String OPERATOR_IS_NULL = "is_null";
public final static String OPERATOR_EQ = "=";
public final static String OPERATOR_NE = "!=";
public final static String OPERATOR_LT = "<";
public final static String OPERATOR_GT = ">";
public final static String OPERATOR_LE = "<=";
public final static String OPERATOR_GE = ">=";
}
