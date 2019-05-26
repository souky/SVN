package com.jy.moudles.examSeason.controller;

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
import com.jy.moudles.examSeason.entity.ExamSeason;
import com.jy.moudles.examSeason.service.ExamSeasonService;

/** 
 * ExamSeason实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Controller
@RequestMapping(value="/examseason")
public class ExamSeasonController {
	
	@Autowired
	private ExamSeasonService examseasonService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExamSeasonController.class);
	
	/**
	 * 新增examseason对象
	 * 
	 * @param examseason
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveExamSeason", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveExamSeason(ExamSeason examseason) throws Exception{
		logger.info("新增ExamSeason Start");
		
		examseasonService.insertExamSeason(examseason);
		
		logger.info("新增ExamSeason End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改examseason对象
	 * 
	 * @param examseason
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateExamSeason", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateExamSeason(ExamSeason examseason) throws Exception{
		logger.info("修改ExamSeason Start");
		
		examseasonService.updateExamSeason(examseason);
		
		logger.info("修改ExamSeason End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除examseason对象
	 * 
	 * @param examseason
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteExamSeason", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteExamSeason(ExamSeason examseason) throws Exception{
		logger.info("删除ExamSeason Start");
		
		examseasonService.deleteExamSeasonByExamNo(examseason.getExamId());
		
		logger.info("删除ExamSeason End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取examseason对象
	 * 
	 * @param examseason
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryExamSeasons", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryExamSeasons(ExamSeason examseason) throws Exception{
		logger.info("获取ExamSeason Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ExamSeason> examseasons= examseasonService.queryExamSeasonsFilter(filter);
		logger.info("获取ExamSeason End");
		
		return AsyncResponseData.getSuccess(examseasons);
	}
	
	/**
	 * 根据ID获取examseason对象
	 * 
	 * @param examseason
	 * @throws Exception
	 */
	@RequestMapping(value = "/getExamSeasonById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getExamSeasonById(String id) throws Exception{
		logger.info("获取ExamSeason Start");
		
		ExamSeason examseason = new ExamSeason();
		
		examseason = examseasonService.getExamSeasonById(id);
		
		logger.info("获取ExamSeason End");
		
		return AsyncResponseData.getSuccess(examseason);
	}
	
}
