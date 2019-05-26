package com.jy.moudles.score.controller;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.auth.UserUtils;
import com.jy.common.utils.mathtools.EduEvaluatingUtils;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;
import com.jy.moudles.knowledgePoint.entity.KnowledgePoint;
import com.jy.moudles.knowledgePoint.service.KnowledgePointService;
import com.jy.moudles.role.constant.RoleConstant;
import com.jy.moudles.score.constant.ScoreConstant;
import com.jy.moudles.score.constant.SubjectConstant;
import com.jy.moudles.score.entity.Score;
import com.jy.moudles.score.entityVO.AbilityVO;
import com.jy.moudles.score.entityVO.CountKnowVO;
import com.jy.moudles.score.entityVO.DetailVO;
import com.jy.moudles.score.entityVO.KnowDetailVO;
import com.jy.moudles.score.entityVO.KnowledgeAnalysisVO;
import com.jy.moudles.score.entityVO.ScoreDetailVO;
import com.jy.moudles.score.entityVO.SummaryVO;
import com.jy.moudles.score.entityVO.TargetVO;
import com.jy.moudles.score.entityVO.TestAnalysisVO;
import com.jy.moudles.score.service.ScoreService;
import com.jy.moudles.spDetailStep.entity.SpDetailStep;
import com.jy.moudles.subjectAblity.entity.SubjectAblity;
import com.jy.moudles.subjectAblity.service.SubjectAblityService;
import com.jy.moudles.twoWaySpecification.entity.TwoWaySpecification;
import com.jy.moudles.twoWaySpecification.service.TwoWaySpecificationService;
import com.jy.moudles.twoWaySpecificationDetail.entity.TwoWaySpecificationDetail;
import com.jy.moudles.twoWaySpecificationDetail.service.TwoWaySpecificationDetailService;
import com.jy.moudles.user.entity.User;

@Controller
public class TestAnalysisController {
	
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private TwoWaySpecificationService twoWaySpecificationService;
	@Autowired
	private TwoWaySpecificationDetailService twoWaySpecificationDetailService;
	@Autowired
	private KnowledgePointService knowledgePointService;
	@Autowired
	private ExamService examService;
	@Autowired
	private SubjectAblityService subjectablityService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestAnalysisController.class);
	
	/**
	 * 试卷分析
	 *
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/testAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData testAnalysis(String subject,String examId,@RequestParam(required = false)String classroomId,
			@RequestParam(required = false)String studentId) throws Exception{
		LOGGER.info("获取testAnalysis Start");
		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		String orgId = user.getOrgId();

		String tab = "";
		if(StringUtils.isBlank(studentId)) {
			if(RoleConstant.STUDENT_ROLE.equals(user.getRoleName())){
				studentId = user.getSourceId();
				tab = ScoreConstant.STUDENT_REPORT;
			}
		}
		
		if(StringUtils.isBlank(examId)) {
			return AsyncResponseData.getSuccess().asParamError("考试参数为空");
		}
		
		if(StringUtils.isBlank(subject)) {
			return AsyncResponseData.getSuccess().asParamError("科目为空");
		}
		
		//返回值
		List<TestAnalysisVO> listVO = new ArrayList<>();
		
		//学生信息
		List<DetailVO> detailList = new ArrayList<>();
		if(StringUtils.isNotBlank(studentId)) {
			Map<String,Object> filter = new HashMap<>();
			filter.put("orgId", orgId);
			filter.put("examId", examId);
			filter.put("studentId", studentId);
			filter.put("classroomId1", "classroomId1");
			filter.put("detailScore1", "detailScore1");
			List<Score> listStudent = scoreService.queryScoresFilterNewTwo(filter);
			if(null != listStudent && listStudent.size() > 0) {
				Score score = listStudent.get(0);
				classroomId = score.getClassroomId();
				if(StringUtils.isBlank(score.getDetailScore())) {
					return AsyncResponseData.getSuccess().asParamError("该学生成绩未录入");
				}
				List<ScoreDetailVO> listDetailss = JSON.parseArray(score.getDetailScore(), ScoreDetailVO.class);
				for(ScoreDetailVO es : listDetailss) {
					if(subject.equals(es.getSubject())) {
						detailList = es.getDetailList();
						break;
					}
				}
			}
			
		}
		
		Map<String,Object> filterSchool = new HashMap<>();
		if(StringUtils.isNotBlank(orgId)) {
			filterSchool.put("orgId", orgId);
		}
		
		filterSchool.put("examId", examId);
		Map<String,Object> dbMap = SubjectConstant.subjectDbMap;
		String soft = (String) dbMap.get(subject);
		filterSchool.put("sort", soft);
		filterSchool.put("sortType", "DESC");
		filterSchool.put("detailScore1", "detailScore1");
		filterSchool.put("studentId1", "studentId1");
		
		Map<String,Object> filterClass = new HashMap<>();
		if(StringUtils.isNotBlank(classroomId)) {
			filterClass.put("classroomId", classroomId);
			tab = ScoreConstant.CLASS_REPORT;
		}
		
		filterClass.put("orgId", orgId);
		filterClass.put("examId", examId);
		filterClass.put("detailScore1", "detailScore1");
		filterClass.put("studentId1", "studentId1");
		List<Score> listScSchool = scoreService.queryScoresFilterNewTwo(filterSchool);
		List<Score> listScClass = scoreService.queryScoresFilterNewTwo(filterClass);
		List<ScoreDetailVO> listDetailSchool = getlist(listScSchool, subject);
		List<ScoreDetailVO> listDetailClass = getlist(listScClass, subject);
		
		TargetVO targetVO = new TargetVO();
		List<Integer> esayTotal = new ArrayList<>();
		List<Integer> secondaryTotal = new ArrayList<>();
		List<Integer> difficultTotal = new ArrayList<>();
		
		//找出高分组 低分组
		List<String> highArray = new ArrayList<>();
		List<String> lowArray = new ArrayList<>();
		if(null != listScSchool && listScSchool.size() > 0) {
			Long sum = Math.round(listScSchool.size() * 0.27);
			int size = sum.intValue();
			List<Score> highPart = listScSchool.subList(0, size);
			List<Score> lowPart = listScSchool.subList((listScSchool.size() - size), listScSchool.size());
			for(Score e : lowPart) {
				lowArray.add(e.getStudentId());
			}
			for(Score e : highPart) {
				highArray.add(e.getStudentId());
			}
		}
		
		
		//取出双向细目表
		Map<String,Object> filterTwo = new HashMap<>();
		filterTwo.put("subjectCode", subject);
		filterTwo.put("examId", examId);
		List<TwoWaySpecification> listTwo = twoWaySpecificationService.queryTwoWaySpecificationsByExamIdAndSubject(filterTwo);
		
		filterTwo.clear();
		filterTwo.put("orgId", orgId);
		filterTwo.put("subjectName", subject);
		List<SubjectAblity> subjectablitys = subjectablityService.querySubjectAblitysFilter(filterTwo);
		
		if (subjectablitys.size() < 1) {
			return AsyncResponseData.getSuccess().asParamError("没有查到该科目对应的能力值");
		}
		
		List<String> ablityArray = new ArrayList<>();
		if(null != subjectablitys) {
			for(SubjectAblity e : subjectablitys) {
				ablityArray.add(e.getAblityName());
			}
		}
		
		if(null != listTwo && listTwo.size() > 0) {
			TwoWaySpecification twoWaySpecification = listTwo.get(0);
			if(null != twoWaySpecification) {
				String pid = twoWaySpecification.getId();
				Map<String,Object> filterTwoDe = new HashMap<>();
				filterTwoDe.put("parentId", pid);
				List<TwoWaySpecificationDetail> listDetails =  twoWaySpecificationDetailService.queryTwoWaySpecificationDetailsFilter(filterTwoDe);
				if(null != listDetails && listDetails.size() > 0) {
					
					//处理成绩
					for(TwoWaySpecificationDetail e : listDetails) {
						TestAnalysisVO testAnalysisVO = new TestAnalysisVO();
						//题号
						testAnalysisVO.setQid(e.getItemNo()+"");
						//题型
						testAnalysisVO.setTopic(e.getItemType() == 0?"客观题":"主观题");
						//分值
						testAnalysisVO.setFractionalValue(e.getItemScore());
						
						//校级平均分
						BigDecimal divideSchool = new BigDecimal(0);
						List<BigDecimal> listSchool = new ArrayList<>();
						
						//区分度参数
						BigDecimal highTotal = BigDecimal.ZERO;
						BigDecimal highGet = BigDecimal.ZERO;
						BigDecimal lowTotal = BigDecimal.ZERO;
						BigDecimal lowGet = BigDecimal.ZERO;
						
						//错题人数
						int falseNumSchool = 0;
						//满分人数
						int trueNumSchool = 0;
						for(ScoreDetailVO ee : listDetailSchool) {
							for(DetailVO ed : ee.getDetailList()) {
								if(ed.getItemNo().equals(e.getItemNo()+"")) {
									listSchool.add(ed.getScore());
									if(e.getItemScore() != ed.getScore().intValue()) {
										falseNumSchool++;
									}else {
										trueNumSchool++;
									}
									//低分组得分 总分
									if(lowArray.contains(ee.getStuId())) {
										lowGet = lowGet.add(ed.getScore());
										lowTotal = lowTotal.add(ed.getTotalScore());
									}
									//高分组得分 总分
									if(highArray.contains(ee.getStuId())) {
										highGet = highGet.add(ed.getScore());
										highTotal = highTotal.add(ed.getTotalScore());
									}
								}
							}
						}
						
						//区分度
						BigDecimal highDivide = BigDecimal.ZERO;
						BigDecimal lowDivide = BigDecimal.ZERO;
						BigDecimal differentiation = BigDecimal.ZERO;
						//高分组得分率
						if(BigDecimal.ZERO.compareTo(highTotal) == 0) {
							highTotal = BigDecimal.ONE;
						}
						highDivide = highGet.divide(highTotal,6, RoundingMode.HALF_UP);
						//低分组得分率
						if(BigDecimal.ZERO.compareTo(lowTotal) == 0) {
							lowTotal = BigDecimal.ONE;
						}
						lowDivide = lowGet.divide(lowTotal,6, RoundingMode.HALF_UP);
						differentiation = highDivide.subtract(lowDivide);
						differentiation = differentiation.setScale(2, BigDecimal.ROUND_HALF_UP);
						testAnalysisVO.setDifferentiation(differentiation);
						
						//班级平均分
						BigDecimal divideClass = new BigDecimal(0);
						List<BigDecimal> listClass = new ArrayList<>();
						//错题人数
						int falseNumClass = 0;
						//满分人数
						int trueNumClass = 0;
						for(ScoreDetailVO ee : listDetailClass) {
							for(DetailVO ed : ee.getDetailList()) {
								if(ed.getItemNo().equals(e.getItemNo()+"")) {
									listClass.add(ed.getScore());
									if(e.getItemScore() != ed.getScore().intValue()) {
										falseNumClass++;
									}else {
										trueNumClass++;
									}
								}
							}
						}
						divideSchool = EduEvaluatingUtils.getAverage(listSchool);
						divideClass = EduEvaluatingUtils.getAverage(listClass);
						if(StringUtils.isNotBlank(classroomId)) {
							testAnalysisVO.setDivide(divideClass);
							testAnalysisVO.setFalseNum(falseNumClass);
							testAnalysisVO.setTrueNum(trueNumClass);
						}else {
							testAnalysisVO.setDivide(divideSchool);
							testAnalysisVO.setFalseNum(falseNumSchool);
							testAnalysisVO.setTrueNum(trueNumSchool);
						}
						
						BigDecimal itemValue = new BigDecimal(e.getItemScore());
						//班级得分率
						BigDecimal divideClassString = divideClass.divide(itemValue,4, BigDecimal.ROUND_HALF_UP);
						divideClassString = divideClassString.multiply(new BigDecimal(100)).setScale(2);
						testAnalysisVO.setDivideClass(divideClassString.toString() + "%");
						
						//校级得分率
						BigDecimal divideSchoolString = divideSchool.divide(itemValue,4, BigDecimal.ROUND_HALF_UP);
						divideSchoolString = divideSchoolString.multiply(new BigDecimal(100)).setScale(2);
						testAnalysisVO.setDivideSchool(divideSchoolString.toString() + "%");
						
						//区县得分率
						testAnalysisVO.setDivideAera(testAnalysisVO.getDivideSchool());
						
						//难度
						BigDecimal df = divideSchoolString.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
						testAnalysisVO.setDifficulty(df);
						
						
						//难度为0  改为0.1
						BigDecimal dff = testAnalysisVO.getDifficulty();
						if(dff.compareTo(BigDecimal.ZERO) == 0) {
							testAnalysisVO.setDifficulty(new BigDecimal(0.1));
						}
						
						//难度区分
						float ft = df.floatValue();
						if(ft >= 0.7 && ft <= 1) {
							esayTotal.add(e.getItemNo());
						}
						if(ft > 0.4 && ft < 0.7) {
							secondaryTotal.add(e.getItemNo());
						}
						if(ft > 0 && ft <= 0.4) {
							difficultTotal.add(e.getItemNo());
						}
						
						//区分度
//						BigDecimal differentiation = new BigDecimal(0);
//						differentiation = EduEvaluatingUtils.getDegree(listSchool);
//						testAnalysisVO.setDifferentiation(differentiation.divide(new BigDecimal(100)));
						
						
						
						//知识点
						testAnalysisVO.setKnowPoint(e.getKnowledgeName());
						//能力值
						String ablityString = e.getItemAbility();
						List<String> ablity = new ArrayList<>();
						if(StringUtils.isNotBlank(ablityString)) {
							char[] ablityChar = ablityString.toCharArray();
							for(int i = 0;i < ablityChar.length ;i++) {
								if(ablityChar[i] == '1') {
									ablity.add(ablityArray.get(i));
								}
							}
						}
						testAnalysisVO.setAblity(StringUtils.join(ablity,","));
						
						//学生信息
						if(StringUtils.isNotBlank(studentId) && null != detailList && detailList.size() >0) {
							for(DetailVO ed : detailList) {
								if(ed.getItemNo().equals(e.getItemNo()+"")) {
									BigDecimal score = ed.getScore();
									BigDecimal scoreT = ed.getTotalScore();
									BigDecimal divideStudent = score.divide(scoreT,4, BigDecimal.ROUND_HALF_UP);
									divideStudent = divideStudent.multiply(new BigDecimal(100)).setScale(2);
									BigDecimal divideStudentToClass = divideStudent.subtract(divideClassString);
									BigDecimal divideStudentToSchool = divideStudent.subtract(divideSchoolString);
									
									testAnalysisVO.setDivideStudent(divideStudent.toString() + "%");
									testAnalysisVO.setDivideStudentToClass(divideStudentToClass.toString() + "%");
									testAnalysisVO.setDivideStudentToSchool(divideStudentToSchool.toString() + "%");
									testAnalysisVO.setDivideStudentToArea(divideStudentToSchool.toString() + "%");
									
									testAnalysisVO.setStudentScore(score);
									if(scoreT.subtract(score) == BigDecimal.ZERO) {
										testAnalysisVO.setRightOrNot("1");
									}
								}
							}
						}
						listVO.add(testAnalysisVO);
					}
					
				}else{
					return AsyncResponseData.getSuccess().asParamError("未查询到双向细目表");
				}
				
			}else {
				return AsyncResponseData.getSuccess().asParamError("未查询到双向细目表");
			}
			
		
		}else {
			return AsyncResponseData.getSuccess().asParamError("未查询到双向细目表"); 
		}
		
		//学生信息
		if(StringUtils.isNotBlank(studentId) && null != detailList && detailList.size() >0) {
			targetVO.setEasyNum(esayTotal.size());
			targetVO.setSecondaryNum(secondaryTotal.size());
			targetVO.setDifficultNum(difficultTotal.size());
			
			BigDecimal easyGet = BigDecimal.ZERO;
			BigDecimal secondaryGet = BigDecimal.ZERO;
			BigDecimal difficultGet = BigDecimal.ZERO;
			
			BigDecimal easyT = BigDecimal.ZERO;
			BigDecimal secondaryT = BigDecimal.ZERO;
			BigDecimal difficultT = BigDecimal.ZERO;
			
			for(Integer i : esayTotal) {
				for(DetailVO e : detailList) {
					if(e.getItemNo().equals(i+"")) {
						easyGet = easyGet.add(e.getScore());
						easyT = easyT.add(e.getTotalScore());
					}
				}
			}
			
			for(Integer i : secondaryTotal) {
				for(DetailVO e : detailList) {
					if(e.getItemNo().equals(i+"")) {
						secondaryGet = secondaryGet.add(e.getScore());
						secondaryT = secondaryT.add(e.getTotalScore());
					}
				}
			}
			
			for(Integer i : difficultTotal) {
				for(DetailVO e : detailList) {
					if(e.getItemNo().equals(i+"")) {
						difficultGet = difficultGet.add(e.getScore());
						difficultT = difficultT.add(e.getTotalScore());
					}
				}
			}
			
			if(easyT.compareTo(BigDecimal.ZERO) == 0) {
				easyT = BigDecimal.ONE;
			}
			if(secondaryT.compareTo(BigDecimal.ZERO) == 0) {
				secondaryT = BigDecimal.ONE;
			}
			if(difficultT.compareTo(BigDecimal.ZERO) == 0) {
				difficultT = BigDecimal.ONE;
			}

			BigDecimal divideEasy = easyGet.divide(easyT,4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2);
			BigDecimal divideSecondary = secondaryGet.divide(secondaryT,4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2);
			BigDecimal divideDifficult = difficultGet.divide(difficultT,4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2);
			
			targetVO.setDivideEasy(divideEasy.toString());
			targetVO.setDivideSecondary(divideSecondary.toString());
			targetVO.setDivideDifficult(divideDifficult.toString());
			
		}

		SummaryVO summaryVO = getNextTimeSummary(targetVO);
		getDifficultySummary(listVO,tab,summaryVO);
		
		Map<String,Object> map = new HashMap<>();
		map.put("summaryVO",summaryVO);
		map.put("listVO", listVO);
		map.put("targetVO", targetVO);
		LOGGER.info("获取testAnalysis End");
		return AsyncResponseData.getSuccess(map);
	}
	
	/**
	 * 知识点分析
	 *
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/knowAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData studentReport(String subject,String examId,@RequestParam(required = false)String classroomId,
			@RequestParam(required = false)String studentId) throws Exception{
		
		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		String orgId = user.getOrgId();

		String tab = "";
		if(StringUtils.isBlank(studentId)) {
			if(RoleConstant.STUDENT_ROLE.equals(user.getRoleName())){
				studentId = user.getSourceId();
				tab = ScoreConstant.STUDENT_REPORT;
			}
		}
		
		if(StringUtils.isBlank(examId)) {
			return AsyncResponseData.getSuccess().asParamError("考试参数为空");
		}
		if(StringUtils.isBlank(subject)) {
			return AsyncResponseData.getSuccess().asParamError("科目为空");
		}
		
		//返回值
		List<KnowledgeAnalysisVO> listVO = new ArrayList<>();
		SummaryVO summaryVO;
		
		//学生信息
		List<DetailVO> detailList = new ArrayList<>();
		if(StringUtils.isNotBlank(studentId)) {
			Map<String,Object> filter = new HashMap<>();
			filter.put("orgId", orgId);
			filter.put("examId", examId);
			filter.put("studentId", studentId);
			List<Score> listStudent = scoreService.queryScoresFilter(filter);
			if(null != listStudent && listStudent.size() > 0) {
				Score score = listStudent.get(0);
				classroomId = score.getClassroomId();
				if(StringUtils.isBlank(score.getDetailScore())) {
					return AsyncResponseData.getSuccess().asParamError("该学生成绩未录入");
				}
				List<ScoreDetailVO> listDetailss = JSON.parseArray(score.getDetailScore(), ScoreDetailVO.class);
				for(ScoreDetailVO es : listDetailss) {
					if(subject.equals(es.getSubject())) {
						detailList = es.getDetailList();
						break;
					}
				}
			}
			
		}
		
		Map<String,Object> filterSchool = new HashMap<>();
		if(StringUtils.isNotBlank(orgId)) {
			filterSchool.put("orgId", orgId);
		}
		filterSchool.put("examId", examId);
		
		Map<String,Object> filterClass = new HashMap<>();
		if(StringUtils.isNotBlank(classroomId)) {
			filterClass.put("classroomId", classroomId);
			tab = ScoreConstant.CLASS_REPORT;

		}
		filterClass.put("orgId", orgId);
		filterClass.put("examId", examId);
		
		List<Score> listScSchool = scoreService.queryScoresFilter(filterSchool);
		List<Score> listScClass = scoreService.queryScoresFilter(filterClass);
		List<ScoreDetailVO> listDetailSchool = getlist(listScSchool, subject);
		List<ScoreDetailVO> listDetailClass = getlist(listScClass, subject);
		
		//取出年级信息
		Exam exam = examService.getExamById(examId);
		if(null == exam) {
			return AsyncResponseData.getSuccess().asParamError("未查询到考试信息");
		}
		String classroom = exam.getClassroom();
		if(StringUtils.isBlank(classroom)) {
			return AsyncResponseData.getSuccess().asParamError("未查询到考试班级");
		}
		
		//取出知识点
		Map<String,Object> filterPoint = new HashMap<>();
		filterPoint.put("subjectId", subject);
		filterPoint.put("orgId", orgId);
		filterPoint.put("gradeCode", classroom.split("\\(")[0]);
		List<KnowledgePoint> listPoint = knowledgePointService.queryKnowledgePointsFilter(filterPoint);
		if(null != listPoint && listPoint.size() > 0) {
			//处理知识点列表
			Map<String,String> kvMap = new HashMap<>();
			for(KnowledgePoint knowledgePoint : listPoint){
				kvMap.put(knowledgePoint.getId(), knowledgePoint.getKnowledgeContent());
			}
			
			//取出双向细目表
			Map<String,Object> filterTwo = new HashMap<>();
			filterTwo.put("subjectCode", subject);
			filterTwo.put("examId", examId);
			List<TwoWaySpecification> listTwo = twoWaySpecificationService.queryTwoWaySpecificationsByExamIdAndSubject(filterTwo);
			if(null != listTwo && listTwo.size() > 0) {
				TwoWaySpecification twoWaySpecification = listTwo.get(0);
				if(null != twoWaySpecification) {
					String pid = twoWaySpecification.getId();
					Map<String,Object> filterTwoDe = new HashMap<>();
					filterTwoDe.put("parentId", pid);
					List<TwoWaySpecificationDetail> listDetails =  twoWaySpecificationDetailService.queryTwoWaySpecificationDetailsFilter(filterTwoDe);
					if(null != listDetails && listDetails.size() > 0) {
						Map<String,Map<String,List<CountKnowVO>>> resMap = new LinkedHashMap<>();
						//根据细目表匹配知识点
						for(TwoWaySpecificationDetail e : listDetails) {
							
							//得到学校单题成绩
							List<DetailVO> listSchool = new ArrayList<>();
							for(ScoreDetailVO ee : listDetailSchool) {
								List<DetailVO> listd = ee.getDetailList();
								if(null != listd) {
									for(DetailVO d : listd) {
										if(d.getItemNo().equals(e.getItemNo()+"")) {
											listSchool.add(d);
											break;
										}
									}
								}
							}
							
							//得到班级单题成绩
							List<DetailVO> listClass = new ArrayList<>();
							for(ScoreDetailVO ee : listDetailClass) {
								List<DetailVO> listd = ee.getDetailList();
								if(null != listd) {
									for(DetailVO d : listd) {
										if(d.getItemNo().equals(e.getItemNo()+"")) {
											listClass.add(d);
											break;
										}
									}
								}
							}
							
							//学生单体
							DetailVO studentDetailVO = new DetailVO();
							if(StringUtils.isNotBlank(studentId)) {
								for(DetailVO d : detailList) {
									if(d.getItemNo().equals(e.getItemNo()+"")) {
										studentDetailVO = d;
										break;
									}
								}
							}
							
							CountKnowVO cv = new CountKnowVO();
							cv.setListClass(listClass);
							cv.setListSchool(listSchool);
							cv.setStudentDetailVO(studentDetailVO);
							cv.setQid(e.getItemNo() + "");
							
							//客观题
							if(e.getItemType() == 0) {
								
								if(StringUtils.isNotBlank(e.getKnowledgeId())) {
									String[] knowArray = e.getKnowledgeId().split(",");
									if(knowArray.length > 0) {
										String firstName = knowArray[0];
										String lastName = knowArray[(knowArray.length - 1)];
										Map<String,List<CountKnowVO>> mapSec = resMap.get(firstName);
										if(null == mapSec) {
											mapSec = new LinkedHashMap<>();
											List<CountKnowVO> listC = new ArrayList<>();
											listC.add(cv);
											mapSec.put(lastName,listC);
										}else {
											List<CountKnowVO> listC = mapSec.get(lastName);
											if(null == listC) {
												List<CountKnowVO> listz = new ArrayList<>();
												listz.add(cv);
												mapSec.put(lastName,listz);
											}else {
												listC.add(cv);
												mapSec.put(lastName,listC);
											}
										}
										resMap.put(firstName, mapSec);
									}
								}
							}else{
								//主观题
								List<SpDetailStep> stepList = e.getStepList();
								if(null != stepList && stepList.size() > 0) {
									for(SpDetailStep ss : stepList) {
										CountKnowVO cvs = cv.copy();
										cvs.setIndex(stepList.indexOf(ss));
										if(StringUtils.isNotBlank(ss.getKnowledgePointId())) {
											String[] knowArray = ss.getKnowledgePointId().split(",");
											if(knowArray.length > 0) {
												String firstName = knowArray[0];
												String lastName = knowArray[(knowArray.length - 1)];
												Map<String,List<CountKnowVO>> mapSec = resMap.get(firstName);
												if(null == mapSec) {
													mapSec = new LinkedHashMap<>();
													List<CountKnowVO> listC = new ArrayList<>();
													listC.add(cvs);
													mapSec.put(lastName,listC);
												}else {
													List<CountKnowVO> listC = mapSec.get(lastName);
													if(null == listC) {
														List<CountKnowVO> listz = new ArrayList<>();
														listz.add(cvs);
														mapSec.put(lastName,listz);
													}else {
														listC.add(cvs);
														mapSec.put(lastName,listC);
													}
												}
												resMap.put(firstName, mapSec);
											}
										}
									}
								}
							}
						}
						
						//统计
						List<KnowDetailVO> knowDetailSums = new ArrayList<>();
						Iterator<Entry<String, Map<String, List<CountKnowVO>>>> it = resMap.entrySet().iterator();
						while(it.hasNext()) {
							KnowledgeAnalysisVO v = new KnowledgeAnalysisVO();
							Entry<String, Map<String, List<CountKnowVO>>> s = it.next();
							String knowledgemodule = s.getKey();
							Map<String, List<CountKnowVO>> mapSec = s.getValue();
							List<KnowDetailVO> knowDetail = new ArrayList<>();
							
							Iterator<Entry<String, List<CountKnowVO>>> its = mapSec.entrySet().iterator();
							BigDecimal totalC = BigDecimal.ZERO;
							BigDecimal totalS = BigDecimal.ZERO;
							BigDecimal sizeS = BigDecimal.ZERO;
							while(its.hasNext()) {
								Entry<String, List<CountKnowVO>> sec = its.next();
								String knowledge = sec.getKey();
								List<CountKnowVO> ckv = sec.getValue();
								
								List<String> qid = new ArrayList<>();
								BigDecimal schoolT = BigDecimal.ZERO;
								BigDecimal schoolD = BigDecimal.ZERO;
								BigDecimal classT = BigDecimal.ZERO;
								BigDecimal classD = BigDecimal.ZERO;
								BigDecimal studentT = BigDecimal.ZERO;
								BigDecimal studentD = BigDecimal.ZERO;
								
								for(CountKnowVO c : ckv) {
									List<DetailVO> listSchool = c.getListSchool();
									List<DetailVO> listClass = c.getListClass();
									
									sizeS = new BigDecimal(listClass.size());
									
									for(DetailVO e : listSchool) {
										if(null != e) {
											if(e.getItemType() == 0) {
												schoolT = schoolT.add(e.getTotalScore());
												schoolD = schoolD.add(e.getScore());
											}else {
												List<DetailVO> listStep = e.getListStep();
												if(null != listStep) {
													DetailVO d = listStep.get(c.getIndex());
													schoolT = schoolT.add(d.getTotalScore());
													schoolD = schoolD.add(d.getScore());
												}
											}
										}
									}
									
									for(DetailVO e : listClass) {
										if(null != e) {
											if(e.getItemType() == 0) {
												classT = classT.add(e.getTotalScore());
												classD = classD.add(e.getScore());
											}else {
												List<DetailVO> listStep = e.getListStep();
												if(null != listStep) {
													DetailVO d = listStep.get(c.getIndex());
													classT = classT.add(d.getTotalScore());
													classD = classD.add(d.getScore());
												}
											}
										}
									}
									
									if(StringUtils.isNotBlank(studentId)) {
										DetailVO studentDetailVO = c.getStudentDetailVO();
										if(null != studentDetailVO) {
											if(studentDetailVO.getItemType() == 0) {
												studentT = studentT.add(studentDetailVO.getTotalScore());
												studentD = studentD.add(studentDetailVO.getScore());
											}else {
												List<DetailVO> listStep = studentDetailVO.getListStep();
												if(null != listStep) {
													DetailVO d = listStep.get(c.getIndex());
													studentT = studentT.add(d.getTotalScore());
													studentD = studentD.add(d.getScore());
												}
											}
										}
									}
									qid.add(c.getQid());
								}
								List<String> qidList = new ArrayList<>();
								for(String ss : qid) {
									if(!qidList.contains(ss)) {
										qidList.add(ss);
									}
								}
								String setQid = "";
								for(int i = 0;i < qidList.size();i++) {
									if(i == 0) {
										setQid += qidList.get(i);
									}else {
										setQid += "," + qidList.get(i);
									}
								}
								
								KnowDetailVO kv = new KnowDetailVO();
								kv.setQid(setQid);
								kv.setKnowledgemodule(knowledgemodule);
								kv.setKnowledge(knowledge);
								
								BigDecimal divideClass = BigDecimal.ZERO;
								BigDecimal divideStudent = BigDecimal.ZERO;
								BigDecimal divideSchool = BigDecimal.ZERO;
								
								if(studentT.compareTo(BigDecimal.ZERO) != 0) {
									divideStudent = studentD.divide(studentT, 6, BigDecimal.ROUND_HALF_UP);
									divideStudent = divideStudent.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
									kv.setDivideStudent(divideStudent);
								}
								
								if(classT.compareTo(BigDecimal.ZERO) != 0) {
									divideClass = classD.divide(classT, 6, BigDecimal.ROUND_HALF_UP);
									divideClass = divideClass.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
									kv.setDivideClass(divideClass);
								}
								
								if(schoolT.compareTo(BigDecimal.ZERO) != 0) {
									divideSchool = schoolD.divide(schoolT, 6, BigDecimal.ROUND_HALF_UP);
									divideSchool = divideSchool.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
									kv.setDivideSchool(divideSchool);
									kv.setDivideAera(divideSchool);
								}
								
								if(StringUtils.isBlank(studentId)) {
									BigDecimal differenceOfDivide = divideClass.subtract(divideSchool);
									kv.setDifferenceOfDivide(differenceOfDivide);
									totalC = totalC.add(classT);
									totalS = totalS.add(classD);
								}else {
									BigDecimal differenceOfDivide = divideStudent.subtract(divideSchool);
									kv.setDifferenceOfDivide(differenceOfDivide);
									totalC = totalC.add(studentT);
									totalS = totalS.add(studentD);
								}
								System.out.println(totalC);
								System.out.println(totalS);
								
								knowDetail.add(kv);
							}
							BigDecimal size = new BigDecimal(knowDetail.size());
							if(StringUtils.isBlank(studentId)) {
								totalC = totalC.divide(sizeS,0,BigDecimal.ROUND_HALF_UP);
								totalS = totalS.divide(sizeS,0,BigDecimal.ROUND_HALF_UP);
							}

							v.setTotle(totalC);
							v.setScore(totalS);
							
							BigDecimal divideClass = BigDecimal.ZERO;
							BigDecimal divideSchool = BigDecimal.ZERO;
							BigDecimal divideStudent = BigDecimal.ZERO;
							
							for(KnowDetailVO ec : knowDetail) {
								if(StringUtils.isNotBlank(studentId)) {
									divideStudent = divideStudent.add(ec.getDivideStudent());
								}
								divideClass = divideClass.add(ec.getDivideClass());
								divideSchool = divideSchool.add(ec.getDivideSchool());
								
								ec.setKnowledgeNum(size.intValue());
								String knowledge = ec.getKnowledge();
								String knowledgemodules = ec.getKnowledgemodule();
								knowledge = kvMap.get(knowledge);
								knowledgemodules = kvMap.get(knowledgemodules);
								if(null != knowledge) {
									ec.setKnowledge(knowledge);
								}
								if(null != knowledgemodules) {
									ec.setKnowledgemodule(knowledgemodules);
								}
							}
							divideClass = divideClass.divide(size,2,BigDecimal.ROUND_HALF_UP);
							divideSchool = divideSchool.divide(size,2,BigDecimal.ROUND_HALF_UP);
							if(StringUtils.isNotBlank(studentId)) {
								divideStudent = divideStudent.divide(size,2,BigDecimal.ROUND_HALF_UP);
							}
							
							v.setDivideAera(divideSchool);
							v.setDivideClass(divideClass);
							v.setDivideStudent(divideStudent);
							v.setDivideSchool(divideSchool);
							
							v.setKnowDetail(knowDetail);
							listVO.add(v);
							
							knowDetailSums.addAll(knowDetail);
						}
						
						summaryVO = getKnowSummary(knowDetailSums,tab);
					}else{
						return AsyncResponseData.getSuccess().asParamError("未查询到双向细目表");
					}
				}else {
					return AsyncResponseData.getSuccess().asParamError("未查询到双向细目表");
				}
			}else {
				return AsyncResponseData.getSuccess().asParamError("未查询到双向细目表"); 
			}
		}else {
			return AsyncResponseData.getSuccess().asParamError("未查询到知识点"); 
		}

		Map<String,Object> map = new HashMap<>();
		map.put("summaryVO",summaryVO);
		map.put("listVO", listVO);

		return AsyncResponseData.getSuccess(map);
	}
	
	/**
	 * 能力点分析
	 *
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/ablityAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData ablityAnalysis(String subject,String examId,@RequestParam(required = false)String classroomId,
			@RequestParam(required = false)String studentId) throws Exception{
		
		User user = UserUtils.getLoginUser();
		if (user==null){
			return AsyncResponseData.getSuccess().asSessionError("登录过期，请重新登录");
		}
		String orgId = user.getOrgId();

		String tab = "";
		if(StringUtils.isBlank(studentId)) {
			if(RoleConstant.STUDENT_ROLE.equals(user.getRoleName())){
				studentId = user.getSourceId();
				tab = ScoreConstant.STUDENT_REPORT;
			}
		}
		
		if(StringUtils.isBlank(examId)) {
			return AsyncResponseData.getSuccess().asParamError("考试参数为空");
		}
		if(StringUtils.isBlank(subject)) {
			return AsyncResponseData.getSuccess().asParamError("科目为空");
		}
		
		//返回值
		List<AbilityVO> listVO = new ArrayList<>();
		
		//学生信息
		List<DetailVO> detailList = new ArrayList<>();
		if(StringUtils.isNotBlank(studentId)) {
			Map<String,Object> filter = new HashMap<>();
			filter.put("orgId", orgId);
			filter.put("examId", examId);
			filter.put("studentId", studentId);
			List<Score> listStudent = scoreService.queryScoresFilter(filter);
			if(null != listStudent && listStudent.size() > 0) {
				Score score = listStudent.get(0);
				classroomId = score.getClassroomId();
				if(StringUtils.isBlank(score.getDetailScore())) {
					return AsyncResponseData.getSuccess().asParamError("该学生成绩未录入");
				}
				List<ScoreDetailVO> listDetailss = JSON.parseArray(score.getDetailScore(), ScoreDetailVO.class);
				for(ScoreDetailVO es : listDetailss) {
					if(subject.equals(es.getSubject())) {
						detailList = es.getDetailList();
						break;
					}
				}
			}
			
		}
		
		Map<String,Object> filterSchool = new HashMap<>();
		if(StringUtils.isNotBlank(orgId)) {
			filterSchool.put("orgId", orgId);
		}
		filterSchool.put("examId", examId);
		
		Map<String,Object> filterClass = new HashMap<>();
		if(StringUtils.isNotBlank(classroomId)) {
			filterClass.put("classroomId", classroomId);
			tab = ScoreConstant.CLASS_REPORT;
		}
		filterClass.put("orgId", orgId);
		filterClass.put("examId", examId);
		
		List<Score> listScSchool = scoreService.queryScoresFilter(filterSchool);
		List<Score> listScClass = scoreService.queryScoresFilter(filterClass);
		List<ScoreDetailVO> listDetailSchool = getlist(listScSchool, subject);
		List<ScoreDetailVO> listDetailClass = getlist(listScClass, subject);
		
		//取出双向细目表
		Map<String,Object> filterTwo = new HashMap<>();
		filterTwo.put("subjectCode", subject);
		filterTwo.put("examId", examId);
		List<TwoWaySpecification> listTwo = twoWaySpecificationService.queryTwoWaySpecificationsByExamIdAndSubject(filterTwo);
		if(null != listTwo && listTwo.size() > 0) {
			TwoWaySpecification twoWaySpecification = listTwo.get(0);
			if(null != twoWaySpecification) {
				String pid = twoWaySpecification.getId();
				Map<String,Object> filterTwoDe = new HashMap<>();
				filterTwoDe.put("parentId", pid);
				List<TwoWaySpecificationDetail> listDetails =  twoWaySpecificationDetailService.queryTwoWaySpecificationDetailsFilter(filterTwoDe);
				if(null != listDetails && listDetails.size() > 0) {
					//双项细目表分组
					Map<String, Object> filter = new HashMap<String, Object>();
					filter.put("orgId", orgId);
					filter.put("subjectName", subject);
					
					List<SubjectAblity> subjectablitys = subjectablityService.querySubjectAblitysFilter(filter);
					if (subjectablitys.size() < 1) {
						return AsyncResponseData.getSuccess().asParamError("没有查到该科目对应的能力值");
					}
					
					List<String> ablityArray = new ArrayList<>();
					if(null != subjectablitys) {
						for(SubjectAblity e : subjectablitys) {
							ablityArray.add(e.getAblityName());
						}
					}
					for(int i = 0;i<ablityArray.size();i++) {
						AbilityVO abilityVO = new AbilityVO();
						abilityVO.setAblityName(ablityArray.get(i));
						//数组
						List<String> itemNoArray = new ArrayList<>();
						BigDecimal total = BigDecimal.ZERO;
						BigDecimal totalClass = BigDecimal.ZERO;
						BigDecimal totalSchool = BigDecimal.ZERO;
						BigDecimal divideStudent = BigDecimal.ZERO;
						BigDecimal divideClass = BigDecimal.ZERO;
						BigDecimal divideSchool = BigDecimal.ZERO;
						
						for(TwoWaySpecificationDetail e : listDetails) {
							String ablityString = e.getItemAbility();
							if(StringUtils.isNotBlank(ablityString)) {
								char[] ablityChar = ablityString.toCharArray();
								if(ablityChar[i] == '1') {
									itemNoArray.add(e.getItemNo()+"");
									total = total.add(new BigDecimal(e.getItemScore()));
									//班级
									for(ScoreDetailVO dc : listDetailClass) {
										for(DetailVO ed : dc.getDetailList()) {
											if(ed.getItemNo().equals(e.getItemNo()+"")) {
												divideClass = divideClass.add(ed.getScore());
												totalClass = totalClass.add(ed.getTotalScore());
											}
										}
									}
									
									//校级
									for(ScoreDetailVO dc : listDetailSchool) {
										for(DetailVO ed : dc.getDetailList()) {
											if(ed.getItemNo().equals(e.getItemNo()+"")) {
												divideSchool = divideSchool.add(ed.getScore());
												totalSchool = totalSchool.add(ed.getTotalScore());
											}
										}
									}
									
									
									
									//个人
									if(StringUtils.isNotBlank(studentId) && null != detailList && detailList.size() >0) {
										for(DetailVO ed : detailList) {
											if(ed.getItemNo().equals(e.getItemNo()+"")) {
												divideStudent = divideStudent.add(ed.getScore());
											}
										}
									}
								}
							}
						}
						if(total.compareTo(BigDecimal.ZERO) == 0) {
							total = BigDecimal.ONE;
						}
						if(totalClass.compareTo(BigDecimal.ZERO) == 0) {
							totalClass = BigDecimal.ONE;
						}
						if(totalSchool.compareTo(BigDecimal.ZERO) == 0) {
							totalSchool = BigDecimal.ONE;
						}
						divideStudent = divideStudent.divide(total,4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2);
						divideClass = divideClass.divide(totalClass,4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2);
						divideSchool = divideSchool.divide(totalSchool,4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).setScale(2);
						
						StringBuffer numbers = new StringBuffer();
						for(int j = 0;j<itemNoArray.size();j++) {
							if(j == 0) {
								numbers.append(itemNoArray.get(j));
							}else {
								numbers.append(","+itemNoArray.get(j));
							}
						}
						
						abilityVO.setDivideStudent(divideStudent.toString());
						abilityVO.setDivideClass(divideClass.toString());
						abilityVO.setDivideSchool(divideSchool.toString());
						abilityVO.setDivideAera(abilityVO.getDivideSchool());
						abilityVO.setNumbers(numbers.toString());
						
						listVO.add(abilityVO);
					}
					
				}else{
					return AsyncResponseData.getSuccess().asParamError("未查询到双向细目表");
				}
			}else {
				return AsyncResponseData.getSuccess().asParamError("未查询到双向细目表");
			}
		}else {
			return AsyncResponseData.getSuccess().asParamError("未查询到双向细目表"); 
		}

		SummaryVO summaryVO = getAbilitySummary(listVO,tab);

		Map<String,Object> map = new HashMap<>();
		map.put("summaryVO",summaryVO);
		map.put("listVO", listVO);
		
		return AsyncResponseData.getSuccess(map);
	}
	
	
	
	public static List<ScoreDetailVO> getlist(List<Score> listSc,String subject) {
		//得到成绩
		List<ScoreDetailVO> listDetail = new ArrayList<>();
		if(null != listSc && listSc.size() > 0) {
			for(Score e : listSc) {
				String stuId = e.getStudentId();
				if(StringUtils.isBlank(e.getDetailScore())) {
					ScoreDetailVO scoreDetailVO = new ScoreDetailVO();
					scoreDetailVO.setSubject(subject);
					scoreDetailVO.setDetailList(new ArrayList<>());
					scoreDetailVO.setStuId(stuId);
					listDetail.add(scoreDetailVO);
				}else {
					List<ScoreDetailVO> listDetails = JSON.parseArray(e.getDetailScore(), ScoreDetailVO.class);
					if(null != listDetails && listDetails.size() > 0) {
						for(ScoreDetailVO ee : listDetails) {
							if(subject.equals(ee.getSubject())) {
								ee.setStuId(stuId);
								listDetail.add(ee);
								break;
							}
						}
					}
				}
			}
		}
		return listDetail;
	}
	
//	private void buildOrgVO(List<KnowledgePoint> knowledgepoints, List<KnowledgePointVO> parentKPVOList){
//		List<KnowledgePointVO> allKnowledgepoints = new ArrayList<KnowledgePointVO>();
//		for (KnowledgePointVO parentKPVO : parentKPVOList) {
//			List<KnowledgePointVO> kpVOChildren = new ArrayList<KnowledgePointVO>();
//			for (KnowledgePoint Knowledgepoint : knowledgepoints) {
//				if (StringUtils.isNotBlank(Knowledgepoint.getParentId())){
//					if (Knowledgepoint.getParentId().equals(parentKPVO.getId())){
//						KnowledgePointVO childKPVO  = new KnowledgePointVO();
//						childKPVO.setId(Knowledgepoint.getId());
//						childKPVO.setKnowledgeCode(Knowledgepoint.getKnowledgeCode());
//						childKPVO.setKnowledgeContent(Knowledgepoint.getKnowledgeContent());
//						kpVOChildren.add(childKPVO);
//						allKnowledgepoints.add(childKPVO);
//					}
//				}
//			}
//			parentKPVO.setKpVOChildList(kpVOChildren);
//		}
//		if(!allKnowledgepoints.isEmpty()){
//			buildOrgVO(knowledgepoints,allKnowledgepoints);
//		}
//	}

	/**
	 * 难度二维分析总结
	 * @return
	 */
	public void getDifficultySummary(List<TestAnalysisVO> listVO, String tab, SummaryVO summaryVO){

		if (listVO.size() <= 0){
			summaryVO.setDifficultyAnalysis(" ");
		}else {

			String below = "";
			String easyQes = "";
			String mediumQes = "";

			if (ScoreConstant.CLASS_REPORT.equals(tab)){
				for (TestAnalysisVO eachListVO : listVO){
					if (new BigDecimal(eachListVO.getDivideClass().replaceAll("%", "")).compareTo(new BigDecimal(eachListVO.getDivideSchool().replaceAll("%", ""))) < 0){
						//学生得分率小于学校得分率
						if (StringUtils.isNotBlank(below)){
							below += "、";
						}
						below += ScoreConstant.ITEM_NO.replaceAll("%",eachListVO.getQid());
						if (eachListVO.getDifficulty().compareTo(new BigDecimal("0.4")) >= 0 && eachListVO.getDifficulty().compareTo(new BigDecimal("0.7")) <= 0){
							if (StringUtils.isNotBlank(mediumQes)){
								mediumQes += "、";
							}
							mediumQes += ScoreConstant.ITEM_NO.replaceAll("%",eachListVO.getQid());
						}
						if (eachListVO.getDifficulty().compareTo(new BigDecimal("0.7")) > 0){
							if (StringUtils.isNotBlank(easyQes)){
								easyQes += "、";
							}
							easyQes += ScoreConstant.ITEM_NO.replaceAll("%",eachListVO.getQid());
						}
					}
				}
			}else if (ScoreConstant.STUDENT_REPORT.equals(tab)){
				for (TestAnalysisVO eachListVO : listVO){
					if (new BigDecimal(eachListVO.getDivideStudent().replaceAll("%", "")).compareTo(new BigDecimal(eachListVO.getDivideSchool().replaceAll("%", ""))) < 0){
						//学生得分率小于学校得分率
						if (StringUtils.isNotBlank(below)){
							below += "、";
						}
						below += ScoreConstant.ITEM_NO.replaceAll("%",eachListVO.getQid());
						if (eachListVO.getDifficulty().compareTo(new BigDecimal("0.4")) >= 0 && eachListVO.getDifficulty().compareTo(new BigDecimal("0.7")) <= 0){
							if (StringUtils.isNotBlank(mediumQes)){
								mediumQes += "、";
							}
							mediumQes += ScoreConstant.ITEM_NO.replaceAll("%",eachListVO.getQid());
						}
						if (eachListVO.getDifficulty().compareTo(new BigDecimal("0.7")) > 0){
							if (StringUtils.isNotBlank(easyQes)){
								easyQes += "、";
							}
							easyQes += ScoreConstant.ITEM_NO.replaceAll("%",eachListVO.getQid());
						}
					}
				}
			}else {
				summaryVO.setDifficultyAnalysis(" ");
			}


			String summary = "";
			if (StringUtils.isBlank(below) && StringUtils.isBlank(easyQes) && StringUtils.isBlank(mediumQes)){
				summary = " ";
			}else {
				summary = ScoreConstant.SUMMARY;
				if (StringUtils.isNotBlank(below)){
					summary += ScoreConstant.DIFFICULTY_BELOW_SCHOOL.replaceAll("%",below);
				}
				if (StringUtils.isNotBlank(below)){
					summary += ScoreConstant.MEDIUM_DIFFICULTY.replaceAll("%",mediumQes);
				}
				if (StringUtils.isNotBlank(easyQes)){
					summary += ScoreConstant.EASY_DIFFICULTY.replaceAll("%",easyQes);
				}
			}
			summaryVO.setDifficultyAnalysis(summary);
		}

	}

	/**
	 * 我的目标：下次考试可以增加？分
	 * @return
	 */
	public SummaryVO getNextTimeSummary(TargetVO targetVO){
		SummaryVO summaryVO = new SummaryVO();

		if (0 == targetVO.getEasyNum() && 0 == targetVO.getSecondaryNum() && 0 == targetVO.getDifficultNum()){
			summaryVO.setNextTimeAddScore(" ");
			summaryVO.setPromoteForNextTime(" ");
		}else {
			//失分率最低
			String minLoss = "";
			//失分率最高
			String maxLoss = "";

			BigDecimal minScoreRate = new BigDecimal("1000");
			BigDecimal maxScoreRate = BigDecimal.ZERO;
			if (minScoreRate.compareTo(new BigDecimal(targetVO.getDivideEasy().replaceAll("%", ""))) >= 0){
				minScoreRate = new BigDecimal(targetVO.getDivideEasy().replaceAll("%", ""));
				maxLoss = "简单题";
			}
			if (minScoreRate.compareTo(new BigDecimal(targetVO.getDivideSecondary().replaceAll("%", ""))) >= 0){
				if (minScoreRate.compareTo(new BigDecimal(targetVO.getDivideSecondary().replaceAll("%", ""))) == 0){
					if (StringUtils.isNotBlank(maxLoss)){
						maxLoss += "、";
					}
					maxLoss += "中等题";
				}else {
					minScoreRate = new BigDecimal(targetVO.getDivideSecondary().replaceAll("%", ""));
					maxLoss = "中等题";
				}
			}
			if (minScoreRate.compareTo(new BigDecimal(targetVO.getDivideDifficult())) >= 0){
				if (minScoreRate.compareTo(new BigDecimal(targetVO.getDivideDifficult())) == 0){
					if (StringUtils.isNotBlank(maxLoss)){
						maxLoss += "、";
					}
					maxLoss += "难题";
				}else {
					minScoreRate = new BigDecimal(targetVO.getDivideDifficult().replaceAll("%", ""));
					maxLoss = "难题";
				}
			}
			if (maxScoreRate.compareTo(new BigDecimal(targetVO.getDivideEasy().replaceAll("%", ""))) <= 0){
				maxScoreRate = new BigDecimal(targetVO.getDivideEasy().replaceAll("%", ""));
				minLoss = "简单题";
			}
			if (maxScoreRate.compareTo(new BigDecimal(targetVO.getDivideSecondary())) <= 0){
				if (maxScoreRate.compareTo(new BigDecimal(targetVO.getDivideSecondary().replaceAll("%", ""))) == 0){
					if (StringUtils.isNotBlank(minLoss)){
						minLoss += "、";
					}
					minLoss += "中等题";
				}else {
					maxScoreRate = new BigDecimal(targetVO.getDivideSecondary().replaceAll("%", ""));
					minLoss = "中等题";
				}
			}
			if (maxScoreRate.compareTo(new BigDecimal(targetVO.getDivideDifficult().replaceAll("%", ""))) <= 0){
				if (maxScoreRate.compareTo(new BigDecimal(targetVO.getDivideDifficult().replaceAll("%", ""))) == 0){
					if (StringUtils.isNotBlank(minLoss)){
						minLoss += "、";
					}
					minLoss += "难题";
				}else {
					maxScoreRate = new BigDecimal(targetVO.getDivideDifficult().replaceAll("%", ""));
					minLoss = "难题";
				}
			}
			String summary1 = "";
			summary1 = ScoreConstant.LOSS_RATE.replaceAll("%",minLoss);
			summary1 = summary1.replaceAll("&",maxLoss);

			String summary2 = "";
			if (0 != new BigDecimal("100").compareTo(new BigDecimal(targetVO.getDivideEasy().replaceAll("%", "")))){
				summary2 += ScoreConstant.EASY_SUMMARY;
			}
			if (0 != new BigDecimal("100").compareTo(new BigDecimal(targetVO.getDivideSecondary().replaceAll("%", "")))){
				summary2 += ScoreConstant.MEDIUM_SUMMARY;
			}
			if (0 != new BigDecimal("100").compareTo(new BigDecimal(targetVO.getDivideDifficult().replaceAll("%", "")))){
				summary2 += ScoreConstant.HARD_SUMMARY;
			}

			summaryVO.setNextTimeAddScore(summary1);
			if (StringUtils.isNotBlank(summary2)){
				summaryVO.setPromoteForNextTime(summary2);
			}else {
				summaryVO.setPromoteForNextTime(" ");
			}
		}

		return summaryVO;
	}

	/**
	 * 能力分析
	 * @return
	 */
	public SummaryVO getAbilitySummary(List<AbilityVO> listVO, String tab){
		SummaryVO summaryVO = new SummaryVO();
		if (listVO.size() <= 0){
			summaryVO.setAbilityAnalysis(" ");
		}else {
			String aboveAbility = "";
			String belowAbility = "";
			if (ScoreConstant.CLASS_REPORT.equals(tab)){
				for(AbilityVO eachAbilityVO : listVO){
					if (new BigDecimal(eachAbilityVO.getDivideClass().replaceAll("%", "")).compareTo(new BigDecimal(eachAbilityVO.getDivideSchool().replaceAll("%", ""))) < 0){
						if (StringUtils.isNotBlank(belowAbility)){
							belowAbility += "、";
						}
						belowAbility += eachAbilityVO.getAblityName();
					}
					if (new BigDecimal(eachAbilityVO.getDivideClass().replaceAll("%", "")).compareTo(new BigDecimal(eachAbilityVO.getDivideSchool().replaceAll("%", ""))) > 0){
						if (StringUtils.isNotBlank(aboveAbility)){
							aboveAbility += "、";
						}
						aboveAbility += eachAbilityVO.getAblityName();
					}

				}
			}else if (ScoreConstant.STUDENT_REPORT.equals(tab)){
				for(AbilityVO eachAbilityVO : listVO){
					if (new BigDecimal(eachAbilityVO.getDivideStudent().replaceAll("%", "")).compareTo(new BigDecimal(eachAbilityVO.getDivideSchool().replaceAll("%", ""))) < 0){
						if (StringUtils.isNotBlank(belowAbility)){
							belowAbility += "、";
						}
						belowAbility += eachAbilityVO.getAblityName();
					}
					if (new BigDecimal(eachAbilityVO.getDivideStudent().replaceAll("%", "")).compareTo(new BigDecimal(eachAbilityVO.getDivideSchool().replaceAll("%", ""))) > 0){
						if (StringUtils.isNotBlank(aboveAbility)){
							aboveAbility += "、";
						}
						aboveAbility += eachAbilityVO.getAblityName();
					}

				}
			}else {
				summaryVO.setAbilityAnalysis(" ");
				return summaryVO;
			}

			String summary = "";
			if (StringUtils.isBlank(aboveAbility) && StringUtils.isBlank(belowAbility)){
				summary = " ";
			}else {
				summary = ScoreConstant.SUMMARY;
				if (StringUtils.isNotBlank(belowAbility)){
					summary += ScoreConstant.ABILITY_BELOW_SCHOOL.replaceAll("%",belowAbility);
				}
				if (StringUtils.isNotBlank(aboveAbility)){
					summary += ScoreConstant.ABILITY_ABOVE_SCHOOL.replaceAll("%",aboveAbility);
				}
			}
			summaryVO.setAbilityAnalysis(summary);

		}
		return summaryVO;
	}

	/**
	 * 知识点分析
	 * @return
	 */
	public SummaryVO getKnowSummary(List<KnowDetailVO> listVO, String tab){
		SummaryVO summaryVO = new SummaryVO();
		if (listVO.size() <= 0){
			summaryVO.setKnowledgeAnalysis(" ");
		}else {
			String belowModules = "";
			if (ScoreConstant.CLASS_REPORT.equals(tab)){
				for(KnowDetailVO eachAbilityVO : listVO){
					if (eachAbilityVO.getDivideClass().compareTo(eachAbilityVO.getDivideSchool()) < 0){
						if (StringUtils.isNotBlank(belowModules)){
							belowModules += "；";
						}
						belowModules += ScoreConstant.ITEM_TITLE.replaceAll("%",eachAbilityVO.getKnowledgemodule());
						belowModules = belowModules.replaceAll("&",eachAbilityVO.getKnowledge());
						belowModules = belowModules.replaceAll("#",eachAbilityVO.getQid());
					}
				}
			}else if (ScoreConstant.STUDENT_REPORT.equals(tab)){
				for(KnowDetailVO eachAbilityVO : listVO){
					if (eachAbilityVO.getDivideStudent().compareTo(eachAbilityVO.getDivideSchool()) < 0){
						if (StringUtils.isNotBlank(belowModules)){
							belowModules += "；";
						}
						belowModules += ScoreConstant.ITEM_TITLE.replaceAll("%",eachAbilityVO.getKnowledgemodule());
						belowModules = belowModules.replaceAll("&",eachAbilityVO.getKnowledge());
						belowModules = belowModules.replaceAll("#",eachAbilityVO.getQid());
					}
				}
			}else {
				summaryVO.setAbilityAnalysis(" ");
				return summaryVO;
			}

			String summary = "";
			if (StringUtils.isBlank(belowModules)){
				summary = " ";
			}else {
				summary += ScoreConstant.KNOW_BELOW_SCHOOL.replaceAll("%",belowModules);
			}
			summaryVO.setKnowledgeAnalysis(summary);
		}

		return summaryVO;
	}

}
