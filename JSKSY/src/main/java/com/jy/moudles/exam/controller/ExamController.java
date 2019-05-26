package com.jy.moudles.exam.controller;

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
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;

/** 
 * Exam实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Controller
@RequestMapping(value="/exam")
public class ExamController {
	
	@Autowired
	private ExamService examService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExamController.class);
	
	/**
	 * 新增exam对象
	 * 
	 * @param exam
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExam", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveExam(Exam exam) throws Exception{
		logger.info("新增Exam Start");
		
		examService.insertExam(exam);
		
		logger.info("新增Exam End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改exam对象
	 * 
	 * @param exam
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateExam", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateExam(Exam exam) throws Exception{
		logger.info("修改Exam Start");
		
		examService.updateExam(exam);
		
		logger.info("修改Exam End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除exam对象
	 * 
	 * @param exam
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteExam", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteExam(Exam exam) throws Exception{
		logger.info("删除Exam Start");
		
		examService.deleteExamByNo(exam.getKsbh());
		
		logger.info("删除Exam End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取exam对象
	 * 
	 * @param exam
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryExams", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryExams(Exam exam) throws Exception{
		logger.info("获取Exam Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<Exam> exams= examService.queryExamsFilter(filter);
		logger.info("获取Exam End");
		
		return AsyncResponseData.getSuccess(exams);
	}
	
	/**
	 * 根据ID获取exam对象
	 * 
	 * @param exam
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExamById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getExamById(String ksbh) throws Exception{
		logger.info("获取Exam Start");
		
		Exam exam = new Exam();
		
		exam = examService.getExamByNo(ksbh);
		
		logger.info("获取Exam End");
		
		return AsyncResponseData.getSuccess(exam);
	}
	
}
