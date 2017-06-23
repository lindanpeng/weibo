package com.alphaking.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;

@DynamicInsert
@Entity
@Table(name = "user", catalog = "weibo")
public class User {
private Integer userId;
private String userOpenId;
private String nickname;
private String password;
private String email;
private String portraitUrl;
private Integer gender;
private String introduction;
private Timestamp birthday; 
private Timestamp registerTime;
//激活码
private String activationCode;
//激活状态
private boolean  activationStatus;
@Id
@GeneratedValue

public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
@Column(unique=true)
public String getUserOpenId() {
	return userOpenId;
}
public void setUserOpenId(String userOpenId) {
	this.userOpenId = userOpenId;
}
@Column(unique=true)
public String getNickname() {
	return nickname;
}
public void setNickname(String nickname) {
	this.nickname = nickname;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
@Type(type="text")
@Column(length=65536)
public String getPortraitUrl() {
	return portraitUrl;
}
public void setPortraitUrl(String portraitUrl) {
	this.portraitUrl = portraitUrl;
}
public Integer getGender() {
	return gender;
}
public void setGender(Integer gender) {
	this.gender = gender;
}

public String getIntroduction() {
	return introduction;
}
public void setIntroduction(String introduction) {
	this.introduction = introduction;
}
public Timestamp getRegisterTime() {
	return registerTime;
}
public void setRegisterTime(Timestamp registerTime) {
	this.registerTime = registerTime;
}

public Timestamp getBirthday() {
	return birthday;
}
public void setBirthday(Timestamp birthday) {
	this.birthday = birthday;
}

public String getActivationCode() {
	return activationCode;
}
public void setActivationCode(String activationCode) {
	this.activationCode = activationCode;
}
public boolean isActivationStatus() {
	return activationStatus;
}
public void setActivationStatus(boolean activationStatus) {
	this.activationStatus = activationStatus;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((userOpenId == null) ? 0 : userOpenId.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	User other = (User) obj;
	if (userOpenId == null) {
		if (other.userOpenId != null)
			return false;
	} else if (!userOpenId.equals(other.userOpenId))
		return false;
	return true;

}

}
