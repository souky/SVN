package com.jy.moudles.buttInterface.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {
	/**
	 * 判空
	 * @param obj
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月24日 上午9:22:11
	 */
	public static boolean isNull(Object obj) {
		return  obj == null || "".equals(obj.toString().trim());
	}
	
	/**
	 * 不为空
	 * @param obj
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月25日 上午10:17:52
	 */
	public static boolean isNotNull(Object obj) {
		return  obj != null && !"".equals(obj.toString().trim());
	}
	
	/**
	 * 日期格式化
	 * @param time 时间
	 * @param format 需要的格式
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月25日 上午10:20:05
	 */
	public static String dateToString(Date time,String format)  {  
	    SimpleDateFormat formatter = new  SimpleDateFormat (format); //定义将日期格式要换成的格式  
	    return formatter.format(time);  
    } 
}
