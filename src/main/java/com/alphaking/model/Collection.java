package com.alphaking.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Collection {
private Integer collectionId;
private Integer userId;
private Integer collectedTwitterId;
private Timestamp collectTime;
@Id
@GeneratedValue
public Integer getCollectionId() {
	return collectionId;
}
public void setCollectionId(Integer collectionId) {
	this.collectionId = collectionId;
}
public Integer getUserId() {
	return userId;
}
public void setUserId(Integer userId) {
	this.userId = userId;
}
public Integer getCollectedTwitterId() {
	return collectedTwitterId;
}
public void setCollectedTwitterId(Integer collectedTwitterId) {
	this.collectedTwitterId = collectedTwitterId;
}
public Timestamp getCollectTime() {
	return collectTime;
}
public void setCollectTime(Timestamp collectTime) {
	this.collectTime = collectTime;
}


}
