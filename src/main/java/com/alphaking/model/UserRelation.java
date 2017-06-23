package com.alphaking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 关注用户
 * @author lindanpeng
 *
 */
@Entity
@Table
public class UserRelation {
private Integer userRelationId;
private Integer fanId;
private Integer concernedUserId;
@Id
@GeneratedValue
public Integer getUserRelationId() {
	return userRelationId;
}
public void setUserRelationId(Integer userRelationId) {
	this.userRelationId = userRelationId;
}
public Integer getFanId() {
	return fanId;
}
public void setFanId(Integer fanId) {
	this.fanId = fanId;
}
public Integer getConcernedUserId() {
	return concernedUserId;
}
public void setConcernedUserId(Integer concernedUserId) {
	this.concernedUserId = concernedUserId;
}


}
