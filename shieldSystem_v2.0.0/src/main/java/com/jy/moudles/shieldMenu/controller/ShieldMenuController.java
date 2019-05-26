package com.jy.moudles.shieldMenu.controller;

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
import com.jy.moudles.shieldMenu.entity.ShieldMenu;
import com.jy.moudles.shieldMenu.service.ShieldMenuService;
import com.jy.moudles.shieldUser.entity.ShieldUser;

/** 
 * shieldMenu实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Controller
@RequestMapping(value="/shieldmenu")
public class ShieldMenuController {
	
	@Autowired
	private ShieldMenuService shieldmenuService;
	
	private static final Logger logger = LoggerFactory.getLogger(ShieldMenuController.class);
	
	/**
	 * 新增shieldmenu对象
	 * 
	 * @param shieldmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveShieldMenu", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveShieldMenu(ShieldMenu shieldmenu) throws Exception{
		logger.info("新增ShieldMenu Start");
		
		shieldmenuService.insertShieldMenu(shieldmenu);
		
		logger.info("新增ShieldMenu End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改shieldmenu对象
	 * 
	 * @param shieldmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShieldMenu", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateShieldMenu(ShieldMenu shieldmenu) throws Exception{
		logger.info("修改ShieldMenu Start");
		
		shieldmenuService.updateShieldMenu(shieldmenu);
		
		logger.info("修改ShieldMenu End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除shieldmenu对象
	 * 
	 * @param shieldmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldMenu", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldMenu(ShieldMenu shieldmenu) throws Exception{
		logger.info("删除ShieldMenu Start");
		
		shieldmenuService.deleteShieldMenuById(shieldmenu.getId());
		
		logger.info("删除ShieldMenu End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取shieldmenu对象
	 * 
	 * @param shieldmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryShieldMenus", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryShieldMenus(ShieldMenu shieldmenu,HttpServletRequest request) throws Exception{
		logger.info("获取ShieldMenu Start");
		
		ShieldUser clockUser = (ShieldUser) request.getSession().getAttribute(Global.USERSESSION);
		if(null == clockUser) {
			return AsyncResponseData.getDenied("用户未登录");
		}
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ShieldMenu> shieldmenus = shieldmenuService.queryShieldMenusFilter(filter);
		
		//处理菜单
		List<ShieldMenu> lists = new ArrayList<>();
		for(ShieldMenu e : shieldmenus) {
			if(e.getMenuLevel() == 1) {
				List<ShieldMenu> listNextLevel = new ArrayList<>();
				for(ShieldMenu ee : shieldmenus) {
					if(ee.getMenuLevel() == 2 && e.getId().equals(ee.getpId())) {
						listNextLevel.add(ee);
					}
				}
				e.setListNextLevel(listNextLevel);
				lists.add(e);
			}
		}
		
		return AsyncResponseData.getSuccess(lists);
	}
	
	/**
	 * 根据ID获取shieldmenu对象
	 * 
	 * @param shieldmenu
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShieldMenuById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getShieldMenuById(String id) throws Exception{
		logger.info("获取ShieldMenu Start");
		
		ShieldMenu shieldmenu = new ShieldMenu();
		
		shieldmenu = shieldmenuService.getShieldMenuById(id);
		
		logger.info("获取ShieldMenu End");
		
		return AsyncResponseData.getSuccess(shieldmenu);
	}
	
}
