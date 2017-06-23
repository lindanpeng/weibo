package com.alphaking.pojo.weibo;

import java.util.List;

import com.alphaking.model.Twitter;
import com.alphaking.model.TwitterPicture;
import com.alphaking.model.User;

public class TwitterDto {
private Twitter twitter;
private List<TwitterPicture> twitterPictures;
private UserDto userDto;
private Long commentsAmount;
private Long thumbsupAmount;
private boolean collected;
private boolean thumbsuped;


public UserDto getUserDto() {
	return userDto;
}
public void setUserDto(UserDto userDto) {
	this.userDto = userDto;
}
public Twitter getTwitter() {
	return twitter;
}
public void setTwitter(Twitter twitter) {
	this.twitter = twitter;
}
public List<TwitterPicture> getTwitterPictures() {
	return twitterPictures;
}
public void setTwitterPictures(List<TwitterPicture> twitterPictures) {
	this.twitterPictures = twitterPictures;
}
public Long getCommentsAmount() {
	return commentsAmount;
}
public void setCommentsAmount(Long commentsAmount) {
	this.commentsAmount = commentsAmount;
}
public Long getThumbsupAmount() {
	return thumbsupAmount;
}
public void setThumbsupAmount(Long thumbsupAmount) {
	this.thumbsupAmount = thumbsupAmount;
}
public boolean isCollected() {
	return collected;
}
public void setCollected(boolean collected) {
	this.collected = collected;
}
public boolean isThumbsuped() {
	return thumbsuped;
}
public void setThumbsuped(boolean thumbsuped) {
	this.thumbsuped = thumbsuped;
}

}
