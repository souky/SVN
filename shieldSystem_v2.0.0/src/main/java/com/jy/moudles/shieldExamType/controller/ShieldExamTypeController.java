package com.jy.moudles.shieldExamType.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.shieldExamType.entity.ShieldExamType;
import com.jy.moudles.shieldExamType.service.ShieldExamTypeService;

/** 
 * shieldExamType实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Controller
@RequestMapping(value="/shieldexamtype")
public class ShieldExamTypeController {
	
	@Autowired
	private ShieldExamTypeService shieldexamtypeService;
	
	private static final Logger logger = LoggerFactory.getLogger(ShieldExamTypeController.class);
	
	/**
	 * 新增shieldexamtype对象
	 * 
	 * @param shieldexamtype
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveShieldExamType", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveShieldExamType(ShieldExamType shieldexamtype) throws Exception{
		logger.info("新增ShieldExamType Start");
		
		shieldexamtypeService.insertShieldExamType(shieldexamtype);
		
		logger.info("新增ShieldExamType End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改shieldexamtype对象
	 * 
	 * @param shieldexamtype
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShieldExamType", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateShieldExamType(ShieldExamType shieldexamtype) throws Exception{
		logger.info("修改ShieldExamType Start");
		
		shieldexamtypeService.updateShieldExamType(shieldexamtype);
		
		logger.info("修改ShieldExamType End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除shieldexamtype对象
	 * 
	 * @param shieldexamtype
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldExamType", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldExamType(ShieldExamType shieldexamtype) throws Exception{
		logger.info("删除ShieldExamType Start");
		
		shieldexamtypeService.deleteShieldExamTypeById(shieldexamtype.getId());
		
		logger.info("删除ShieldExamType End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 批量删除shieldexamtype对象
	 * 
	 * @param shieldexamtype
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldExamTypes", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldExamTypes(String ids) throws Exception{
		logger.info("删除ShieldExamType Start");
		
		if(StringUtils.isBlank(ids)) {
			return AsyncResponseData.getSuccess().asParamError("请选择删除项");
		}
		String[] ids_array = ids.split(",");
		List<String> list = Arrays.asList(ids_array);
		shieldexamtypeService.deleteShieldExamTypes(list);
		
		logger.info("删除ShieldExamType End");
		return AsyncResponseData.getSuccess();
	}
	
	
	
	/**
	 * 获取shieldexamtype对象
	 * 
	 * @param shieldexamtype
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryShieldExamTypes", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryShieldExamTypes(int pageNum,int pageSize) throws Exception{
		logger.info("获取ShieldExamType Start");
		
		
		PageHelper.startPage(pageNum, pageSize);
		
		PageInfo<ShieldExamType> shieldexamtypes = new PageInfo<ShieldExamType>(shieldexamtypeService.queryShieldExamTypesFilter(null));
		
		logger.info("获取ShieldExamType End");
		
		return AsyncResponseData.getSuccess(shieldexamtypes);
	}
	
	/**
	 * 根据ID获取shieldexamtype对象
	 * 
	 * @param shieldexamtype
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShieldExamTypeById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getShieldExamTypeById(String id) throws Exception{
		logger.info("获取ShieldExamType Start");
		
		ShieldExamType shieldexamtype = new ShieldExamType();
		
		shieldexamtype = shieldexamtypeService.getShieldExamTypeById(id);
		
		logger.info("获取ShieldExamType End");
		
		return AsyncResponseData.getSuccess(shieldexamtype);
	}
	
}
