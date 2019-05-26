package com.jy.moudles.teacher.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.ExcelRead;
import com.jy.common.utils.ExcelUtil;
import com.jy.common.utils.FileDownload;
import com.jy.moudles.teacher.entity.Teacher;
import com.jy.moudles.teacher.service.TeacherService;
import com.jy.moudles.user.entity.User;
import com.jy.moudles.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * teacher实现类
 * <p>
 * 创建人：1
 * 创建时间：2017-11-30
 */
@Controller
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);

    /**
     * 新增teacher对象
     *
     * @param teacher
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveTeacher", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData saveTeacher(Teacher teacher) throws Exception {
        LOGGER.info("新增Teacher Start");

        User user = LoginInterceptor.getCurrentUser();
        if (null == user) {
            return AsyncResponseData.getDenied("登陆超时");
        }
        String teacherName = teacher.getTeacherName();
        if (StringUtils.isBlank(teacherName)) {
            return AsyncResponseData.getSuccess().asParamError("教师姓名不能为空");
        }
        String reg = "^[\\u4e00-\\u9fa5]*$";
        if (!teacherName.matches(reg)) {
            return AsyncResponseData.getSuccess().asParamError("教师姓名必须为中文");
        }
        if (StringUtils.isBlank(teacher.getTeacherMobile())) {
            return AsyncResponseData.getSuccess().asParamError("教师手机号不能为空");
        }
        Map<String, Object> filter = new HashMap<>();
        filter.put("currentUserOrgId", user.getOrgId());
        filter.put("teacherMobile", teacher.getTeacherMobile());
        List<Teacher> teacherList = teacherService.queryTeachersFilter(filter);
        if (null != teacherList && !teacherList.isEmpty()) {
            return AsyncResponseData.getSuccess().asParamError("该手机号已存在");
        }
        filter.clear();
        String[] subjectArray = teacher.getSubjectArray();
        String[] classArray = teacher.getClassArray();
        filter.put("currentUserOrgId", user.getOrgId());
        List<Teacher> teachers = teacherService.queryTeachersFilter(filter);
        if (teachers != null) {
            for (Teacher teach : teachers) {
                String subject = teach.getSubject();
                String classroom = teach.getClassroom();
                for (String sub : subjectArray) {
                    if (subject != null) {
                        if (subject.contains(sub)) {
                            for (String cla : classArray) {
                                if (classroom.contains(cla)) {
                                    return AsyncResponseData.getSuccess().asParamError("该老师教授的班级科目存在另一老师教授");
                                }
                            }
                        }
                    }
                }
            }
        }
        teacherService.insertTeacher(user, teacher);

        LOGGER.info("新增Teacher End");
        return AsyncResponseData.getSuccess();
    }

    /**
     * 修改teacher对象
     *
     * @param teacher
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData updateTeacher(Teacher teacher) throws Exception {
        LOGGER.info("修改Teacher Start");

        User user = LoginInterceptor.getCurrentUser();
        if (null == user) {
            return AsyncResponseData.getDenied("登陆超时");
        }
        Map<String, Object> filter = new HashMap<>(16);
        filter.put("currentUserOrgId", user.getOrgId());
        filter.put("teacherMobile", teacher.getTeacherMobile());
        List<Teacher> teacherList = teacherService.queryTeachersFilter(filter);
        if (null != teacherList && !teacherList.isEmpty()) {
            if (teacherList.size() > 1) {
                return AsyncResponseData.getSystemError("该手机号在系统中不止一条，请联系管理员核对修改");
            }
            if (!teacher.getId().equals(teacherList.get(0).getId())) {
                return AsyncResponseData.getSuccess().asParamError("该手机号已存在");
            }
        }
        
        //修改登陆账户
        filter.clear();
        filter.put("userName", teacher.getTeacherMobile());
        List<User> listUser = userService.queryUsersFilter(filter);
        if(null != listUser && !listUser.isEmpty() && listUser.size() == 1) {
        	User users = listUser.get(0);
        	if(null != users) {
        		if(!users.getSourceId().equals(teacher.getId())) {
        			return AsyncResponseData.getSuccess().asParamError("该手机号已存在");
        		}
        	}
        	userService.updateUser(users);
        }else {
        	return AsyncResponseData.getSuccess().asParamError("该手机号已存在");
        }
        
        
        filter.clear();
        String[] classArray = teacher.getClassArray();
        String[] subjectArray = teacher.getSubjectArray();
        filter.put("currentUserOrgId", user.getOrgId());
        List<Teacher> teachers = teacherService.queryTeachersFilter(filter);
        if (teachers != null) {
            for (Teacher teach : teachers) {
                if (!teacher.getId().equals(teach.getId())) {
                    String subject = teach.getSubject();
                    String classroom = teach.getClassroom();
                    for (String sub : subjectArray) {
                        if (subject != null) {
                            if (subject.contains(sub)) {
                                for (String cla : classArray) {
                                    if (classroom.contains(cla)) {
                                        return AsyncResponseData.getSuccess().asParamError("该老师教授的班级科目存在另一老师教授");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String classroom = "";
        String subject = "";

        if (null != classArray && 0 < classArray.length) {
            classroom = StringUtils.join(classArray, ",");
        }

        if (null != subjectArray && 0 < subjectArray.length) {
            subject = StringUtils.join(subjectArray, ",");
        }

        teacher.setClassroom(classroom);

        teacher.setSubject(subject);

        teacher.setUpdateUser(user.getUserName());
        teacher.setUpdateDate(new Date());

        teacherService.updateTeacher(teacher);

        LOGGER.info("修改Teacher End");
        return AsyncResponseData.getSuccess();
    }

    /**
     * 通过id获得teacher对象
     *
     * @param teacher
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getTeacherById", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData getTeacherById(Teacher teacher) throws Exception {
        LOGGER.info("修改Teacher Start");

        Teacher teachers = teacherService.getTeacherById(teacher.getId());

        String classroom = teachers.getClassroom();
        String subject = teachers.getSubject();
        String grade = "";

        //处理学段
        if (StringUtils.isNotBlank(classroom)) {
            String[] classArray = classroom.split(",");
            grade = classArray[0].split("\\(")[0];
            teachers.setClassArray(classArray);
        }
        if (StringUtils.isNotBlank(subject)) {
            String[] subjectArray = subject.split(",");
            teachers.setSubjectArray(subjectArray);
        }

        teachers.setGrade(grade);
        LOGGER.info("修改Teacher End");
        return AsyncResponseData.getSuccess(teachers);
    }

    /**
     * 删除teacher对象
     *
     * @param teacher
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteTeacher", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData deleteTeacher(Teacher teacher) throws Exception {
        LOGGER.info("删除Teacher Start");

        teacherService.deleteTeacherById(teacher.getId());
        userService.deleteUserBySourceId(teacher.getId());

        LOGGER.info("删除Teacher End");
        return AsyncResponseData.getSuccess();
    }

    /**
     * 获取teacher对象
     *
     * @param teacher
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/queryTeachers", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData queryTeachers(int pageNum, int pageSize, Teacher teacher) throws Exception {
        LOGGER.info("获取Teacher Start");

        User user = LoginInterceptor.getCurrentUser();



        Map<String, Object> filter = new HashMap<String, Object>();

        if (teacher != null) {
            if (!StringUtils.isBlank(teacher.getTeacherName())) {
                filter.put("teacherName", teacher.getTeacherName().trim());
            }
            if (!StringUtils.isBlank(teacher.getTeacherDuty())) {
                filter.put("teacherDuty", teacher.getTeacherDuty().trim());
            }
        }



        filter.put("classroom", teacher.getClassroom());

        filter.put("currentUserOrgId", user.getOrgId());

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Teacher> teachers = new PageInfo<Teacher>(teacherService.queryTeachersFilter(filter));

        LOGGER.info("获取Teacher End");

        return AsyncResponseData.getSuccess(teachers);
    }

    /**
     * 下载模板
     *
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/dowmloadTeacherListTemplate", method = RequestMethod.GET)
    @ResponseBody
    public void dowmloadTeacherListTemplate(HttpServletResponse response) throws Exception {
        String path = Objects.requireNonNull(TeacherController.class.getClassLoader().getResource("Template/teacherTemplate.xlsx")).getPath();
        FileDownload.fileDownload(response, path, "teacherTemplate.xlsx");
    }


    /**
     * 导入老师名单
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/importTeacherList", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData importTeacherList(MultipartFile file) {
        LOGGER.info("导入老师名单 Start");
        if (file == null) {
            return AsyncResponseData.getSuccess().asParamError("上传文件不合法");
        }
        String filename = file.getOriginalFilename();
        long size = file.getSize();
        if (filename == null || ExcelUtil.EMPTY.equals(filename) && size == 0) {
            return AsyncResponseData.getSuccess().asParamError("上传文件不合法");
        }
        try {
            //读取Excel数据到List中
            List<ArrayList<String>> teacherLists = new ExcelRead().readExcel(file);
            User user = LoginInterceptor.getCurrentUser();
            //list中存的就是excel中的数据，可以根据excel中每一列的值转换成你所需要的值（从0开始
            //去除重复后的名单
            List<ArrayList<String>> other = new ArrayList<>();
            List<ArrayList<String>> echo = new ArrayList<>();
            //重复名单
            List<ArrayList<String>> echosTeacher = new ArrayList<>();
            //校验手机号
            ArrayList<String> otherLists = new ArrayList<>();
            //表单数据去重
            for (ArrayList<String> list : teacherLists) {
                if (!otherLists.contains(list.get(5))) {
                    otherLists.add(list.get(5));
                    other.add(list);
                } else {
                    echo.add(list);
                }
            }
            if (other.size() != teacherLists.size()) {
                //二次去重
                teacherLists.clear();
                for (ArrayList<String> list : other) {
                    for (ArrayList<String> ec : echo) {
                        if (!ec.contains(list.get(5))) {
                            teacherLists.add(list);
                        } else {
                            echosTeacher.add(list);
                        }
                    }
                }
            }
            echosTeacher.addAll(echo);
            otherLists.clear();
            //拿到重复的老师姓名
            StringBuilder buffer = new StringBuilder();
            for (ArrayList<String> ec : echosTeacher) {
                buffer.append(ec.get(0)).append(",");
            }
            if (buffer.length() > 0) {
                buffer = new StringBuilder(buffer.substring(0, buffer.length() - 1));
            }
            Set<ArrayList<String>> setTeacher = new HashSet<>();
            Map<String, Object> filter = new HashMap<>();
            filter.put("currentUserOrgId", user.getOrgId());
            List<Teacher> teachers = teacherService.queryTeachersFilter(filter);
            //库里数据与表单数据去重
            for (ArrayList<String> list : teacherLists) {
                for (Teacher teach : teachers) {
                    String teacherMobile = teach.getTeacherMobile();
                    String mobile = list.get(5);
                    if (!teacherMobile.equals(mobile)) {
                        setTeacher.add(list);
                    }
                }
            }
            LOGGER.info("导入老师名单 End");
            if (setTeacher.size() > 0) {
                teacherService.insertTeacherListBatch(setTeacher);
                if (buffer.length() > 0) {
                    return AsyncResponseData.getSuccess().asParamError(buffer + "手机号重复，其他导入成功。");
                } else {
                    return AsyncResponseData.getSuccess().asParamError("导入成功");
                }
            } else {
                return AsyncResponseData.getSuccess().asParamError("表单无数据");
            }
        } catch (IOException e) {
            return AsyncResponseData.getSuccess().asParamError("文件数据错误");
        }
    }

    /**
     * 导出老师名单
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/exportTeacherList", method = RequestMethod.GET)
    @ResponseBody
    public void exportTeacherList(HttpServletResponse response, HttpServletRequest request) {
        LOGGER.info("导出老师名单 Start");
        Map<String, Object> filter = new HashMap<>();
        String teacherName = request.getParameter("teacherName");
        String teacherSex = request.getParameter("teacherSex");
        String teacherAge = request.getParameter("teacherAge");
        String teacherMobile = request.getParameter("teacherMobile");
        String teacherDuty = request.getParameter("teacherDuty");
        String teacherJobTitle = request.getParameter("teacherJobTitle");
        String classroom = request.getParameter("classroom");
        String subject = request.getParameter("subject");
        String remark = request.getParameter("remark");

        filter.put("teacherName", teacherName);
        filter.put("teacherSex", teacherSex);
        filter.put("teacherAge", teacherAge);
        filter.put("teacherMobile", teacherMobile);
        filter.put("teacherDuty", teacherDuty);
        filter.put("teacherJobTitle", teacherJobTitle);
        filter.put("classroom", classroom);
        filter.put("subject", subject);
        filter.put("remark", remark);
        teacherService.getTeacherLists(response, filter);
        LOGGER.info("导出老师名单 End");
    }
}