package com.alphaking.util;

import java.util.Calendar;

public class TimeUtil {
/**
 * 获取当前日期的毫秒数
 */
public static long nowDay(){
	Calendar calendar=Calendar.getInstance();
	calendar.set(Calendar.HOUR, 0);
	calendar.set(Calendar.MINUTE,0);
	calendar.set(Calendar.SECOND, 0);
	return calendar.getTimeInMillis();
}

}
