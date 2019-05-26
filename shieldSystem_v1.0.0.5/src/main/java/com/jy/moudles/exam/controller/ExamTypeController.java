package com.jy.moudles.exam.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.LogUtil;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.exam.entity.ExamType;
import com.jy.moudles.exam.service.ExamTypeService;
import com.jy.moudles.systemLog.constants.LogOperationType;
import com.jy.moudles.user.entity.User;

/** 
 *
 * 创建人：wb
 * 创建时间：2017-10-16 21:29:18
 */
@Controller
@RequestMapping(value="/examtype")
public class ExamTypeController {
	
	@Autowired
	private ExamTypeService examTypeService;

	@Autowired
    private ExamService examService;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExamTypeController.class);
	
	/**
	 * 修改examtype对象
	 * 
	 * @param examType
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/examTypeSaveOrUpdata", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData examTypeSaveOrUpdata(ExamType examType,HttpServletRequest request) throws Exception{
    	LOGGER.info("编辑考试类型 开始");
		
		User user = LoginInterceptor.getCurrentUser();
		
		if(StringUtils.isBlank(examType.getExamTypeName())){
			return AsyncResponseData.getSuccess().asParamError("考试类型名称不能为空");
		}
		
		Map<String, Object> filter = new HashMap<String, Object>();
		//判断考试类型名称是否相同
		filter.put("examTypeName", examType.getExamTypeName());
		List<ExamType> examTypeList = examTypeService.queryExamTypesFilter(filter);
		if(examTypeList != null && !examTypeList.isEmpty()){
			if(!examTypeList.get(0).getId().equals(examType.getId())){
				return AsyncResponseData.getSuccess().asParamError("该考试类型名称已经存在");
			}
		}

		filter.clear();
		filter.put("examTypeNo", examType.getExamTypeNo());
		examTypeList = examTypeService.queryExamTypesFilter(filter);
		if(examTypeList != null && !examTypeList.isEmpty()){
			if(!examTypeList.get(0).getId().equals(examType.getId())){
				return AsyncResponseData.getSuccess().asParamError("该考试类型编号已经存在");
			}
		}
		
		examType.setUpdateUser(user.getId());
		examType.setUpdateDate(new Date());
		if(StringUtils.isBlank(examType.getId())){
            examType.setId(UUIDUtil.get32UUID());
            examType.setCreateDate(new Date());
            examType.setCreateUser(user.getId());
            examType.setOrgId(user.getOrgId());
            examTypeService.insertExamType(examType);
            LogUtil.addLog("新增考试类型", LogOperationType.ADD, request);
		}else{
            examTypeService.updateExamType(examType);
            LogUtil.addLog("修改考试类型", LogOperationType.EDIT, request);
		}
		
		LOGGER.info("编辑考试类型 结束");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除examtype对象
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteExamType", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteExamType(HttpServletRequest request) throws Exception{
    	LOGGER.info("删除考试类型 开始");
		String id = request.getParameter("id");
		Integer rows = 0;
		if(StringUtils.isBlank(id)){
			return AsyncResponseData.getSuccess(rows);
		}
		//删除考试类型时,先确认是否删除含有该类型的考试计划
        Map<String,Object> filter = new HashMap<>();
		filter.put("examTypeId", id);
		List<Exam> exams = examService.queryExamFilter(filter);
		List<String> ids = new ArrayList<>();
		if(exams != null && !exams.isEmpty()) {
		    for(Exam exam : exams){
                examService.deleteExamById(exam.getId());
                ids.add(exam.getId());
            }
		}

		rows = examTypeService.deleteExamTypeById(id);
		LogUtil.addLog("删除考试类型", LogOperationType.DELETE, request);
    	LOGGER.info("删除考试类型 结束");
		
		return AsyncResponseData.getSuccess(rows);
	}
	
	/**
	 * 获取examtype对象
	 * 
	 * @param pageNum pageSize
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/queryExamTypes", method = RequestMethod.POST)
	public ModelAndView queryExamTypes(@RequestParam int pageNum,@RequestParam int pageSize, HttpServletRequest request){
    	LOGGER.info("获取考试类型 开始");
		ModelAndView mv = new ModelAndView();
		PageHelper.startPage(pageNum, pageSize);
		Map<String, Object> filter = new HashMap<String, Object>();
		PageInfo<ExamType> examType = new PageInfo<ExamType>(examTypeService.queryExamTypesFilter(filter));
		mv.setViewName("exam/post/examTypeListPost");
		mv.addObject("examTypeList", examType.getList());
		mv.addObject("pageNow", examType.getPageNum());
		mv.addObject("pageTotal", examType.getPages());
		//传入basePath
		String path = request.getContextPath();
	    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	    mv.addObject("basePath",basePath);

    	LOGGER.info("获取考试类型 结束");
		
		return mv;
	}
	
	/**
	 * 获取考试类型data
	 * @return data
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getExamTypeData", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getExamTypeData(HttpServletRequest request) throws Exception{
    	LOGGER.info("获取考试类型 开始");
		
		Map filter = new HashMap();
		List list = examTypeService.queryExamTypesFilter(filter);
		LOGGER.info("获取考试类型 结束");
		return AsyncResponseData.getSuccess(list);
	}
	
	
	/**
	 * 批量删除examtype对象
	 * @param idss
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteExamTypeByIds", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteExamTypeByIds(@RequestParam("ids") String idss,HttpServletRequest request) throws Exception{
    	LOGGER.info("删除考试类型 开始");
		
		Integer rows = 0;
		String[] idsString = idss.split(",");
		List<String> ids = new ArrayList<String>();
		for(String s:idsString){
			if(!StringUtils.isBlank(s)){
				ids.add(s);
			}
		}
		
		if(ids.isEmpty()){
			return AsyncResponseData.getSuccess(rows);
		}
		
		rows = examTypeService.deleteExamTypes(ids);
		for(String id : ids){
            examService.deleteExamById(id);
        }
		LogUtil.addLog("批量删除考试类型", LogOperationType.DELETE, request);
		LOGGER.info("删除考试类型 结束");
		
		return AsyncResponseData.getSuccess(rows);
	}


}
