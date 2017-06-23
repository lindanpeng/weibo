package com.alphaking.pojo.weibo;
/**
 * 前台向后台请求的数据
 * @author lindanpeng
 *
 */
public class DataWrapper {
private Long unReadMessageAmount;
private Long unReadCommentAmount;
private Long unReadAtmeAmount;
private Long unReadThumbsupAmount;
private Long totalAmount;
public Long getUnReadMessageAmount() {
	return unReadMessageAmount;
}
public void setUnReadMessageAmount(Long unReadMessageAmount) {
	this.unReadMessageAmount = unReadMessageAmount;
}
public Long getUnReadCommentAmount() {
	return unReadCommentAmount;
}
public void setUnReadCommentAmount(Long unReadCommentAmount) {
	this.unReadCommentAmount = unReadCommentAmount;
}
public Long getUnReadAtmeAmount() {
	return unReadAtmeAmount;
}
public void setUnReadAtmeAmount(Long unReadAtmeAmount) {
	this.unReadAtmeAmount = unReadAtmeAmount;
}
public Long getUnReadThumbsupAmount() {
	return unReadThumbsupAmount;
}
public void setUnReadThumbsupAmount(Long unReadThumbsupAmount) {
	this.unReadThumbsupAmount = unReadThumbsupAmount;
}
public Long getTotalAmount() {
	return totalAmount;
}
public void setTotalAmount(Long totalAmount) {
	this.totalAmount = totalAmount;
}

}
