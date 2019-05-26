package com.jy.moudles.subject.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.auth.UserUtils;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.school.service.SchoolService;
import com.jy.moudles.subject.entity.Subject;
import com.jy.moudles.subject.service.SubjectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/** 
 * subject实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/subject")
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private SchoolService schoolService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);
	
	/**
	 * 新增subject对象
	 * 
	 * @param subject
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveSubject", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveSubject(Subject subject) throws Exception{
		LOGGER.info("新增Subject Start");
		
		subjectService.insertSubject(subject);
		
		LOGGER.info("新增Subject End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改subject对象
	 * 
	 * @param subject
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSubject", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateSubject(Subject subject) throws Exception{
		LOGGER.info("修改Subject Start");
		
		subjectService.updateSubject(subject);
		
		LOGGER.info("修改Subject End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除subject对象
	 * 
	 * @param subject
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSubject", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteSubject(Subject subject) throws Exception{
		LOGGER.info("删除Subject Start");
		
		subjectService.deleteSubjectById(subject.getId());
		
		LOGGER.info("删除Subject End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取subject对象
	 * 
	 * @param subject
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/querySubjects", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData querySubjects(int pageNum, int pageSize,Subject subject) throws Exception{
		LOGGER.info("获取Subject Start");
		//去除以科目名称为过滤条件，不除去空格
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("subjectCode", subject.getSubjectCode());
		if (!StringUtils.isBlank(subject.getSubjectName())) {
			filter.put("subjectName", subject.getSubjectName().trim());
		}
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<Subject> subjects = new PageInfo<Subject>(subjectService.querySubjectsFilter(filter));
		
		LOGGER.info("获取Subject End");
		
		return AsyncResponseData.getSuccess(subjects);
	}
	
	/**
	 * 获取当前登陆用户的subject对象
	 * 
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSubjectByLogin", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getSubjectByLogin() throws Exception{
		LOGGER.info("获取Subject Start");
		List<Subject> subjects = new ArrayList<>();
		
		String orgId = UserUtils.getLoginUserOrgId();
		School school = schoolService.getSchoolByOrgId(orgId);
		
		if(null != school && StringUtils.isNotBlank(school.getSubject())) {
			List<String> subject = Arrays.asList(school.getSubject().split(","));
			subjects = subjectService.querySubjectsByNames(subject);
		}

		LOGGER.info("获取Subject End");
		return AsyncResponseData.getSuccess(subjects);
	}
	
}
