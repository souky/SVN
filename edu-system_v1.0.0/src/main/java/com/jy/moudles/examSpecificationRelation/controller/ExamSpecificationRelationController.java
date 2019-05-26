package com.jy.moudles.examSpecificationRelation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.examSpecificationRelation.entity.ExamSpecificationRelation;
import com.jy.moudles.examSpecificationRelation.service.ExamSpecificationRelationService;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * examSpecificationRelation实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/examspecificationrelation")
public class ExamSpecificationRelationController {
	
	@Autowired
	private ExamSpecificationRelationService examspecificationrelationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamSpecificationRelationController.class);
	
	/**
	 * 新增examspecificationrelation对象
	 * 
	 * @param examspecificationrelation
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExamSpecificationRelation", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveExamSpecificationRelation(ExamSpecificationRelation examspecificationrelation) throws Exception{
		LOGGER.info("新增ExamSpecificationRelation Start");

		examspecificationrelationService.insertExamSpecificationRelation(examspecificationrelation);
		
		LOGGER.info("新增ExamSpecificationRelation End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 修改examspecificationrelation对象
	 * 
	 * @param examspecificationrelation
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateExamSpecificationRelation", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateExamSpecificationRelation(ExamSpecificationRelation examspecificationrelation) throws Exception{
		LOGGER.info("修改ExamSpecificationRelation Start");
		
		examspecificationrelationService.updateExamSpecificationRelation(examspecificationrelation);
		
		LOGGER.info("修改ExamSpecificationRelation End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取examspecificationrelation对象
	 * 
	 * @param examspecificationrelation
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryExamSpecificationRelations", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryExamSpecificationRelations(ExamSpecificationRelation examspecificationrelation) throws Exception{
		LOGGER.info("获取ExamSpecificationRelation Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ExamSpecificationRelation> examspecificationrelations= examspecificationrelationService.queryExamSpecificationRelationsFilter(filter);
		LOGGER.info("获取ExamSpecificationRelation End");
		
		return AsyncResponseData.getSuccess(examspecificationrelations);
	}
	
}
