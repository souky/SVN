package com.jy.moudles.school.controller;

import java.util.Date;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.UUIDUtil;
import com.jy.common.utils.auth.UserUtils;
import com.jy.moudles.organization.entity.Organization;
import com.jy.moudles.organization.service.OrganizationService;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.school.service.SchoolService;

/** 
 * school实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/school")
public class SchoolController {
	
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private OrganizationService organizationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);
	
	/**
	 * 新增school对象
	 * 
	 * @param school
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSchool", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveSchool(School school) throws Exception{
		LOGGER.info("新增School Start");
		
		schoolService.insertSchool(school);
		
		LOGGER.info("新增School End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改school对象
	 * 
	 * @param school
	 * @return ResultData
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSchool", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateSchool(School school) throws Exception{
		LOGGER.info("修改School Start");
		
		//处理学段 学科
		String[] periodArray = school.getPeriodArray();
		String[] subjectArray = school.getSubjectArray();
		
		String schoolPeriod = "";
		String subject = "";
		
		if(null != periodArray && periodArray.length > 0) {
			for(int i = 0;i < periodArray.length;i++){
				if(i==0) {
					schoolPeriod += periodArray[i];
				}else {
					schoolPeriod += "," + periodArray[i];
				}
			}
		}
		
		if(null != subjectArray && subjectArray.length > 0) {
			for(int i = 0;i < subjectArray.length;i++){
				if(i==0) {
					subject += subjectArray[i];
				}else {
					subject += "," + subjectArray[i];
				}
			}
		}
		
		school.setSchoolPeriod(schoolPeriod);
		school.setSubject(subject);
		school.setUpdateDate(new Date());
		
		if(StringUtils.isBlank(school.getId())) {
			school.setId(UUIDUtil.get32UUID());
			schoolService.insertSchool(school);
		}else {
			schoolService.updateSchool(school);
		}
		
		//跟新组织机构
		String orgId = school.getOrgId();
		if(StringUtils.isNotBlank(orgId)) {
			Organization organization = organizationService.getOrganizationById(orgId);
			if(null != organization) {
				organization.setOrgName(school.getSchoolName());
				organizationService.updateOrganization(organization);
			}
		}
		
		LOGGER.info("修改School End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除school对象
	 * 
	 * @param school
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSchool", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteSchool(School school) throws Exception{
		LOGGER.info("删除School Start");
		
		schoolService.deleteSchoolById(school.getId());
		
		LOGGER.info("删除School End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取school对象
	 * 
	 * @param school
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySchools", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData querySchools() throws Exception{
		LOGGER.info("获取School Start");
		
		String orgId = UserUtils.getLoginUserOrgId();
		School school = schoolService.getSchoolByOrgId(orgId);
		
		if(null == school) {
			school = new School();
			return AsyncResponseData.getSuccess(school);
		}
		
		String schoolPeriod = school.getSchoolPeriod();
		String subject = school.getSubject();
		
		//处理学段
		if(StringUtils.isNotBlank(schoolPeriod)) {
			String[] periodArray = schoolPeriod.split(",");
			school.setPeriodArray(periodArray);
		}
		if(StringUtils.isNotBlank(subject)) {
			String[] subjectArray = subject.split(",");
			school.setSubjectArray(subjectArray);
		}
		
		LOGGER.info("获取School End");
		
		return AsyncResponseData.getSuccess(school);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
