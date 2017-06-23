package com.alphaking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Conversation {
private Integer conversationId;
private Integer  userId;
private Integer friendUserId;
@Id
@GeneratedValue
public Integer getConversationId() {
	return conversationId;
}
public void setConversationId(Integer conversationId) {
	this.conversationId = conversationId;
}

public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public Integer getFriendUserId() {
	return friendUserId;
}
public void setFriendUserId(Integer friendUserId) {
	this.friendUserId = friendUserId;
}
}
