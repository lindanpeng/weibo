package com.alphaking.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.alphaking.constant.TwitterAtConstant;

/**
 * 在微博里@
 * @author lindanpeng
 *
 */
@Entity
public class TwitterAt {
private Integer twitterAtId;
private Integer twitterId;
private Integer userId;
private Integer isRead=TwitterAtConstant.IS_READ_FALSE;
private Timestamp twitterAtTime;
@Id
@GeneratedValue
public Integer getTwitterAtId() {
	return twitterAtId;
}
public void setTwitterAtId(Integer twitterAtId) {
	this.twitterAtId = twitterAtId;
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
public Integer getIsRead() {
	return isRead;
}
public void setIsRead(Integer isRead) {
	this.isRead = isRead;
}
public Timestamp getTwitterAtTime() {
	return twitterAtTime;
}
public void setTwitterAtTime(Timestamp twitterAtTime) {
	this.twitterAtTime = twitterAtTime;
}

}
