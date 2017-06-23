package com.alphaking.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 评论
 * @author lindanpeng
 *
 */
@Entity
@Table(name="comment")
public class Comment {
private Integer commentId;
private Integer fromUserId;
private Integer toUserId;
private Integer twitterId;
private String content;
private Timestamp commentTime;
private Integer isRead;
private Integer twitterUserId;
@Id
@GeneratedValue
public Integer getCommentId() {
	return commentId;
}
public void setCommentId(Integer commentId) {
	this.commentId = commentId;
}
public Integer getFromUserId() {
	return fromUserId;
}
public void setFromUserId(Integer fromUserId) {
	this.fromUserId = fromUserId;
}
public Integer getToUserId() {
	return toUserId;
}
public void setToUserId(Integer toUserId) {
	this.toUserId = toUserId;
}
public Integer getTwitterId() {
	return twitterId;
}
public void setTwitterId(Integer twitterId) {
	this.twitterId = twitterId;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}

public Integer getIsRead() {
	return isRead;
}
public void setIsRead(Integer isRead) {
	this.isRead = isRead;
}
public Timestamp getCommentTime() {
	return commentTime;
}
public void setCommentTime(Timestamp commentTime) {
	this.commentTime = commentTime;
}
public Integer getTwitterUserId() {
	return twitterUserId;
}
public void setTwitterUserId(Integer twitterUserId) {
	this.twitterUserId = twitterUserId;
}

}
