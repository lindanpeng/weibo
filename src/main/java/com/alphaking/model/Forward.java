package com.alphaking.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 转发
 * @author lindanpeng
 *
 */
@Entity
public class Forward {
private Integer forwarId;
private Integer twitterId;
private Integer userId;
private Timestamp ForwardTime;
@Id
@GeneratedValue
public Integer getForwarId() {
	return forwarId;
}
public void setForwarId(Integer forwarId) {
	this.forwarId = forwarId;
}
public Integer getTwitterId() {
	return twitterId;
}
public void setTwitterId(Integer twitterId) {
	this.twitterId = twitterId;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public Timestamp getForwardTime() {
	return ForwardTime;
}
public void setForwardTime(java.sql.Timestamp timestamp) {
	ForwardTime = timestamp;
}

}
