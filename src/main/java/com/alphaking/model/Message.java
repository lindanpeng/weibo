package com.alphaking.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 私信
 * @author lindanpeng
 *
 */
@Entity
public class Message {
private Integer messageId;
private String content;//暂时只能发文字吧
private Integer fromUserId;
private Integer toUserId;
private Timestamp sendTime;
private Integer isDeleted=0;//0表示双方都没有删除消息记录，如果某一方删除了记录，则isDeleted的值为该用户Id
@Column(nullable=false,columnDefinition="INT default 0")
private Integer isRead=0;
@Id
@GeneratedValue
public Integer getMessageId() {
	return messageId;
}
public void setMessageId(Integer messageId) {
	this.messageId = messageId;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
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
public Timestamp getSendTime() {
	return sendTime;
}
public void setSendTime(Timestamp sendTime) {
	this.sendTime = sendTime;
}
public Integer getIsRead() {
	return isRead;
}
public void setIsRead(Integer isRead) {
	this.isRead = isRead;
}
public Integer getIsDeleted() {
	return isDeleted;
}
public void setIsDeleted(Integer isDeleted) {
	this.isDeleted = isDeleted;
}

}
