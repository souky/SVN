package com.jy.moudles.spDetailKnowledgeRelation.controller;

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
import com.jy.moudles.spDetailKnowledgeRelation.entity.SpDetailKnowledgeRelation;
import com.jy.moudles.spDetailKnowledgeRelation.service.SpDetailKnowledgeRelationService;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * spDetailKnowledgeRelation实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/spdetailknowledgerelation")
public class SpDetailKnowledgeRelationController {
	
	@Autowired
	private SpDetailKnowledgeRelationService spdetailknowledgerelationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpDetailKnowledgeRelationController.class);
	
	/**
	 * 新增spdetailknowledgerelation对象
	 * 
	 * @param spdetailknowledgerelation
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSpDetailKnowledgeRelation", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveSpDetailKnowledgeRelation(SpDetailKnowledgeRelation spdetailknowledgerelation) throws Exception{
		LOGGER.info("新增SpDetailKnowledgeRelation Start");
		
		spdetailknowledgerelationService.insertSpDetailKnowledgeRelation(spdetailknowledgerelation);
		
		LOGGER.info("新增SpDetailKnowledgeRelation End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改spdetailknowledgerelation对象
	 * 
	 * @param spdetailknowledgerelation
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSpDetailKnowledgeRelation", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateSpDetailKnowledgeRelation(SpDetailKnowledgeRelation spdetailknowledgerelation) throws Exception{
		LOGGER.info("修改SpDetailKnowledgeRelation Start");
		
		spdetailknowledgerelationService.updateSpDetailKnowledgeRelation(spdetailknowledgerelation);
		
		LOGGER.info("修改SpDetailKnowledgeRelation End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取spdetailknowledgerelation对象
	 * 
	 * @param spdetailknowledgerelation
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySpDetailKnowledgeRelations", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData querySpDetailKnowledgeRelations(SpDetailKnowledgeRelation spdetailknowledgerelation) throws Exception{
		LOGGER.info("获取SpDetailKnowledgeRelation Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<SpDetailKnowledgeRelation> spdetailknowledgerelations= spdetailknowledgerelationService.querySpDetailKnowledgeRelationsFilter(filter);
		LOGGER.info("获取SpDetailKnowledgeRelation End");
		
		return AsyncResponseData.getSuccess(spdetailknowledgerelations);
	}
	
}
