package com.alphaking.pojo.weibo;

import com.alphaking.model.Comment;
import com.alphaking.model.Twitter;
import com.alphaking.model.User;

public class CommentDto {
private User fromUser;
private User toUser;
private Comment comment;
private Twitter twitter;

public Twitter getTwitter() {
	return twitter;
}
public void setTwitter(Twitter twitter) {
	this.twitter = twitter;
}
public User getFromUser() {
	return fromUser;
}
public void setFromUser(User fromUser) {
	this.fromUser = fromUser;
}
public Comment getComment() {
	return comment;
}
public void setComment(Comment comment) {
	this.comment = comment;
}
public User getToUser() {
	return toUser;
}
public void setToUser(User toUser) {
	this.toUser = toUser;
}

}
