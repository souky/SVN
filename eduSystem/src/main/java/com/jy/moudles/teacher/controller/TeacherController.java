package com.jy.moudles.teacher.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.ExcelRead;
import com.jy.common.utils.ExcelUtil;
import com.jy.common.utils.FileDownload;
import com.jy.moudles.classroom.entity.Classroom;
import com.jy.moudles.classroom.service.ClassroomService;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.school.service.SchoolService;
import com.jy.moudles.subject.entity.Subject;
import com.jy.moudles.subject.service.SubjectService;
import com.jy.moudles.teacher.entity.Teacher;
import com.jy.moudles.teacher.service.TeacherService;
import com.jy.moudles.teacher.service.util.CheckData;
import com.jy.moudles.teacher.service.util.Util;
import com.jy.moudles.user.entity.User;
import com.jy.moudles.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
    @Autowired
    private SchoolService schoolService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private SubjectService subjectService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherController.class);


    private static Map<String, List<ArrayList<String>>> wrongsMap = new HashMap<>();
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
                boolean checkTeacherSub = Util.checkTeacherSub(subjectArray, classArray, subject, classroom);
                if (checkTeacherSub) {
                    return AsyncResponseData.getSuccess().asParamError("该老师教授的班级科目存在另一老师教授");
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
        if (null != listUser && !listUser.isEmpty() && listUser.size() == 1) {
            User users = listUser.get(0);
            if (null != users) {
                if (!users.getSourceId().equals(teacher.getId())) {
                    return AsyncResponseData.getSuccess().asParamError("该手机号已存在");
                }
            }
            userService.updateUser(users);
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
                    boolean checkTeacherSub = Util.checkTeacherSub(subjectArray, classArray, subject, classroom);
                    if (checkTeacherSub) {
                        return AsyncResponseData.getSuccess().asParamError("该老师教授的班级科目存在另一老师教授");
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
        FileDownload.fileDownload(response, path, "教师信息模板.xlsx");
    }

    /**
     * 导入老师名单
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/importTeacherList", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData importTeacherList(MultipartFile file) throws IOException {
        LOGGER.info("导入老师名单 Start");
        if (file == null) {
            return AsyncResponseData.getSuccess().asParamError("上传文件不合法");
        }
        String filename = file.getOriginalFilename();
        long size = file.getSize();
        if (filename == null || ExcelUtil.EMPTY.equals(filename) && size == 0) {
            return AsyncResponseData.getSuccess().asParamError("上传文件不合法");
        }
        String xlsx = filename.substring(filename.length() - 4, filename.length());
        if (!ExcelUtil.OFFICE_EXCEL_2010_POSTFIX.equals(xlsx) && !ExcelUtil.OFFICE_EXCEL_2003_POSTFIX.equals(filename.substring(filename.length() - 3, filename.length()))) {
                return AsyncResponseData.getSuccess().asParamError("文件格式错误，必须为excel文件。");
        }
        try {
            Map<String, Object> filter = new HashMap<>();
            //读取Excel数据到List中
            List<ArrayList<String>> teacherLists = new ExcelRead().readExcel(file);
            User user = LoginInterceptor.getCurrentUser();
            //校验手机号
            ArrayList<String> mobileLists = new ArrayList<>();
            String reg = "^[\\u4e00-\\u9fa5]*$";
            School school = schoolService.getSchoolByOrgId(user.getOrgId());
            filter.put("schoolId", school.getId());
            filter.put("currentUserOrgId", user.getOrgId());
            List<Classroom> classrooms = classroomService.queryClassroomsFilterNew(filter);
            List<String> classroomName = new ArrayList<>();
            for (Classroom classroom : classrooms) {
                classroomName.add(classroom.getClassroomName());
            }
            filter.clear();
            List<Subject> subjects = subjectService.querySubjectsFilter(filter);
            List<String> subjectsName = new ArrayList<>();

            for (Subject subject : subjects) {
                subjectsName.add(subject.getSubjectName());
            }
            //校验数据
            Map<String, List<ArrayList<String>>> objectMap = Util.check(teacherLists, reg, classroomName, subjectsName);
            List<ArrayList<String>> errorData = objectMap.get("error");
            List<ArrayList<String>> trueData = objectMap.get("true");

            Map<String, List<ArrayList<String>>> stringObjectMap = CheckData.handleData(trueData, Util.TEACHERMOBILE);
            //最终正确的数据
            List<ArrayList<String>> rightParam = stringObjectMap.get("rightParam");
            //表单重复的数据
            List<ArrayList<String>> errorParam = stringObjectMap.get("errorParam");
            //最终错误的数据
            errorParam.addAll(errorData);
            wrongsMap.put(user.getOrgId(), errorParam);
            List<ArrayList<String>> setTeacher = new ArrayList<>();
            filter.put("schoolId", school.getId());
            filter.put("currentUserOrgId", user.getOrgId());
            List<Teacher> teachers = teacherService.queryTeachersFilter(filter);
            //库里数据与表单数据去重
            StringBuffer teachname = new StringBuffer();
            for (Teacher teach : teachers) {
                mobileLists.add(teach.getTeacherMobile());//所有老师的号码
            }
            for (ArrayList<String> list : rightParam) {
                if (!mobileLists.contains(list.get(5))) {
                    setTeacher.add(list);
                } else {
                    teachname.append(list.get(0)).append(",");
                }
            }
            if (teachname.length() > 0) {
                teachname = new StringBuffer(teachname.substring(0, teachname.length() - 1));
            }
            LOGGER.info("导入老师名单 End");
            if (setTeacher.size() > 0) {
                teacherService.insertTeacherListBatch(setTeacher);
                if (wrongsMap.size() > 0) {
                    wrongsMap.clear();
                }
                if (!StringUtils.isBlank(teachname) && errorParam.size() > 0) {
                    return AsyncResponseData.getSuccess().asParamError(" 表单数据存在错误，已将excel形式返回请仔细检查，且" + teachname + "手机号已存在， 其他导入成功");
                }
                if (!StringUtils.isBlank(teachname)) {
                    return AsyncResponseData.getSuccess().asParamError(teachname + "手机号已存在， 其他导入成功");
                } else if (errorParam.size() > 0) {
                    return AsyncResponseData.getSuccess().asParamError("  表单数据存在错误，已将excel形式返回请仔细检查，其他导入成功");
                } else {
                    return AsyncResponseData.getSuccess().asParamError("导入成功");
                }
            } else {
                return AsyncResponseData.getSuccess().asParamError("表单数据已存在");
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
//        String teacherJobTitle = request.getParameter("teacherJobTitle");
        String classroom = request.getParameter("classroom");
        String subject = request.getParameter("subject");
//        String remark = request.getParameter("remark");

        filter.put("teacherName", teacherName);
        filter.put("teacherSex", teacherSex);
        filter.put("teacherAge", teacherAge);
        filter.put("teacherMobile", teacherMobile);
        filter.put("teacherDuty", teacherDuty);
//        filter.put("teacherJobTitle", teacherJobTitle);
        filter.put("classroom", classroom);
        filter.put("subject", subject);
//        filter.put("remark", remark);
        teacherService.getTeacherLists(response, filter);
        LOGGER.info("导出老师名单 End");
    }

    /**
     * 返回错误数据
     *
     * @param
     * @throws IOException
     */
    @SuppressWarnings("resource")
	@RequestMapping(value = "/exportData", method = RequestMethod.GET)
    public AsyncResponseData.ResultData exportData(HttpServletResponse response) throws IOException {

        String path = Objects.requireNonNull(TeacherController.class.getClassLoader().getResource("Template/teacherTemplate.xlsx")).getPath();
        /************ 创建工作簿 *************/
        InputStream input = new FileInputStream(new File(path));
        HSSFWorkbook work = new HSSFWorkbook(input);

        User user = LoginInterceptor.getCurrentUser();
        List<ArrayList<String>> wrongsData = wrongsMap.get(user.getOrgId());
        HSSFSheet sheet = work.createSheet("错误数据列表");
        input.close();
        HSSFRow row;
        for (int i = 2; i < wrongsData.size(); i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(0);
            row.createCell(1).setCellValue(wrongsData.get(i).get(0));
            row.createCell(2).setCellValue(wrongsData.get(i).get(1));
            row.createCell(3).setCellValue(wrongsData.get(i).get(2));
            row.createCell(4).setCellValue(wrongsData.get(i).get(3));
            row.createCell(5).setCellValue(wrongsData.get(i).get(4));
            row.createCell(6).setCellValue(wrongsData.get(i).get(5));
            row.createCell(7).setCellValue(wrongsData.get(i).get(6));
        }
        work.write();
        response.reset();
        //获取当前系统时间
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        OutputStream output = response.getOutputStream();
        String fileName = "错误信息集.xlsx";
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName + format, "utf-8"));
        response.setContentType("application/msexcel");
        work.write(output);
        output.flush();
        output.close();
        return AsyncResponseData.getSuccess();
    }
}