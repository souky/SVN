package com.jy.moudles.spDetailStep.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.spDetailStep.entity.SpDetailStep;
import com.jy.moudles.spDetailStep.service.SpDetailStepService;

/** 
 * spDetailStep实现类
 *
 * 创建人：1
 * 创建时间：2018-01-08
 */
@Controller
@RequestMapping(value="/spdetailstep")
public class SpDetailStepController {
	
	@Autowired
	private SpDetailStepService spdetailstepService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SpDetailStepController.class);
	
	/**
	 * 新增spdetailstep对象
	 * 
	 * @param spdetailstep
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSpDetailStep", method = RequestMethod.POST)
	public AsyncResponseData.ResultData saveSpDetailStep(@RequestBody SpDetailStep spdetailstep) throws Exception{
		LOGGER.info("新增SpDetailStep Start");
		
		spdetailstepService.insertSpDetailStep(spdetailstep);
		
		LOGGER.info("新增SpDetailStep End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改spdetailstep对象
	 * 
	 * @param spdetailstep
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSpDetailStep", method = RequestMethod.POST)
	public AsyncResponseData.ResultData updateSpDetailStep(@RequestBody SpDetailStep spdetailstep) throws Exception{
		LOGGER.info("修改SpDetailStep Start");
		
		spdetailstepService.updateSpDetailStep(spdetailstep);
		
		LOGGER.info("修改SpDetailStep End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除spdetailstep对象
	 * 
	 * @param spdetailstep
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSpDetailStep", method = RequestMethod.POST)
	public AsyncResponseData.ResultData deleteSpDetailStep(@RequestBody SpDetailStep spdetailstep) throws Exception{
		LOGGER.info("删除SpDetailStep Start");
		
		spdetailstepService.deleteSpDetailStepById(spdetailstep.getId());
		
		LOGGER.info("删除SpDetailStep End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取spdetailstep对象
	 * 
	 * @param spdetailstep
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySpDetailSteps", method = RequestMethod.POST)
	public AsyncResponseData.ResultData querySpDetailSteps(@RequestBody SpDetailStep spdetailstep) throws Exception{
		LOGGER.info("获取SpDetailStep Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<SpDetailStep> spdetailsteps= spdetailstepService.querySpDetailStepsFilter(filter);
		LOGGER.info("获取SpDetailStep End");
		
		return AsyncResponseData.getSuccess(spdetailsteps);
	}
	
}
