package com.alphaking.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.alphaking.constant.ThumbsupConstant;

@Entity
public class Thumbsup {
private Integer thumbsupId;
private Integer twitterId;
//点赞的用户
private Integer userId;
//被点赞的用户
private Integer thumbsupedUserId;
private Timestamp thumbsupTime;
private Integer isRead=ThumbsupConstant.THUMBSUP_READ_FALSE;
@Id
@GeneratedValue
public Integer getThumbsupId() {
	return thumbsupId;
}
public void setThumbsupId(Integer thumbsupId) {
	this.thumbsupId = thumbsupId;
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
public Timestamp getThumbsupTime() {
	return thumbsupTime;
}
public void setThumbsupTime(Timestamp thumbsupTime) {
	this.thumbsupTime = thumbsupTime;
}
public Integer getThumbsupedUserId() {
	return thumbsupedUserId;
}
public void setThumbsupedUserId(Integer thumbsupedUserId) {
	this.thumbsupedUserId = thumbsupedUserId;
}
public Integer getIsRead() {
	return isRead;
}
public void setIsRead(Integer isRead) {
	this.isRead = isRead;
}

}
