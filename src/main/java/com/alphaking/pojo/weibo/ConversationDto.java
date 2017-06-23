package com.alphaking.pojo.weibo;

import com.alphaking.model.Conversation;
import com.alphaking.model.Message;

public class ConversationDto implements Comparable<ConversationDto>{
private Conversation conversation;
private UserDto friend;
private Message latestMessage;
private Long unReadMessageAmount;
public Conversation getConversation() {
	return conversation;
}
public void setConversation(Conversation conversation) {
	this.conversation = conversation;
}
public Message getLatestMessage() {
	return latestMessage;
}
public void setLatestMessage(Message latestMessage) {
	this.latestMessage = latestMessage;
}
@Override
public int compareTo(ConversationDto o) {
   return -latestMessage.getSendTime().compareTo(o.latestMessage.getSendTime());
}
public Long getUnReadMessageAmount() {
	return unReadMessageAmount;
}
public void setUnReadMessageAmount(Long unReadMessageAmount) {
	this.unReadMessageAmount = unReadMessageAmount;
}
public UserDto getFriend() {
	return friend;
}
public void setFriend(UserDto friend) {
	this.friend = friend;
}

}
