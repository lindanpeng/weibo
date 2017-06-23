package com.alphaking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class SearchRecord {
private Integer searchRecordId;
private String searchKey;
private Integer userId;
@Id
@GeneratedValue
public Integer getSearchRecordId() {
	return searchRecordId;
}
public void setSearchRecordId(Integer searchRecordId) {
	this.searchRecordId = searchRecordId;
}
public String getSearchKey() {
	return searchKey;
}
public void setSearchKey(String searchKey) {
	this.searchKey = searchKey;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}

}
