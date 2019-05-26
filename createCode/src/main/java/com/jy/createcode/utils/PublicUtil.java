package com.jy.createcode.utils;


public class PublicUtil {

	public static String getPorjectPath() {
		String nowpath = "";
		nowpath = System.getProperty("user.dir") + "/";
		return nowpath;
	}
}