package com.alphaking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.alphaking.constant.TwitterAtConstant;
@Entity
public class CommentAt {
private Integer commentAtId;
private Integer commentId;
private Integer twitterId;
private Integer userId;
private Integer isRead=TwitterAtConstant.IS_READ_FALSE;
@Id
@GeneratedValue
public Integer getCommentAtId() {
	return commentAtId;
}
public void setCommentAtId(Integer commentAtId) {
	this.commentAtId = commentAtId;
}
public Integer getCommentId() {
	return commentId;
}
public void setCommentId(Integer commentId) {
	this.commentId = commentId;
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
public Integer getTwitterId() {
	return twitterId;
}
public void setTwitterId(Integer twitterId) {
	this.twitterId = twitterId;
}

}
