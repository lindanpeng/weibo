package com.alphaking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微博动态的相关图片
 * @author lindanpeng
 *
 */
@Entity
@Table(name="TwitterPicture")
public class TwitterPicture {
private Integer twitterPictureId;
private Integer twitterId;
private String pictureUrl;
@Id
@GeneratedValue
public Integer getTwitterPictureId() {
	return twitterPictureId;
}
public void setTwitterPictureId(Integer twitterPictureId) {
	this.twitterPictureId = twitterPictureId;
}
public Integer getTwitterId() {
	return twitterId;
}
public void setTwitterId(Integer twitterId) {
	this.twitterId = twitterId;
}
public String getPictureUrl() {
	return pictureUrl;
}
public void setPictureUrl(String pictureUrl) {
	this.pictureUrl = pictureUrl;
}

}
