package com.jy.moudles.shieldExamPlan.controller;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.shieldExamPlan.entity.ShieldExamPlan;
import com.jy.moudles.shieldExamPlan.service.ShieldExamPlanService;

/** 
 * shieldExamPlan实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Controller
@RequestMapping(value="/shieldexamplan")
public class ShieldExamPlanController {
	
	@Autowired
	private ShieldExamPlanService shieldexamplanService;
	
	private static final Logger logger = LoggerFactory.getLogger(ShieldExamPlanController.class);
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new MyDateEditor());
    }

    private class MyDateEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Date date = null;
            long longs = 0l;
            try {
            	if(StringUtils.isNotBlank(text)) {
            		longs = Long.parseLong(text);
            	}
            	if(longs != 0) {
            		date = new Date(longs);
            	}
            } catch (Exception e) {
                date = null;
            }
            setValue(date);
        }
    }
	
	/**
	 * 新增shieldexamplan对象
	 * 
	 * @param shieldexamplan
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveShieldExamPlan", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveShieldExamPlan(ShieldExamPlan shieldexamplan) throws Exception{
		logger.info("新增ShieldExamPlan Start");
		
		shieldexamplanService.insertShieldExamPlan(shieldexamplan);
		
		logger.info("新增ShieldExamPlan End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改shieldexamplan对象
	 * 
	 * @param shieldexamplan
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateShieldExamPlan", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateShieldExamPlan(ShieldExamPlan shieldexamplan) throws Exception{
		logger.info("修改ShieldExamPlan Start");
		
		shieldexamplanService.updateShieldExamPlan(shieldexamplan);
		
		logger.info("修改ShieldExamPlan End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除shieldexamplan对象
	 * 
	 * @param shieldexamplan
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldExamPlan", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldExamPlan(ShieldExamPlan shieldexamplan) throws Exception{
		logger.info("删除ShieldExamPlan Start");
		
		shieldexamplanService.deleteShieldExamPlanById(shieldexamplan.getId());
		
		logger.info("删除ShieldExamPlan End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 批量删除shieldexamplan对象
	 * 
	 * @param shieldexamtype
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteShieldExamPlans", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteShieldExamPlans(String ids) throws Exception{
		logger.info("删除ShieldExamType Start");
		
		if(StringUtils.isBlank(ids)) {
			return AsyncResponseData.getSuccess().asParamError("请选择删除项");
		}
		String[] ids_array = ids.split(",");
		List<String> list = Arrays.asList(ids_array);
		shieldexamplanService.deleteShieldExamPlans(list);
		
		logger.info("删除ShieldExamType End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取shieldexamplan对象
	 * 
	 * @param shieldexamplan
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryShieldExamPlans", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryShieldExamPlans(ShieldExamPlan shieldexamplan) throws Exception{
		logger.info("获取ShieldExamPlan Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		// 放入查询条件
		if(StringUtils.isNotBlank(shieldexamplan.getExamName())) {
			filter.put("examName", shieldexamplan.getExamName());
		}
		
		if(StringUtils.isNotBlank(shieldexamplan.getExamNo())) {
			filter.put("examNo", shieldexamplan.getExamNo());
		}
		
		if(null != shieldexamplan.getStartTime()) {
			filter.put("startTime", shieldexamplan.getStartTime());
		}
		
		if(null != shieldexamplan.getEndTime()) {
			filter.put("endTime", shieldexamplan.getEndTime());
		}
		
		PageHelper.startPage(shieldexamplan.getPageNum(), shieldexamplan.getPageSize());
		
		PageInfo<ShieldExamPlan> shieldexamplans = new PageInfo<ShieldExamPlan>(shieldexamplanService.queryShieldExamPlansFilter(filter));
		
		logger.info("获取ShieldExamPlan End");
		
		return AsyncResponseData.getSuccess(shieldexamplans);
	}
	
	/**
	 * 根据ID获取shieldexamplan对象
	 * 
	 * @param shieldexamplan
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShieldExamPlanById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getShieldExamPlanById(String id) throws Exception{
		logger.info("获取ShieldExamPlan Start");
		
		ShieldExamPlan shieldexamplan = new ShieldExamPlan();
		
		shieldexamplan = shieldexamplanService.getShieldExamPlanById(id);
		
		logger.info("获取ShieldExamPlan End");
		
		return AsyncResponseData.getSuccess(shieldexamplan);
	}
	
}
