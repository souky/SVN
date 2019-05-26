package com.jy.moudles.classroomInfo.controller;

import java.util.*;

import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.user.entity.User;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.classroomInfo.entity.ClassroomInfo;
import com.jy.moudles.classroomInfo.service.ClassroomInfoService;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/** 
 * classroomInfo实现类
 *
 * 创建人：wb
 * 创建时间：2018-01-06
 */
@Controller
@RequestMapping(value="/classroominfo")
public class ClassroomInfoController {
	
	@Autowired
	private ClassroomInfoService classroominfoService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomInfoController.class);
	
	/**
	 * 新增classroominfo对象
	 * 
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClassroomInfo", method = RequestMethod.POST)
    @ResponseBody
	public AsyncResponseData.ResultData saveClassroomInfo(HttpServletRequest request) throws Exception{
		LOGGER.info("新增ClassroomInfo Start");

		String buildingNoStr = request.getParameter("buildingNo");
        String floorNoStr = request.getParameter("floorNo");
        String classroomNoStr = request.getParameter("classroomNo");
        if(StringUtils.isBlank(buildingNoStr) || StringUtils.isBlank(floorNoStr) || StringUtils.isBlank(classroomNoStr)){
            return AsyncResponseData.getSuccess().asParamError("参数错误");
        }

        User user = LoginInterceptor.getCurrentUser();
        if(user == null){
            return AsyncResponseData.getSuccess().asParamError("查询不到用户信息,请重新登陆");
        }



        ClassroomInfo classroominfo = new ClassroomInfo();
        try{
            int buildingNo = Integer.parseInt(buildingNoStr);
            int floorNo = Integer.parseInt(floorNoStr);
            int classroomNo = Integer.parseInt(classroomNoStr);
            classroominfo.setBuildingNo(buildingNo);
            classroominfo.setClassroomNo(classroomNo);
            classroominfo.setFloorNo(floorNo);
        }catch (NumberFormatException nfe){
			LOGGER.info("数字转换异常");
			return AsyncResponseData.getSuccess().asParamError("参数错误");
		}

//		String[] buildingNamesList = request.getParameterValues("buildingNamesList");

		String[] buildingNamesList = new String[classroominfo.getBuildingNo()];
		for(int i = 0;i < buildingNamesList.length;i++){
			buildingNamesList[i] = "教学楼" + (i + 1);
		}
		if(buildingNamesList != null){
			String buildingNames = StringUtils.join(buildingNamesList, ";");
			classroominfo.setBuildingNames(buildingNames);
		}

        classroominfo.setId(UUIDUtil.get32UUID());
        classroominfo.setCreateUser(user.getId());
        classroominfo.setCreateDate(new Date());
		classroominfoService.insertClassroomInfo(classroominfo);
		
		LOGGER.info("新增ClassroomInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改classroominfo对象
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClassroomInfo", method = RequestMethod.POST)
    @ResponseBody
	public AsyncResponseData.ResultData updateClassroomInfo(HttpServletRequest request, String id) throws Exception{
		LOGGER.info("修改ClassroomInfo Start");
		if(StringUtils.isBlank(id)){
            return AsyncResponseData.getSuccess().asParamError("参数错误");
        }
		String[] buildingNamesList = request.getParameterValues("buildingNamesList");
		if(buildingNamesList == null || buildingNamesList.length == 0){
			return AsyncResponseData.getSuccess().asParamError("参数错误,教学楼信息不能为空");
		}
        ClassroomInfo classroomInfo = classroominfoService.getClassroomInfoById(id);
		if(classroomInfo == null){
            return AsyncResponseData.getSuccess().asParamError("参数错误,查询不到教学楼信息");
        }

        String buildingNames = StringUtils.join(buildingNamesList, ";");
        classroomInfo.setBuildingNames(buildingNames);
        classroomInfo.setUpdateDate(new Date());

		classroominfoService.updateClassroomInfo(classroomInfo);
		
		LOGGER.info("修改ClassroomInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除classroominfo对象
	 * 
	 * @param classroominfo
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClassroomInfo", method = RequestMethod.POST)
    @ResponseBody
	public AsyncResponseData.ResultData deleteClassroomInfo(ClassroomInfo classroominfo) throws Exception{
		LOGGER.info("删除ClassroomInfo Start");
		
		classroominfoService.deleteClassroomInfoById(classroominfo.getId());
		
		LOGGER.info("删除ClassroomInfo End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取classroominfo对象
	 * 
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClassroomInfos", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClassroomInfos() throws Exception{
		LOGGER.info("获取ClassroomInfo Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		List<ClassroomInfo> classroominfos = classroominfoService.queryClassroomInfosFilter(filter);
		List<String> buildingNamesList = new ArrayList<>();
		if(classroominfos != null && classroominfos.size() == 1){
			ClassroomInfo classroom = classroominfos.get(0);
			if(classroom != null){
				String names = classroom.getBuildingNames();
				if(StringUtils.isBlank(names)){
					for(int i = 0;i < classroom.getBuildingNo(); i++){
						buildingNamesList.add("教学楼" + (i + 1));
					}
				}else{
					String[] arr = names.split(";");
					buildingNamesList = Arrays.asList(arr);
				}
				classroom.setBuildingNameList(buildingNamesList);
			}
		}

		LOGGER.info("获取ClassroomInfo End");
		
		return AsyncResponseData.getSuccess(classroominfos);
	}
	
}
