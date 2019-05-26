package com.jy.moudles.organization.controller;

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
import com.jy.moudles.organization.entity.Organization;
import com.jy.moudles.organization.service.OrganizationService;

/** 
 * Organization实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Controller
@RequestMapping(value="/organization")
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService;
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	
	/**
	 * 新增organization对象
	 * 
	 * @param organization
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrganization", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveOrganization(Organization organization) throws Exception{
		logger.info("新增Organization Start");
		
		organizationService.insertOrganization(organization);
		
		logger.info("新增Organization End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改organization对象
	 * 
	 * @param organization
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOrganization", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateOrganization(Organization organization) throws Exception{
		logger.info("修改Organization Start");
		
		organizationService.updateOrganization(organization);
		
		logger.info("修改Organization End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除organization对象
	 * 
	 * @param organization
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteOrganization", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteOrganization(Organization organization) throws Exception{
		logger.info("删除Organization Start");
		
		organizationService.deleteOrganizationById(organization.getId());
		
		logger.info("删除Organization End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取organization对象
	 * 
	 * @param organization
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryOrganizations", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryOrganizations(Organization organization) throws Exception{
		logger.info("获取Organization Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<Organization> organizations= organizationService.queryOrganizationsFilter(filter);
		logger.info("获取Organization End");
		
		return AsyncResponseData.getSuccess(organizations);
	}
	
	/**
	 * 根据ID获取organization对象
	 * 
	 * @param organization
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOrganizationById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getOrganizationById(String id) throws Exception{
		logger.info("获取Organization Start");
		
		Organization organization = new Organization();
		
		organization = organizationService.getOrganizationById(id);
		
		logger.info("获取Organization End");
		
		return AsyncResponseData.getSuccess(organization);
	}
	
}
