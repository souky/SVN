package com.jy.moudles.score.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.score.entity.ProcessedScores;
import com.jy.moudles.score.entity.Score;
import com.jy.moudles.score.entity.ScoreVO;

/** 
 * score数据接口
 * 创建人：1
 * 创建时间：2017-12-11
 */
@MyBatisDao
public interface ScoreDao {

	/**
	 * 新增score对象
	 *
	 * @param score
	 */
	public void insertScore(Score score);
	
	/**
	 * 更新score对象
	 *
	 * @param score
	 */
	public void updateScore(Score score);
	/**
	 * 根据ID获取score对象
	 *
	 * @param id
	 */
	public Score getScoreById(String id);

	public List<BigDecimal> getExactScore(Map<String, Object> filter);

	public List<Score> getScoresByInterval(Map<String, Object> filter);

	/**
	 * 根据examId,studentId 获取score对象
	 *
	 * @param
	 */
	public Score getScoreByExamIdAndStuId(@Param("examId") String examId, @Param("studentId") String studentId);
	public Score getScoreByExamIdAndStuIdNew(Map<String,Object> map);
	
	/**
	 * 根据过滤条件获取score列表对象
	 *
	 * @param filter
	 */
	public List<Score> queryScoresFilter(Map<String, Object> filter);
	
	/**
	 * 根据过滤条件获取score列表对象 优化
	 *
	 * @param filter
	 */
	public List<Score> queryScoresFilterNew(Map<String, Object> filter);

	/**
	 * 根据过滤条件获取score列表对象 优化
	 *
	 * @param filter
	 */
	public List<Score> queryScoresFilterNewOne(Map<String, Object> filter);
	public List<Score> queryScoresFilterNewTwo(Map<String, Object> filter);
	public List<Map<String,Object>> queryScoresFilterNewThree(Map<String, Object> filter);
	/**
	 * 根据Id删除score列表对象
	 *
	 * @param id
	 */
	public void deleteScoreById(String id);
	
	/**
	 * 根据Id集合批量删除score列表对象
	 *
	 * @param ids
	 */
	public void deleteScores(List<String> ids);

	public List<Score> getScoresByClassroom(String orgId, String examId, String classroomId);

	public ProcessedScores getProcessedScores(Map<String, Object> filter);

	public List<ProcessedScores> getProcessedScoresForClasses(Map<String, Object> filter);

	public List<ScoreVO> getExamFullMarks(String examId);

	public List<Score> getSubjScoresByExamIds(Map<String,Object> dataMap);
}



