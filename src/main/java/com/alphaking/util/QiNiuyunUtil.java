package com.alphaking.util;

import com.qiniu.util.Auth;

public class QiNiuyunUtil {
public static final String ACCESS_KEY="0h75l0xNKaJbgOTIHjpm1HFjDcty9sW1wxKJWIda";
public static final String SECRET_KEY="_WrP_lxe12VIHhcsrqY29RxUi30wnkT6WsWJSLzN";
public static final String BUCKET_NAME="zone";
public static  Auth AUTH=null;
static{
	AUTH=Auth.create(ACCESS_KEY, SECRET_KEY);
}
public static String getUpToken(){
	return AUTH.uploadToken(BUCKET_NAME);
}
}
