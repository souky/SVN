package com.jy.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.jy.common.entity.MenuInfo;

/**
 * 菜单工具类
 * 
 * @author jinxiaoxiang@jrycn.cn
 * @since 2017-12-17
 *
 */
public class MenuUtil {
	
	// 5个0字符串
	private static final String FIVE_ZERO_STRING = "00000";
	
	// 分隔符（-）
	private static final String SEPARATOR_LINE = "-";
	
	// BSID分割后数组长度
	private static final int BSID_ARRAY_LENGTH = 5;
	
	// BSID长度
	private static final int BSID_LENGTH = 29;
	
	// 正则表达式校验BSID
	private static final String PATTERN_BSID_REGEX = "^([0-9]{5}\\-){4}[0-9]{5}$";
	
	// 系统标识位BSID数组位置
	private static final int SYSTEM_IDENTIFICATION_MENU_INDEX = 0;
		
	// 操作系统类型BSID数组位置
	private static final int SYSTEM_OS_TYPE_MENU_INDEX = 1;
	
	// 一级菜单BSID数组位置
	private static final int FIRST_LEVEL_MENU_INDEX = 2;
	
	// 二级菜单BSID数组位置
	private static final int SECOND_LEVEL_MENU_INDEX = 3;
	
	// 三级菜单BSID数组位置
	private static final int THIRD_LEVEL_MENU_INDEX = 4;
	
	/**
	 * 重新拼装菜单，符合输出菜单数据类型
	 * 当前系统最多含有3级菜单
	 * 菜单bsid构成：系统标识-操作系统类型（0：PC，1：安卓，2：IOS）-一级菜单-二级菜单-三级菜单
	 * 每级菜单编号从1开始
	 * 例： 一级菜单：00000-00000-00001-00000-00000
	 *    二级菜单：00000-00001-00002-00001-00000
	 *    三级菜单：00000-00002-00003-00002-00001
	 * 
	 * @param menuInfos
	 * @return
	 */
	public static List<MenuInfo> reassemblyMenuInfo(List<MenuInfo> menuInfos) {
		List<MenuInfo> reassemblyMenuInfos = new ArrayList<MenuInfo>();
		if (null != menuInfos) {
			// 一级菜单
			Map<String, List<MenuInfo>> firstLevelMeun = new HashMap<String, List<MenuInfo>>();
			// 二级菜单
			Map<String, List<MenuInfo>> secondLevelMeun = new HashMap<String, List<MenuInfo>>();
			// 三级菜单
			Map<String, List<MenuInfo>> thirdLevelMeun = new HashMap<String, List<MenuInfo>>();
			
			
			for (MenuInfo menuInfo : menuInfos) {
				String bsid = menuInfo.getBsid();
				if (!StringUtils.isBlank(bsid) && BSID_LENGTH == bsid.length()) {
					Pattern p = Pattern.compile(PATTERN_BSID_REGEX);
					Matcher m = p.matcher(bsid);
					if (m.matches()) {
						String[] bsids = bsid.split(SEPARATOR_LINE);
						
						if (BSID_ARRAY_LENGTH == bsids.length) {
							if (FIVE_ZERO_STRING.equals(bsids[THIRD_LEVEL_MENU_INDEX]) && FIVE_ZERO_STRING.equals(bsids[SECOND_LEVEL_MENU_INDEX])) {
								// 一级菜单key
								String key = bsids[SYSTEM_IDENTIFICATION_MENU_INDEX] + SEPARATOR_LINE + bsids[SYSTEM_OS_TYPE_MENU_INDEX];
								List<MenuInfo> firstLevelMeuns = firstLevelMeun.get(key);
								
								if (null == firstLevelMeuns) {
									firstLevelMeuns = new ArrayList<MenuInfo>();
								}
								
								firstLevelMeuns.add(menuInfo);
								firstLevelMeun.put(key, firstLevelMeuns);
							} else if (FIVE_ZERO_STRING.equals(bsids[THIRD_LEVEL_MENU_INDEX])) {
								// 二级菜单key
								String key = bsids[SYSTEM_IDENTIFICATION_MENU_INDEX] + SEPARATOR_LINE + bsids[SYSTEM_OS_TYPE_MENU_INDEX] + SEPARATOR_LINE + bsids[FIRST_LEVEL_MENU_INDEX];
								List<MenuInfo> secondLevelMeuns = secondLevelMeun.get(key);
								
								if (null == secondLevelMeuns) {
									secondLevelMeuns = new ArrayList<MenuInfo>();
								}
								
								secondLevelMeuns.add(menuInfo);
								secondLevelMeun.put(key, secondLevelMeuns);
							} else {
								// 三级菜单
								String key = bsids[SYSTEM_IDENTIFICATION_MENU_INDEX] + SEPARATOR_LINE + bsids[SYSTEM_OS_TYPE_MENU_INDEX] + SEPARATOR_LINE + bsids[FIRST_LEVEL_MENU_INDEX]
										+ SEPARATOR_LINE +  bsids[SECOND_LEVEL_MENU_INDEX];
								List<MenuInfo> thirdLevelMeuns = thirdLevelMeun.get(key);
								
								if (null == thirdLevelMeuns) {
									thirdLevelMeuns = new ArrayList<MenuInfo>();
								}
								
								thirdLevelMeuns.add(menuInfo);
								thirdLevelMeun.put(key, thirdLevelMeuns);
							}
						}
					}
				}
			}
			
			// 先拼接二级菜单
			for (Map.Entry<String, List<MenuInfo>> entry : secondLevelMeun.entrySet()) {
				List<MenuInfo> value = entry.getValue();
				for (MenuInfo menuInfo : value) {
					String thirdLevelMenuKey = menuInfo.getBsid().substring(0, 23);
					List<MenuInfo> thirdLevelMeuns = thirdLevelMeun.get(thirdLevelMenuKey);
					if (null == thirdLevelMeuns) {
						thirdLevelMeuns = new ArrayList<MenuInfo>();
					}
					menuInfo.setChildren(thirdLevelMeuns);
				}
			}
			
			// 拼接一级菜单
			for (Map.Entry<String, List<MenuInfo>> entry : firstLevelMeun.entrySet()) {
				List<MenuInfo> value = entry.getValue();
				for (MenuInfo menuInfo : value) {
					String secondLevelMenuKey = menuInfo.getBsid().substring(0, 17);
					List<MenuInfo> secondLevelMeuns = secondLevelMeun.get(secondLevelMenuKey);
					
					if (null == secondLevelMeuns) {
						secondLevelMeuns = new ArrayList<MenuInfo>();
					}
					menuInfo.setChildren(secondLevelMeuns);
				}
			}
			
			// 拼接一级菜单
			for (Map.Entry<String, List<MenuInfo>> entry : firstLevelMeun.entrySet()) {
				List<MenuInfo> value = entry.getValue();
				for (MenuInfo menuInfo : value) {
					reassemblyMenuInfos.add(menuInfo);
				}
			}
		}
		
		return reassemblyMenuInfos;
		
	}
	
	public static void main(String[] args) {
		List<MenuInfo> menuInfos = new ArrayList<MenuInfo>();
		MenuInfo menuInfo = new MenuInfo();
		menuInfo.setId("13");
		menuInfo.setBsid("00000-00000-00004-00000-00000");
		menuInfo.setIcon("el-icon-setting");
		menuInfo.setMenuName("系统设置");
		menuInfo.setMenuOrder(4);
		menuInfo.setPath("");
		menuInfos.add(menuInfo);
		
		MenuInfo menuInfo1 = new MenuInfo();
		menuInfo1.setId("14");
		menuInfo1.setBsid("00000-00000-00004-00001-00000");
		menuInfo1.setIcon("el-icon-arrow-right");
		menuInfo1.setMenuName("角色管理");
		menuInfo1.setMenuOrder(0);
		menuInfo1.setPath("roleManager");
		menuInfos.add(menuInfo1);
		
		MenuInfo menuInfo2 = new MenuInfo();
		menuInfo2.setId("15");
		menuInfo2.setBsid("00000-00000-00004-00002-00000");
		menuInfo2.setIcon("el-icon-arrow-right");
		menuInfo2.setMenuName("用户管理");
		menuInfo2.setMenuOrder(1);
		menuInfo2.setPath("userManager");
		menuInfos.add(menuInfo2);
		
		MenuInfo menuInfo3 = new MenuInfo();
		menuInfo3.setId("16");
		menuInfo3.setBsid("00000-00000-00004-00003-00000");
		menuInfo3.setIcon("el-icon-arrow-right");
		menuInfo3.setMenuName("机构管理");
		menuInfo3.setMenuOrder(2);
		menuInfo3.setPath("orgManager");
		menuInfos.add(menuInfo3);
		
		reassemblyMenuInfo(menuInfos);
		
	}
}
