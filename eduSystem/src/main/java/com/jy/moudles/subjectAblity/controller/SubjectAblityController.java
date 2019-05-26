package com.jy.moudles.subjectAblity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.auth.UserUtils;
import com.jy.moudles.subjectAblity.entity.SubjectAblity;
import com.jy.moudles.subjectAblity.service.SubjectAblityService;

/** 
 * subjectAblity实现类
 *
 * 创建人：1
 * 创建时间：2018-01-08
 */
@Controller
@RequestMapping(value="/subjectablity")
public class SubjectAblityController {
	
	@Autowired
	private SubjectAblityService subjectablityService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectAblityController.class);
	
	/**
	 * 新增subjectablity对象
	 * 
	 * @param subjectablity
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSubjectAblity", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveSubjectAblity(String[] list,String subject) throws Exception{
		LOGGER.info("新增SubjectAblity Start");
		
		String orgId = UserUtils.getLoginUserOrgId();
		if(StringUtils.isBlank(orgId)) {
			return AsyncResponseData.getSuccess().asParamError("登陆超时");
		}
		boolean flag = false;
		List<String> listCheckde = new ArrayList<>();
		
		int num = 0;
		if(null != list && list.length == 6) {
			for(int i = 0;i < list.length;i++) {
				if(StringUtils.isBlank(list[i])) {
					flag = true;
					num = i;
					break;
				}else {
					if(listCheckde.contains(list[i])) {
						return AsyncResponseData.getSuccess().asParamError("能力值"+(i+1)+"重复");
					}else {
						listCheckde.add(list[i]);
					}
				}
			}
			if(flag) {
				return AsyncResponseData.getSuccess().asParamError("能力值"+(num+1)+"不能为空");
			}else {
				subjectablityService.insertSubjectAblity(list,subject,orgId);
			}
		}else {
			return AsyncResponseData.getSuccess().asParamError("能力值必须填写完整");
		}
		
		
		LOGGER.info("新增SubjectAblity End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改subjectablity对象
	 * 
	 * @param subjectablity
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSubjectAblity", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateSubjectAblity(SubjectAblity subjectablity) throws Exception{
		LOGGER.info("修改SubjectAblity Start");
		
		subjectablityService.updateSubjectAblity(subjectablity);
		
		LOGGER.info("修改SubjectAblity End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除subjectablity对象
	 * 
	 * @param subjectablity
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSubjectAblity", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteSubjectAblity(SubjectAblity subjectablity) throws Exception{
		LOGGER.info("删除SubjectAblity Start");
		
		subjectablityService.deleteSubjectAblityById(subjectablity.getId());
		
		LOGGER.info("删除SubjectAblity End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取subjectablity对象
	 * 
	 * @param subjectablity
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySubjectAblitys", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData querySubjectAblitys(SubjectAblity subjectablity) throws Exception{
		LOGGER.info("获取SubjectAblity Start");
		
		String orgId = UserUtils.getLoginUserOrgId();
		if(StringUtils.isBlank(orgId)) {
			return AsyncResponseData.getSuccess().asParamError("登陆超时");
		}
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("orgId", orgId);
		filter.put("subjectName", subjectablity.getSubjectName());
		
		List<SubjectAblity> subjectablitys = subjectablityService.querySubjectAblitysFilter(filter);
		LOGGER.info("获取SubjectAblity End");
		List<String> ablityArray = new ArrayList<>();
		for(SubjectAblity e : subjectablitys) {
			ablityArray.add(e.getAblityName());
		}
		
		return AsyncResponseData.getSuccess(ablityArray);
	}
	
}
