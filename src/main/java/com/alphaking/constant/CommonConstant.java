package com.alphaking.constant;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonConstant {
 public static final String DEFAULT_PORTRAIT_URL="/weibo/resources/img/default_portrait.jpg";
 public static final String SERVER_WRONG="服务器出错！";
 public static final String DOMAIN_NAME;
 public static final String PROJECT_NAME;
 static{
	 InputStream inputStream=CommonConstant.class.getResourceAsStream("/web.properties");
	 Properties properties=new Properties();
	 try {
		properties.load(inputStream);
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	 String ipAddress=properties.getProperty("ip_address");
	 String port=properties.getProperty("port");
	 PROJECT_NAME=properties.getProperty("project_name");
	 DOMAIN_NAME=ipAddress+":"+port;
	 
 }
}
