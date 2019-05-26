package com.jy.moudles.examSeasonSubject.controller;

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
import com.jy.moudles.examSeasonSubject.entity.ExamSeasonSubject;
import com.jy.moudles.examSeasonSubject.service.ExamSeasonSubjectService;

/** 
 * ExamSeasonSubject实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Controller
@RequestMapping(value="/examseasonsubject")
public class ExamSeasonSubjectController {
	
	@Autowired
	private ExamSeasonSubjectService examseasonsubjectService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExamSeasonSubjectController.class);
	
	/**
	 * 新增examseasonsubject对象
	 * 
	 * @param examseasonsubject
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExamSeasonSubject", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveExamSeasonSubject(ExamSeasonSubject examseasonsubject) throws Exception{
		logger.info("新增ExamSeasonSubject Start");
		
		examseasonsubjectService.insertExamSeasonSubject(examseasonsubject);
		
		logger.info("新增ExamSeasonSubject End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改examseasonsubject对象
	 * 
	 * @param examseasonsubject
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateExamSeasonSubject", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateExamSeasonSubject(ExamSeasonSubject examseasonsubject) throws Exception{
		logger.info("修改ExamSeasonSubject Start");
		
		examseasonsubjectService.updateExamSeasonSubject(examseasonsubject);
		
		logger.info("修改ExamSeasonSubject End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除examseasonsubject对象
	 * 
	 * @param examseasonsubject
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteExamSeasonSubject", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteExamSeasonSubject(ExamSeasonSubject examseasonsubject) throws Exception{
		logger.info("删除ExamSeasonSubject Start");
		
		examseasonsubjectService.deleteExamSeasonSubjectBySeasonNo(examseasonsubject.getExamSeasonNo());
		
		logger.info("删除ExamSeasonSubject End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取examseasonsubject对象
	 * 
	 * @param examseasonsubject
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryExamSeasonSubjects", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryExamSeasonSubjects(ExamSeasonSubject examseasonsubject) throws Exception{
		logger.info("获取ExamSeasonSubject Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ExamSeasonSubject> examseasonsubjects= examseasonsubjectService.queryExamSeasonSubjectsFilter(filter);
		logger.info("获取ExamSeasonSubject End");
		
		return AsyncResponseData.getSuccess(examseasonsubjects);
	}
	
	/**
	 * 根据ID获取examseasonsubject对象
	 * 
	 * @param examseasonsubject
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExamSeasonSubjectById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getExamSeasonSubjectById(String id) throws Exception{
		logger.info("获取ExamSeasonSubject Start");
		
		ExamSeasonSubject examseasonsubject = new ExamSeasonSubject();
		
		examseasonsubject = examseasonsubjectService.getExamSeasonSubjectById(id);
		
		logger.info("获取ExamSeasonSubject End");
		
		return AsyncResponseData.getSuccess(examseasonsubject);
	}
	
}
