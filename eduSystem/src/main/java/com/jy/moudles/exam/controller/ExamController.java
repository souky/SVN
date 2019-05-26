package com.jy.moudles.exam.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.UUIDUtil;
import com.jy.common.utils.auth.UserUtils;
import com.jy.moudles.classroom.entity.Classroom;
import com.jy.moudles.classroom.service.ClassroomService;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;
import com.jy.moudles.examSpecificationRelation.entity.ExamSpecificationRelation;
import com.jy.moudles.examSpecificationRelation.service.ExamSpecificationRelationService;
import com.jy.moudles.examStudentRelation.entity.ExamStudentByClass;
import com.jy.moudles.examStudentRelation.entity.ExamStudentRelation;
import com.jy.moudles.examStudentRelation.service.ExamStudentRelationService;
import com.jy.moudles.role.constant.RoleConstant;
import com.jy.moudles.role.entity.Role;
import com.jy.moudles.role.service.RoleService;
import com.jy.moudles.score.entity.Score;
import com.jy.moudles.student.entity.Student;
import com.jy.moudles.student.service.StudentService;
import com.jy.moudles.subject.entity.Subject;
import com.jy.moudles.subject.service.SubjectService;
import com.jy.moudles.twoWaySpecificationDetail.entity.TwoWaySpecificationDetail;
import com.jy.moudles.twoWaySpecificationDetail.service.TwoWaySpecificationDetailService;
import com.jy.moudles.user.entity.User;

/**
 * exam实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/exam")
public class ExamController {
	@Autowired
	private TwoWaySpecificationDetailService twoWaySpecificationDetailService;
	@Autowired
	private StudentService studentService;
    @Autowired
    private ExamService examService;

    @Autowired
    private ExamStudentRelationService examStudentRelationService;

    @Autowired
    private ExamSpecificationRelationService examSpecificationRelationService;
    
    @Autowired
    private SubjectService subjectService;
    
    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private RoleService roleService;
    
//    @Autowired
//    private TwoWaySpecificationService twoWaySpecificationService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamController.class);

    /**
     * 新增exam对象
     *
     * @param exam
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/saveExam", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData saveExam(Exam exam,@RequestParam(required = false) String examStudent, 
    		@RequestParam(required = false)String examSpecification) throws Exception{
        LOGGER.info("新增Exam Start");

        User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}
        
        if(exam == null) {
        	return AsyncResponseData.getSuccess().asParamError("请输入考试计划基本信息");
        }
        
        //查重
        String examName = exam.getExamName();
        Map<String,Object> filter = new HashMap<>();
        if(null == examName || examName.trim().equals("")) {
        	return AsyncResponseData.getSuccess().asParamError("请输入考试计划名称");
        }
        
        filter.put("examName", examName);
        filter.put("orgId", user.getOrgId());
        List<Exam> listCheck = new ArrayList<>();
        listCheck = examService.queryExamsFilter(filter);
        if(null != listCheck && listCheck.size() > 1) {
        	return AsyncResponseData.getSuccess().asParamError("考试计划名称重复");
        }
        if(null != listCheck && listCheck.size() > 0) {
        	Exam exams = listCheck.get(0);
        	if(!exams.getId().equals(exam.getId())) {
        		return AsyncResponseData.getSuccess().asParamError("考试计划名称重复");
        	}
        }
        
        Date examStartDate = exam.getExamStartDate();
        Date examEndDate = exam.getExamEndDate();
        if(null == examStartDate) {
        	return AsyncResponseData.getSuccess().asParamError("考试开始时间为空");
        }
        if(null == examEndDate) {
        	return AsyncResponseData.getSuccess().asParamError("考试结束时间为空");
        }
        if(examStartDate.getTime() > examEndDate.getTime()) {
        	return AsyncResponseData.getSuccess().asParamError("考试开始时间不能大于结束时间");
        }
        if (examStartDate.getTime() < System.currentTimeMillis()) {
            return AsyncResponseData.getSuccess().asParamError("开始时间不能小于当前时间");
        }
        if (examEndDate.getTime() < System.currentTimeMillis()) {
            return AsyncResponseData.getSuccess().asParamError("结束时间不能小于当前时间");
        }     
        
        filter.clear();
        filter.put("examClassroom", exam.getClassroom());
        filter.put("orgId", user.getOrgId());
        //filter.put("examSubject", exam.getSubject());
        List<Exam> satisficationExam = new ArrayList<>();
        satisficationExam = examService.queryExamsFilter(filter);
        for (Exam exam2 : satisficationExam) {
        	if(!exam2.getId().equals(exam.getId())) {
        		if ((exam.getExamStartDate()).after(exam2.getExamStartDate()) && (exam.getExamStartDate()).before(exam2.getExamEndDate()) || examStartDate.getTime() ==exam2.getExamStartDate().getTime()) {
    				return AsyncResponseData.getSuccess().asParamError("考试时间冲突");
    			}
    			if ((exam.getExamStartDate().before(exam2.getExamStartDate()))) {
    				if (exam.getExamEndDate().after(exam2.getExamStartDate()) || examEndDate.getTime() == exam2.getExamEndDate().getTime() || examEndDate.getTime() ==exam2.getExamStartDate().getTime()) {
    					return AsyncResponseData.getSuccess().asParamError("考试时间冲突");
    				}
    			}
        	}
		}
        
        
        exam.setOrgId(user.getOrgId());
        exam.setCreateUser(user.getUserName());
        if(StringUtils.isBlank(exam.getId())) {
        	//放入uuid
            exam.setId(UUIDUtil.get32UUID());
            
            //插入考试计划
            examService.insertExam(exam);
        }else {
        	examService.updateExam(exam);
        	//删除现有的考试&考生关系
            examStudentRelationService.deleteExamStudentRelationByExamId(exam.getId());
            //删除现有的考试&细目表关系
            examSpecificationRelationService.deleteExamSpecificationRelationByExamId(exam.getId());
        }
        
        
    	List<ExamSpecificationRelation> examSpecifications = JSON.parseArray(examSpecification, ExamSpecificationRelation.class);
        List<ExamStudentRelation> examStudents = JSON.parseArray(examStudent, ExamStudentRelation.class);
        
        if(examStudents!=null && !examStudents.isEmpty()){
            for(ExamStudentRelation examStu : examStudents){
                examStu.setExamId(exam.getId());
                examStu.setId(UUIDUtil.get32UUID());
                examStu.setCreateUser(user.getUserName());
            }
            //批量插入考生信息
            examStudentRelationService.batchInsertExamStudentRelation(examStudents);
        }else {
        	return AsyncResponseData.getSuccess().asParamError("未绑定考生信息");
        }

        if(examSpecifications!=null && !examSpecifications.isEmpty()){
        	List<ExamSpecificationRelation> batchList = new ArrayList<>();
            for(ExamSpecificationRelation examSp : examSpecifications){
            	if(StringUtils.isNotBlank(examSp.getSpId())) {
            		 examSp.setExamId(exam.getId());
                     examSp.setId(UUIDUtil.get32UUID());
                     examSp.setCreateUser(user.getUserName());
                     batchList.add(examSp);
            	}
            }
            
            if (batchList.size() > 1)  {
            	 //批量插入双向细目表信息
                examSpecificationRelationService.batchInsertExamSpecificationRelation(batchList);
            }
        }else {
        	return AsyncResponseData.getSuccess().asParamError("未绑定双向细目表信息");
        }
        
        LOGGER.info("新增Exam End");
        return AsyncResponseData.getSuccess();
    }

    @RequestMapping(value = "/saves",method = RequestMethod.GET)
    public void saves() {
    	//批量插入学生
    	List<Student> students = new ArrayList<>();
    	Student student = null;
    	for (int i = 0; i < 1000; i++) {
    		student = new Student();
    		student.setId(UUIDUtil.get32UUID());
    		student.setStudentNo("111222");
    		student.setSchoolName("张"+i);
    		student.setStudentSex(1);
    		student.setStudentAge(11);
    		if (0 <= i && i < 100) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04342");
    		} else if (100 <= i && i < 200) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04343");
    		} else if (200 <= i && i < 300) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04344");
    		} else if (300 <= i && i < 400) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04345");
    		} else if (400 <= i && i < 500) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04346");
    		} else if (500 <= i && i < 600) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04347");
    		} else if (600 <= i && i < 700) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04348");
    		} else if (700 <= i && i < 800) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04349");
    		} else if (800 <= i && i < 900) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04351");
    		} else if (900 <= i && i < 1000) {
    			student.setClassroomId("e22bf46049b44746900489fb7e04352");
    		} 
    		student.setSchoolId("803bb8e7f3a34f3abb7e339d227a2a23");
    		student.setOrgId("803bb8e7f3a34f3abb7e339d227a2ad1");
    		student.setCreateUser("test1");
    		students.add(student);
    	}
    	
    	studentService.insertStudentsNew(students);
    	
    	//批量插入考生信息
    	List<ExamStudentRelation> examStudentRelations = new ArrayList<>();
    	ExamStudentRelation ex = null;
    	for (Student student1 : students) {
    		ex = new ExamStudentRelation();
    		ex.setExamId("1fda6353b4354f5491dfed20b50508c2");
            ex.setId(UUIDUtil.get32UUID());
            ex.setCreateUser("test1");
            ex.setStudentId(student1.getId());
            ex.setExamStuNo("131407321");
            ex.setStatus(0);
            ex.setCreateUser("test1");
            examStudentRelations.add(ex);
		}
    	examStudentRelationService.batchInsertExamStudentRelation(examStudentRelations); 
    	
    	//批量插入分数
    	List<Score> scoreList = new ArrayList<>();
    	Score score = null;
    	List<Map<String,Object>> paramAll = new ArrayList<>();
    	List<Map<String,Object>> param = new ArrayList<>();
    	Map<String,Object> paramMap = new LinkedHashMap<>();
    	Map<String,Object> paramMap1 = new LinkedHashMap<>();
    	Random random = new Random();
    	for (Student sts : students) {
    		score = new Score();
    		score.setId(UUIDUtil.get32UUID());
    		score.setExamId("1fda6353b4354f5491dfed20b50508c2");
    		score.setStudentId(sts.getId());
    		score.setOrgId("803bb8e7f3a34f3abb7e339d227a2ad1");
    		score.setClassroomId(sts.getClassroomId());
    		score.setSchoolId("803bb8e7f3a34f3abb7e339d227a2a23");
    		paramAll =  new ArrayList<>();
    		int totalScore = 0;
    		for (int y = 0; y < 5; y++) {
    			paramMap = new LinkedHashMap<>();
    			String sub = y == 0?"语文":y == 1?"数学":y == 2?"英语":y == 3?"物理":"化学";
    			paramMap.put("subject", sub) ;
    			param =  new ArrayList<>();
    			int gkScore = 0;
    			for (int k = 0; k < 11; k++) {
    				int ro = random.nextInt(5)+1;
    				totalScore += ro;
    				gkScore += ro;
    				paramMap1 = new LinkedHashMap<>();
    				paramMap1.put("itemNo", ""+(k+1));
        			paramMap1.put("itemType", k > 4?"1":"0");
        			paramMap1.put("score", ro);
        			paramMap1.put("totalScore", "5");
        			param.add(paramMap1);
    			}
    			
    			if (y == 0) {
    				score.setChineseScore(new BigDecimal(gkScore+""));
    			} else if (y == 1) {
    				score.setMathScore(new BigDecimal(gkScore+""));
    			} else if (y == 2) {
    				score.setEnglishScore(new BigDecimal(gkScore+""));
    			} else if (y == 3) {
    				score.setPhysicalScore(new BigDecimal(gkScore+""));
    			} else if (y == 4) {
    				score.setChemicalScore(new BigDecimal(gkScore+""));
    			}
    			
    			paramMap.put("detailList", param);
    			paramAll.add(paramMap);
    		}
    		
    		
    		JSONArray array = (JSONArray) JSONArray.toJSON(paramAll);
    		score.setDetailScore(array.toString());
    		score.setTotalScore(new BigDecimal(totalScore+""));
    		score.setCreateUser("test1");
    		score.setCreateDate(new Date());
    		scoreList.add(score);
		}
    	studentService.insertScores(scoreList);
    }
    
    @RequestMapping("/saves1")
    public void saves1() {
    	List<TwoWaySpecificationDetail> list = new LinkedList<>();
    	TwoWaySpecificationDetail twoWay = null;
    	for (int y = 0; y < 5; y++) {
    		for (int i = 0; i < 11; i++) {
    			twoWay = new TwoWaySpecificationDetail();
        		twoWay.setId(UUIDUtil.get32UUID());
        		twoWay.setItemNo(i+1);
        		twoWay.setItemType(i > 4?1:0);
        		twoWay.setItemScore(5);
        		String parntId = y == 0?"e889e3acc9154a398a0336de728bfa50":y==1?"e889e3acc9154a398a0336de728bfa51":y==2?"e889e3acc9154a398a0336de728bfa52":y==3?"e889e3acc9154a398a0336de728bfa53":"e889e3acc9154a398a0336de728bfa54";
        		twoWay.setParentId(parntId);
        		twoWay.setOrgId("803bb8e7f3a34f3abb7e339d227a2ad1");
        		twoWay.setCreateUser("test1");
        		list.add(twoWay);
        	}
    	}
    	
    	twoWaySpecificationDetailService.batchInsertTwoWaySpecificationDetail(list);
    }
    
    
    /**
     * 修改exam对象
     *
     * @param exam
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/updateExam", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData updateExam(Exam exam, ArrayList<ExamStudentRelation> examStudents,
    		ArrayList<ExamSpecificationRelation> examSpecifications) throws Exception{
        LOGGER.info("修改Exam Start");
        
        User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}

        if(null == exam) {
        	return AsyncResponseData.getSuccess().asParamError("请输入考试计划基本信息");
        }
        
        if (!user.getOrgId().equals(exam.getOrgId())) {
        	return AsyncResponseData.getDenied("该角色无权限删除此信息");
        }
        //如果该考试的结束时间小于当前时间，不允许更新操作
        if(exam.getExamEndDate().before(new Date())){
            return AsyncResponseData.getSuccess().asParamError("已结束的考试不允许编辑操作");
        }
        
        examService.updateExam(exam);
        //删除现有的考试&考生关系
        examStudentRelationService.deleteExamStudentRelationByExamId(exam.getId());
        //删除现有的考试&细目表关系
        examSpecificationRelationService.deleteExamSpecificationRelationByExamId(exam.getId());

        //重新绑定考试&细目表关系、考试&考生关系
        if(examStudents!=null && !examStudents.isEmpty()){
            for(ExamStudentRelation examStu : examStudents){
                examStu.setExamId(exam.getId());
            }
            //批量插入考生信息
            examStudentRelationService.batchInsertExamStudentRelation(examStudents);
        }

        if(examSpecifications!=null && !examSpecifications.isEmpty()){
        	
            for(ExamSpecificationRelation examSp : examSpecifications){
                examSp.setExamId(exam.getId());
            }
            //批量插入双向细目表信息
            examSpecificationRelationService.batchInsertExamSpecificationRelation(examSpecifications);
        }

        LOGGER.info("修改Exam End");
        return AsyncResponseData.getSuccess();
    }


    /**
     *
     * @param exam
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteExam", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData deleteExam(Exam exam) throws Exception{
    	
    	LOGGER.info("删除Exam Start");
    	
    	User user = LoginInterceptor.getCurrentUser();
 		
 		if (null == user){
 			return AsyncResponseData.getDenied("登录过期，请重新登录");
 		}

		if (null == exam || StringUtils.isBlank(exam.getId())) {
			Map<String, String> errMsg = new HashMap<String, String>();
			errMsg.put("id", "请选择要删除的文件");
			return AsyncResponseData.getParamError(errMsg);
		}
        
        exam = examService.getExamById(exam.getId());
        
        if (null != exam) {
        	if (!exam.getOrgId().equals(user.getOrgId())) {
        		return AsyncResponseData.getDenied("该角色无权限删除此信息");
            }
        	
        	//如果该考试的结束时间小于当前时间，不允许删除操作
        	if(exam.getExamEndDate().before(new Date())){
        		return AsyncResponseData.getSuccess().asParamError("已结束的考试不允许删除操作");
        	}
        	
        	
        	//删除考试计划
        	examService.deleteExamById(exam.getId());
        	
        	//删除现有的考试&考生关系
        	examStudentRelationService.deleteExamStudentRelationByExamId(exam.getId());
        	
        	//删除现有的考试&细目表关系
            examSpecificationRelationService.deleteExamSpecificationRelationByExamId(exam.getId());
        	
        }
        
//        Map<String,Object> filter = new HashMap<>();
//        filter.put("examId",exam.getId());
//        List<ExamSpecificationRelation> examSpecificationRelations = examSpecificationRelationService.queryExamSpecificationRelationsFilter(filter);
//        if (examSpecificationRelations!=null && !examSpecificationRelations.isEmpty()){
//            for (ExamSpecificationRelation examSp : examSpecificationRelations){
//                //删除与该考试计划绑定双向细目详细表
//                twoWaySpecificationDetailService.deleteTwoWaySpDetailsByPid(examSp.getExamId());
//                //删除与该考试计划绑定的双向细目表
//                twoWaySpecificationService.deleteTwoWaySpecificationById(examSp.getExamId());
//            }
//        }
        
        LOGGER.info("删除Exam End");
        return AsyncResponseData.getSuccess();
    }
    
    /**
     * 获取exam对象
     *
     * @param exam
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/queryExams", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData queryExams(int pageNum, int pageSize, Exam exam) throws Exception{
        LOGGER.info("获取Exam Start");
        
        User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}

        Map<String, Object> filter = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(exam.getExamName())){
            filter.put("examName", exam.getExamName());
        }
        if (StringUtils.isNotBlank(exam.getClassroom())){
            filter.put("classroom", exam.getClassroom());
        }
        if (StringUtils.isNotBlank(exam.getSubject())){
            filter.put("subject",exam.getSubject());
        }
        if(exam.getExamStatus() != 0) {
        	filter.put("examStatus",exam.getExamStatus());
        }
        filter.put("orgId", user.getOrgId());

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Exam> exams= new PageInfo<Exam>(examService.queryExamsFilter(filter));
        LOGGER.info("获取Exam End");

        return AsyncResponseData.getSuccess(exams);
    }

    /**
     * 根据examId获取exam对象
     *
     * @param examId
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/getExamById", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData getExamById(String examId) throws Exception{
        LOGGER.info("获取Exam Start");
        
        User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> filter = new HashMap<String, Object>();

        //得到考试基本信息
        Exam exam = examService.getExamById(examId);
        
        if (null != exam && !exam.getOrgId().equals(user.getOrgId())) {
        	exam = null;
        }
        if (exam!=null){
            //得到考生信息
        	List<ExamStudentRelation> listStu = examStudentRelationService.getExamStudentRelationByExamId(examId);
        	String [] classrooms = exam.getClassroom().split(",");
        	List<ExamStudentByClass> examStudentByClassList = new ArrayList<>();
          	ExamStudentByClass examStudentByClass;
        	if(null != listStu && listStu.size() > 0) {
        		for(String s : classrooms) {
        			examStudentByClass = new ExamStudentByClass();
        			if(StringUtils.isNotBlank(s)) {
        				examStudentByClass.setClassroomName(s);
        				List<ExamStudentRelation> lists = new ArrayList<>();
            			for(ExamStudentRelation e : listStu) {
                			if(s.equals(e.getClassroom())) {
                				lists.add(e);
                			}
                		}
            			examStudentByClass.setList(lists);
            			examStudentByClassList.add(examStudentByClass);
        			}
        		}
        		
        	}
        	
//            filter.put("examId", examId);
//            String [] classrooms = exam.getClassroom().split(",");
//            List<ExamStudentByClass> examStudentByClassList = new ArrayList<>();
//            ExamStudentByClass examStudentByClass;
//
//            for(int i = 0 ; i < classrooms.length ; i++){
//                examStudentByClass = new ExamStudentByClass();
//                filter.put("classroom",classrooms[i]);
//                examStudentByClass.setClassroom(classrooms[i]);
//                examStudentByClass.setExamStudentRelationList(examStudentRelationService.queryExamStudentRelationsFilter(filter));
//                examStudentByClassList.add(examStudentByClass);
//            }
        	

            //得到双向细目表信息
            filter.clear();
            filter.put("examId", examId);
            List<ExamSpecificationRelation> twList = examSpecificationRelationService.queryExamSpecificationRelationsFilter(filter);
            
//            String [] subjects = exam.getSubject().split(",");
//            List<ExamSpBySubject> examSpBySubjectList = new ArrayList<>();
//            ExamSpBySubject examSpBySubject;
//
//            for(int i = 0; i < subjects.length; i++){
//                examSpBySubject = new ExamSpBySubject();
//                filter.put("subject",subjects[i]);
//                examSpBySubject.setSubject(subjects[i]);
//                examSpBySubject.setExamSpecificationRelations(examSpecificationRelationService.queryExamSpecificationRelationsFilter(filter));
//                examSpBySubjectList.add(examSpBySubject);
//            }

            resultMap.put("mapStu",examStudentByClassList);
            resultMap.put("examSpBySubjectList",twList);
        }
        
        resultMap.put("exam",exam);

        LOGGER.info("获取Exam End");
        return AsyncResponseData.getSuccess(resultMap);
    }

    /**
     * 获取exam对象
     *
     * @param exam
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "/queryExamsOnline", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData queryExamsOnline(Exam exam) throws Exception {
        LOGGER.info("获取Exam Start");
        Map<String, Object> filter = new HashMap<>();
        
        String orgId = UserUtils.getLoginUserOrgId();
        //组织机构
        if (StringUtils.isNotBlank(orgId)) {
            filter.put("orgId", orgId);
        }else {
        	return AsyncResponseData.getSuccess().asParamError("登陆超时");
        }
        
        if (exam == null) {
            return null;
        }
        //考试名称不能为空
        if (StringUtils.isNotBlank(exam.getExamName())) {
            filter.put("examName", exam.getExamName());
        }
        //开始时间不能为空
        if (exam.getExamStartDate() != null) {
            filter.put("examStartDate", exam.getExamStartDate());
        }
        //结束时间不能为空
        if (exam.getExamEndDate() != null) {
            filter.put("examEndDate", exam.getExamEndDate());
        }
        //考试状态不能为空
        if (exam.getExamStatus() > -1) {
            filter.put("examStatus", exam.getExamStatus());
        }
        
        List<Exam> exams = examService.queryExamsFilter(filter);
        LOGGER.info("获取Exam End");

        return AsyncResponseData.getSuccess(exams);
    }

    /**
     * 获取 exam 对象
     *
     * 预留一个平台上的考试查询
     *
     * @param examId
     * @return ModelAndView
     * @throws Exception
     */

    @RequestMapping(value = "getExamByIdOnline" ,method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData getExamByIdOnline(String examId) throws Exception{
        LOGGER.info("获取exam Start");
        Exam examById = examService.getExamById(examId);
        LOGGER.info("获取exam End");
        return AsyncResponseData.getSuccess(examById);
    }

    @RequestMapping(value = "/getExamListForTab", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData getExamListForTab() throws Exception {
        LOGGER.info("获取getExamListForTab Start");

        Map<String, Object> resultMap = new HashMap<>();

        User user = UserUtils.getLoginUser();
        if (user == null){
            return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
        }

        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("examStatus",2);
        filter.put("orgId", UserUtils.getLoginUserOrgId());
        filter.put("sort","exam_end_date");
        filter.put("sortType","desc");
        if(StringUtils.isNotBlank(user.getRoleId())){
            Role role = roleService.getRoleById(user.getRoleId());
            if (role != null && RoleConstant.STUDENT_ROLE.equals(role.getRoleName())){
                filter.put("studentId",user.getSourceId());
            }
        }
        List<Exam> exams = examService.getExamListForTab(filter);
        
       
      
        
        if(exams!=null && !exams.isEmpty()) {
        	for(int i = 0;i < exams.size();i++) {
        		Map<String,Object> mapId = new HashMap<>();
        		String classroomString = exams.get(i).getClassroom();
        		if(StringUtils.isNotBlank(classroomString)) {
        			List<String> list = Arrays.asList(classroomString.split(","));
        			List<Classroom> listClass = classroomService.getClassroomByNames(list,user.getOrgId());
        			mapId.put("classroom", listClass);
        		}
        			
        		String subjectString = exams.get(i).getSubject();
        		if(StringUtils.isNotBlank(subjectString)) {
        			List<String> list = Arrays.asList(subjectString.split(","));
        			List<Subject> listSub = subjectService.querySubjectsByNames(list);
        			mapId.put("subject", listSub);
        		}
        		resultMap.put(exams.get(i).getId(), mapId);
        	}
        	
        }
        
        
//        if (exams!=null && !exams.isEmpty()){
//
//            if (StringUtils.isNotBlank(examId)){
//                Exam tmpExam = new Exam();
//                for (Exam exam : exams){
//                    if (examId.equals(exam.getId())){
//                        tmpExam = exam;
//                    }
//                }
//                exams.remove(tmpExam);
//                exams.add(0,tmpExam);
//            }
//
//            String [] subjects;
//            String [] classrooms;
//            if (StringUtils.isNotBlank(exams.get(0).getSubject()) && StringUtils.isNotBlank(exams.get(0).getClassroom())){
//                subjects = exams.get(0).getSubject().split(",");
//                classrooms = exams.get(0).getClassroom().split(",");
//                resultMap.put("subjects",subjects);
//                resultMap.put("classrooms",classrooms);
//            }
//
//        }
        resultMap.put("exams",exams);


        LOGGER.info("获取getExamListForTab End");

        return AsyncResponseData.getSuccess(resultMap);
    }

}
