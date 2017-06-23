package com.alphaking.pojo.weibo;

import com.alphaking.model.User;

public class UserDto {
private User user;
private Long fanAmount;
private Long twitterAmount;
private Long concernedUserAmount;
private Integer isMyConcernedUser;
private Integer isMyFan;
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}

public Long getFanAmount() {
	return fanAmount;
}
public void setFanAmount(Long fanAmount) {
	this.fanAmount = fanAmount;
}
public Long getTwitterAmount() {
	return twitterAmount;
}
public void setTwitterAmount(Long twitterAmount) {
	this.twitterAmount = twitterAmount;
}
public Long getConcernedUserAmount() {
	return concernedUserAmount;
}
public void setConcernedUserAmount(Long concernedUserAmount) {
	this.concernedUserAmount = concernedUserAmount;
}
public Integer getIsMyConcernedUser() {
	return isMyConcernedUser;
}
public void setIsMyConcernedUser(Integer isMyConcernedUser) {
	this.isMyConcernedUser = isMyConcernedUser;
}
public Integer getIsMyFan() {
	return isMyFan;
}
public void setIsMyFan(Integer isMyFan) {
	this.isMyFan = isMyFan;
}

}
