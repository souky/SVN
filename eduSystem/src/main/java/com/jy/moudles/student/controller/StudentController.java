package com.jy.moudles.student.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.ExcelRead;
import com.jy.common.utils.ExcelUtil;
import com.jy.common.utils.FileDownload;
import com.jy.common.utils.auth.UserUtils;
import com.jy.moudles.classroom.entity.Classroom;
import com.jy.moudles.classroom.service.ClassroomService;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.school.service.SchoolService;
import com.jy.moudles.student.entity.Student;
import com.jy.moudles.student.entity.StudentVo;
import com.jy.moudles.student.service.StudentService;
import com.jy.moudles.teacher.service.util.CheckData;
import com.jy.moudles.teacher.service.util.Util;
import com.jy.moudles.user.entity.User;
import com.jy.moudles.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * student实现类
 * <p>
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private SchoolService schoolService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClassroomService classroomService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    private static Map<String, List<ArrayList<String>>> wrongsMap = new HashMap<>();

    /**
     * 新增student对象
     *
     * @param student
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveStudent", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData saveStudent(Student student) throws Exception {
        LOGGER.info("新增Student Start");

        User user = LoginInterceptor.getCurrentUser();

        if (null == user) {
            return AsyncResponseData.getSuccess().asParamError("登陆超时");
        }

        School school = schoolService.getSchoolByOrgId(user.getOrgId());
        if (null != school && !StringUtils.isBlank(school.getId())) {
            if (StringUtils.isBlank(student.getStudentNo())) {
                return AsyncResponseData.getSuccess().asParamError("学号不能为空");
            }
            if (StringUtils.isBlank(student.getStudentName())) {
                return AsyncResponseData.getSuccess().asParamError("姓名不能为空");
            }
            if (StringUtils.isBlank(student.getClassroomId())) {
                return AsyncResponseData.getSuccess().asParamError("班级不能为空");
            }
            // 修改 【学生信息-新增】不同学校学生的学号不可以重复的问题。期望不同学校学生学号可以重复 by jinxiaoxiang 2018-1-2 Start
            if (null != studentService.getStudentByStuNo(student.getStudentNo(), user.getOrgId())) {
                return AsyncResponseData.getSuccess().asParamError("该学号已存在");
            }
            // 修改 【学生信息-新增】不同学校学生的学号不可以重复的问题。期望不同学校学生学号可以重复 by jinxiaoxiang 2018-1-2 End
            student.setCreateUser(user.getUserName());
            student.setOrgId(user.getOrgId());
            student.setSchoolId(school.getId());
            student.setCreateUser(user.getUserName());
            student.setCreateDate(new Date());
            student.setUpdateUser(user.getUserName());
            student.setUpdateDate(new Date());

            studentService.insertStudent(student);

            LOGGER.info("新增Student End");

            return AsyncResponseData.getSuccess();

        } else {

            LOGGER.info("新增Student End");

            return AsyncResponseData.getSuccess().asParamError("该用户无法添加学生");
        }

    }

    /**
     * 修改student对象
     *
     * @param student
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData updateStudent(Student student) throws Exception {
        LOGGER.info("修改Student Start");

        User user = LoginInterceptor.getCurrentUser();
        if (null == user) {
            return AsyncResponseData.getDenied("登陆超时");
        }
        if (StringUtils.isBlank(student.getStudentNo())) {
            return AsyncResponseData.getSuccess().asParamError("学号不能为空");
        }
        if (StringUtils.isBlank(student.getStudentName())) {
            return AsyncResponseData.getSuccess().asParamError("姓名不能为空");
        }
        Student studentTemp = studentService.getStudentByStuNo(student.getStudentNo(), user.getOrgId());

        if (null != studentTemp && !studentTemp.getId().equals(student.getId())) {
            return AsyncResponseData.getSuccess().asParamError("该学号已存在");
        }
        studentService.updateStudent(student);

        LOGGER.info("修改Student End");
        return AsyncResponseData.getSuccess();
    }

    /**
     * 删除student对象
     *
     * @param student
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/deleteStudent", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData deleteStudent(Student student) throws Exception {
        LOGGER.info("删除Student Start");

        studentService.deleteStudentById(student.getId());
        userService.deleteUserBySourceId(student.getId());

        LOGGER.info("删除Student End");
        return AsyncResponseData.getSuccess();
    }

    @RequestMapping(value = "/getStudentById", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData getStudentById(Student student) throws Exception {
        LOGGER.info("删除Student Start");

        Student students = studentService.getStudentById(student.getId());

        LOGGER.info("删除Student End");
        return AsyncResponseData.getSuccess(students);
    }

    /**
     * 获取student对象
     *
     * @param student
     * @return ResultData
     * @throws Exception
     */
    @RequestMapping(value = "/queryStudents", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData queryStudents(int pageNum, int pageSize, Student student) throws Exception {
        LOGGER.info("获取Student Start");

        User user = LoginInterceptor.getCurrentUser();
        if (null == user) {
            return AsyncResponseData.getSuccess().asParamError("登陆超时");
        }
        Map<String, Object> filter = new HashMap<String, Object>();
        if (null != student) {
            if (!StringUtils.isBlank(student.getStudentName())) {
                filter.put("studentName", student.getStudentName().trim());
            }
            if (!StringUtils.isBlank(student.getStudentNo())) {
                filter.put("studentNo", student.getStudentNo().trim());
            }
        }

        filter.put("classroomId", student.getClassroomId());
        filter.put("orgId", user.getOrgId());

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Student> students = new PageInfo<Student>(studentService.queryStudentsFilter(filter));
        LOGGER.info("获取Student End");

        return AsyncResponseData.getSuccess(students);
    }

    /**
     * 获取student对象 不分页
     *
     * @return ResultData
     * @throws Exception
     */
    @RequestMapping(value = "/queryStudentsWithoutPage", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData queryStudentsWithoutPage(@RequestParam(required = false) String grade) throws Exception {
        LOGGER.info("获取Student Start");

        Map<String, Object> filter = new HashMap<String, Object>();

        String orgId = UserUtils.getLoginUserOrgId();
        if (StringUtils.isNotBlank(orgId)) {
            filter.put("orgId", orgId);
        }
        if (StringUtils.isNotBlank(grade)) {
            filter.put("classroomName", grade);
        }
        List<Student> list = studentService.queryStudentsFilter(filter);
        //处理list
        List<StudentVo> listVo = new ArrayList<>();
        if (null != list && list.size() > 0) {
            for (Student e : list) {
                String classroom = e.getClassroomName();
                if (classroom != null) {
                    boolean flag = true;
                    for (StudentVo ee : listVo) {
                        if (classroom.equals(ee.getClassroomName())) {
                            List<Student> tempList = ee.getList();
                            tempList.add(e);
                            ee.setList(tempList);
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        StudentVo sv = new StudentVo();
                        sv.setClassroomName(classroom);
                        List<Student> tempList = new ArrayList<>();
                        tempList.add(e);
                        sv.setList(tempList);
                        listVo.add(sv);
                    }

                }
            }
        }
        LOGGER.info("获取Student End");

        return AsyncResponseData.getSuccess(listVo);
    }

    /**
     * 导入student
     *
     * @throws IOException
     */
    @RequestMapping(value = "/importStudent", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData importStudent(MultipartFile file) throws IOException {
        LOGGER.info("导入学生名单 Start");
        if (file == null) {
            return AsyncResponseData.getSuccess().asParamError("上传文件不合法");
        }
        String name = file.getOriginalFilename();
        long size = file.getSize();
        if (name == null || ExcelUtil.EMPTY.equals(name) && size == 0) {
            return AsyncResponseData.getSuccess().asParamError("上传文件不合法");
        }
        String xlsx = name.substring(name.length() - 4, name.length());
        if (!"xlsx".equals(xlsx) && !"xls".equals(name.substring(name.length() - 3, name.length()))) {
            return AsyncResponseData.getSuccess().asParamError("文件格式错误，必须为excel文件。");
        }
        try {
            User user = LoginInterceptor.getCurrentUser();
            //读取Excel数据到List中
            List<ArrayList<String>> studentList = new ExcelRead().readExcel(file);
            //学号校验
            List<String> listCheck = new ArrayList<>();
            List<String> classroomName = new ArrayList<>();
            String reg = "^[\\u4e00-\\u9fa5]*$";
            Map<String, Object> filter = new HashMap<>();
            School school = schoolService.getSchoolByOrgId(user.getOrgId());
            filter.put("schoolId", school.getId());
            filter.put("currentUserOrgId", user.getOrgId());
            List<Classroom> classrooms = classroomService.queryClassroomsFilterNew(filter);
            for (Classroom clam : classrooms) {
                classroomName.add(clam.getClassroomName());
            }
            //校验数据
            Map<String, List<ArrayList<String>>> check = Util.check(studentList, reg, classroomName);
            List<ArrayList<String>> errorData = check.get("error");
            List<ArrayList<String>> trueData = check.get("true");

            Map<String, List<ArrayList<String>>> stringObjectMap = CheckData.handleData(trueData, Util.STUDENTNO);
            //最终正确的数据
            List<ArrayList<String>> rightParam = stringObjectMap.get("rightParam");
            //表单重复的数据
            List<ArrayList<String>> errorParam = stringObjectMap.get("errorParam");
            //最终错误的数据
            errorParam.addAll(errorData);
            wrongsMap.put(user.getOrgId(), errorParam);
            List<ArrayList<String>> lists = new ArrayList<>();
            List<Student> studentsList = studentService.queryStudentsFilter(filter);
            StringBuffer stuname = new StringBuffer();
            //库里数据与表单数据去重   拿出库的所有学生的学号
            for (Student stu : studentsList) {
                listCheck.add(stu.getStudentNo());
            }
            for (ArrayList<String> stu : rightParam) {
                if (!listCheck.contains(stu.get(3))) {
                    lists.add(stu);
                } else {
                    stuname.append(stu.get(0)).append(",");
                }
            }
            if (stuname.length() > 0) {
                stuname = new StringBuffer(stuname.substring(0, stuname.length() - 1));
            }
            LOGGER.info("导入学生名单 End");
            if (lists.size() > 0) {
                studentService.insertStudents(lists);
                if (wrongsMap.size() > 0) {
                    wrongsMap.clear();
                }
                if (errorParam.size() > 0 && !StringUtils.isBlank(stuname)) {
                    return AsyncResponseData.getSuccess().asParamError(" 表单数据有误，已将excel形式返回请仔细检查，且 " + stuname + " 学号已存在，其他导入成功");
                } else if (!StringUtils.isBlank(stuname)) {
                    return AsyncResponseData.getSuccess().asParamError(stuname + " 学号已存在，其他导入成功");
                } else if (errorParam.size() > 0) {
                    return AsyncResponseData.getSuccess().asParamError(" 表单数据有误，已将excel形式返回请仔细检查,其他导入成功");
                } else {
                    return AsyncResponseData.getSuccess().asParamError("导入成功");
                }
            } else {
                return AsyncResponseData.getSuccess().asParamError("表单数据已存在");
            }

        } catch (OfficeXmlFileException e) {
            return AsyncResponseData.getSuccess().asParamError("文件数据不合法");
        }

    }

    /**
     * 下载模板
     *
     * @throws Exception
     */
    @RequestMapping(value = "/downloadStudentTemplate", method = RequestMethod.GET)
    @ResponseBody
    public void downloadStudentTemplate(HttpServletResponse response) throws Exception {
        String path = StudentController.class.getClassLoader().getResource("/Template/studentTemplate.xlsx").getPath();
        FileDownload.fileDownload(response, path, "学生信息模板.xlsx");
    }

    /**
     * 导出学生名单
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/exportStudentList", method = RequestMethod.GET)
    @ResponseBody
    public void exportStudentList(HttpServletResponse response, HttpServletRequest request) throws Exception {
        LOGGER.info("导出学生名单");
        Map<String, Object> filter = new HashMap<>();

        String studentName = request.getParameter("studentName");
        String studentNo = request.getParameter("studentNo");
        String grade = request.getParameter("grade");
        String classroom = request.getParameter("classtoom");

        if (StringUtils.isNotBlank(studentName) && !"null".equals(studentName)) {
            filter.put("studentName", studentName);
        }
        if (StringUtils.isNotBlank(studentNo) && !"null".equals(studentNo)) {
            filter.put("studentNo", studentNo);
        }
        if (StringUtils.isNotBlank(grade) && !"null".equals(grade)) {
            filter.put("grade", grade);
        }
        if (StringUtils.isNotBlank(classroom) && !"null".equals(classroom)) {
            filter.put("classroom", classroom);
        }
        studentService.getStudentLists(response, filter);
        LOGGER.info("导出学生名单 End");
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

        String path = Objects.requireNonNull(StudentController.class.getClassLoader().getResource("Template/studentTemplate.xlsx")).getPath();
        /************ 创建工作簿 *************/
        InputStream input = new FileInputStream(new File(path));
        HSSFWorkbook work = new HSSFWorkbook(input);
        HSSFSheet sheet = work.createSheet("错误数据列表");
        User user = LoginInterceptor.getCurrentUser();
        List<ArrayList<String>> wrongsData = wrongsMap.get(user.getOrgId());
        input.close();
        HSSFRow row;
        for (int i = 2; i < wrongsData.size(); i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(1);
            row.createCell(1).setCellValue(wrongsData.get(i).get(0));
            row.createCell(2).setCellValue(wrongsData.get(i).get(1));
            row.createCell(3).setCellValue(wrongsData.get(i).get(2));
            row.createCell(4).setCellValue(wrongsData.get(i).get(3));
            row.createCell(5).setCellValue(wrongsData.get(i).get(4));
            row.createCell(6).setCellValue(wrongsData.get(i).get(5));
            row.createCell(7).setCellValue(wrongsData.get(i).get(6));
            row.createCell(8).setCellValue(wrongsData.get(i).get(7));
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
