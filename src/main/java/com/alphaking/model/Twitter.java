package com.alphaking.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微博动态
 * @author lindanpeng
 *
 */
@Entity
@Table(name="twitter")
public class Twitter {
private Integer userId;
private String userNickname;
private Integer twitterId;
private String textContent;
private Timestamp publicTime;
private String twitterVideoUrl;
@Id
@GeneratedValue
public Integer getTwitterId() {
	return twitterId;
}
public void setTwitterId(Integer twitterId) {
	this.twitterId = twitterId;
}
public String getTextContent() {
	return textContent;
}
public void setTextContent(String textContent) {
	this.textContent = textContent;
}
public Timestamp getPublicTime() {
	return publicTime;
}
public void setPublicTime(Timestamp publicTime) {
	this.publicTime = publicTime;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public String getTwitterVideoUrl() {
	return twitterVideoUrl;
}
public void setTwitterVideoUrl(String twitterVideoUrl) {
	this.twitterVideoUrl = twitterVideoUrl;
}
public String getUserNickname() {
	return userNickname;
}
public void setUserNickname(String userNickname) {
	this.userNickname = userNickname;
}

}
