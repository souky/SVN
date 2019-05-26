package com.jy.moudles.score.controller;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.auth.UserUtils;
import com.jy.common.utils.mathtools.EduEvaluatingUtils;
import com.jy.moudles.classroom.entity.Classroom;
import com.jy.moudles.classroom.service.ClassroomService;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;
import com.jy.moudles.organization.service.OrganizationService;
import com.jy.moudles.role.constant.RoleConstant;
import com.jy.moudles.role.service.RoleService;
import com.jy.moudles.score.constant.CompareUtils;
import com.jy.moudles.score.constant.ScoreConstant;
import com.jy.moudles.score.entity.ClassReport;
import com.jy.moudles.score.entity.ClassTopScore;
import com.jy.moudles.score.entity.ProcessedScores;
import com.jy.moudles.score.entity.Score;
import com.jy.moudles.score.entity.ScoreVO;
import com.jy.moudles.score.entity.SubjectScore;
import com.jy.moudles.score.entityVO.StudentAppVO;
import com.jy.moudles.score.entityVO.SummaryVO;
import com.jy.moudles.score.service.ScoreService;
import com.jy.moudles.student.entity.Student;
import com.jy.moudles.student.service.StudentService;
import com.jy.moudles.teacher.entity.Teacher;
import com.jy.moudles.teacher.service.TeacherService;
import com.jy.moudles.user.entity.User;

/**
 * score实现类
 *
 * 创建人：1
 * 创建时间：2017-12-11
 */
@Controller
@RequestMapping(value="/score")
public class ScoreController {

	@Autowired
	private ScoreService scoreService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ExamService examService;


	@Autowired
	private ClassroomService classroomService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ScoreController.class);

	private static Map<String,String> subjectMap = new HashMap<String, String>();
	static {
		subjectMap.put("总分"     , "totalScore");
		subjectMap.put("语文"     , "chineseScore");
		subjectMap.put("数学"     , "mathScore");
		subjectMap.put("英语"     , "englishScore");
		subjectMap.put("物理"     , "physicalScore");
		subjectMap.put("化学"     , "chemicalScore");
		subjectMap.put("生物"     , "biologyScore");
		subjectMap.put("地理"     , "geographyScore");
		subjectMap.put("科学"     , "scienceScore");
		subjectMap.put("政治"     , "politicsScore");
		subjectMap.put("历史"     , "historyScore");
		subjectMap.put("思想品德"  , "moralScore");
		subjectMap.put("历史与社会", "historySocietyScore");
		subjectMap.put("品德与社会", "qualitySocietyScore");
		subjectMap.put("品德与生活", "qualityLifeScore");
		subjectMap.put("美术"     , "paintingScore");
		subjectMap.put("艺术"     , "artScore");
		subjectMap.put("音乐"     , "musicScore");
		subjectMap.put("体育"     , "sportsScore");
	}

    private static Map<String,Object> subjectDbMap = new HashMap<String, Object>();
	static {
		subjectDbMap.put("总分"     , "total_score");
		subjectDbMap.put("语文"     , "chinese_score");
		subjectDbMap.put("数学"     , "math_score");
		subjectDbMap.put("英语"     , "english_score");
		subjectDbMap.put("物理"     , "physical_score");
		subjectDbMap.put("化学"     , "chemical_score");
		subjectDbMap.put("生物"     , "biology_score");
		subjectDbMap.put("地理"     , "geography_score");
		subjectDbMap.put("科学"     , "science_score");
		subjectDbMap.put("政治"     , "politics_score");
		subjectDbMap.put("历史"     , "history_score");
		subjectDbMap.put("思想品德"  , "moral_score");
		subjectDbMap.put("历史与社会", "history_society_score");
		subjectDbMap.put("品德与社会", "quality_society_score");
		subjectDbMap.put("品德与生活", "quality_life_score");
		subjectDbMap.put("美术"     , "painting_score");
		subjectDbMap.put("艺术"     , "art_score");
		subjectDbMap.put("音乐"     , "music_score");
		subjectDbMap.put("体育"     , "sports_score");
	}

	private static Map<String,Object> subjectAvgDbMap = new HashMap<String, Object>();
	static {
		subjectAvgDbMap.put("总分"     , "avg_total_score");
		subjectAvgDbMap.put("语文"     , "avg_chinese_score");
		subjectAvgDbMap.put("数学"     , "avg_math_score");
		subjectAvgDbMap.put("英语"     , "avg_english_score");
		subjectAvgDbMap.put("物理"     , "avg_physical_score");
		subjectAvgDbMap.put("化学"     , "avg_chemical_score");
		subjectAvgDbMap.put("生物"     , "avg_biology_score");
		subjectAvgDbMap.put("地理"     , "avg_geography_score");
		subjectAvgDbMap.put("科学"     , "avg_science_score");
		subjectAvgDbMap.put("政治"     , "avg_politics_score");
		subjectAvgDbMap.put("历史"     , "avg_history_score");
		subjectAvgDbMap.put("思想品德"  , "avg_moral_score");
		subjectAvgDbMap.put("历史与社会", "avg_history_society_score");
		subjectAvgDbMap.put("品德与社会", "avg_quality_society_score");
		subjectAvgDbMap.put("品德与生活", "avg_quality_life_score");
		subjectAvgDbMap.put("美术"     , "avg_painting_score");
		subjectAvgDbMap.put("艺术"     , "avg_art_score");
		subjectAvgDbMap.put("音乐"     , "avg_music_score");
		subjectAvgDbMap.put("体育"     , "avg_sports_score");
	}
	
	//TODO 以优化完成
	/**
	 * 获取学生
	 *
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getStudentOrList", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getStudentOrList(String examId) throws Exception{
		LOGGER.info("获取Score Start");

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}

		if (StringUtils.isBlank(examId)){
			return AsyncResponseData.getSuccess().asParamError("考试ID不能为空");
		}
		
		Exam exam = examService.getExamById(examId);

		if (exam == null){
			return AsyncResponseData.getSuccess().asParamError("考试ID传参错误");
		}


		Map<String, Object> resultMap = new HashMap<String, Object>();

		String studentId = "";
		Map<String,Object> param = new LinkedHashMap<>();
		//登录用户角色为学生
		if (RoleConstant.STUDENT_ROLE.equals(user.getRoleName())){
			studentId = user.getSourceId();
			param.put("studentId", studentId);
			param.put("examId", examId);
			Score score = scoreService.getScoreByExamIdAndStuIdNew(param);
			if (score == null){
				return AsyncResponseData.getSuccess("没有您的考试数据");
			}
			
		}else { //登录用户角色不为学生
			Map<String, Object> filter = new HashMap<String, Object>();
			filter.put("examId",examId);
			filter.put("orgId",user.getOrgId());
			filter.put("sort","total_score");
			filter.put("sortType","desc");
			filter.put("studentName1", "studentName1");
			filter.put("studentId1", "studentId1");
			List<Score> schoolScores = scoreService.queryScoresFilterNewOne(filter);

			if (schoolScores.isEmpty() || schoolScores == null){
				return AsyncResponseData.getSuccess("尚无该考试相关数据");
			}
			
			resultMap.put("schoolScores",schoolScores);
			//返回该组织机构这场考试第一名的学生成绩报告单
			studentId = schoolScores.get(0).getStudentId();
		}

		resultMap.put("studentId",studentId);
		LOGGER.info("获取Score End");

		return AsyncResponseData.getSuccess(resultMap);
	}

	//TODO ：优化完成
	/**
	 * 获取成长趋势
	 *
	 * @param score
	 * @param range : 本班、本校、本区县
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/getGrowthTrends", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getGrowthTrends(Score score, String subject, String range) throws Exception{
		LOGGER.info("获取 学生报告-总分/科目 成长趋势 Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		
		if (!ScoreConstant.CLASS_LEVEL.equals(range) && !ScoreConstant.SCHOOL_LEVEL.equals(range) && !ScoreConstant.REGION_LEVEL.equals(range)){
			return AsyncResponseData.getSuccess().asParamError("范围参数错误");
		}
		
		if (StringUtils.isBlank(score.getExamId()) || StringUtils.isBlank(subject)){
			return AsyncResponseData.getSuccess().asParamError("参数不能为空");
		}

		Exam exam = examService.getExamById(score.getExamId());
		if (exam == null){
			return AsyncResponseData.getSuccess().asParamError("考试ID传参错误");
		}

		if (StringUtils.isBlank(exam.getSubject()) || StringUtils.isBlank(exam.getClassroom())){
			return AsyncResponseData.getSystemError("数据错误，考试学科或班级不能为空");
		}

	    //TODO1 优化
		String studentId = "";
		//登录用户角色为学生
		if (RoleConstant.STUDENT_ROLE.equals(user.getRoleName())){
			studentId = user.getSourceId();
		}else { //登录用户角色不为学生，返回该组织机构这场考试指定学生的成绩报告单
			if (StringUtils.isBlank(score.getStudentId())){
				return AsyncResponseData.getSuccess().asParamError("学生ID传参不能为空");
			}
			studentId = score.getStudentId();
		}
		Student student = studentService.getStudentByIdNew(studentId);
		if (student == null){
			return AsyncResponseData.getSystemError("数据错误");
		}
		
		//TODO1 优化
		Classroom classroom = classroomService.getClassroomByIdNew(student.getClassroomId());
		if (!ScoreConstant.TOTAL_SCORE.equals(subject)){
			filter.put("subject",subject);
		}
        filter.put("examStartDate1","examStartDate");
		filter.put("examStatus",2);
		filter.put("orgId", user.getOrgId());
		filter.put("studentId",studentId);
		filter.put("examEndDate",exam.getExamEndDate());
		filter.put("status",1);
		filter.put("sort","exam_end_date");
		filter.put("sortType","desc");
		filter.put("type", 4);
		//该学生参加的考试列表
		//TODO1 优化
		List<Exam> examList = examService.queryStuExamsFilter(filter);
		if (examList == null || examList.isEmpty()){
			return AsyncResponseData.getSuccess("数据错误，查询不到考试列表");
		}

		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("list",examList);
		dataMap.put(subjectMap.get(subject)+"1",subject);
		List<Score> orgScores = scoreService.getSubjScoresByExamIds(dataMap);

		//班级单科成绩列表

		List<BigDecimal> standardScores = new ArrayList<>();
		List<Date> examDates = new ArrayList<>();

		Collections.reverse(examList);
		for (Exam eachExam : examList){
			List<BigDecimal> classSubjectScoreList = new ArrayList<>();
			List<BigDecimal> schoolSubjectScoreList = new ArrayList<>();
			BigDecimal stuSubjectScore = BigDecimal.ZERO;

			for (Score orgScore : orgScores){
				BigDecimal subjectScore = getSubjectScore(orgScore,subject);
				if (orgScore.getExamId().equals(eachExam.getId())){
					schoolSubjectScoreList.add(subjectScore);
					if (orgScore.getClassroomId().equals(student.getClassroomId())){
						classSubjectScoreList.add(subjectScore);
					}
					if (orgScore.getStudentId().equals(student.getId())){
						stuSubjectScore = subjectScore;
					}
				}
			}

			if (stuSubjectScore == null){
				standardScores.add(BigDecimal.ZERO);
			}else {
				if (ScoreConstant.CLASS_LEVEL.equals(range)){
					standardScores.add(EduEvaluatingUtils.getStandardT(classSubjectScoreList,stuSubjectScore));
				}
				if (ScoreConstant.SCHOOL_LEVEL.equals(range)){
					standardScores.add(EduEvaluatingUtils.getStandardT(schoolSubjectScoreList,stuSubjectScore));
				}
				if (ScoreConstant.REGION_LEVEL.equals(range)){
					standardScores.add(EduEvaluatingUtils.getStandardT(schoolSubjectScoreList,stuSubjectScore));
				}
			}

			if (eachExam.getExamStartDate()==null){
				return AsyncResponseData.getSystemError("数据错误");
			}
			examDates.add(eachExam.getExamStartDate());
		}

		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("standardScores",standardScores);
		resultMap.put("examDates",examDates);

		LOGGER.info("获取 获取 学生报告-总分/科目 成长趋势 End");

		return AsyncResponseData.getSuccess(resultMap);
	}
	
	//TODO 已经优化
	/**
	 * 班级报告／教研报告 考试对比
	 * @param score
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/compareExamScores", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData compareExamScores(Score score, String subject, String tab) throws Exception{
		//TODO1 全部修改
		LOGGER.info("获取Score Start");

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		
		if (StringUtils.isBlank(score.getExamId())){
			return AsyncResponseData.getSuccess().asParamError("考试ID不能为空");
		}
		
		//TODO 优化 等你
		Exam exam = examService.getExamById(score.getExamId());
		if (exam == null){
			return AsyncResponseData.getSuccess().asParamError("考试ID传参错误");
		}
		//TODO1 传班级中文名称 所以简略
		Map<String, Object> filter = new HashMap<String, Object>();
		
		if (ScoreConstant.CLASS_REPORT.equals(tab)){
			if (StringUtils.isBlank(score.getClassroomName())){
				return AsyncResponseData.getSuccess().asParamError("班级名称不能为空");
			}

			filter.put("classroom",score.getClassroomName());
		}else if (ScoreConstant.TEACHING_REPORT.equals(tab)){
			if (StringUtils.isBlank(subject)){
				return AsyncResponseData.getSuccess().asParamError("学科不能为空");
			}
			
			filter.put("subject",subject);
		}else {
			return AsyncResponseData.getSuccess().asParamError("成绩报告类型传参错误");
		}
		
		filter.put("examStatus",2);
		filter.put("orgId", user.getOrgId());
		filter.put("examEndDate",exam.getExamEndDate());
		filter.put("sort","exam_end_date");
		filter.put("sortType","desc");
		filter.put("type",5);
		//该学校包含该班级的考试列表
		List<Exam> examList = examService.queryExamsFilterNew(filter);
		
		if (examList==null || examList.isEmpty()){
			return AsyncResponseData.getSystemError("数据错误");
		}

		List<BigDecimal> scoreAvgList = new ArrayList<>();
		List<String> examNameList = new ArrayList<>();

		filter.clear();
		filter.put("orgId",user.getOrgId());
		for (Exam eachExam : examList){
			examNameList.add(eachExam.getExamName());
			filter.put("examId",eachExam.getId());

			//TODO1 
			List<Score> classScoreList = scoreService.queryScoresFilterNew(filter);
			if (classScoreList!=null && !classScoreList.isEmpty()){
				List<BigDecimal> subjectScoreList = getSubjectScoreList(classScoreList,subject);
				scoreAvgList.add(EduEvaluatingUtils.getAverage(subjectScoreList));
			}else{
				scoreAvgList.add(BigDecimal.ZERO);
			}
		}
		
		Collections.reverse(examNameList);
		Collections.reverse(scoreAvgList);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("examNameList",examNameList);
		resultMap.put("scoreAvgList",scoreAvgList);

		LOGGER.info("获取Score End");
		return AsyncResponseData.getSuccess(resultMap);
	}
	
	//TODO 优化完成
	/**
	 *	学生成绩单
	 * @param examId 考试id
	 * @param subject 
	 * @return
	 * @throws Exception
	 * Create on 2018年1月15日 上午11:40:52
	 */
	@RequestMapping(value = "/getSingleStuReport", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getSingleStuReport(String examId,String subject) throws Exception{
		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}

		String studentId = user.getSourceId();
		
		Student student = studentService.getStudentByIdNew(studentId);
		
		if (student == null){
			return AsyncResponseData.getSystemError("数据错误");
		}

		Map<String,Object> filter = new HashMap<>();
		filter.put("examId",examId);
		filter.put("orgId",user.getOrgId());
		filter.put("sort",subjectDbMap.get(subject));
		filter.put("sortType","desc");
		filter.put("classroomId1", "classroomId1");
		filter.put("studentId1", "studentId1");
		filter.put(subjectMap.get(subject)+"1", "subject");
		//TODO1 优化
		List<Score> schoolScores = scoreService.queryScoresFilterNewOne(filter);
		
		List<StudentAppVO> studentAppVOList = new ArrayList<>();
		//学生单科成绩
		BigDecimal stuSubjScore = BigDecimal.ZERO;
		
		if (schoolScores == null || schoolScores.isEmpty()) {
			return AsyncResponseData.getSuccess("尚无成绩");
		}
		
		List<BigDecimal> schoolSubjScores = new ArrayList<>();
		List<BigDecimal> classSubjScores = new ArrayList<>();
		Boolean flag = false;
		for (Score schoolScore : schoolScores){
			BigDecimal subjectScore = getSubjectScore(schoolScore,subject);
			schoolSubjScores.add(subjectScore);
			if (student.getClassroomId().equals(schoolScore.getClassroomId())){
				classSubjScores.add(subjectScore);
				if (studentId.equals(schoolScore.getStudentId())){
					flag = true;
					stuSubjScore = subjectScore;
				}
			}
		}
		
		if (classSubjScores.size()==0 || !flag){
			return AsyncResponseData.getSystemError("系统错误");
		}
		StudentAppVO studentAppVO = new StudentAppVO();
		//平均分
		studentAppVO.setLabel("平均分");
		studentAppVO.setClassScore(EduEvaluatingUtils.getAverage(classSubjScores));
		studentAppVO.setSchoolScore(EduEvaluatingUtils.getAverage(schoolSubjScores));
		studentAppVO.setRegionScore(EduEvaluatingUtils.getAverage(schoolSubjScores));
		studentAppVOList.add(studentAppVO);
		//最高分
		studentAppVO = new StudentAppVO();
		studentAppVO.setLabel("最高分");
		studentAppVO.setClassScore(classSubjScores.get(0));
		studentAppVO.setSchoolScore(schoolSubjScores.get(0));
		studentAppVO.setRegionScore(schoolSubjScores.get(0));
		studentAppVOList.add(studentAppVO);
		//超越率
		studentAppVO = new StudentAppVO();
		studentAppVO.setLabel("超越率");
		studentAppVO.setClassScore(EduEvaluatingUtils.getPercentagerank(classSubjScores,stuSubjScore));
		studentAppVO.setSchoolScore(EduEvaluatingUtils.getPercentagerank(schoolSubjScores,stuSubjScore));
		studentAppVO.setRegionScore(EduEvaluatingUtils.getPercentagerank(schoolSubjScores,stuSubjScore));
		studentAppVOList.add(studentAppVO);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("studentAppVOList",studentAppVOList);
		resultMap.put("stuSubjScore",stuSubjScore);
		
		return AsyncResponseData.getSuccess(resultMap);
	}
	
	//TODO 完成优化
	/**
	 * 超越率
	 *
	 * @param score
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPercentageRank", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getPercentageRank(Score score,String subject) throws Exception{
		LOGGER.info("获取超越率 Start");

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		
		if (StringUtils.isBlank(subject) || StringUtils.isBlank(score.getExamId())){
			return AsyncResponseData.getSuccess().asParamError("参数不能为空");
		}
		
		Exam exam = examService.getExamById(score.getExamId());
		if (exam == null){
			return AsyncResponseData.getSuccess().asParamError("考试ID参数错误");
		}

		if (StringUtils.isBlank(exam.getSubject()) || StringUtils.isBlank(exam.getClassroom())){
			return AsyncResponseData.getSystemError("数据错误");
		}
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("examId",exam.getId());
		filter.put("orgId",user.getOrgId());
		filter.put("sort","total_score");
		filter.put("sortType","desc");
		filter.put("classroomId1", "classroomId1");
		filter.put("studentId1", "studentId1");
		filter.put(subjectMap.get(subject)+"1", "subject");
		List<Score> schoolScores = scoreService.queryScoresFilterNewOne(filter);

		String studentId = "";
		//登录用户角色为学生
		if (RoleConstant.STUDENT_ROLE.equals(user.getRoleName())){
			studentId = user.getSourceId();
		}else { //登录用户角色不为学生，返回该组织机构这场考试指定学生的成绩报告单
			if (StringUtils.isBlank(score.getStudentId())){
				return AsyncResponseData.getSuccess().asParamError("学生ID传参不能为空");
			}
			studentId = score.getStudentId();
		}
		Student student = studentService.getStudentByIdNew(studentId);
		if (student == null){
			return AsyncResponseData.getSystemError("数据错误");
		}
		
		//根据examId,studentId获取学生的成绩报告单
		String classroomId = student.getClassroomId();
		//班级总分成绩列表
		List<BigDecimal> classSubjectScoreList = new ArrayList<>();
		//学校总分成绩列表
		List<BigDecimal> schoolSubjectScoreList = new ArrayList<>();
		BigDecimal stuSubjectScore = BigDecimal.ZERO;
		
		
		for (Score schoolScore : schoolScores){
			BigDecimal subjectScore = getSubjectScore(schoolScore,subject);
			if (classroomId.equals(schoolScore.getClassroomId())){
				classSubjectScoreList.add(subjectScore);
				if (studentId.equals(schoolScore.getStudentId())){
					stuSubjectScore = subjectScore;
				}
			}
			schoolSubjectScoreList.add(subjectScore);
		}
		
		BigDecimal schoolPercentageRank = EduEvaluatingUtils.getPercentagerank(schoolSubjectScoreList,stuSubjectScore);
		BigDecimal classPercentageRank = EduEvaluatingUtils.getPercentagerank(classSubjectScoreList,stuSubjectScore);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("schoolPercentageRank",schoolPercentageRank);
		resultMap.put("classPercentageRank",classPercentageRank);

		LOGGER.info("获取超越率 End");
		return AsyncResponseData.getSuccess(resultMap);
	}


	//TODO 学生角色优化完成  老师部分没有数据待优化
	/**
	 * 得到首页图表
	 */
	@RequestMapping(value = "/getHomePageChart", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getHomePageChart() throws Exception{
		
		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		
		Map<String,Object> resultMap = new HashMap<>();
		Map<String,Object> filter = new HashMap<>();

		if (RoleConstant.STUDENT_ROLE.equals(user.getRoleName())){//登陆用户角色为学生，取最近一场考试的校级高分率、优秀率......
			Student student = studentService.getStudentByIdNew(user.getSourceId());
			if (student==null){
				return AsyncResponseData.getSystemError("数据错误");
			}
			resultMap.put("name",student.getStudentName());
			resultMap.put("label",student.getStudentNo());
			
			//获取该学生最近一场考试成绩
			filter.put("studentId",student.getId());
			filter.put("sort","s.create_date");
			filter.put("sortType","desc");
			filter.put("type", 1);
			filter.put("examId1", "examId1");
			filter.put("totalScore1", "totalScore1");
			//TODO1 优化 只查一条数据 不是列表
			List<Score> scores = scoreService.queryScoresFilterNewOne(filter);
			if (scores==null || scores.size() < 1){
				resultMap.put("msg","没有该学生最近一场考试数据");
				return AsyncResponseData.getSuccess(resultMap);
			}
			
			Score score = scores.get(0);
			filter.clear();
			filter.put("orgId",user.getOrgId());
			filter.put("examId",score.getExamId());
			filter.put("columnName",subjectDbMap.get(ScoreConstant.TOTAL_SCORE));
			//班级总分列表
			//TODO 数据量大时需要优化
			List<BigDecimal> schoolTotalScores = scoreService.getExactScore(filter);
			
			int totalScore = getExamScoreVO(score.getExamId()).get(0).getFullMarks();
			Map<String,BigDecimal> rateMap = EduEvaluatingUtils.getRatio(schoolTotalScores,BigDecimal.valueOf(totalScore));
			String studentLevel = EduEvaluatingUtils.getPersonalRatio(BigDecimal.valueOf(totalScore),score.getTotalScore());
			resultMap.put("rateMap",rateMap);
			resultMap.put("studentLevel",studentLevel);
			return AsyncResponseData.getSuccess(resultMap);
		}else {
			
			Teacher teacher = teacherService.getTeacherById(user.getSourceId());
			if (teacher==null){
				return AsyncResponseData.getSystemError("数据错误");
			}
			
			resultMap.put("name",teacher.getTeacherName());
			resultMap.put("label",teacher.getTeacherJobTitle());

			//判断该用户是否为班主任
			filter.clear();
			filter.put("classTeacherId",teacher.getId());
			filter.put("recordNum",1);
			//TODO1 优化  只需要一条数据  不需要列表
			List<Classroom> classrooms = classroomService.queryClassroomsFilterNew(filter);
			Classroom classroom;
			String subject = ScoreConstant.TOTAL_SCORE;
			if (classrooms == null || classrooms.isEmpty()){//用户角色为任课老师，取他所教班级所交科目的最近一场考试总分的校级高分率、优秀率......

				subject = teacher.getSubject().split(",")[0];
				classroom = classroomService.getClassroomByName(teacher.getClassroom().split(",")[0],user.getOrgId());
				if (classroom == null){
					return AsyncResponseData.getSystemError("数据错误");
				}
				filter.clear();
				filter.put("subject",subject);
				filter.put("classroom",teacher.getClassroom().split(",")[0]);
				filter.put("examStatus",2);
				filter.put("sort","exam_start_date");
				filter.put("sortType","desc");
				filter.put("type", 1);

			}else {//用户的角色为班主任，取该班级最近一场考试总分的校级高分率、优秀率......

				classroom = classrooms.get(0);

				//获取该班级最近一场考试ID
				filter.clear();
				filter.put("classroom",classroom.getClassroomName());
				filter.put("examStatus",2);
				filter.put("sort","exam_start_date");
				filter.put("sortType","desc");
				filter.put("type", 1);

			}

			//TODO1 优化 只要一个  不要列表
			List<Exam> exams = examService.queryExamsFilterNew(filter);
			if (exams == null || exams.isEmpty()){
				resultMap.put("msg","没有该教师所在班级的最近一场考试数据");
				return AsyncResponseData.getSuccess(resultMap);
			}
			
			String examId = exams.get(0).getId();

			filter.clear();
			filter.put("examId",examId);
			filter.put("classroomId",classroom.getId());
			filter.put("columnName",subjectDbMap.get(subject));
			//TODO1 优化  只查总分
			List<BigDecimal> classTotalScores = scoreService.getExactScore(filter);
			if (classTotalScores == null || classTotalScores.isEmpty()){
				return AsyncResponseData.getSuccess().asParamError("尚无最近一场考试的成绩数据");
			}
			//TODO1 这个就不需要了
				/*for (Score eachScore : classScoreList){
					classTotalScores.add(eachScore.getTotalScore());
				}*/

			//TODO1 优化总分流程
			List<ScoreVO> scoreVOList = getExamScoreVO(examId);

			int totalScore = 0;
			for (ScoreVO svo : scoreVOList){
				if (subject.equals(svo.getSubject())){
					totalScore = svo.getFullMarks();
					break;
				}
			}

			Map<String,BigDecimal> rateMap = EduEvaluatingUtils.getRatio(classTotalScores,BigDecimal.valueOf(totalScore));
			resultMap.put("rateMap",rateMap);

			return AsyncResponseData.getSuccess(resultMap);
		}
	}
	
	
	//TODO 无需优化 科目数据少以后优化
	/**
	 * 班级总成绩
	 */
	@RequestMapping(value = "/getClassScoreReport", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClassScoreReport(Score score, String subject) throws Exception{

		Map<String, Object> filter = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		
		if (StringUtils.isBlank(score.getExamId()) || StringUtils.isBlank(subject) || StringUtils.isBlank(score.getClassroomId())){
			return AsyncResponseData.getSuccess().asParamError("参数不能为空");
		}

		Exam exam = examService.getExamById(score.getExamId());
		if (exam == null){
			return AsyncResponseData.getSuccess().asParamError("考试ID传参错误");
		}

		if (StringUtils.isBlank(exam.getSubject()) || StringUtils.isBlank(exam.getClassroom())){
			return AsyncResponseData.getSystemError("数据错误");
		}

		//TODO1 优化 公用
		//List<ScoreVO> scoreVO = getExamScoreVO(exam.getId());

		filter.clear();
		filter.put("examId",exam.getId());
		filter.put("columnName",subjectDbMap.get(subject));
		filter.put("sort",subjectDbMap.get(subject));
		filter.put("sortType","desc");
		List<BigDecimal> schoolSubjectScores = scoreService.getExactScore(filter);
		//TODO1 优化

		if (schoolSubjectScores!=null && !schoolSubjectScores.isEmpty()){

			filter.put("classroomId",score.getClassroomId());
			List<BigDecimal> classSubjectScores = scoreService.getExactScore(filter);

			if (classSubjectScores!=null && !classSubjectScores.isEmpty()){

				List<ClassReport> classReportForSM = new ArrayList<>();
				List<ClassReport> classReports = new ArrayList<>();
				ClassReport classReport = new ClassReport();

				classReport.setName("平均分");
				classReport.setRegionScore(EduEvaluatingUtils.getAverage(schoolSubjectScores).toString());
				classReport.setSchoolScore(EduEvaluatingUtils.getAverage(schoolSubjectScores).toString());
				classReport.setClassScore(EduEvaluatingUtils.getAverage(classSubjectScores).toString());
				classReports.add(classReport);
				classReportForSM.add(classReport);

				classReport = new ClassReport();
				classReport.setName("最高分");
				classReport.setRegionScore(schoolSubjectScores.get(0).toString());
				classReport.setSchoolScore(schoolSubjectScores.get(0).toString());
				classReport.setClassScore(classSubjectScores.get(0).toString());
				classReports.add(classReport);

				classReport = new ClassReport();
				classReport.setName("最低分");
				classReport.setRegionScore(schoolSubjectScores.get(schoolSubjectScores.size()-1).toString());
				classReport.setSchoolScore(schoolSubjectScores.get(schoolSubjectScores.size()-1).toString());
				classReport.setClassScore(classSubjectScores.get(classSubjectScores.size()-1).toString());
				classReports.add(classReport);

				classReport = new ClassReport();
				classReport.setName("分化程度");
				classReport.setRegionScore(EduEvaluatingUtils.getDifferenceCoefficient(schoolSubjectScores));
				classReport.setSchoolScore(EduEvaluatingUtils.getDifferenceCoefficient(schoolSubjectScores));
				classReport.setClassScore(EduEvaluatingUtils.getDifferenceCoefficient(classSubjectScores));
				classReports.add(classReport);
				classReportForSM.add(classReport);

				SummaryVO summaryVO = getClassTSSummary(classReportForSM,subject);

				resultMap.put("classReports",classReports);
				resultMap.put("summaryVO",summaryVO);
				return AsyncResponseData.getSuccess(resultMap);
			}else {
				return AsyncResponseData.getSuccess("没有班级相关数据");
			}
		}else {
			return AsyncResponseData.getSuccess("没有学校相关数据");
		}

	}
	
	
	/**
	 * 获取班级总成绩总结
	 * @return summaryVO
	 */
	public SummaryVO getClassTSSummary(List<ClassReport> classReports, String subject) {
		SummaryVO summaryVO = new SummaryVO();
		String summary = ScoreConstant.SUMMARY + "本班级" + subject;

		if (2 != classReports.size()){
			summaryVO.setClassTotalScore(" ");
			return summaryVO;
		}
		if (!ScoreConstant.AVG_SCORE.equals(classReports.get(0).getName()) || !ScoreConstant.DIFF_COEFFICIENT.equals(classReports.get(1).getName())){
			summaryVO.setClassTotalScore(" ");
			return summaryVO;
		}
		for (ClassReport classReport : classReports){
			BigDecimal classScore = new BigDecimal(classReport.getClassScore().replaceAll("%",""));
			BigDecimal schoolScore = new BigDecimal(classReport.getSchoolScore().replaceAll("%",""));
			BigDecimal regionScore = new BigDecimal(classReport.getRegionScore().replaceAll("%",""));
			if (classScore.compareTo(schoolScore) > 0){
				if (ScoreConstant.AVG_SCORE.equals(classReport.getName())){
					summary += ScoreConstant.CLASS_TOTAL_AVG_SCORE.replaceAll("666","高于");
				}else {
					summary += ScoreConstant.CLASS_TOTAL_DIFF.replaceAll("666","高于");
				}
			}else if (classScore.compareTo(schoolScore) < 0){
				if (ScoreConstant.AVG_SCORE.equals(classReport.getName())){
					summary += ScoreConstant.CLASS_TOTAL_AVG_SCORE.replaceAll("666","低于");
				}else {
					summary += ScoreConstant.CLASS_TOTAL_DIFF.replaceAll("666","低于");
				}
			}else {
				if (ScoreConstant.AVG_SCORE.equals(classReport.getName())){
					summary += ScoreConstant.CLASS_TOTAL_AVG_SCORE.replaceAll("666","等于");
				}else {
					summary += ScoreConstant.CLASS_TOTAL_DIFF.replaceAll("666","等于");
				}
			}
			if (classScore.compareTo(regionScore) > 0){
				summary = summary.replaceAll("888","高于");
			}else if (classScore.compareTo(regionScore) < 0){
				summary = summary.replaceAll("888","低于");
			}else {
				summary = summary.replaceAll("888","等于");
			}
			if (classScore.compareTo(schoolScore) > 0 || classScore.compareTo(regionScore) > 0){
				if (ScoreConstant.AVG_SCORE.equals(classReport.getName())){
					summary += ScoreConstant.KEEP_ON + "。";
				}else {
					summary += ScoreConstant.PAY_ATTENTION + "。";
				}
			}else {
				if (ScoreConstant.AVG_SCORE.equals(classReport.getName())){
					summary += ScoreConstant.PAY_ATTENTION + "。";
				}else {
					summary += ScoreConstant.KEEP_ON + "。";

				}
			}

		}

		summaryVO.setClassTotalScore(summary);
		return summaryVO;
	}

	/**
	 * 班级学科均衡水平
	 * 学生科目诊断
	 * 平均分：总分平均分及各个学科的平均分
	 * 对于班级：班级总分标准分及班级各个学科的标准分 && 校级总分平均分及校级各个学科的平均分
	 * @param tab
	 * @param score examId classroomId
	 * 对于学生个人：个人总分标准分及个人各个学科的标准分 && 班级总分平均分及班级各个学科的平均分
	 * @param tab
	 * @param score examId studentId
	 */
	
	//TODO 小玉优化
	/**
	 * 成绩报告单
	 *
	 * @param tab: 报告单类型
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/geReportCards", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData geReportCards(String tab, Score score, String subject, String range) throws Exception{
		LOGGER.info("获取Score Start");

		Map<String, Object> filter = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		
		if (user.getOrgId() == null || user.getRoleId() == null || roleService.getRoleById(user.getRoleId())==null){
			return AsyncResponseData.getSystemError("数据错误");
		}
		if (StringUtils.isBlank(range)){
			range = ScoreConstant.CLASS_LEVEL;
		}
		if (StringUtils.isBlank(subject)){
			if (ScoreConstant.SCHOOL_REPORT.equals(tab)){
				subject = ScoreConstant.TOTAL_SCORE;
			}else {
				return AsyncResponseData.getSuccess().asParamError("参数subject不能为空");
			}
		}
		if (!ScoreConstant.CLASS_LEVEL.equals(range) && !ScoreConstant.SCHOOL_LEVEL.equals(range) && !ScoreConstant.REGION_LEVEL.equals(range) && StringUtils.isNotBlank(range)){
			return AsyncResponseData.getSuccess().asParamError("参数tab传参错误");
		}
		if (StringUtils.isBlank(score.getExamId())){
			return AsyncResponseData.getSuccess().asParamError("参数examId不能为空");
		}
		if (StringUtils.isNotBlank(tab) && (ScoreConstant.STUDENT_REPORT.equals(tab) || ScoreConstant.CLASS_REPORT.equals(tab)
				|| ScoreConstant.TEACHING_REPORT.equals(tab) || ScoreConstant.SCHOOL_REPORT.equals(tab))){

			String orgId = user.getOrgId();
			String regionCode = organizationService.getOrganizationById(user.getOrgId()).getRegionCode();

			Exam exam = examService.getExamById(score.getExamId());
			if (exam == null){
				return AsyncResponseData.getSuccess().asParamError("不存在这场考试的数据");
			}
			filter.put("examId",score.getExamId());
			filter.put("orgId",orgId);
			filter.put("type",1);
			//TODO1 优化 有歧义待定
			List<Score> orgScoreList = scoreService.queryScoresFilterNewOne(filter);

			//根据双向细目表得到每科对应的满分分数
			List<ScoreVO> scoreVOList = getExamScoreVO(exam.getId());

			if (orgScoreList == null || orgScoreList.isEmpty() || scoreVOList.size()<=1){
				//数据库中取不到该场考试的科目，以下处理 方便前端 暂无数据
				scoreVOList = new ArrayList<>();
				resultMap.put("scoreVOList",scoreVOList);
				if (ScoreConstant.CLASS_REPORT.equals(tab)){
					List<Score> classScores = new ArrayList<>();
					resultMap.put("classScores",classScores);
				}
				
				if (ScoreConstant.TEACHING_REPORT.equals(tab) || ScoreConstant.SCHOOL_REPORT.equals(tab)){
					List<ProcessedScores> classProcessedScore = new ArrayList<>();
					resultMap.put("classProcessedScore",classProcessedScore);
				}

				if (scoreVOList.size()<=1){
					return AsyncResponseData.getSuccess("该考试尚未制定双向细目表");
				} else {
					return AsyncResponseData.getSuccess("尚无该考试相关数据");
				}

			}

			if (StringUtils.isBlank(exam.getSubject()) || StringUtils.isBlank(exam.getClassroom())){
				return AsyncResponseData.getSystemError("不存在该场考试的学科或班级数据");
			}
			
			if (exam.getSubject().split(",").length != scoreVOList.size()-1){
				return AsyncResponseData.getSuccess("该考试科目数与双向细目表数不一致");
			}

			if (ScoreConstant.STUDENT_REPORT.equals(tab)){ //学生报告里的成绩单
				String studentId = "";
				//登录用户角色为学生
				if (RoleConstant.STUDENT_ROLE.equals(user.getRoleName())){
					studentId = user.getSourceId();
				}else { //登录用户角色不为学生
					if (StringUtils.isBlank(score.getStudentId())){
						return AsyncResponseData.getSuccess().asParamError("参数studentId不能为空");
					}
					//sourceId不为空，返回该组织机构这场考试指定学生的成绩报告单
					studentId = score.getStudentId();
				}
				
				Student student = studentService.getStudentByIdNew(studentId);
				if (student == null){
					return AsyncResponseData.getSystemError("studentId传参错误");
				}
				
				resultMap.put("studentId",studentId);
				//根据examId,studentId获取学生的成绩报告单

				List<Score> schoolScoreList;
				List<Score> classScoreList;
				List<BigDecimal> avgScoreList = new ArrayList<>();
				List<BigDecimal> stuScoreList = new ArrayList<>();
				List<String> subjectList = new ArrayList<>();
				List<BigDecimal> standardScoreList = new ArrayList<>();
				ScoreVO stuScoreVO = new ScoreVO();

				if (ScoreConstant.TOTAL_SCORE.equals(subject)){

					filter.clear();
					filter.put("examId",score.getExamId());
					filter.put("orgId",orgId);
					filter.put("orgId1","orgId");
					filter.put("classroomId1","classroomId");
					filter.put("studentId1","studentId");
					for (ScoreVO eachsvo : scoreVOList){
						filter.put(subjectMap.get(eachsvo.getSubject())+"1","subject");
					}
					schoolScoreList = scoreService.queryScoresFilterNewOne(filter);

					classScoreList = new ArrayList<>();
					Score stuScore = new Score();
					for (Score schoolScore : schoolScoreList){
						if (student.getClassroomId().equals(schoolScore.getClassroomId())){
							classScoreList.add(schoolScore);
							if (student.getId().equals(schoolScore.getStudentId())){
								stuScore = schoolScore;
							}
						}
					}

					if (stuScore == null){
						return AsyncResponseData.getSuccess().asParamError("参数examId传参错误，该学生没有参加这场考试");
					}
					
					//校级最高分、平均分
					ProcessedScores orgProcessedScore = scoreService.getProcessedScores(filter);
					//班级最高分、平均分
					filter.put("classroomId",student.getClassroomId());
					ProcessedScores classProcessedScore = scoreService.getProcessedScores(filter);

					for (ScoreVO eachScoreVO : scoreVOList){
						getSubjectScore(eachScoreVO,stuScore,null);
						//分数等级
						eachScoreVO.setScoreLevel(EduEvaluatingUtils.getPersonalRatio(BigDecimal.valueOf(eachScoreVO.getFullMarks()),eachScoreVO.getScore()));
						getSubjectScore(eachScoreVO,orgProcessedScore,ScoreConstant.REGION_LEVEL);
						getSubjectScore(eachScoreVO,orgProcessedScore,ScoreConstant.SCHOOL_LEVEL);
						getSubjectScore(eachScoreVO,classProcessedScore,ScoreConstant.CLASS_LEVEL);
						if (0==eachScoreVO.getFullMarks()){
							return AsyncResponseData.getSystemError("数据错误，科目的总分不应为0分");
						}
						if (ScoreConstant.REGION_LEVEL.equals(range)){
							avgScoreList.add(eachScoreVO.getRegionAvgScore().divide(BigDecimal.valueOf(eachScoreVO.getFullMarks()),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")));
						}
						if (ScoreConstant.SCHOOL_LEVEL.equals(range)){
							avgScoreList.add(eachScoreVO.getSchoolAvgScore().divide(BigDecimal.valueOf(eachScoreVO.getFullMarks()),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")));
						}
						if (ScoreConstant.CLASS_LEVEL.equals(range)){
							avgScoreList.add(eachScoreVO.getClassAvgScore().divide(BigDecimal.valueOf(eachScoreVO.getFullMarks()),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")));
						}
						subjectList.add(eachScoreVO.getSubject());
						stuScoreList.add(eachScoreVO.getScore().divide(BigDecimal.valueOf(eachScoreVO.getFullMarks()),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")));

					}
					//获取学生个人各科目标准分
					if (ScoreConstant.REGION_LEVEL.equals(range)){
						getStandardTScore(scoreVOList,schoolScoreList,ScoreConstant.SINGLE_STUDENT);
					}
					if (ScoreConstant.SCHOOL_LEVEL.equals(range)){
						getStandardTScore(scoreVOList,schoolScoreList,ScoreConstant.SINGLE_STUDENT);
					}
					if (ScoreConstant.CLASS_LEVEL.equals(range)){
						getStandardTScore(scoreVOList,classScoreList,ScoreConstant.SINGLE_STUDENT);
					}
					for (ScoreVO eachScoreVO : scoreVOList){
						standardScoreList.add(eachScoreVO.getClassSubjectStandardScore());
					}

					if (standardScoreList.size() != subjectList.size() || standardScoreList.size() != avgScoreList.size() || standardScoreList.size() != stuScoreList.size()){
						return AsyncResponseData.getSystemError("系统错误");
					}

					//获取总结性语句
					SummaryVO summaryVO = getSubjectBalanceSummary(tab,subject,range,standardScoreList,subjectList,avgScoreList,stuScoreList);

					resultMap.put("summaryVO",summaryVO);
					resultMap.put("subjectList",subjectList);
					resultMap.put("standardScoreList",standardScoreList);
					resultMap.put("classAvgScoreList",avgScoreList);
					resultMap.put("stuScoreList",stuScoreList);

				}else {

					filter.clear();
					filter.put("examId",score.getExamId());
					filter.put("orgId",orgId);
					filter.put("orgId1","orgId");
					filter.put("classroomId1","classroomId");
					filter.put("studentId1","studentId");
					filter.put(subjectMap.get(subject)+"1","subject");
					filter.put("sort",subjectDbMap.get(subject));
					filter.put("sortType","desc");
					schoolScoreList = scoreService.queryScoresFilterNewOne(filter);

					classScoreList = new ArrayList<>();
					Score stuScore = new Score();
					for (Score schoolScore : schoolScoreList){
						if (student.getClassroomId().equals(schoolScore.getClassroomId())){
							classScoreList.add(schoolScore);
							if (student.getId().equals(schoolScore.getStudentId())){
								stuScore = schoolScore;
							}
						}
					}

					if (stuScore == null){
						return AsyncResponseData.getSuccess().asParamError("参数examId传参错误，该学生没有参加这场考试");
					}

					for (ScoreVO eachsvo : scoreVOList){
						if (subject.equals(eachsvo.getSubject())){
							eachsvo.setScore(getSubjectScore(stuScore,subject));
							eachsvo.setSchoolAvgScore(EduEvaluatingUtils.getAverage(getSubjectScoreList(schoolScoreList,subject)));
							eachsvo.setClassTopScore(getSubjectScore(classScoreList.get(0),subject));
							eachsvo.setSchoolTopScore(getSubjectScore(schoolScoreList.get(0),subject));
							eachsvo.setRegionTopScore(getSubjectScore(schoolScoreList.get(0),subject));
							stuScoreVO = eachsvo;
							break;
						}
					}

					scoreVOList = new ArrayList<>();
					scoreVOList.add(stuScoreVO);
				}

				resultMap.put("scoreVOList",scoreVOList);

			}else if (ScoreConstant.CLASS_REPORT.equals(tab)){ //班级报告里的成绩单

				String classroomId = "";
				if (StringUtils.isNotBlank(score.getClassroomId())){
					//sourceId不为空，返回该组织机构这场考试指定班级的成绩报告单
					classroomId = score.getClassroomId();
				}else {
					return AsyncResponseData.getSuccess().asParamError("参数错误");
				}

				//根据examId,classroomId获取班级成绩单
				filter.clear();
				filter.put("examId",score.getExamId());
				filter.put("orgId",user.getOrgId());
				filter.put("orgId1","orgId");
				filter.put("classroomId1","classroomId");
				filter.put("studentId1","studentId");
				for (ScoreVO eachsvo : scoreVOList){
					filter.put(subjectMap.get(eachsvo.getSubject())+"1","subject");
				}
				//TODO1 优化  有歧义待定
				List<Score> orgScores = scoreService.queryScoresFilterNewOne(filter);
				ProcessedScores orgProcessedScore = scoreService.getProcessedScores(filter);
				filter.put("classroomId",classroomId);
				ProcessedScores classProcessedScore = scoreService.getProcessedScores(filter);
				List<BigDecimal> schoolAvgScoreList = new ArrayList<>();
				List<BigDecimal> classAvgScoreList = new ArrayList<>();
				List<String> subjectList = new ArrayList<>();
				List<BigDecimal> standardScoreList = new ArrayList<>();
				for (ScoreVO eachScoreVO : scoreVOList){
					getSubjectScore(eachScoreVO,orgProcessedScore,ScoreConstant.SCHOOL_LEVEL);
					getSubjectScore(eachScoreVO,classProcessedScore,ScoreConstant.CLASS_LEVEL);
					if (0==eachScoreVO.getFullMarks()){
						return AsyncResponseData.getSystemError("数据错误，科目的总分不应为0分");
					}
					schoolAvgScoreList.add(eachScoreVO.getSchoolAvgScore().divide(BigDecimal.valueOf(eachScoreVO.getFullMarks()),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")));
					classAvgScoreList.add(eachScoreVO.getClassAvgScore().divide(BigDecimal.valueOf(eachScoreVO.getFullMarks()),4, RoundingMode.HALF_UP).multiply(new BigDecimal("100")));
					subjectList.add(eachScoreVO.getSubject());
				}
				//###### 学科均衡 ######  获取班级各科目标准分
				int index = 0;
				getStandardTScore(scoreVOList,orgScores,ScoreConstant.CLASS_LEVEL);
				for (ScoreVO eachScoreVO : scoreVOList){
					standardScoreList.add(eachScoreVO.getClassSubjectStandardScore());
					if (eachScoreVO.getSubject().equals(subject)){
						index = scoreVOList.indexOf(eachScoreVO);
					}
				}

				//获取总结性语句
				SummaryVO summaryVO = getSubjectBalanceSummary(tab,subject,range,standardScoreList,subjectList,schoolAvgScoreList,classAvgScoreList);

				resultMap.put("summaryVO",summaryVO);
				resultMap.put("scoreVO",scoreVOList.get(index));
				resultMap.put("subjectList",subjectList);
				resultMap.put("standardScoreList",standardScoreList);
				resultMap.put("schoolAvgScoreList",schoolAvgScoreList);
				resultMap.put("classAvgScoreList",classAvgScoreList);

			}else if (ScoreConstant.TEACHING_REPORT.equals(tab) || ScoreConstant.SCHOOL_REPORT.equals(tab)){
				//教研报告里的成绩报告单 && 学校报告里的成绩报告单

				// #####学校整体成绩报告单#####
				//获取 区县 总分以及所有科目的最高分、最低分、平均分
				//ProcessedScores regionProcessedScore = scoreService.getProcessedScores(filter);
				//获取 全校 总分以及所有科目的最高分、最低分、平均分
				filter.clear();
				filter.put("examId",score.getExamId());
				filter.put("orgId",user.getOrgId());
				filter.put("orgId1","orgId");
				filter.put("classroomId1","classroomId");
				filter.put("studentId1","studentId");
				for (ScoreVO eachsvo : scoreVOList){
					filter.put(subjectMap.get(eachsvo.getSubject())+"1","subject");
				}
				ProcessedScores orgProcessedScore = scoreService.getProcessedScores(filter);

				BigDecimal schoolAvgTotalScore = orgProcessedScore.getAvgTotalScore();
				BigDecimal schoolAvgSubjectScore = getProcessedScore(orgProcessedScore,"AVG",subject);
				for (ScoreVO eachScoreVO : scoreVOList){
					getSubjectScore(eachScoreVO,orgProcessedScore,ScoreConstant.REGION_LEVEL);
					getSubjectScore(eachScoreVO,orgProcessedScore,ScoreConstant.SCHOOL_LEVEL);
				}

				//获取学校成绩列表
				//TODO1 优化 有歧义待定
				List<Score> orgScores = scoreService.queryScoresFilterNewOne(filter);
				//统计人数
				int studentNum = orgScores.size();
				resultMap.put("studentNum",studentNum);
				//获取 全校 总分以及所有科目的 高分率、优秀率、良好率、及格率、不及格率
				getSubjectScoreRate(scoreVOList,orgScores);

				if (ScoreConstant.SCHOOL_REPORT.equals(tab)){
					resultMap.put("scoreVOList",scoreVOList);
					resultMap.put("schoolAvgTotalScore",schoolAvgTotalScore);

					//#####各班成绩报告单#####
					filter.put("subjectDbScoreType","avg_total_score");
					//TODO 优化
					List<ProcessedScores> classProcessedScores = scoreService.getProcessedScoresForClasses(filter);
					List<ScoreVO> classScoreVOList = new ArrayList<>();
					//获取全校各班的 (班级名、班主任、总分平均分、最高分、最低分)list , 按班级总分降序排列
					for (ProcessedScores processedScore : classProcessedScores){
						ScoreVO scoreVO = new ScoreVO();
						scoreVO.setSubject(ScoreConstant.TOTAL_SCORE);
						scoreVO.setFullMarks(scoreVOList.get(0).getFullMarks());
						scoreVO.setClassroomId(processedScore.getClassroomId());
						scoreVO.setClassroomName(processedScore.getClassroomName());
						scoreVO.setClassTeacherName(processedScore.getClassTeacherName());
						scoreVO.setClassAvgScore(processedScore.getAvgTotalScore());
						scoreVO.setClassTopScore(processedScore.getTopTotalScore());
						scoreVO.setClassMinScore(processedScore.getMinTotalScore());
						classScoreVOList.add(scoreVO);
					}
					
					
					for (ScoreVO scoreVO : classScoreVOList){
						List<Score> classScoreList = new ArrayList<>();
						for (Score orgScore : orgScores){
							if (orgScore.getClassroomId().equals(scoreVO.getClassroomId())){
								classScoreList.add(orgScore);
							}
						}
						
						//获取各班成绩报告单(高分率、优秀率、良好率、合格率、不及格率)
						getSubjectScoreRate(scoreVO,classScoreList);
						//总分标准差、分化程度
						scoreVO.setClassTotalStandardDeviation(EduEvaluatingUtils.getStandardDeviation(getSubjectScoreList(classScoreList, ScoreConstant.TOTAL_SCORE)));
						scoreVO.setClassTotalDiffCoefficient(EduEvaluatingUtils.getDifferenceCoefficient(getSubjectScoreList(classScoreList, ScoreConstant.TOTAL_SCORE)));
					}
					List<BigDecimal> avgList = new ArrayList<>();
					List<String> classList = new ArrayList<>();
					for (ScoreVO classScore : classScoreVOList){
						avgList.add(classScore.getClassAvgScore());
						classList.add(classScore.getClassroomName());
						classScore.setSchoolRanking(avgList.indexOf(classScore.getClassAvgScore())+1);
					}
					
					classScoreVOList = CompareUtils.compareTo(classScoreVOList);
					resultMap.put("classScoreVOList",classScoreVOList);
					
					
					resultMap.put("classList",classList);
					resultMap.put("avgList",avgList);
					classProcessedScores.clear();


					//#####科目成绩报告单#####
					//获取全校各班的(班级名、班主任、总分以及所有科目的平均分、最高分、最低分)list ，按subject参数对应科目平均分降序排列
					List<ScoreVO> classSubScoreList = new ArrayList<>();

					Object subjectDbScoreType = subjectAvgDbMap.get(subject);
					filter.put("subjectDbScoreType",subjectDbScoreType);
					//TODO 优化
					classProcessedScores = scoreService.getProcessedScoresForClasses(filter);
					
					for (ProcessedScores eachPScores : classProcessedScores){
						ScoreVO scoreVO = new ScoreVO();
						scoreVO.setClassroomName(eachPScores.getClassroomName());
						scoreVO.setClassTeacherName(eachPScores.getClassTeacherName());
						scoreVO.setSubject(subject);
						//班级科目平均分
						getSubjectScore(scoreVO,eachPScores,ScoreConstant.CLASS_LEVEL);
						BigDecimal classSubjectAvgScore = scoreVO.getClassAvgScore();
						scoreVO.setClassSubjectAvgScore(classSubjectAvgScore);
						scoreVO.setSubject(ScoreConstant.TOTAL_SCORE);
						//班级总分平均分
						getSubjectScore(scoreVO,eachPScores,ScoreConstant.CLASS_LEVEL);
						scoreVO.setSubject(subject);
						//班级总分标准分
						BigDecimal classTotalStandardScore = getStandardTScore(orgScores,ScoreConstant.TOTAL_SCORE,scoreVO.getClassAvgScore());
						scoreVO.setClassTotalStandardScore(classTotalStandardScore);
						if (classTotalStandardScore.compareTo(BigDecimal.ZERO) == 0){
							return AsyncResponseData.getSystemError("数据错误，班级总分标准分不应为0");
						}
						//班级科目标准分
						BigDecimal classSubjectStandardScore = getStandardTScore(orgScores,subject,classSubjectAvgScore);
						scoreVO.setClassSubjectStandardScore(classSubjectStandardScore);
						//贡献率
						scoreVO.setContribution(classSubjectStandardScore.divide(classTotalStandardScore,2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						classSubScoreList.add(scoreVO);
					}

					List<BigDecimal> subjectAvgScores = new ArrayList<>();
					for (ScoreVO classSubScore : classSubScoreList){
						subjectAvgScores.add(classSubScore.getClassSubjectAvgScore());
						classSubScore.setSchoolRanking(subjectAvgScores.indexOf(classSubScore.getClassSubjectAvgScore())+1);
					}

					SummaryVO summaryVO = getEachClassSummary(classScoreVOList,schoolAvgTotalScore,ScoreConstant.SCHOOL_REPORT);
					getContributionSummary(summaryVO,subject,classSubScoreList);
					resultMap.put("summaryVO",summaryVO);
					resultMap.put("classSubScoreList",classSubScoreList);

				}else{
					resultMap.put("schoolAvgSubjectScore",schoolAvgSubjectScore);
					//获得指定科目的科目报告单
					ScoreVO subjectScoreVO = new ScoreVO();
					int index = 0;
					for (ScoreVO eachScoreVO : scoreVOList){
						if (eachScoreVO.getSubject().equals(subject)){
							subjectScoreVO = eachScoreVO;
							index = scoreVOList.indexOf(eachScoreVO);
							break;
						}
					}
					resultMap.put("scoreVO",subjectScoreVO);
					
					//#####科目成绩报告单#####
					//获取全校各班的(班级名、班主任、总分以及所有科目的平均分、最高分、最低分)list ，按subject参数对应科目平均分降序排列
					filter.put("subjectDbScoreType",subjectAvgDbMap.get(subject));
					//TODO 优化
					List<ProcessedScores> classProcessedScores = scoreService.getProcessedScoresForClasses(filter);
					List<ScoreVO> classScoreVOList = new ArrayList<>();
					//获取全校各班的 (班级名、班主任、科目平均分、最高分、最低分)list , 按对应科目总分降序排列
					for (ProcessedScores processedScore : classProcessedScores){
						ScoreVO scoreVO = new ScoreVO();
						scoreVO.setSubject(subject);
						scoreVO.setFullMarks(scoreVOList.get(index).getFullMarks());
						scoreVO.setClassroomId(processedScore.getClassroomId());
						scoreVO.setClassroomName(processedScore.getClassroomName());
						scoreVO.setClassTeacherName(processedScore.getClassTeacherName());
						//班级科目平均分
						getSubjectScore(scoreVO,processedScore,ScoreConstant.CLASS_LEVEL);
						//校级科目平均分
						scoreVO.setSchoolAvgScore(schoolAvgSubjectScore);
						BigDecimal classSubjectAvgScore = scoreVO.getClassAvgScore();
						scoreVO.setClassSubjectAvgScore(classSubjectAvgScore);
						classScoreVOList.add(scoreVO);
					}
					
					List<BigDecimal> subjectAvgScores = new ArrayList<>();
					for (ScoreVO scoreVO : classScoreVOList){
						subjectAvgScores.add(scoreVO.getClassSubjectAvgScore());
						scoreVO.setSchoolRanking(subjectAvgScores.indexOf(scoreVO.getClassSubjectAvgScore())+1);
						List<Score> classScoreList = new ArrayList<>();
						for (Score orgScore : orgScores){
							if (orgScore.getClassroomId().equals(scoreVO.getClassroomId())){
								classScoreList.add(orgScore);
							}
						}
						//获取各班成绩报告单(高分率、优秀率、良好率、合格率、不及格率)
						getSubjectScoreRate(scoreVO,classScoreList);
						//科目标准差、分化程度
						scoreVO.setClassSubjectStandardDeviation(EduEvaluatingUtils.getStandardDeviation(getSubjectScoreList(classScoreList, subject)));
						scoreVO.setClassSubjectDiffCoefficient(EduEvaluatingUtils.getDifferenceCoefficient(getSubjectScoreList(classScoreList, subject)));
					}

					SummaryVO summaryVO = getEachClassSummary(classScoreVOList,schoolAvgSubjectScore,ScoreConstant.TEACHING_REPORT);
					resultMap.put("summaryVO",summaryVO);
					resultMap.put("classScoreVOList",classScoreVOList);
				}
			}

		}else {
			return AsyncResponseData.getSuccess().asParamError("tab传参错误");
		}

		LOGGER.info("获取Score End");

		return AsyncResponseData.getSuccess(resultMap);
	}
	
	/**
	 * 校级报告－贡献率折线图－总结
	 */
	public SummaryVO getContributionSummary(SummaryVO summaryVO, String subject,List<ScoreVO> classSubScoreList){
		String summary = "";

		String aboveClasses = "";
		String belowClasses = "";
		for (ScoreVO classSubScore : classSubScoreList){
			if (0 > new BigDecimal("100").compareTo(classSubScore.getContribution())){//贡献率大于100的班级
				if (StringUtils.isNotBlank(aboveClasses)){
					aboveClasses += "、";
				}
				aboveClasses += classSubScore.getClassroomName();
			}
			if (0 < new BigDecimal("100").compareTo(classSubScore.getContribution())){
				if (StringUtils.isNotBlank(belowClasses)){
					belowClasses += "、";
				}
				belowClasses += classSubScore.getClassroomName();
			}
		}

		if (StringUtils.isNotBlank(aboveClasses) || StringUtils.isNotBlank(belowClasses)){
			summary += ScoreConstant.SUMMARY;
			if (StringUtils.isNotBlank(aboveClasses)){
				summary += aboveClasses + subject + ScoreConstant.CONTRIBUTION_ABOVE;
			}
			if (StringUtils.isNotBlank(belowClasses)){
				summary += belowClasses + subject + ScoreConstant.CONTRIBUTION_BELOW;
			}
		}

		if(StringUtils.isBlank(summary)){
			summary = " ";
		}

		summaryVO.setContribution(summary);
		return summaryVO;
	}

	/**
	 * 各班级科目报告单，各班级平均分对比
	 * @param classScoreVOList
	 * @param schoolAvgSubjectScore
	 * @return
	 */
	public SummaryVO getEachClassSummary(List<ScoreVO> classScoreVOList, BigDecimal schoolAvgSubjectScore, String tab){
		SummaryVO summaryVO = new SummaryVO();
		if (ScoreConstant.TEACHING_REPORT.equals(tab)){
			if (classScoreVOList.size()<=3){
				summaryVO.setEachClassSubjScore(" ");
			}else {
				StringBuilder classAvgSummary = new StringBuilder(ScoreConstant.TOP_THREE_CLASSES);

				List<BigDecimal> classAvgList = new ArrayList<>();
				List<BigDecimal> diffCoefficients = new ArrayList<>();
				for (ScoreVO classSvo: classScoreVOList){
					classAvgList.add(classSvo.getClassAvgScore());
					diffCoefficients.add(new BigDecimal(classSvo.getClassSubjectDiffCoefficient().replaceAll("%","")));
				}

				Collections.sort(diffCoefficients);
				Collections.sort(classAvgList);

				//求第一名、第二名、第三名的平均分，考虑并列情况
				String top3AvgClasses = "";
				//求分化率前三名的班级，考虑并列情况
				String top3DiffClasses = "";
				for (ScoreVO classScoreVO : classScoreVOList){
					Boolean flag = (classAvgList.indexOf(classScoreVO.getClassAvgScore()) >= classAvgList.size()-3);
					if (flag){
						if (StringUtils.isNotBlank(top3AvgClasses)){
							top3AvgClasses += "、";
						}
						top3AvgClasses += classScoreVO.getClassroomName();
					}

					flag = (diffCoefficients.indexOf(new BigDecimal(classScoreVO.getClassSubjectDiffCoefficient().replaceAll("%",""))) >= diffCoefficients.size()-3);
					if (flag){
						if (StringUtils.isNotBlank(top3DiffClasses)){
							top3DiffClasses += "、";
						}
						top3DiffClasses += classScoreVO.getClassroomName();
					}
				}
				classAvgSummary.insert(18,top3AvgClasses);
				classAvgSummary.insert(33+top3AvgClasses.length(),top3DiffClasses);
				summaryVO.setEachClassSubjScore(classAvgSummary.toString());
			}
		}

		if (classScoreVOList.size()<=1){
			summaryVO.setSubjAvgComparation(" ");
		}else {
			StringBuilder avgSummary = new StringBuilder(ScoreConstant.AVG_SCORE_COMPARISON);
			String aboveClasses = "";
			String belowClasses = "";
			String topAvgClasses = "";
			String minAvgClasses = "";
			int aboveCount = 0;
			int belowCount = 0;
			for (ScoreVO classScoreVO : classScoreVOList){
				//平均分高于学校平均分的班级
				if (schoolAvgSubjectScore.compareTo(classScoreVO.getClassAvgScore()) < 0){
					if (StringUtils.isNotBlank(aboveClasses)){
						aboveClasses += "、";
					}
					aboveClasses += classScoreVO.getClassroomName();
					aboveCount++;
				}
				//平均分低于学校平均分的班级
				if (schoolAvgSubjectScore.compareTo(classScoreVO.getClassAvgScore()) > 0){
					if (StringUtils.isNotBlank(belowClasses)){
						belowClasses += "、";
					}
					belowClasses += classScoreVO.getClassroomName();
					belowCount++;
				}
				if (0 == classScoreVO.getClassAvgScore().compareTo(classScoreVOList.get(0).getClassAvgScore())){
					if (StringUtils.isNotBlank(topAvgClasses)){
						topAvgClasses += "、";
					}
					topAvgClasses += classScoreVO.getClassroomName();
				}
				if (0 == classScoreVO.getClassAvgScore().compareTo(classScoreVOList.get(classScoreVOList.size()-1).getClassAvgScore())){
					if (StringUtils.isNotBlank(minAvgClasses)){
						minAvgClasses += "、";
					}
					minAvgClasses += classScoreVO.getClassroomName();
				}
			}

			if(0 != aboveCount && 0 != belowCount) {
				avgSummary.insert(8,aboveCount);
				int num = (aboveCount+"").length();
				avgSummary.insert(30+num,aboveClasses);
				num += aboveClasses.length();
				avgSummary.insert(33+num,topAvgClasses);
				num += topAvgClasses.length();
				avgSummary.insert(40+num,belowClasses);
				num += belowClasses.length();
				avgSummary.insert(55+num,minAvgClasses);
			}else {
				summaryVO.setSubjAvgComparation(" ");
			}


			summaryVO.setSubjAvgComparation(avgSummary.toString());
		}

		return summaryVO;
	}
	
	//TODO 优化完成
	/**
	 * 获取分数等级分布
	 *
	 * @param score
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLevelDistribution", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getLevelDistribution(Score score, String tab, String subject, @RequestParam(required = false) String rateType) throws Exception{
		LOGGER.info("获取Score Start");

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		
		if (StringUtils.isBlank(score.getExamId())){
			return AsyncResponseData.getSuccess().asParamError("参数不能为空");
		}

		Exam exam = examService.getExamById(score.getExamId());
		if (exam == null){
			return AsyncResponseData.getSuccess().asUnknownError("考试ID传参错误");
		}
		
		List<ScoreVO> scoreVOList = getExamScoreVO(exam.getId());
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("examId",exam.getId());
		filter.put("orgId",user.getOrgId());
		for (ScoreVO scoreVO : scoreVOList) {
			filter.put(subjectMap.get(scoreVO.getSubject())+"1", "subject");
		}
		
		List<Score> orgScoreList = scoreService.queryScoresFilterNewOne(filter);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<BigDecimal> subjectList = new ArrayList<>();

		if (ScoreConstant.SCHOOL_REPORT.equals(tab)){
			BigDecimal totalScore = BigDecimal.valueOf(scoreVOList.get(0).getFullMarks());
			//TODO1 优化
			for (Score eachScore : orgScoreList){
				subjectList.add(eachScore.getTotalScore());
			}

			Map<String,BigDecimal> rateMap = EduEvaluatingUtils.getRatio(subjectList,totalScore);
			return AsyncResponseData.getSuccess(rateMap);

		}else if (ScoreConstant.TEACHING_REPORT.equals(tab)){
			if (StringUtils.isBlank(subject)){
				return AsyncResponseData.getSuccess().asParamError("参数不能为空");
			}
			getSubjectScoreRate(scoreVOList,orgScoreList);
			return AsyncResponseData.getSuccess(scoreVOList);

		}else if (ScoreConstant.CLASS_REPORT.equals(tab)){
			if (StringUtils.isBlank(subject)){
				return AsyncResponseData.getSuccess().asParamError("参数不能为空");
			}

			//校级分数列表
			Classroom classroom;
			if (StringUtils.isBlank(score.getClassroomId())){
				return AsyncResponseData.getSuccess().asParamError("班级ID不能为空");
			}

			classroom = classroomService.getClassroomByIdNew(score.getClassroomId());
			if (classroom==null){
				return AsyncResponseData.getSystemError("数据错误");
			}
			resultMap.put("classroomName",classroom.getClassroomName());

			//班级分数列表 按照科目传参对应的分数降序排列
			filter.put("classroomId",classroom.getId());
			filter.put("studentId1","studentId");
			filter.put("sort",subjectDbMap.get(subject));
			filter.put("sortType","desc");
			List<Score> classScoreList = scoreService.queryScoresFilterNewOne(filter);
			if (classScoreList==null || classScoreList.isEmpty()){
				return AsyncResponseData.getSuccess("尚无该班级考试成绩数据");
			}

			filter.remove("sort");
			filter.remove("sortType");

			if (ScoreConstant.TOTAL_SCORE.equals(subject)){
				ScoreVO orgScoreVO = new ScoreVO();
				orgScoreVO.setSubject(ScoreConstant.TOTAL_SCORE);
				orgScoreVO.setScoreLevel(ScoreConstant.SCHOOL_LEVEL);
				orgScoreVO.setFullMarks(scoreVOList.get(0).getFullMarks());
				getSubjectScoreRate(orgScoreVO,orgScoreList);

				ScoreVO classScoreVO = new ScoreVO();
				classScoreVO.setSubject(ScoreConstant.TOTAL_SCORE);
				classScoreVO.setScoreLevel(ScoreConstant.CLASS_LEVEL);
				classScoreVO.setFullMarks(scoreVOList.get(0).getFullMarks());
				getSubjectScoreRate(classScoreVO,classScoreList);
				scoreVOList.clear();
				scoreVOList.add(orgScoreVO);
				scoreVOList.add(classScoreVO);
				SummaryVO summaryVO = getClassTSLevelDistrSummary(scoreVOList);
				resultMap.put("summaryVO",summaryVO);
				resultMap.put("scoreVOList",scoreVOList);
				return AsyncResponseData.getSuccess(resultMap);
			}
			//获取各个科目的分数等级
			getSubjectScoreRate(scoreVOList,classScoreList);
			//获取各个科目各个分数等级的学生

			BigDecimal totalScore = BigDecimal.ZERO;
			for (ScoreVO scoreVO : scoreVOList){
				if (subject.equals(scoreVO.getSubject())){
					totalScore = BigDecimal.valueOf(scoreVO.getFullMarks());
					break;
				}
			}

			BigDecimal startScore = BigDecimal.ZERO;
			BigDecimal endScore = BigDecimal.ZERO;
			Boolean flag = false;
			if ("".equals(rateType) || ScoreConstant.HIGH_SCORE.equals(rateType)){
				startScore = totalScore.multiply(BigDecimal.valueOf(0.9));
				endScore = totalScore;
				flag = true;
			}else if (ScoreConstant.EXCELLENT.equals(rateType)){
				startScore = totalScore.multiply(BigDecimal.valueOf(0.8));
				endScore = totalScore.multiply(BigDecimal.valueOf(0.9));
			}else if (ScoreConstant.WELL.equals(rateType)){
				startScore = totalScore.multiply(BigDecimal.valueOf(0.7));
				endScore = totalScore.multiply(BigDecimal.valueOf(0.8));
			}else if (ScoreConstant.PASS.equals(rateType)){
				startScore = totalScore.multiply(BigDecimal.valueOf(0.6));
				endScore = totalScore.multiply(BigDecimal.valueOf(0.7));
			}else if (ScoreConstant.FAIL.equals(rateType)){
				endScore = totalScore.multiply(BigDecimal.valueOf(0.6));
			}else {
				return AsyncResponseData.getSuccess().asParamError("参数错误");
			}

			List<Score> stuScoreList = new ArrayList<>();
			filter.clear();
			filter.put("examId",exam.getId());
			filter.put("classroomId",classroom.getId());
			filter.put("subjectDbScoreType",subjectDbMap.get(subject));
			filter.put("flag",flag);
			filter.put("startScore",startScore);
			filter.put("endScore",endScore);
			stuScoreList = scoreService.getScoresByInterval(filter);
			Map<String,Integer> classScoreMap = new HashMap<>();
			//将班级 学生对应科目传参的名次 存放在Map中
			for(Score classScore : classScoreList){
				classScoreMap.put(classScore.getStudentId(), classScoreList.indexOf(classScore)+1);
			}

			if(stuScoreList != null && !stuScoreList.isEmpty()) {
				for (Score stuScore : stuScoreList){
					stuScore.setRanking(classScoreMap.get(stuScore.getStudentId()));
				}
			}
			
			resultMap.put("stuNum",classScoreList.size());
			resultMap.put("scoreVOList",scoreVOList);
			resultMap.put("startScore",startScore);
			resultMap.put("endScore",endScore);
			resultMap.put("stuScoreList",stuScoreList);
		}else {
			return AsyncResponseData.getSuccess().asParamError("参数错误");
		}

		LOGGER.info("获取Score End");

		return AsyncResponseData.getSuccess(resultMap);
	}
	
	
	/**
	 * 班级报告－总分－分数等级分布图 总结
	 * @return
	 */
	public SummaryVO getClassTSLevelDistrSummary(List<ScoreVO> scoreVOList){
		SummaryVO summaryVO = new SummaryVO();
		String summary = "";
		String above = "";
		String below = "";
		if (2 != scoreVOList.size()){
			summaryVO.setScoreLevelDistr(" ");
			return summaryVO;
		}
		if (!ScoreConstant.SCHOOL_LEVEL.equals(scoreVOList.get(0).getScoreLevel()) || !ScoreConstant.CLASS_LEVEL.equals(scoreVOList.get(1).getScoreLevel())){
			summaryVO.setScoreLevelDistr(" ");
			return summaryVO;
		}
		if (scoreVOList.get(1).getHighRate().compareTo(scoreVOList.get(0).getHighRate()) > 0){
			above += "高分率";
		}
		if (scoreVOList.get(1).getHighRate().compareTo(scoreVOList.get(0).getHighRate()) < 0){
			below += "高分率";
		}
		if (scoreVOList.get(1).getExcellentRate().compareTo(scoreVOList.get(0).getExcellentRate()) > 0){
			if (StringUtils.isNotBlank(above)){
				above += "、";
			}
			above += "优秀率";
		}
		if (scoreVOList.get(1).getExcellentRate().compareTo(scoreVOList.get(0).getExcellentRate()) < 0){
			if (StringUtils.isNotBlank(below)){
				below += "、";
			}
			below += "优秀率";
		}
		if (scoreVOList.get(1).getCommissionRate().compareTo(scoreVOList.get(0).getCommissionRate()) > 0){
			if (StringUtils.isNotBlank(above)){
				above += "、";
			}
			above += "良好率";
		}
		if (scoreVOList.get(1).getCommissionRate().compareTo(scoreVOList.get(0).getCommissionRate()) < 0){
			if (StringUtils.isNotBlank(below)){
				below += "、";
			}
			below += "良好率";
		}
		if (scoreVOList.get(1).getPassRate().compareTo(scoreVOList.get(0).getPassRate()) > 0){
			if (StringUtils.isNotBlank(above)){
				above += "、";
			}
			above += "合格率";
		}
		if (scoreVOList.get(1).getPassRate().compareTo(scoreVOList.get(0).getPassRate()) < 0){
			if (StringUtils.isNotBlank(below)){
				below += "、";
			}
			below += "合格率";
		}
		if (StringUtils.isNotBlank(above)){
			summary = ScoreConstant.SUMMARY + "本班级";
			summary += above + ScoreConstant.ABOVE_SCHOOl_LEVEL;
		}
		if (StringUtils.isNotBlank(below)){
			if (StringUtils.isBlank(summary)){
				summary = ScoreConstant.SUMMARY + "本班级";
			}
			summary += below + ScoreConstant.BELOW_SCHOOl_LEVEL;
		}

		if (scoreVOList.get(1).getFailureRate().compareTo(scoreVOList.get(0).getFailureRate()) > 0){
			summary += ScoreConstant.FAILURE_RATE_ABOVE_SL;
		}
		if (scoreVOList.get(1).getFailureRate().compareTo(scoreVOList.get(0).getFailureRate()) < 0){
			summary += ScoreConstant.FAILURE_RATE_BELOW_SL;
		}

		if (StringUtils.isBlank(summary)){
			summary = " ";
		}

		summaryVO.setScoreLevelDistr(summary);
		return summaryVO;
	}
	
	//TODO 优化完成
	//TODO 已完成
	/**
	 * 获取分数等级分布
	 *
	 * @param score
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEachClassLevelDistribution", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getEachClassLevelDistribution(Score score) throws Exception{
		LOGGER.info("获取 校级报告-各班等级分布对比 start");
		
		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		
		if (StringUtils.isBlank(score.getExamId())){
			return AsyncResponseData.getSuccess().asParamError("考试ID不能为空");
		}
		Exam exam = examService.getExamById(score.getExamId());
		
		if (exam == null){
			return AsyncResponseData.getSuccess().asUnknownError("查询不到最近一次考试");
		}

		Map<String,Object> filter = new HashMap<>();
		filter.put("examId",exam.getId());
		filter.put("orgId",user.getOrgId());
		//TODO1 优化
		//只查寻这个字段的意思
		filter.put("totalScore1", "totalScore1");
		filter.put("classroomId1", "classroomId1");
		filter.put("classroomName1", "classroom_name1");
		List<Score> scoreList = scoreService.queryScoresFilterNewOne(filter);
		if (scoreList==null || scoreList.isEmpty()){
			return AsyncResponseData.getSuccess("没有最近一次考试的相关数据");
		}
		
		if (StringUtils.isBlank(exam.getClassroom())) {
			return AsyncResponseData.getSuccess().asSystemError("数据错误");		
		}
		
		String[] classrooms = exam.getClassroom().split(",");
		//计算每一科满分的分数
		List<ScoreVO> scoreVOList = getExamScoreVO(exam.getId());
		List<BigDecimal> classScoreList;
		ScoreVO scoreVO;
		if (scoreVOList.size() < 1) {
			return AsyncResponseData.getSuccess().asSystemError("数据错误");
		}
		
		//总门数满分
		BigDecimal totalScore = BigDecimal.valueOf(scoreVOList.get(0).getFullMarks());
		scoreVOList.clear();
		//TODO1 必须优化  如果班级数据表多的话查询压力很大  并且还是循环查询 建议首先把循环去掉  然后再测试大数据的sql压力
		Map<String,List<Score>> param = new LinkedHashMap<>();
		List<Score> classList = null;
		for (Score eachScore : scoreList) {
			//以classId为key,如果这个里面有这个key 那么直接取出来重新装
			if (param.containsKey(eachScore.getClassroomName())) {
				List<Score> classListParam = param.get(eachScore.getClassroomName());
				classListParam.add(eachScore);
				param.put(eachScore.getClassroomName(), classListParam);
			} else {//如果没有
				classList = new ArrayList<>();
				classList.add(eachScore);
				param.put(eachScore.getClassroomName(), classList);
			}
		}
		
		for (int i=0; i<classrooms.length; i++){
			scoreVO = new ScoreVO();
			classScoreList = new ArrayList<>();
			//Classroom classroom = classroomService.getClassroomByName(classrooms[i],user.getOrgId());
			//			for (Score eachScore : scoreList){
//				if (eachScore.getClassroomId().equals(classroom.getId())){
//				classScoreList.add(eachScore.getTotalScore());
//				}
//			}
			//变历所有分好班级的 map
			for (String key : param.keySet()) {
				if (classrooms[i].equals(key)) {
					//取出对应班级的集合
					List<Score> p1 = param.get(key);
					for (Score score2 : p1) {
						classScoreList.add(score2.getTotalScore());
					}
				}
			}

			if (0 == classScoreList.size()){
				continue;
			}

			Map<String,BigDecimal> rateMap = EduEvaluatingUtils.getRatio(classScoreList,totalScore);
			scoreVO.setHighRate(rateMap.get("highRate"));
			scoreVO.setExcellentRate(rateMap.get("excellentRate"));
			scoreVO.setCommissionRate(rateMap.get("commissionRate"));
			scoreVO.setPassRate(rateMap.get("passRate"));
			scoreVO.setFailureRate(rateMap.get("failureRate"));
			//scoreVO.setClassroomId(classroom.getId());
			
			if (param.get(classrooms[i]).size() < 1) return AsyncResponseData.getSuccess().asSystemError("数据错误"); 
			scoreVO.setClassroomId(param.get(classrooms[i]).get(0).getClassroomId());
			//scoreVO.setClassroomName(classroom.getClassroomName());
			scoreVO.setClassroomName(classrooms[i]);
			scoreVOList.add(scoreVO);
		}
		
		List<BigDecimal> subjectList = new ArrayList<>();
		for (Score eachScore : scoreList){
			subjectList.add(eachScore.getTotalScore());
		}
		
		Map<String,BigDecimal> rateMap = EduEvaluatingUtils.getRatio(subjectList,totalScore);
		ScoreVO schoolScoreVO = new ScoreVO();
		schoolScoreVO.setClassroomName(ScoreConstant.WHOLE_SCHOOL);
		schoolScoreVO.setHighRate(rateMap.get("highRate"));
		schoolScoreVO.setExcellentRate(rateMap.get("excellentRate"));
		schoolScoreVO.setCommissionRate(rateMap.get("commissionRate"));
		schoolScoreVO.setPassRate(rateMap.get("passRate"));
		schoolScoreVO.setFailureRate(rateMap.get("failureRate"));
		scoreVOList.add(0,schoolScoreVO);

		SummaryVO summaryVO = getClassesLevelDistriSummary(scoreVOList);

		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("summaryVO",summaryVO);
		resultMap.put("scoreVOList",scoreVOList);

		LOGGER.info("获取 校级报告-各班等级分布对比 End");

		return AsyncResponseData.getSuccess(resultMap);
	
	}

	//TODO 优化完成
	/**
	 * 获取分数等级分布
	 *
	 * @param score
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEachSubjectLevelDistribution", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getEachSubjectLevelDistribution(Score score, String subject) throws Exception{
		LOGGER.info("获取Score Start");

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}

		if (StringUtils.isBlank(score.getExamId()) || StringUtils.isBlank(subject)){
			 return AsyncResponseData.getSuccess().asParamError("考试ID或学科传参不能为空");
		}

		Exam exam = examService.getExamById(score.getExamId());
		if (exam == null){
			return AsyncResponseData.getSuccess().asUnknownError("查询不到最近一次考试");
		}
		Map<String,Object> filter = new HashMap<>();
		filter.put("examId",exam.getId());
		filter.put("orgId",user.getOrgId());
		filter.put("classroomName1", "classroom_name1");
		//filter.put("classroomId1", "classroomId1");
		filter.put(subjectMap.get(subject)+"1","subject");
		//校级成绩列表
		//TODO1 优化
		List<Score> scoreList = scoreService.queryScoresFilterNewOne(filter);

		if (scoreList==null || scoreList.isEmpty()){
			return AsyncResponseData.getSuccess("没有最近一次考试的相关数据");
		}

		if (StringUtils.isBlank(exam.getClassroom()) || StringUtils.isBlank(exam.getSubject())){
			return AsyncResponseData.getSuccess().asSystemError("数据错误");
		}
		String[] classrooms = exam.getClassroom().split(",");

		Map<String, List<Score>> classScoresMap = new HashMap<>();
		for (Score orgScore : scoreList){
			if (classScoresMap.containsKey(orgScore.getClassroomName())){
				List<Score> currClassScores = classScoresMap.get(orgScore.getClassroomName());
				currClassScores.add(orgScore);
				classScoresMap.put(orgScore.getClassroomName(),currClassScores);
			}else {
				List<Score> currClassScores = new ArrayList<>();
				currClassScores.add(orgScore);
				classScoresMap.put(orgScore.getClassroomName(),currClassScores);
			}
		}

		List<ScoreVO> scoreVOList = getExamScoreVO(exam.getId());
		int index = 0;
		for (ScoreVO eachScoreVO : scoreVOList){
			if (subject.equals(eachScoreVO.getSubject())){
				index = scoreVOList.indexOf(eachScoreVO);
				break;
			}
		}
		//该科目总分
		BigDecimal totalScore = BigDecimal.valueOf(scoreVOList.get(index).getFullMarks());
		//校级科目成绩列表
		List<BigDecimal> orgSubjectScoreList = getSubjectScoreList(scoreList,subject);
		//校级高分率、优秀率...
		Map<String,BigDecimal> schoolRateMap = EduEvaluatingUtils.getRatio(orgSubjectScoreList,totalScore);
		List<ScoreVO> classScoreVOList = new ArrayList<>();
		ScoreVO schoolScoreVO = new ScoreVO();
		schoolScoreVO.setClassroomName(ScoreConstant.WHOLE_SCHOOL);
		schoolScoreVO.setHighRate(schoolRateMap.get("highRate"));
		schoolScoreVO.setExcellentRate(schoolRateMap.get("excellentRate"));
		schoolScoreVO.setCommissionRate(schoolRateMap.get("commissionRate"));
		schoolScoreVO.setPassRate(schoolRateMap.get("passRate"));
		schoolScoreVO.setFailureRate(schoolRateMap.get("failureRate"));
		classScoreVOList.add(schoolScoreVO);

		ScoreVO classScoreVO;
		for (String classroomName : classrooms){

			classScoreVO = new ScoreVO();
			classScoreVO.setClassroomName(classroomName);

			List<BigDecimal> subjectScoreList = getSubjectScoreList(classScoresMap.get(classroomName),subject);

			Map<String,BigDecimal> classRateMap = EduEvaluatingUtils.getRatio(subjectScoreList,totalScore);
			if (classRateMap != null){
				classScoreVO.setHighRate(classRateMap.get("highRate"));
				classScoreVO.setExcellentRate(classRateMap.get("excellentRate"));
				classScoreVO.setCommissionRate(classRateMap.get("commissionRate"));
				classScoreVO.setPassRate(classRateMap.get("passRate"));
				classScoreVO.setFailureRate(classRateMap.get("failureRate"));
			}else {
				classScoreVO.setHighRate(BigDecimal.ZERO);
				classScoreVO.setExcellentRate(BigDecimal.ZERO);
				classScoreVO.setCommissionRate(BigDecimal.ZERO);
				classScoreVO.setPassRate(BigDecimal.ZERO);
				classScoreVO.setFailureRate(BigDecimal.ZERO);
			}

			classScoreVOList.add(classScoreVO);
		}

		SummaryVO summaryVO = getClassesLevelDistriSummary(classScoreVOList);

		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("summaryVO",summaryVO);
		resultMap.put("classScoreVOList",classScoreVOList);

		LOGGER.info("获取Score End");
		return AsyncResponseData.getSuccess(resultMap);


	}

	public SummaryVO getClassesLevelDistriSummary(List<ScoreVO> classScoreVOList){
		SummaryVO summaryVO = new SummaryVO();
		String summary = "";
		List<ScoreVO> classSvoList = new ArrayList<>();
		classSvoList.addAll(classScoreVOList);
		classSvoList.remove(0);

		int size = classSvoList.size();
		if (size < 2){
			summaryVO.setClassesLevelDistri(" ");
			return summaryVO;
		}

		BigDecimal topHigh = BigDecimal.ZERO;
		BigDecimal topExcellent = BigDecimal.ZERO;
		BigDecimal topWell = BigDecimal.ZERO;
		BigDecimal topPass = BigDecimal.ZERO;
		BigDecimal topFail = BigDecimal.ZERO;

		String topHighClass = "";
		String topExceClass = "";
		String topWellClass = "";
		String topPassClass = "";
		String topFailClass = "";

		BigDecimal schoolFailRate = classScoreVOList.get(0).getFailureRate();
		String failAboveSchool = "";

		for (ScoreVO classSvo : classSvoList){

			if (schoolFailRate.compareTo(classSvo.getFailureRate()) < 0){
				if (StringUtils.isNotBlank(failAboveSchool)){
					failAboveSchool += "、";
				}
				failAboveSchool += classSvo.getClassroomName();
			}

			if (0 != BigDecimal.ZERO.compareTo(classScoreVOList.get(0).getHighRate()) && 0 != BigDecimal.ZERO.compareTo(classSvo.getHighRate()) && topHigh.compareTo(classSvo.getHighRate()) <= 0){
				if (topHigh.compareTo(classSvo.getHighRate()) < 0){
					topHigh = classSvo.getHighRate();
					topHighClass = classSvo.getClassroomName();
				}else {
					if (StringUtils.isNotBlank(topHighClass)){
						topHighClass += "、";
					}
					topHighClass += classSvo.getClassroomName();
				}
			}
			if (0 != BigDecimal.ZERO.compareTo(classScoreVOList.get(0).getExcellentRate()) && 0 != BigDecimal.ZERO.compareTo(classSvo.getExcellentRate()) && topExcellent.compareTo(classSvo.getExcellentRate()) <= 0){
				if (topExcellent.compareTo(classSvo.getExcellentRate()) < 0){
					topExcellent = classSvo.getExcellentRate();
					topExceClass = classSvo.getClassroomName();
				}else {
					if (StringUtils.isNotBlank(topExceClass)){
						topExceClass += "、";
					}
					topExceClass += classSvo.getClassroomName();
				}
			}
			if (0 != BigDecimal.ZERO.compareTo(classScoreVOList.get(0).getCommissionRate()) && 0 != BigDecimal.ZERO.compareTo(classSvo.getCommissionRate()) && topWell.compareTo(classSvo.getCommissionRate()) <= 0){
				if (topWell.compareTo(classSvo.getCommissionRate()) < 0){
					topWell = classSvo.getCommissionRate();
					topWellClass = classSvo.getClassroomName();
				}else {
					if (StringUtils.isNotBlank(topWellClass)){
						topWellClass += "、";
					}
					topWellClass += classSvo.getClassroomName();
				}
			}
			if (0 != BigDecimal.ZERO.compareTo(classScoreVOList.get(0).getPassRate()) && 0 != BigDecimal.ZERO.compareTo(classSvo.getPassRate()) && topPass.compareTo(classSvo.getPassRate()) <= 0){
				if (topPass.compareTo(classSvo.getPassRate()) < 0){
					topPass = classSvo.getPassRate();
					topPassClass = classSvo.getClassroomName();
				}else {
					if (StringUtils.isNotBlank(topPassClass)){
						topPassClass += "、";
					}
					topPassClass += classSvo.getClassroomName();
				}
			}
			if (0 != BigDecimal.ZERO.compareTo(classScoreVOList.get(0).getFailureRate()) && 0 != BigDecimal.ZERO.compareTo(classSvo.getFailureRate()) && topFail.compareTo(classSvo.getFailureRate()) <= 0){
				if (topFail.compareTo(classSvo.getFailureRate()) < 0){
					topFail = classSvo.getFailureRate();
					topFailClass = classSvo.getClassroomName();
				}else {
					if (StringUtils.isNotBlank(topFailClass)){
						topFailClass += "、";
					}
					topFailClass += classSvo.getClassroomName();
				}
			}

		}

		if (StringUtils.isNotBlank(topHighClass) || StringUtils.isNotBlank(topExceClass) || StringUtils.isNotBlank(topWellClass) || StringUtils.isNotBlank(topPassClass) || StringUtils.isNotBlank(topFailClass)){
			summary += ScoreConstant.SUMMARY;
			if (StringUtils.isNotBlank(topHighClass)){
				summary += ScoreConstant.TOP_HIGH_RATE.replaceAll("%",topHighClass);
			}
			if (StringUtils.isNotBlank(topExceClass)){
				summary += ScoreConstant.TOP_EXCELLENT_RATE.replaceAll("%",topExceClass);
			}
			if (StringUtils.isNotBlank(topWellClass)){
				summary += ScoreConstant.TOP_WELL_RATE.replaceAll("%",topWellClass);
			}
			if (StringUtils.isNotBlank(topPassClass)){
				summary += ScoreConstant.TOP_PASS_RATE.replaceAll("%",topPassClass);
			}
			if (StringUtils.isNotBlank(topFailClass)){
				summary += ScoreConstant.TOP_FAIL_RATE.replaceAll("%",topFailClass);
			}
			if (StringUtils.isNotBlank(failAboveSchool)){
				summary += ScoreConstant.HIGHER_THAN_SCHOOL.replaceAll("%",failAboveSchool);
			}
		}else {
			summary = " ";
		}

		summaryVO.setClassesLevelDistri(summary);
		return summaryVO;
	}

	//TODO 优化完成
	/**
	 * 获取score对象
	 *
	 * @param score
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getEachClassTopScores", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getEachClassTopScores(Score score,String tab,@RequestParam(required = false) String subject) throws Exception{
		LOGGER.info("getEachClassTopScores Start");

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		
		Exam exam;
		if (StringUtils.isNotBlank(score.getExamId())){
			exam = examService.getExamById(score.getExamId());
		}else {
			exam = examService.getOrgLastCompletedExam(user.getOrgId());
		}
		
		if (exam == null){
			return AsyncResponseData.getSuccess().asUnknownError("查询不到最近一次考试");
		}
		
		Map<String,Object> filter = new HashMap<>();
		filter.put("examId",exam.getId());
		filter.put("orgId",user.getOrgId());
		filter.put("classroomName1", "classroom_name1");
		//filter.put("classroomId1", "classroomId1");
		filter.put("totalScore1", "totalScore1");
		filter.put(subjectMap.get(subject)+"1","subject");

		if (ScoreConstant.SCHOOL_REPORT.equals(tab)){
			subject = ScoreConstant.TOTAL_SCORE;
			filter.put("sort","total_score");
		}else if (ScoreConstant.TEACHING_REPORT.equals(tab)){
			
			if (StringUtils.isBlank(subject)){
				String[] subjects = exam.getSubject().split(",");
				
				if (subjects.length>0){
					subject = subjects[0];
				}else {
					return AsyncResponseData.getSuccess().asSystemError("数据错误");
				}
			}

			Object subjectDbScoreType = subjectDbMap.get(subject);
			if (subjectDbScoreType!=null){
				filter.put("sort",subjectDbScoreType);
			}else {
				return AsyncResponseData.getSuccess().asParamError("参数错误");
			}
		}
		
		filter.put("sortType","desc");
		filter.put("recordNum",1000);
		//全校前1000名学生成绩
		//TODO1 优化
		List<Score> scoreList = scoreService.queryScoresFilterNewOne(filter);
		if (scoreList==null || scoreList.isEmpty()){
			return AsyncResponseData.getSuccess("没有最近一次考试的相关数据");
		}

		List<BigDecimal> schoolScoreList = new ArrayList<>();
		Map<String, List<Score>> classScoresMap = new HashMap<>();
		for (Score schoolScore : scoreList){
			schoolScoreList.add(getSubjectScore(schoolScore,subject));
			if (classScoresMap.containsKey(schoolScore.getClassroomName())){
				List<Score> currClassScores = classScoresMap.get(schoolScore.getClassroomName());
				currClassScores.add(schoolScore);
				classScoresMap.put(schoolScore.getClassroomName(),currClassScores);
			}else {
				List<Score> currClassScores = new ArrayList<>();
				currClassScores.add(schoolScore);
				classScoresMap.put(schoolScore.getClassroomName(),currClassScores);
			}
		}

		String[] classrooms = exam.getClassroom().split(",");
		if (classrooms.length>0){
			List<ClassTopScore> classTopScoreList = new ArrayList<>();
			ClassTopScore classTopScore;
			for (String className : classrooms){
				if (null  == classScoresMap.get(className)){
					continue;
				}
				classTopScore = new ClassTopScore();
				classTopScore.setClassroomName(className);

				int classTopTenStuNum = 0;
				int classTopTwentyStuNum = 0;
				int classTopFiftyStuNum = 0;
				int classTopOneHundredStuNum = 0;
				int classTopTwoHundredStuNum = 0;
				int classTopFiveHundredStuNum = 0;
				int classTopOneThousandStuNum = 0;

				List<BigDecimal> classScoreList = getSubjectScoreList(classScoresMap.get(className),subject);
				for (BigDecimal classScore : classScoreList){
					int rank = schoolScoreList.indexOf(classScore) + 1;
					//TODO1 if不能多嵌套
					if (rank <= 1000){
						classTopOneThousandStuNum++;
					}
					if (rank <= 500){
						classTopFiveHundredStuNum++;
					}
					if (rank <= 200){
						classTopTwoHundredStuNum++;
					}
					if (rank <= 100){
						classTopOneHundredStuNum++;
					}
					if (rank <= 50){
						classTopFiftyStuNum++;
					}
					if (rank <= 20){
						classTopTwentyStuNum++;
					}
					if (rank <= 10){
						classTopTenStuNum++;
					}
				}
				
				classTopScore.setClassTopTenStuNum(classTopTenStuNum);
				classTopScore.setClassTopTwentyStuNum(classTopTwentyStuNum);
				classTopScore.setClassTopFiftyStuNum(classTopFiftyStuNum);
				classTopScore.setClassTopOneHundredStuNum(classTopOneHundredStuNum);
				classTopScore.setClassTopTwoHundredStuNum(classTopTwoHundredStuNum);
				classTopScore.setClassTopFiveHundredStuNum(classTopFiveHundredStuNum);
				classTopScore.setClassTopOneThousandStuNum(classTopOneThousandStuNum);

				classTopScoreList.add(classTopScore);
			}

			LOGGER.info("获取Score End");
			return AsyncResponseData.getSuccess(classTopScoreList);
		}else {
			return AsyncResponseData.getSuccess().asSystemError("数据错误");
		}

	}
	
	//TODO 优化完成
	@RequestMapping(value = "/getTopOrgScoresInClass", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getTopOrgScoresInClass(Score score,@RequestParam(required = false) Integer topNum) throws Exception{
		Map<String,Object> resultMap = new HashMap<>();
		
		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}

		
		if (StringUtils.isBlank(score.getExamId()) || StringUtils.isBlank(score.getClassroomId())){
			return AsyncResponseData.getSuccess().asParamError("考试ID或班级ID不能为空");
		}
		Exam exam = examService.getExamById(score.getExamId());
		if (exam == null){
			return AsyncResponseData.getSuccess().asUnknownError("查询不到最近一次考试");
		}
		
		Map<String,Object> filter = new HashMap<>();
		filter.put("examId",exam.getId());
		filter.put("orgId",user.getOrgId());
		filter.put("sort","total_score");
		filter.put("sortType","desc");
		filter.put("type",1000);
		filter.put("totalScore1", "totalScore1");
		filter.put("classroomId1", "classroomId1");
		filter.put("studentName1", "studentName1");
		//TODO1 优化 有歧义
		List<Score> scoreList = scoreService.queryScoresFilterNewOne(filter);
		if (scoreList==null || scoreList.isEmpty()){
			return AsyncResponseData.getSuccess("没有最近一次考试的相关数据");
		}
		
		List<Score> classList = new ArrayList<>();
		Score score1;
		int top10= 0;
		int top20= 0;
		int top50= 0;
		int top100 = 0;
		int top200 = 0;
		int top500 = 0;
		int top1000 = 0;
		ClassTopScore classTopScore = new ClassTopScore();
		List<BigDecimal> schoolScores = new ArrayList<>();
		for (Score eachScore : scoreList){
			BigDecimal totalScore = eachScore.getTotalScore();
			schoolScores.add(totalScore);
			score1 = new Score();
			if (score.getClassroomId().equals(eachScore.getClassroomId())){
				score1.setClassroomId(score.getClassroomId());
				score1.setStudentName(eachScore.getStudentName());
				score1.setRanking(schoolScores.indexOf(totalScore)+1);
				classList.add(score1);
				
				//TODO1 优化
				if (schoolScores.indexOf(totalScore)+1<=1000){
					top1000++;
				}
				if (schoolScores.indexOf(totalScore)+1<=500){
					top500++;
				}
				if (schoolScores.indexOf(totalScore)+1<=200){
					top200++;
				}
				if (schoolScores.indexOf(totalScore)+1<=100){
					top100++;
				}
				if (schoolScores.indexOf(totalScore)+1<=50){
					top50++;
				}
				if (schoolScores.indexOf(totalScore)+1<=20){
					top20++;
				}
				if (schoolScores.indexOf(totalScore)+1<=10){
					top10++;
				}
			}
		}
		
		classTopScore.setClassTopOneThousandStuNum(top1000);
		classTopScore.setClassTopFiveHundredStuNum(top500);
		classTopScore.setClassTopTwoHundredStuNum(top200);
		classTopScore.setClassTopOneHundredStuNum(top100);
		classTopScore.setClassTopFiftyStuNum(top50);
		classTopScore.setClassTopTwentyStuNum(top20);
		classTopScore.setClassTopTenStuNum(top10);
		if (topNum==null){
			topNum = 50;
		}

		List<Score> resultScoreList = new ArrayList<>();
		for (Score eachScore : classList){
			if (eachScore.getRanking() > topNum){
				break;
			}
			resultScoreList.add(eachScore);
		}
		resultMap.put("classTopScore",classTopScore);
		resultMap.put("resultScoreList",resultScoreList);
		resultMap.put("schoolStuNum",scoreList.size());
		resultMap.put("classStuNum",classList.size());
		return AsyncResponseData.getSuccess(resultMap);
	}
	
	//TODO 优化
	@RequestMapping(value = "/getClassRankingReport", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClassRankingReport(Score score) throws Exception{

		Map<String,Object> resultMap = new HashMap<>();

		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}

		if (StringUtils.isBlank(score.getExamId()) || StringUtils.isBlank(score.getClassroomId())){
			return AsyncResponseData.getSuccess().asParamError("考试ID或班级ID传参不能为空");
		}
		Exam exam = examService.getExamById(score.getExamId());

		if (exam == null){
			return AsyncResponseData.getSuccess().asUnknownError("查询不到最近一次考试");
		}

		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("examId",exam.getId());
		filter.put("orgId",user.getOrgId());
		filter.put("studentId1","studentId1");
		filter.put("studentName1","studentName");
		filter.put("studentNo1","studentNo");
		filter.put("classroomId1", "classroomId1");
		filter.put("sort","total_score");
		filter.put("sortType","desc");
		//TODO1 优化
		List<ScoreVO> scoreVOList = getExamScoreVO(exam.getId());
		for (ScoreVO scoreVO : scoreVOList) {
			filter.put(subjectMap.get(scoreVO.getSubject())+"1", "subject");
		}
		List<Score> scoreList = scoreService.queryScoresFilterNewOne(filter);

		if (scoreList==null || scoreList.isEmpty()){
			return AsyncResponseData.getSuccess("没有最近一次考试的相关数据");
		}

		List<Score> classScoreList = new ArrayList<>();
		List<BigDecimal> schoolScoreNums = new ArrayList<>();
		List<BigDecimal> classScoreNums = new ArrayList<>();
		Score score1;
		for (Score eachScore : scoreList){
			BigDecimal totalScore = getSubjectScore(eachScore,ScoreConstant.TOTAL_SCORE);
			schoolScoreNums.add(totalScore);
			score1 = new Score();
			if (score.getClassroomId().equals(eachScore.getClassroomId())){
				classScoreNums.add(totalScore);
				score1 = eachScore;
				score1.setClassRanking(classScoreNums.indexOf(totalScore)+1);
				score1.setRanking(schoolScoreNums.indexOf(totalScore)+1);
				classScoreList.add(score1);
			}
		}

		//获取当前考试的上一场考试对象
		filter.clear();
		filter.put("examStatus",2);
		filter.put("orgId", UserUtils.getLoginUserOrgId());
		filter.put("sort","exam_end_date");
		filter.put("sortType","desc");
		//filter.put("type",1);
		//TODO1 优化
		List<Exam> exams = examService.getExamListForTab(filter);
		if (exams==null || exams.isEmpty()){
			return AsyncResponseData.getSystemError("数据错误");
		}

		if (1 == exams.size()){
			for (Score classScore : classScoreList){
				classScore.setChangedRanking(0);
			}
			resultMap.put("classScoreList",classScoreList);
			return AsyncResponseData.getSuccess(resultMap);
		}
		Exam compareExam = new Exam();
		int index = 0;
		for (Exam exam1 : exams){
			if (exam.getId().equals(exam1.getId())){
				index = exams.indexOf(exam1)+1;
				break;
			}
		}
		if (index>0 && index<=exams.size()-1){
			compareExam = exams.get(index);
		}else {
			{
				for (Score classScore : classScoreList){
					classScore.setChangedRanking(0);
				}
				resultMap.put("classScoreList",classScoreList);
				return AsyncResponseData.getSuccess(resultMap);
			}
		}

		if (compareExam!=null){
			filter.clear();
			filter.put("examId",compareExam.getId());
			filter.put("orgId",user.getOrgId());
			filter.put("sort","total_score");
			filter.put("sortType","desc");
			//TODO1 优化 有歧义
			List<Score> compareExamScoreList =  scoreService.queryScoresFilterNew(filter);

			if (compareExamScoreList!=null && !compareExamScoreList.isEmpty()){
				List<BigDecimal> totalScoreList = new ArrayList<>();
				for (Score compareScore : compareExamScoreList){
					totalScoreList.add(getSubjectScore(compareScore,ScoreConstant.TOTAL_SCORE));
				}
				Map<String,Integer> compareExamScoreMap = new HashMap<>();
				//获取该班级上次考试的班级名次
				for (Score compareExamScore : compareExamScoreList){
					compareExamScoreMap.put(compareExamScore.getStudentId(),totalScoreList.indexOf(compareExamScore.getTotalScore())+1);
				}
				for (Score classScore : classScoreList){
					if (compareExamScoreMap.get(classScore.getStudentId())!=null){
						//比较两次考试的名次
						if (classScore.getRanking()>compareExamScoreMap.get(classScore.getStudentId())){
							classScore.setIsImproved(2);
							//进退步名次 数值
							classScore.setChangedRanking(Math.abs(classScore.getRanking()-compareExamScoreMap.get(classScore.getStudentId())));

						}else if (classScore.getRanking()<compareExamScoreMap.get(classScore.getStudentId())){
							classScore.setIsImproved(1);
							//进退步名次 数值
							classScore.setChangedRanking(Math.abs(classScore.getRanking()-compareExamScoreMap.get(classScore.getStudentId())));

						}else {
							//classScore.setIsImproved(0);
							classScore.setChangedRanking(0);
						}
					}else {
						classScore.setChangedRanking(0);
					}

				}
			}
		}
		resultMap.put("classScoreList",classScoreList);

		return AsyncResponseData.getSuccess(resultMap);
	}

	public SummaryVO getSubjectBalanceSummary(String tab, String subject, String range, List<BigDecimal> standardScoreList, List<String> subjectList, List<BigDecimal> avgScoreList, List<BigDecimal> stuScoreList){
		String summaryST = "";
		String summaryPR = "";
		SummaryVO summaryVO = new SummaryVO();
		if (ScoreConstant.TOTAL_SCORE.equals(subject)) {
			String ofRange = "";
			String consSTAbove = "";
			String consSTBelow = "";
			String consPRAbove = "";
			String consPRBelow = "";

			if (ScoreConstant.STUDENT_REPORT.equals(tab)) {
				consSTAbove = ScoreConstant.STU_STANDARD_ABOVE;
				consSTBelow = ScoreConstant.STU_STANDARD_BELOW;
				if (ScoreConstant.CLASS_LEVEL.equals(range)) {
					ofRange = ScoreConstant.CLASS_RANGE;
					consPRAbove = ScoreConstant.STU_PR_CLASS_ABOVE;
					consPRBelow = ScoreConstant.STU_PR_CLASS_BELOW;
				} else if (ScoreConstant.SCHOOL_LEVEL.equals(range)) {
					ofRange = ScoreConstant.SCHOOL_RANGE;
					consPRAbove = ScoreConstant.STU_PR_SCHOOL_ABOVE;
					consPRBelow = ScoreConstant.STU_PR_SCHOOL_BELOW;
				} else {
					ofRange = ScoreConstant.REGION_RANGE;
					consPRAbove = ScoreConstant.STU_PR_REGION_ABOVE;
					consPRBelow = ScoreConstant.STU_PR_REGION_BELOW;
				}
			} else {
				consSTAbove = ScoreConstant.CLASS_STANDARD_ABOVE;
				consSTBelow = ScoreConstant.CLASS_STANDARD_BELOW;
				consPRAbove = ScoreConstant.CLASS_PR_SCHOOL_ABOVE;
				consPRBelow = ScoreConstant.CLASS_PR_SCHOOL_BELOW;
			}

			if (subjectList.size() <= 2) {
				//一门学科不显示高于低于标准分这句话
				summaryVO.setStuSubjBalanceST(" ");
			} else {

				List<SubjectScore> subjectAboveArray = new ArrayList<>();
				List<SubjectScore> subjectBelowArray = new ArrayList<>();

				SubjectScore subjScore;
				for (String eachSubject : subjectList) {
					BigDecimal stScore = standardScoreList.get(subjectList.indexOf(eachSubject));
					if (ScoreConstant.TOTAL_SCORE.equals(eachSubject)) {
						continue;
					}
					if (stScore.compareTo(standardScoreList.get(0)) > 0) {
						subjScore = new SubjectScore();
						subjScore.setSubject(eachSubject);
						subjScore.setScore(stScore);
						subjectAboveArray.add(subjScore);
					}
					if (stScore.compareTo(standardScoreList.get(0)) < 0) {
						subjScore = new SubjectScore();
						subjScore.setSubject(eachSubject);
						subjScore.setScore(stScore);
						subjectBelowArray.add(subjScore);
					}
				}

				if (subjectAboveArray.size() > 0 || subjectBelowArray.size() > 0) {
					summaryST += ScoreConstant.SUMMARY;
					summaryST += ofRange;

					List<BigDecimal> tmpSTScore = new ArrayList<>();
					tmpSTScore.addAll(standardScoreList);
					Collections.sort(tmpSTScore);

					String subjectsSTAbove = "";
					String subjectsSTBelow = "";

					// 2-4门学科，高于、低于标准分 取前一名学科、后一名学科
					if (subjectList.size() >= 3 && subjectList.size() <= 5) {
						for (SubjectScore sso : subjectAboveArray){
							if (tmpSTScore.size()-1 == tmpSTScore.indexOf(sso.getScore())){
								if (StringUtils.isNotBlank(subjectsSTAbove)){
									subjectsSTAbove += "、";
								}
								subjectsSTAbove += sso.getSubject();
							}
						}
						for (SubjectScore sso : subjectBelowArray){
							if (0 == tmpSTScore.indexOf(sso.getScore())){
								if (StringUtils.isNotBlank(subjectsSTBelow)){
									subjectsSTBelow += "、";
								}
								subjectsSTBelow += sso.getSubject();
							}
						}
					}

					// 5-7门学科，高于、低于标准分 取前两名学科、后两名学科
					if (subjectList.size() >= 6 && subjectList.size() <= 8) {
						for (SubjectScore sso : subjectAboveArray){
							if (tmpSTScore.size()-2 <= tmpSTScore.indexOf(sso.getScore())){
								if (StringUtils.isNotBlank(subjectsSTAbove)){
									subjectsSTAbove += "、";
								}
								subjectsSTAbove += sso.getSubject();
							}
						}
						for (SubjectScore sso : subjectBelowArray){
							if (1 >= tmpSTScore.indexOf(sso.getScore())){
								if (StringUtils.isNotBlank(subjectsSTBelow)){
									subjectsSTBelow += "、";
								}
								subjectsSTBelow += sso.getSubject();
							}
						}
					}
					// 大于等于8门学科，高于、低于标准分 取前三名学科、后三名学科
					if (subjectList.size() >= 9) {
						for (SubjectScore sso : subjectAboveArray){
							if (tmpSTScore.size()-3 <= tmpSTScore.indexOf(sso.getScore())){
								if (StringUtils.isNotBlank(subjectsSTAbove)){
									subjectsSTAbove += "、";
								}
								subjectsSTAbove += sso.getSubject();
							}
						}
						for (SubjectScore sso : subjectBelowArray){
							if (2 >= tmpSTScore.indexOf(sso.getScore())){
								if (StringUtils.isNotBlank(subjectsSTBelow)){
									subjectsSTBelow += "、";
								}
								subjectsSTBelow += sso.getSubject();
							}
						}
					}

					if (StringUtils.isNotBlank(subjectsSTAbove)){
						summaryST += subjectsSTAbove + consSTAbove;
					}
					if (StringUtils.isNotBlank(subjectsSTBelow)){
						if (StringUtils.isNotBlank(subjectsSTAbove)){
							summaryST += "；";
						}
						summaryST += subjectsSTBelow + consSTBelow;
					}
				}

			}


			String subjectsPRAbove = "";
			String subjectsPRBelow = "";
			for (String eachSubject : subjectList){
				int index = subjectList.indexOf(eachSubject);
				if (avgScoreList.get(index).compareTo(stuScoreList.get(index)) == 0){
					continue;
				}

				if (avgScoreList.get(index).compareTo(stuScoreList.get(index)) < 0){
					if (StringUtils.isNotBlank(subjectsPRAbove)){
						subjectsPRAbove += "、";
					}
					subjectsPRAbove += eachSubject;
				}
				if (avgScoreList.get(index).compareTo(stuScoreList.get(index)) > 0){
					if (StringUtils.isNotBlank(subjectsPRBelow)){
						subjectsPRBelow += "、";
					}
					subjectsPRBelow += eachSubject;
				}
			}

			if (StringUtils.isNotBlank(subjectsPRAbove)){
				summaryPR += ScoreConstant.SUMMARY + ofRange;
				summaryPR += subjectsPRAbove + consPRAbove;
			}
			if (StringUtils.isNotBlank(subjectsPRBelow)){
				if (StringUtils.isNotBlank(subjectsPRAbove)){
					summaryPR += "；";
				}
				summaryPR += ScoreConstant.SUMMARY + ofRange;
				summaryPR += subjectsPRBelow + consPRBelow;
			}

		}

		summaryVO.setStuSubjBalanceST(summaryST);
		summaryVO.setStuSubjBalancePR(summaryPR);

		return summaryVO;
	}

	//List集合中任意两个数据互相调换位置 ［注意］：index1 < index2
	public List<Object> changeLocation(List<Object> dataList, int index1, int index2){
		dataList.add(index1, dataList.get(index2));
		dataList.add(index2+1, dataList.get(index1+1));
		dataList.remove(index1+1);
		dataList.remove(index2+1);
		return dataList;
	}


	public void getSubjectScore(ScoreVO scoreVO, Object object, String range){
		String subject = subjectMap.get(scoreVO.getSubject());
		if (StringUtils.isNotBlank(subject)) {
			try {
				String methodName = "";
				if (object instanceof Score){
					Class<?> clz = Class.forName("com.jy.moudles.score.entity.Score");
					methodName = "get" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
					Method method = clz.getMethod(methodName);
					scoreVO.setScore((BigDecimal) method.invoke(object));
				}else if (object instanceof ProcessedScores){
					if (StringUtils.isNotBlank(range)){
						Class<?> clz = Class.forName("com.jy.moudles.score.entity.ProcessedScores");
						if (ScoreConstant.REGION_LEVEL.equals(range)){
							methodName = "getTop" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
							Method method = clz.getMethod(methodName);
							scoreVO.setRegionTopScore((BigDecimal) method.invoke(object));
							methodName = "getAvg" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
							method = clz.getMethod(methodName);
							scoreVO.setRegionAvgScore((BigDecimal) method.invoke(object));
							methodName = "getMin" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
							method = clz.getMethod(methodName);
							scoreVO.setRegionMinScore((BigDecimal) method.invoke(object));
						}else if (ScoreConstant.SCHOOL_LEVEL.equals(range)){
							methodName = "getTop" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
							Method method = clz.getMethod(methodName);
							scoreVO.setSchoolTopScore((BigDecimal) method.invoke(object));
							methodName = "getAvg" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
							method = clz.getMethod(methodName);
							scoreVO.setSchoolAvgScore((BigDecimal) method.invoke(object));
							methodName = "getMin" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
							method = clz.getMethod(methodName);
							scoreVO.setSchoolMinScore((BigDecimal) method.invoke(object));
						}else if (ScoreConstant.CLASS_LEVEL.equals(range)){
							methodName = "getTop" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
							Method method = clz.getMethod(methodName);
							scoreVO.setClassTopScore((BigDecimal) method.invoke(object));
							methodName = "getAvg" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
							method = clz.getMethod(methodName);
							scoreVO.setClassAvgScore((BigDecimal) method.invoke(object));
							methodName = "getMin" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
							method = clz.getMethod(methodName);
							scoreVO.setClassMinScore((BigDecimal) method.invoke(object));
						}
					}

				}


			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

	public BigDecimal getProcessedScore(ProcessedScores processedScores, String type, String subject){
		subject = subjectMap.get(subject);
		if (StringUtils.isNotBlank(subject)) {
			try {
				String methodName = "";
				Method method;
				if (StringUtils.isNotBlank(type)){
					Class<?> clz = Class.forName("com.jy.moudles.score.entity.ProcessedScores");
					if ("TOP".equals(type)){//regionTopScore
						methodName = "getTop" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
					}
					if ("AVG".equals(type)){//regionAvgScore
						methodName = "getAvg" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
					}
					if ("MIN".equals(type)){//regionMinScore
						methodName = "getMin" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
					}
					method = clz.getMethod(methodName);
					return (BigDecimal) method.invoke(processedScores);
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return null;
	}
	
	/**
	 * 单科成绩
	 * @param score
	 * @param subject
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月15日 下午5:08:25
	 */
	public BigDecimal getSubjectScore(Score score,String subject){
		subject = subjectMap.get(subject);
		if (StringUtils.isNotBlank(subject)) {
			try {
				String methodName = "";

				Class<?> clz = Class.forName("com.jy.moudles.score.entity.Score");
				methodName = "get" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
				Method method = clz.getMethod(methodName);
				return (BigDecimal) method.invoke(score);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return null;
	}

	public List<BigDecimal> getSubjectScoreList(List<Score> scoreList,String subject){
		List<BigDecimal> subjectScoreList = new ArrayList<>();
		subject = subjectMap.get(subject);
		if (StringUtils.isNotBlank(subject)) {
			try {
				for (Score score : scoreList){
					Class<?> clz = Class.forName("com.jy.moudles.score.entity.Score");
					String methodName = "";
					methodName = "get" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
					Method method = clz.getMethod(methodName);
					subjectScoreList.add((BigDecimal) method.invoke(score));
				}
				return subjectScoreList;

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return null;
	}

	/**
	 * 标准分
	 * @param scoreList：学生成绩列表
	 * @param subject：科目
	 * @param avgNum：平均分
	 */
	public BigDecimal getStandardTScore(List<Score> scoreList,String subject,BigDecimal avgNum){
		List<BigDecimal> subjectScoreList = getSubjectScoreList(scoreList,subject);
		return EduEvaluatingUtils.getStandardT(subjectScoreList,avgNum);
	}

	/**
	 * 标准分 ： 求总分以及各科目标准分
	 * 对于班级：scoreList为学校成绩列表, x 为班级平均分
	 * 对于学生：scoreList为班级成绩列表, x 为学生成绩
	 * @param scoreVOList:科目列表
	 * @param scoreList：成绩列表
	 */
	public void getStandardTScore(List<ScoreVO> scoreVOList, List<Score> scoreList, String range){

		List<BigDecimal> subjectScoreList = new ArrayList<>();
		try {
			for (ScoreVO scoreVO : scoreVOList){
				String subject = subjectMap.get(scoreVO.getSubject());
				for (Score score : scoreList){
					Class<?> clz = Class.forName("com.jy.moudles.score.entity.Score");
					String methodName = "";
					methodName = "get" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
					Method method = clz.getMethod(methodName);
					subjectScoreList.add((BigDecimal) method.invoke(score));
				}
				if (ScoreConstant.CLASS_LEVEL.equals(range)){
					scoreVO.setClassSubjectStandardScore(EduEvaluatingUtils.getStandardT(subjectScoreList,scoreVO.getClassAvgScore()));
				}
				if (ScoreConstant.SINGLE_STUDENT.equals(range)){
					scoreVO.setClassSubjectStandardScore(EduEvaluatingUtils.getStandardT(subjectScoreList,scoreVO.getScore()));
				}
				subjectScoreList.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void getSubjectScoreRate(List<ScoreVO> scoreVOList, List<Score> scoreList){

		List<BigDecimal> subjectScoreList = new ArrayList<>();
		try {
			for (ScoreVO scoreVO : scoreVOList){
				String subject = subjectMap.get(scoreVO.getSubject());
				for (Score score : scoreList){
					Class<?> clz = Class.forName("com.jy.moudles.score.entity.Score");
					String methodName = "";
					methodName = "get" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
					Method method = clz.getMethod(methodName);
					subjectScoreList.add((BigDecimal) method.invoke(score));
				}
				if (subjectScoreList.size()>0){
					Map<String,BigDecimal> rateMap = EduEvaluatingUtils.getRatio(subjectScoreList,BigDecimal.valueOf(scoreVO.getFullMarks()));
					scoreVO.setHighRate(rateMap.get("highRate"));
					scoreVO.setExcellentRate(rateMap.get("excellentRate"));
					scoreVO.setCommissionRate(rateMap.get("commissionRate"));
					scoreVO.setPassRate(rateMap.get("passRate"));
					scoreVO.setFailureRate(rateMap.get("failureRate"));
				}else {
					scoreVO.setHighRate(BigDecimal.ZERO);
					scoreVO.setExcellentRate(BigDecimal.ZERO);
					scoreVO.setCommissionRate(BigDecimal.ZERO);
					scoreVO.setPassRate(BigDecimal.ZERO);
					scoreVO.setFailureRate(BigDecimal.ZERO);
				}
				subjectScoreList.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void getSubjectScoreRate(ScoreVO scoreVO, List<Score> scoreList){

		List<BigDecimal> subjectScoreList = new ArrayList<>();
		try {
				String subject = subjectMap.get(scoreVO.getSubject());
				for (Score score : scoreList){
					Class<?> clz = Class.forName("com.jy.moudles.score.entity.Score");
					String methodName = "";
					methodName = "get" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
					Method method = clz.getMethod(methodName);
					subjectScoreList.add((BigDecimal) method.invoke(score));
				}
				if (subjectScoreList.size()>0){
					Map<String,BigDecimal> rateMap = EduEvaluatingUtils.getRatio(subjectScoreList,BigDecimal.valueOf(scoreVO.getFullMarks()));
					scoreVO.setHighRate(rateMap.get("highRate"));
					scoreVO.setExcellentRate(rateMap.get("excellentRate"));
					scoreVO.setCommissionRate(rateMap.get("commissionRate"));
					scoreVO.setPassRate(rateMap.get("passRate"));
					scoreVO.setFailureRate(rateMap.get("failureRate"));
				}else {
					scoreVO.setHighRate(BigDecimal.ZERO);
					scoreVO.setExcellentRate(BigDecimal.ZERO);
					scoreVO.setCommissionRate(BigDecimal.ZERO);
					scoreVO.setPassRate(BigDecimal.ZERO);
					scoreVO.setFailureRate(BigDecimal.ZERO);
				}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	//TODO1 优化
	public List<ScoreVO> getExamScoreVO(String examId){

		List<ScoreVO> scoreVOList = scoreService.getExamFullMarks(examId);

		if (scoreVOList != null && !scoreVOList.isEmpty()){
			int totalScore = 0;
			for (ScoreVO eachScoreVO : scoreVOList){
				totalScore += eachScoreVO.getFullMarks();
			}

			ScoreVO scoreVO = new ScoreVO();
			scoreVO.setSubject(ScoreConstant.TOTAL_SCORE);
			scoreVO.setFullMarks(totalScore);
			scoreVOList.add(0,scoreVO);
		}

		return scoreVOList;
	}
	
}
