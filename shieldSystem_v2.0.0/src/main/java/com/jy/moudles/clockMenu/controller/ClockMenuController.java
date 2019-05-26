package com.jy.moudles.clockMenu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.config.Global;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.clockMenu.entity.ClockMenu;
import com.jy.moudles.clockMenu.service.ClockMenuService;
import com.jy.moudles.clockUser.entity.ClockUser;

/** 
 * clockMenu实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Controller
@RequestMapping(value="/clockmenu")
public class ClockMenuController {
	
	@Autowired
	private ClockMenuService clockmenuService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClockMenuController.class);
	
	/**
	 * 新增clockmenu对象
	 * 
	 * @param clockmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClockMenu", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveClockMenu(ClockMenu clockmenu) throws Exception{
		logger.info("新增ClockMenu Start");
		
		clockmenuService.insertClockMenu(clockmenu);
		
		logger.info("新增ClockMenu End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改clockmenu对象
	 * 
	 * @param clockmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClockMenu", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateClockMenu(ClockMenu clockmenu) throws Exception{
		logger.info("修改ClockMenu Start");
		
		clockmenuService.updateClockMenu(clockmenu);
		
		logger.info("修改ClockMenu End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除clockmenu对象
	 * 
	 * @param clockmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClockMenu", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteClockMenu(ClockMenu clockmenu) throws Exception{
		logger.info("删除ClockMenu Start");
		
		clockmenuService.deleteClockMenuById(clockmenu.getId());
		
		logger.info("删除ClockMenu End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取clockmenu对象
	 * 
	 * @param clockmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClockMenus", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClockMenus(ClockMenu clockmenu,HttpServletRequest request) throws Exception{
		logger.info("获取ClockMenu Start");
		
		ClockUser clockUser = (ClockUser) request.getSession().getAttribute(Global.USERSESSION);
		if(null == clockUser) {
			return AsyncResponseData.getDenied("用户未登录");
		}
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ClockMenu> clockmenus = clockmenuService.queryClockMenusFilter(filter);
		
		//处理菜单
		List<ClockMenu> lists = new ArrayList<>();
		for(ClockMenu e : clockmenus) {
			if(e.getMenuLevel() == 1) {
				List<ClockMenu> listNextLevel = new ArrayList<>();
				for(ClockMenu ee : clockmenus) {
					if(ee.getMenuLevel() == 2 && e.getId().equals(ee.getpId())) {
						listNextLevel.add(ee);
					}
				}
				e.setListNextLevel(listNextLevel);
				lists.add(e);
			}
		}
		
		logger.info("获取ClockMenu End");
		
		return AsyncResponseData.getSuccess(lists);
	}
	
	/**
	 * 根据ID获取clockmenu对象
	 * 
	 * @param clockmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClockMenuById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClockMenuById(String id) throws Exception{
		logger.info("获取ClockMenu Start");
		
		ClockMenu clockmenu = new ClockMenu();
		
		clockmenu = clockmenuService.getClockMenuById(id);
		
		logger.info("获取ClockMenu End");
		
		return AsyncResponseData.getSuccess(clockmenu);
	}
	
}
