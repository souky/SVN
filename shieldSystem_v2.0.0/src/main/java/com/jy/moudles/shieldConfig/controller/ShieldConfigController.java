package com.jy.moudles.shieldConfig.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.shieldConfig.entity.ShieldConfig;
import com.jy.moudles.shieldConfig.service.ShieldConfigService;

/** 
 * ShieldConfig实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Controller
@RequestMapping(value="/shieldconfig")
public class ShieldConfigController {
	
	@Autowired
	private ShieldConfigService shieldconfigService;
	
	private static final Logger logger = LoggerFactory.getLogger(ShieldConfigController.class);
	
	/**
	 * 新增shieldconfig对象
	 * 
	 * @param shieldconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveShieldConfig", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveShieldConfig(ShieldConfig shieldconfig) throws Exception{
		logger.info("新增ShieldConfig Start");
		
		shieldconfigService.insertShieldConfig(shieldconfig);
		
		logger.info("新增ShieldConfig End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改shieldconfig对象
	 * 
	 * @param shieldconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShieldConfig", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateShieldConfig(ShieldConfig shieldconfig) throws Exception{
		logger.info("修改ShieldConfig Start");
		
		shieldconfigService.updateShieldConfig(shieldconfig);
		
		logger.info("修改ShieldConfig End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除shieldconfig对象
	 * 
	 * @param shieldconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldConfig", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldConfig(ShieldConfig shieldconfig) throws Exception{
		logger.info("删除ShieldConfig Start");
		
		shieldconfigService.deleteShieldConfigById(shieldconfig.getId());
		
		logger.info("删除ShieldConfig End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取shieldconfig对象
	 * 
	 * @param shieldconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryShieldConfigs", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryShieldConfigs(ShieldConfig shieldconfig) throws Exception{
		logger.info("获取ShieldConfig Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ShieldConfig> shieldconfigs= shieldconfigService.queryShieldConfigsFilter(filter);
		logger.info("获取ShieldConfig End");
		
		return AsyncResponseData.getSuccess(shieldconfigs);
	}
	
	/**
	 * 根据ID获取shieldconfig对象
	 * 
	 * @param shieldconfig
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShieldConfigById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getShieldConfigById(String id) throws Exception{
		logger.info("获取ShieldConfig Start");
		
		ShieldConfig shieldconfig = new ShieldConfig();
		
		shieldconfig = shieldconfigService.getShieldConfigById(id);
		
		logger.info("获取ShieldConfig End");
		
		return AsyncResponseData.getSuccess(shieldconfig);
	}
	
}
