package com.jy.moudles.examOrgInfo.controller;

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
import com.jy.moudles.examOrgInfo.entity.ExamOrgInfo;
import com.jy.moudles.examOrgInfo.service.ExamOrgInfoService;

/** 
 * ExamOrgInfo实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-04
 */
@Controller
@RequestMapping(value="/examorginfo")
public class ExamOrgInfoController {
	
	@Autowired
	private ExamOrgInfoService examorginfoService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExamOrgInfoController.class);
	
	/**
	 * 新增examorginfo对象
	 * 
	 * @param examorginfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExamOrgInfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveExamOrgInfo(ExamOrgInfo examorginfo) throws Exception{
		logger.info("新增ExamOrgInfo Start");
		
		examorginfoService.insertExamOrgInfo(examorginfo);
		
		logger.info("新增ExamOrgInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改examorginfo对象
	 * 
	 * @param examorginfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateExamOrgInfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateExamOrgInfo(ExamOrgInfo examorginfo) throws Exception{
		logger.info("修改ExamOrgInfo Start");
		
		examorginfoService.updateExamOrgInfo(examorginfo);
		
		logger.info("修改ExamOrgInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除examorginfo对象
	 * 
	 * @param examorginfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteExamOrgInfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteExamOrgInfo(ExamOrgInfo examorginfo) throws Exception{
		logger.info("删除ExamOrgInfo Start");
		
		examorginfoService.deleteExamOrgInfoById(examorginfo.getId());
		
		logger.info("删除ExamOrgInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取examorginfo对象
	 * 
	 * @param examorginfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryExamOrgInfos", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryExamOrgInfos(ExamOrgInfo examorginfo) throws Exception{
		logger.info("获取ExamOrgInfo Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ExamOrgInfo> examorginfos= examorginfoService.queryExamOrgInfosFilter(filter);
		logger.info("获取ExamOrgInfo End");
		
		return AsyncResponseData.getSuccess(examorginfos);
	}
	
	/**
	 * 根据ID获取examorginfo对象
	 * 
	 * @param examorginfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExamOrgInfoById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getExamOrgInfoById(String id) throws Exception{
		logger.info("获取ExamOrgInfo Start");
		
		ExamOrgInfo examorginfo = new ExamOrgInfo();
		
		examorginfo = examorginfoService.getExamOrgInfoById(id);
		
		logger.info("获取ExamOrgInfo End");
		
		return AsyncResponseData.getSuccess(examorginfo);
	}
	
}
