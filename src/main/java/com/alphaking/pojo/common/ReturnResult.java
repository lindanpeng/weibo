package com.alphaking.pojo.common;

/**
 * 返回给客户端的 通用结果bean
 */
public class ReturnResult<T> {
	/**
	 * 返回信息
	 */
	private String retMsg;
	/**
	 * 返回码
	 */
	private int code;
	/**
	 * 返回数据集
	 */
	private T data;
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ResultBean [retMsg=" + retMsg + ", code=" + code + ", data="
				+ data + "]";
	}


}
