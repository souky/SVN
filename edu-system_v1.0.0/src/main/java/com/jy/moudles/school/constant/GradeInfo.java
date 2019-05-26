package com.jy.moudles.school.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradeInfo {

	public static Map<String,List<String>> GRADE_MAP = new HashMap<>();
	
	static {
		List<String> list_one = new ArrayList<String>();
		List<String> list_two = new ArrayList<String>();
		List<String> list_three = new ArrayList<String>();
		
		list_one.add("一年级");
		list_one.add("二年级");
		list_one.add("三年级");
		list_one.add("四年级");
		list_one.add("五年级");
		list_one.add("六年级");
		
		list_two.add("七年级");
		list_two.add("八年级");
		list_two.add("九年级");
		
		list_three.add("高一");
		list_three.add("高二");
		list_three.add("高三");
		
		GRADE_MAP.put("小学", list_one);
		GRADE_MAP.put("初中", list_two);
		GRADE_MAP.put("高中", list_three);
	}
}
