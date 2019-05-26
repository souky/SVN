package com.jy.moudles.twoWaySpecification.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.UUIDUtil;
import com.jy.common.utils.auth.UserUtils;
import com.jy.moudles.twoWaySpecification.entity.TwoWaySpecification;
import com.jy.moudles.twoWaySpecification.service.TwoWaySpecificationService;
import com.jy.moudles.twoWaySpecificationDetail.entity.TwoWaySpecificationDetail;
import com.jy.moudles.twoWaySpecificationDetail.service.TwoWaySpecificationDetailService;
import com.jy.moudles.user.entity.User;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jy.moudles.spDetailStep.entity.SpDetailStep;
import com.jy.moudles.spDetailStep.service.SpDetailStepService;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * twoWaySpecification实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/twowayspecification")
public class TwoWaySpecificationController {

	@Autowired
	private TwoWaySpecificationService twowayspecificationService;

	@Autowired
	private TwoWaySpecificationDetailService twoWaySpecificationDetailService;
	
	@Autowired
	private SpDetailStepService spDetailStepService;
	

	private static final Logger LOGGER = LoggerFactory.getLogger(TwoWaySpecificationController.class);
	
	/**
	 * 新增twowayspecification对象
	 * 
	 * @param twowayspecification
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTwoWaySpecification", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveTwoWaySpecification(TwoWaySpecification twowayspecification,
			@RequestParam(required = false) String twDetails
			) throws Exception{
		LOGGER.info("新增TwoWaySpecification Start");

		if(twowayspecification == null){
			return AsyncResponseData.getSuccess().asParamError("请输入双向细目表必要信息");
		}
		User user = UserUtils.getLoginUser();
        if(null == user) {
        	 return AsyncResponseData.getSuccess().asParamError("登陆超时");
        }
        
        //查重
        String specificationName = twowayspecification.getSpecificationName().trim();
        Map<String,Object> filter = new HashMap<>();
        filter.put("orgId", user.getOrgId());
        if (null == specificationName || "".equals(specificationName.trim())) {
        	return AsyncResponseData.getSuccess().asParamError("请输入双向细目表名称");
        }
        filter.put("specificationName", specificationName);
        List<TwoWaySpecification> listCheck = new ArrayList<>();
        listCheck = twowayspecificationService.queryTwoWaySpecificationsFilter(filter);
        if(null != listCheck && listCheck.size() > 0) {
			return AsyncResponseData.getSuccess().asParamError("双向细目表名称重复");
        }
        
        twowayspecification.setOrgId(user.getOrgId());
        twowayspecification.setCreateUser(user.getUserName());
        
		twowayspecification.setId(UUIDUtil.get32UUID());
		
		//双向细目表详情添加  统一事务
		
		List<TwoWaySpecificationDetail> list = JSON.parseArray(twDetails, TwoWaySpecificationDetail.class);
		
		if(null != list && list.size() > 0) {
			for(TwoWaySpecificationDetail e : list) {
				e.setCreateUser(twowayspecification.getCreateUser());
				e.setOrgId(twowayspecification.getOrgId());
				e.setParentId(twowayspecification.getId());
				e.setId(UUIDUtil.get32UUID());
				twoWaySpecificationDetailService.insertTwoWaySpecificationDetail(e);
				//分步信息
				if(e.getItemType() == 1) {
					List<SpDetailStep> lists = e.getStepList();
					if(null != lists && lists.size() > 0) {
						spDetailStepService.insertSpDetailStepList(lists, e);
					}
				}
			}
		}
		
		twowayspecificationService.insertTwoWaySpecification(twowayspecification);
		
		LOGGER.info("新增TwoWaySpecification End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改twowayspecification对象
	 * 
	 * @param twowayspecification
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTwoWaySpecification", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateTwoWaySpecification(TwoWaySpecification twowayspecification,
			@RequestParam(required = false) String twDetails) throws Exception{
		LOGGER.info("修改TwoWaySpecification Start");
        User user = LoginInterceptor.getCurrentUser();
        Map<String, Object> filter = new HashMap<>();
        filter.put("orgId", user.getOrgId());
        filter.put("specificationName", twowayspecification.getSpecificationName());
        List<TwoWaySpecification> twoWaySpecifications = twowayspecificationService.queryTwoWaySpecificationsFilter(filter);
        //查重
        String specificationName = twowayspecification.getSpecificationName().trim();
        if(null == specificationName || specificationName.trim().equals("")) {
        	return AsyncResponseData.getSuccess().asParamError("请输入双向细目表名称");
        }
        if(null != twoWaySpecifications && twoWaySpecifications.size() > 2) {
        	return AsyncResponseData.getSuccess().asParamError("双向细目表名称重复");
        }
        if(null != twoWaySpecifications && twoWaySpecifications.size() == 1) {
        	TwoWaySpecification e = twoWaySpecifications.get(0);
        	if(!e.getId().equals(twowayspecification.getId())) {
        		return AsyncResponseData.getSuccess().asParamError("双向细目表名称重复");
        	}
        }
        
        //删除双向细目表详情 和分步详情
        twoWaySpecificationDetailService.deleteTwoWaySpDetailsByPid(twowayspecification.getId());
        spDetailStepService.deleteSpDetailStepById(twowayspecification.getId());
		
		//双向细目表详情添加  统一事务
		
		List<TwoWaySpecificationDetail> list = JSON.parseArray(twDetails, TwoWaySpecificationDetail.class);
		
		if(null != list && list.size() > 0) {
			for(TwoWaySpecificationDetail e : list) {
				e.setCreateUser(twowayspecification.getCreateUser());
				e.setOrgId(twowayspecification.getOrgId());
				e.setParentId(twowayspecification.getId());
				e.setId(UUIDUtil.get32UUID());
				//知识点处理
				twoWaySpecificationDetailService.insertTwoWaySpecificationDetail(e);
				//分步信息
				if(e.getItemType() == 1) {
					List<SpDetailStep> lists = e.getStepList();
					if(null != lists && lists.size() > 0) {
						spDetailStepService.insertSpDetailStepList(lists, e);
					}
				}
			}
		}
		
		twowayspecificationService.updateTwoWaySpecification(twowayspecification);
		
		LOGGER.info("修改TwoWaySpecification End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除twowayspecification对象
	 * 
	 * @param twowayspecification
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTwoWaySpecification", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteTwoWaySpecification(TwoWaySpecification twowayspecification) throws Exception{
		if(twowayspecification.getId()==null){
			Map<String,String> errMsg = new HashMap<String,String>();
			errMsg.put("id","请选择要删除的文件");
			return AsyncResponseData.getParamError(errMsg);
		}
		LOGGER.info("删除TwoWaySpecification Start");

		//删除双向细目表对象
		twowayspecificationService.deleteTwoWaySpecificationById(twowayspecification.getId());
		//删除双向细目表items
		twoWaySpecificationDetailService.deleteTwoWaySpDetailsByPid(twowayspecification.getId());
		//删除分步表
		spDetailStepService.deleteSpDetailStepById(twowayspecification.getId());
		
		//TODO 删除关联的考试
		

		LOGGER.info("删除TwoWaySpecification End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取twowayspecification对象
	 * 
	 * @param twowayspecification
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTwoWaySpecifications", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryTwoWaySpecifications(int pageNum, int pageSize, 
			TwoWaySpecification twowayspecification) throws Exception{
		LOGGER.info("获取TwoWaySpecification Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();

		if (StringUtils.isNotBlank(twowayspecification.getSpecificationName())){
			filter.put("specificationName",twowayspecification.getSpecificationName());
		}
		if (StringUtils.isNotBlank(twowayspecification.getSubjectCode())){
			filter.put("subjectCode",twowayspecification.getSubjectCode());
		}
		if (StringUtils.isNotBlank(twowayspecification.getGradeCode()+"")){
			filter.put("gradeCode",twowayspecification.getGradeCode());
		}
		
		String orgId = UserUtils.getLoginUserOrgId();
		if (StringUtils.isNotBlank(orgId)){
			filter.put("orgId",orgId);
        }


		PageHelper.startPage(pageNum, pageSize);
		PageInfo<TwoWaySpecification> twowayspecifications = new PageInfo<TwoWaySpecification>(twowayspecificationService.queryTwoWaySpecificationsFilter(filter));
		LOGGER.info("获取TwoWaySpecification End");
		
		return AsyncResponseData.getSuccess(twowayspecifications);
	}
	
	@RequestMapping(value = "/queryTwoWaySpecificationsByCode", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryTwoWaySpecificationsByCode(String gradeCode,String[] subjectArray) throws Exception{
		LOGGER.info("获取TwoWaySpecification Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();

		filter.put("subjectCode",subjectArray);
		filter.put("gradeCode",gradeCode);
		
		String orgId = UserUtils.getLoginUserOrgId();
		if (StringUtils.isNotBlank(orgId)){
			filter.put("orgId",orgId);
        }
		
		List<TwoWaySpecification> list = twowayspecificationService.queryTwoWaySpecificationsFilterByCode(filter);
		
		//处理数据
		Map<String,List<TwoWaySpecification>> map = new LinkedMap<>();
		if(null != list && list.size() > 0) {
			for(TwoWaySpecification e : list) {
				List<TwoWaySpecification> lists = map.get(e.getSubjectCode());
				if(null == lists) {
					List<TwoWaySpecification> liste = new ArrayList<>();
					liste.add(e);
					map.put(e.getSubjectCode(), liste);
				}else {
					lists.add(e);
				}
			}
		}
		
		LOGGER.info("获取TwoWaySpecification End");
		
		return AsyncResponseData.getSuccess(map);
	}
	
	/**
	 * 获取twoTwoSpecdificationName
	 * 
	 * @param twowayspecification
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/getTwoWaySpecificationsName", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getTwoWaySpecificationsName(int pageNum, int pageSize, 
			TwoWaySpecification twowayspecification) throws Exception{
		LOGGER.info("获取TwoWaySpecification Start");
		User user = LoginInterceptor.getCurrentUser();
		if (null == user) {
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}
		
		Map<String, Object> filter = new HashMap<String, Object>();

		if (StringUtils.isNotBlank(twowayspecification.getSpecificationName())){
			filter.put("specificationName",twowayspecification.getSpecificationName());
		}
		if (StringUtils.isNotBlank(twowayspecification.getSubjectCode())){
			filter.put("subjectCode",twowayspecification.getSubjectCode());
		}
		if (StringUtils.isNotBlank(twowayspecification.getGradeCode()+"")){
			filter.put("gradeCode",twowayspecification.getGradeCode());
		}
		
		String orgId = UserUtils.getLoginUserOrgId();
		if (StringUtils.isNotBlank(orgId)){
			filter.put("orgId",orgId);
		}	
		
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<TwoWaySpecification> twowayspecifications = new PageInfo<TwoWaySpecification>(twowayspecificationService.queryTwoWaySpecificationsFilter(filter));
		if (twowayspecifications == null && twowayspecifications.getList().isEmpty()) {
			return AsyncResponseData.getSuccess().asParamError("数据错误");
		}
		for (TwoWaySpecification twoWay : twowayspecifications.getList()) {
			
			//Exam exam = examService.getExamById(twowayspecification.getExamId());
			twoWay.setSpecificationName((StringUtils.isBlank(twoWay.getExamId()) ? "" : twoWay.getExamId()) + twoWay.getGradeCode() + twoWay.getSubjectCode() + twoWay.getSpecificationName());
		}
		
		LOGGER.info("获取TwoWaySpecification End");
		
		return AsyncResponseData.getSuccess(twowayspecifications);
	}
	
	/**
	 * 获取twowayspecification对象
	 * 
	 * @param twowayspecification
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTwoWaySpecificationById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryTwoWaySpecificationById(TwoWaySpecification twowayspecification) throws Exception{
		LOGGER.info("获取TwoWaySpecification Start");
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("parentId",twowayspecification.getId());
		twowayspecification = twowayspecificationService.getTwoWaySpecificationById(twowayspecification.getId());
		List<TwoWaySpecificationDetail> listDetail = twoWaySpecificationDetailService.queryTwoWaySpecificationDetailsFilter(filter);
		
		//双向细目表主体
		resultMap.put("twowayspecification",twowayspecification);
		resultMap.put("listDetail",listDetail);
		
		LOGGER.info("获取TwoWaySpecification End");
		
		return AsyncResponseData.getSuccess(resultMap);
	}
	
}
