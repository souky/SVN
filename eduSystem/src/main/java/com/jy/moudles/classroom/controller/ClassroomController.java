package com.jy.moudles.classroom.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.moudles.classroom.entity.Classroom;
import com.jy.moudles.classroom.service.ClassroomService;
import com.jy.moudles.student.entity.Student;
import com.jy.moudles.student.service.StudentService;
import com.jy.moudles.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * classroom实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/classroom")
public class ClassroomController {
	
	@Autowired
	private ClassroomService classroomService;
	@Autowired
	private StudentService studentService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomController.class);
	
	/**
	 * 新增classroom对象
	 * 
	 * @param classroom
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClassroom", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveClassroom(Classroom classroom) throws Exception{
		LOGGER.info("新增Classroom Start");
		
		User user = LoginInterceptor.getCurrentUser();
		if(null == user) {
			return AsyncResponseData.getSuccess().asParamError("登陆超时");
		}

		Classroom checkValue = classroomService.getClassroomByName(classroom.getClassroomName(),user.getOrgId());

		if (null != checkValue && !StringUtils.isBlank(checkValue.getId())){
			return AsyncResponseData.getSuccess().asParamError("该班级存在!");
		}
		
		if (!StringUtils.isBlank(classroom.getClassTeacherId())) {
			Map<String, Object> filter = new HashMap<>(16);
			filter.put("orgId", user.getOrgId());
			filter.put("classTeacherId", classroom.getClassTeacherId());
			List<Classroom> classrooms = classroomService.queryClassroomsFilter(filter);
			if (classrooms != null && !classrooms.isEmpty()) {
				return AsyncResponseData.getSuccess().asParamError("此班主任已是另一班的班主任");
			}
		}
		classroomService.insertClassroom(user, classroom);
		
		LOGGER.info("新增Classroom End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改classroom对象
	 * 
	 * @param classroom
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClassroom", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateClassroom(Classroom classroom) throws Exception{
		LOGGER.info("修改Classroom Start");
        User user = LoginInterceptor.getCurrentUser();
		Map<String, Object> filter = new HashMap<>(16);
        filter.put("orgId", user.getOrgId());
        filter.put("classTeacherId", classroom.getClassTeacherId());
        List<Classroom> classrooms = classroomService.queryClassroomsFilter(filter);
        if (classrooms != null && !classrooms.isEmpty()) {
            return AsyncResponseData.getSuccess().asParamError("此班主任已是另一班的班主任");
        }
		classroomService.updateClassroom(classroom);
		
		LOGGER.info("修改Classroom End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除classroom对象
	 * 
	 * @param classroom
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClassroom", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteClassroom(Classroom classroom) throws Exception{
		LOGGER.info("删除Classroom Start");
		
		classroomService.deleteClassroomById(classroom.getId());
		//TODO 删除关联的学生
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("classroomId", classroom.getId());
		List<Student> list = studentService.queryStudentsFilter(filter);
		List<String> ids = new ArrayList<>();
		for(Student e : list) {
			ids.add(e.getId());
		}
		if(ids.size() > 0) {
			studentService.deleteStudents(ids);
		}
		LOGGER.info("删除Classroom End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除classroom对象
	 * 
	 * @param classroom
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClassroomById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClassroomById(Classroom classroom) throws Exception{
        LOGGER.info("获取Classroom Start");

		Classroom classrooms = classroomService.getClassroomById(classroom.getId());

        LOGGER.info("获取Classroom End");
		return AsyncResponseData.getSuccess(classrooms);
	}
	
	@RequestMapping(value = "/queryClassroomsByGrade", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClassroomsByGrade(Classroom classroom) throws Exception{
		LOGGER.info("删除Classroom Start");
		
		User user = LoginInterceptor.getCurrentUser();
		if(null == user) {
			return AsyncResponseData.getSuccess().asParamError("登陆超时");
		}
		classroom.setOrgId(user.getOrgId());
		
		List<Classroom> classrooms = classroomService.queryClassroomsByGrade(classroom);
		
		LOGGER.info("删除Classroom End");
		return AsyncResponseData.getSuccess(classrooms);
	}
	
	/**
	 * 获取classroom对象
	 * 
	 * @param classroom
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClassrooms", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClassrooms(int pageNum, int pageSize, Classroom classroom, String classTeather) throws Exception {
		
		LOGGER.info("获取Classroom Start");
		
		User user = LoginInterceptor.getCurrentUser();
        Map<String, Object> filter = new HashMap<String, Object>();
		if(null == user) {
			return AsyncResponseData.getSuccess().asParamError("登陆超时");
		}
        if (classroom != null) {
            if (!StringUtils.isBlank(classroom.getClassroomName())) {
                filter.put("classroomName", classroom.getClassroomName().trim());
            }
			if (!StringUtils.isBlank(classTeather)) {
				filter.put("teacherName", classTeather.trim());
            }
        }

		filter.put("orgId", user.getOrgId());

        //班级信息
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<Classroom> classrooms = new PageInfo<Classroom>(classroomService.queryClassroomsFilter(filter));
		
		LOGGER.info("获取Classroom End");
		
		return AsyncResponseData.getSuccess(classrooms);
	}
	
	
}
