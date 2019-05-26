package com.jy.moudles.score.controller;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.auth.UserUtils;
import com.jy.common.utils.mathtools.EduEvaluatingUtils;
import com.jy.moudles.classroom.entity.Classroom;
import com.jy.moudles.classroom.service.ClassroomService;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;
import com.jy.moudles.score.constant.ScoreConstant;
import com.jy.moudles.score.entity.ProcessedScores;
import com.jy.moudles.score.entity.Score;
import com.jy.moudles.score.entity.ScoreVO;
import com.jy.moudles.score.entityVO.ContributeRateVO;
import com.jy.moudles.score.service.ScoreService;
import com.jy.moudles.teacher.entity.Teacher;
import com.jy.moudles.teacher.service.TeacherService;
import com.jy.moudles.twoWaySpecification.entity.TwoWaySpecification;
import com.jy.moudles.twoWaySpecification.service.TwoWaySpecificationService;
import com.jy.moudles.user.entity.User;

/**
 * @author zhang chao
 *
 */
@Controller
@RequestMapping(value = "/contribute")
public class ContributeController {
    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ExamService examService;

    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private TwoWaySpecificationService twoWaySpecificationService;
    @Autowired
    private TeacherService teacherService;
    
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ContributeController.class);
    /**
     * 贡献率
     *
     * @param examId
     * @param orgId
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping(value = "/contributeRate", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData contributeRate(String examId, String subject) throws Exception {
        LOGGER.info("获取 contributeRate Start");
        User loginUser = UserUtils.getLoginUser();
        if (loginUser == null) {
            return AsyncResponseData.getSuccess().asSessionError("登陆过期,请重新登陆");
        }
        List<ContributeRateVO> contributeRateVOList = new ArrayList<>();
        List<ScoreVO> scoreVO =  getExamScoreVO(examId);
        Map<String, Object> filter = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        Exam examById = examService.getExamById(examId);
        String[] classrooms = examById.getClassroom().split(",");
        String[] subjects = examById.getSubject().split(",");
        if (classrooms.length == 0 || subjects.length == 0) {
            return AsyncResponseData.getSuccess().asSessionError("参数错误");
        }
        List<BigDecimal> totalScoreList = new ArrayList<>();
        List<Score> scores = getClassTotalScore(examId, loginUser.getOrgId());
        for (Score score : scores) {
            totalScoreList.add(score.getTotalScore());//..................................
        }
        for (String subj : subjects) {
            if (subj.equals(subject)) {
                for (String classroom : classrooms) {
                    //teacherName
                	Classroom classroomEntity = classroomService.getClassroomByName(classroom, loginUser.getOrgId());
                	if(null == classroomEntity) {
                		return AsyncResponseData.getSuccess().asParamError("参数错误");
                	}
                    String teacherId = classroomEntity.getClassTeacherId();
                    Teacher teacher = teacherService.getTeacherById(teacherId);
                    String teacherName = teacher.getTeacherName();
                    String classroomId = classroomEntity.getId();
                    filter.put("examId",examId);
                    filter.put("orgId",loginUser.getOrgId());
                    filter.put("classroomId", classroomId);
                    BigDecimal classAvgScore = BigDecimal.ZERO;
                    //班级总分平均分
                    ProcessedScores processedScores = scoreService.getProcessedScores(filter);
                    BigDecimal classAvgTotalScore = processedScores.getAvgTotalScore();
                    //班级总分标准分
                    BigDecimal standardTByClass = EduEvaluatingUtils.getStandardT(totalScoreList, classAvgTotalScore);
                    List<BigDecimal> subjectScoreList = new ArrayList<>();
                    for ( ScoreVO score1:scoreVO) {
                        if (classroom.equals(score1.getClassroomName())) {
                            getSubjectAvgScore(score1, processedScores, subject);//拿到科目平均分....
                            classAvgScore = score1.getClassAvgScore();
                        }
                    }
                    String subjectClass = subjectMap.get(subject);
                    List<Score> classSubjectScore = getClassTotalScore(examId, loginUser.getOrgId());
                    for (Score score : classSubjectScore) {
                        Class<?> clz = Score.class;
                        String methodName = "";
                        methodName = "get" + subjectClass.substring(0, 1).toUpperCase() + subjectClass.substring(1);
                        Method method = clz.getMethod(methodName);
                        subjectScoreList.add((BigDecimal) method.invoke(score));    //该科目的分数集合
                    }
                    BigDecimal standardTSubject = EduEvaluatingUtils.getStandardT(subjectScoreList, classAvgScore);
                    BigDecimal contributeRateSubject = BigDecimal.ZERO;
                    if(null != standardTSubject) {
                    	contributeRateSubject = standardTSubject.divide(standardTByClass, 1, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                    }
                    
                    ContributeRateVO contributeRateVO = new ContributeRateVO();
                    
                    contributeRateVO.setTeacherName(teacherName);//
                    contributeRateVO.setClassroom(classroom);
                    contributeRateVO.setAvgTotalScore(classAvgTotalScore);
                    contributeRateVO.setStandardTSubject(standardTSubject);//班级科目标准分
                    contributeRateVO.setStandardTByClass(standardTByClass);//班级总分标准分
                    contributeRateVO.setClassAvgScore(classAvgScore);
                    contributeRateVO.setContributeRateSubject(contributeRateSubject);
                    contributeRateVOList.add(contributeRateVO);

                    resultMap.put("classSubScore", contributeRateVOList);

                }

            }
        }
        LOGGER.info("获取 contributeRate End");
        return AsyncResponseData.getSuccess(resultMap);
    }

    /**
     * 科目平均分
     * @param scoreVO
     * @param object
     * @param subject
     * @throws Exception
     */
    private void getSubjectAvgScore(ScoreVO scoreVO, Object object, String subject) throws Exception {
        if (StringUtils.isNotBlank(subject)) {
            String methodName = "";
            if (object instanceof Score) {
                if (StringUtils.isNotBlank(subject)) {
                    Class<?> clz = Score.class;
                    methodName = "get" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
                    Method method = clz.getMethod(methodName);
                    scoreVO.setScore((BigDecimal) method.invoke(object));
                }
            } else if (object instanceof ProcessedScores) {
                if (StringUtils.isNotBlank(subject)) {
                    Class<?> clz = ProcessedScores.class;
                    Method method = clz.getMethod(methodName);
                    methodName = "getAvg" + subject.substring(0, 1).toUpperCase() + subject.substring(1);
                    method = clz.getMethod(methodName);
                    scoreVO.setClassAvgScore((BigDecimal) method.invoke(object));
                }
            }
        }
    }

    /**
     *  校级分数集合
     * @param examId
     * @param orgId
     * @return
     */
    private List<Score> getClassTotalScore(String examId,String orgId){
        Map<String,Object> filter = new HashMap<>();
        filter.put("examId",examId);
        filter.put("orgId",orgId);
        List<Score> scores = scoreService.queryScoresFilter(filter);
        return scores;
    }

    private List<ScoreVO> getExamScoreVO(String examId){
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("examId",examId);
        List<TwoWaySpecification> twoWaySpecifications = twoWaySpecificationService.queryTotalTwoWaySpecificationsFilter(filter);
        List<ScoreVO> scoreVOList = new ArrayList<>();
        ScoreVO scoreVO;
        int totalScore = 0;
        for (TwoWaySpecification twoWaySpecification : twoWaySpecifications){
            scoreVO = new ScoreVO();
            scoreVO.setSubject(twoWaySpecification.getSubjectCode());
            scoreVO.setFullMarks(twoWaySpecification.getScore());
            scoreVOList.add(scoreVO);
            totalScore += twoWaySpecification.getScore();
        }
        scoreVO = new ScoreVO();
        scoreVO.setSubject(ScoreConstant.TOTAL_SCORE);
        scoreVO.setFullMarks(totalScore);
        scoreVOList.add(0,scoreVO);
        return scoreVOList;
    }
}
