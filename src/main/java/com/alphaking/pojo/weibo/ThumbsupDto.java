package com.alphaking.pojo.weibo;

import com.alphaking.model.Thumbsup;
import com.alphaking.model.Twitter;
import com.alphaking.model.User;

public class ThumbsupDto {
private Twitter twitter;
private Thumbsup thumbsup;
private User user;


public Twitter getTwitter() {
	return twitter;
}
public void setTwitter(Twitter twitter) {
	this.twitter = twitter;
}
public Thumbsup getThumbsup() {
	return thumbsup;
}
public void setThumbsup(Thumbsup thumbsup) {
	this.thumbsup = thumbsup;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}

}
