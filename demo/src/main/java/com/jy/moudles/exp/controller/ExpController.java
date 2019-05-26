package com.jy.moudles.exp.controller;

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
import com.jy.moudles.exp.entity.Exp;
import com.jy.moudles.exp.service.ExpService;

/** 
 * Exp实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-11-09
 */
@Controller
@RequestMapping(value="/exp")
public class ExpController {
	
	@Autowired
	private ExpService expService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpController.class);
	
	/**
	 * 新增exp对象
	 * 
	 * @param exp
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExp", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveExp(Exp exp) throws Exception{
		logger.info("新增Exp Start");
		
		expService.insertExp(exp);
		
		logger.info("新增Exp End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改exp对象
	 * 
	 * @param exp
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateExp", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateExp(Exp exp) throws Exception{
		logger.info("修改Exp Start");
		
		expService.updateExp(exp);
		
		logger.info("修改Exp End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除exp对象
	 * 
	 * @param exp
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteExp", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteExp(Exp exp) throws Exception{
		logger.info("删除Exp Start");
		
		expService.deleteExpById(exp.getId());
		
		logger.info("删除Exp End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取exp对象
	 * 
	 * @param exp
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryExps", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryExps(Exp exp) throws Exception{
		logger.info("获取Exp Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<Exp> exps= expService.queryExpsFilter(filter);
		logger.info("获取Exp End");
		
		return AsyncResponseData.getSuccess(exps);
	}
	
	/**
	 * 根据ID获取exp对象
	 * 
	 * @param exp
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExpById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getExpById(String id) throws Exception{
		logger.info("获取Exp Start");
		
		Exp exp = new Exp();
		
		exp = expService.getExpById(id);
		
		logger.info("获取Exp End");
		
		return AsyncResponseData.getSuccess(exp);
	}
	
}
