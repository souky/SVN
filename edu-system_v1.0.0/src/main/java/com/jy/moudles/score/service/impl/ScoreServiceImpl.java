package com.jy.moudles.score.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.score.dao.ScoreDao;
import com.jy.moudles.score.entity.ProcessedScores;
import com.jy.moudles.score.entity.Score;
import com.jy.moudles.score.entity.ScoreVO;
import com.jy.moudles.score.service.ScoreService;

/** 
 * score业务实现类
 * 创建人：1
 * 创建时间：2017-12-11
 */
@Service
public class ScoreServiceImpl implements ScoreService {

	@Autowired
	private ScoreDao scoredao;

	@Override
	public void insertScore(Score score){
		score.setId(UUIDUtil.get32UUID());
		scoredao.insertScore(score);
	}
	
	@Override
	public void updateScore(Score score){
		scoredao.updateScore(score);
	}
	
	@Override
	public Score getScoreById(String id){
		return scoredao.getScoreById(id);
	}

	@Override
	public List<BigDecimal> getExactScore(Map<String, Object> filter){
		return scoredao.getExactScore(filter);
	}

	@Override
	public List<Score> getScoresByInterval(Map<String, Object> filter) {
		return scoredao.getScoresByInterval(filter);
	}

	@Override
	public Score getScoreByExamIdAndStuId(String examId, String studentId) {
		return scoredao.getScoreByExamIdAndStuId(examId,studentId);
	}
	
	@Override
	public Score getScoreByExamIdAndStuIdNew(Map<String,Object> map) {
		return scoredao.getScoreByExamIdAndStuIdNew(map);
	}
	
	@Override
	public List<Score> queryScoresFilter(Map<String, Object> filter){
		return scoredao.queryScoresFilter(filter);
	}
	
	@Override
	public List<Score> queryScoresFilterNew(Map<String, Object> filter){
		return scoredao.queryScoresFilterNew(filter);
	}

	@Override
	public List<Score> queryScoresFilterNewOne(Map<String, Object> filter) {
		return scoredao.queryScoresFilterNewOne(filter);
	}

	@Override
	public void deleteScoreById(String id){
		scoredao.deleteScoreById(id);
	}
	
	@Override
	public void deleteScores(List<String> ids){
		scoredao.deleteScores(ids);
	}

	@Override
	public List<Score> getScoresByClassroom(String orgId, String examId, String classroomId) {
		return scoredao.getScoresByClassroom(orgId,examId,classroomId);
	}

	@Override
	public ProcessedScores getProcessedScores(Map<String, Object> filter) {
		return scoredao.getProcessedScores(filter);
	}

	@Override
	public List<ProcessedScores> getProcessedScoresForClasses(Map<String, Object> filter) {
		return scoredao.getProcessedScoresForClasses(filter);
	}

	@Override
	public List<ScoreVO> getExamFullMarks(String examId) {
		return scoredao.getExamFullMarks(examId);
	}

	@Override
	public List<Score> getSubjScoresByExamIds(Map<String,Object> dataMap) {
		return scoredao.getSubjScoresByExamIds(dataMap);
	}

	@Override
	public List<Score> queryScoresFilterNewTwo(Map<String, Object> filter) {
		return scoredao.queryScoresFilterNewTwo(filter);
	}

	@Override
	public List<Map<String, Object>> queryScoresFilterNewThree(Map<String, Object> filter,String subject) {
		return sortList(scoredao.queryScoresFilterNewThree(filter),subject);
	}
	
	//排序工具
	public  List<Map<String,Object>> sortList(List<Map<String,Object>> list,String subject) {
		Collections.sort(list, new Comparator<Map<String,Object>>() {
            public int compare(Map<String,Object> s1,Map<String,Object> s2) {
            	BigDecimal b1 = new BigDecimal(s1.get(subject).toString());
            	BigDecimal b2 = new BigDecimal(s2.get(subject).toString());
            	int i = b1.compareTo(b2) == 1?-1:b1.compareTo(b2) == -1?1:0;
            	return i;
            }
        });
		return list;
	}


}

