package com.jy.moudles.examStudentRelation.controller;

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
import com.jy.moudles.examStudentRelation.entity.ExamStudentRelation;
import com.jy.moudles.examStudentRelation.service.ExamStudentRelationService;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * examStudentRelation实现类
 *
 * 创建人：1
 * 创建时间：2017-11-30
 */
@Controller
@RequestMapping(value="/examstudentrelation")
public class ExamStudentRelationController {
	
	@Autowired
	private ExamStudentRelationService examstudentrelationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamStudentRelationController.class);
	
	/**
	 * 新增examstudentrelation对象
	 * 
	 * @param examstudentrelation
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExamStudentRelation", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveExamStudentRelation(ExamStudentRelation examstudentrelation) throws Exception{
		LOGGER.info("新增ExamStudentRelation Start");
		
		examstudentrelationService.insertExamStudentRelation(examstudentrelation);
		
		LOGGER.info("新增ExamStudentRelation End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改examstudentrelation对象
	 * 
	 * @param examstudentrelation
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateExamStudentRelation", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateExamStudentRelation(ExamStudentRelation examstudentrelation) throws Exception{
		LOGGER.info("修改ExamStudentRelation Start");
		
		examstudentrelationService.updateExamStudentRelation(examstudentrelation);
		
		LOGGER.info("修改ExamStudentRelation End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取examstudentrelation对象
	 * 
	 * @param examstudentrelation
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryExamStudentRelations", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryExamStudentRelations(ExamStudentRelation examstudentrelation) throws Exception{
		LOGGER.info("获取ExamStudentRelation Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ExamStudentRelation> examstudentrelations= examstudentrelationService.queryExamStudentRelationsFilter(filter);
		LOGGER.info("获取ExamStudentRelation End");
		
		return AsyncResponseData.getSuccess(examstudentrelations);
	}
	
}
