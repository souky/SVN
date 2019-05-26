package com.jy.moudles.examPlaceInfo.controller;

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
import com.jy.moudles.examPlaceInfo.entity.ExamPlaceInfo;
import com.jy.moudles.examPlaceInfo.service.ExamPlaceInfoService;

/** 
 * ExamPlaceInfo实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-04
 */
@Controller
@RequestMapping(value="/examplaceinfo")
public class ExamPlaceInfoController {
	
	@Autowired
	private ExamPlaceInfoService examplaceinfoService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExamPlaceInfoController.class);
	
	/**
	 * 新增examplaceinfo对象
	 * 
	 * @param examplaceinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExamPlaceInfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveExamPlaceInfo(ExamPlaceInfo examplaceinfo) throws Exception{
		logger.info("新增ExamPlaceInfo Start");
		
		examplaceinfoService.insertExamPlaceInfo(examplaceinfo);
		
		logger.info("新增ExamPlaceInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改examplaceinfo对象
	 * 
	 * @param examplaceinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateExamPlaceInfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateExamPlaceInfo(ExamPlaceInfo examplaceinfo) throws Exception{
		logger.info("修改ExamPlaceInfo Start");
		
		examplaceinfoService.updateExamPlaceInfo(examplaceinfo);
		
		logger.info("修改ExamPlaceInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除examplaceinfo对象
	 * 
	 * @param examplaceinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteExamPlaceInfo", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteExamPlaceInfo(ExamPlaceInfo examplaceinfo) throws Exception{
		logger.info("删除ExamPlaceInfo Start");
		
		
		logger.info("删除ExamPlaceInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取examplaceinfo对象
	 * 
	 * @param examplaceinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryExamPlaceInfos", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryExamPlaceInfos(ExamPlaceInfo examplaceinfo) throws Exception{
		logger.info("获取ExamPlaceInfo Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ExamPlaceInfo> examplaceinfos= examplaceinfoService.queryExamPlaceInfosFilter(filter);
		logger.info("获取ExamPlaceInfo End");
		
		return AsyncResponseData.getSuccess(examplaceinfos);
	}
	
	/**
	 * 根据ID获取examplaceinfo对象
	 * 
	 * @param examplaceinfo
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExamPlaceInfoById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getExamPlaceInfoById(String id) throws Exception{
		logger.info("获取ExamPlaceInfo Start");
		
		ExamPlaceInfo examplaceinfo = new ExamPlaceInfo();
		
		examplaceinfo = examplaceinfoService.getExamPlaceInfoById(id);
		
		logger.info("获取ExamPlaceInfo End");
		
		return AsyncResponseData.getSuccess(examplaceinfo);
	}
	
}
