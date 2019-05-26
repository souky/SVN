package com.jy.moudles.buttInterface.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.moudles.buttInterface.dao.ButtInterFaceDao;
import com.jy.moudles.buttInterface.entity.StudentExam;
import com.jy.moudles.buttInterface.entity.TwoWaySpecificationDetailNew;
import com.jy.moudles.buttInterface.service.ButtInterfaceService;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.teacher.entity.Teacher;
@Service
public class ButtInterfaceServiceImp implements ButtInterfaceService{
	@Autowired
	private ButtInterFaceDao buttInterFaceDao;
	/**
	 * 根据条件查询考试信息
	 */
	@Override
	@Transactional(readOnly=true,rollbackFor=Exception.class)
	public List<Exam> queryExamInfoMsg(Map<String,Object> param) {
		return buttInterFaceDao.queryExamInfoMsg(param);
	}
	
	/**
	 * 根据条件查询场考试的所有科目双向细目表
	 */
	@Override
	@Transactional(readOnly=true,rollbackFor=Exception.class)
	public Map<String,List<TwoWaySpecificationDetailNew>> queryTwoWaySpecificationMsg(Map<String, Object> param) {
		List<TwoWaySpecificationDetailNew> resultParam = buttInterFaceDao.queryTwoWaySpecificationMsg(param);
		
		//科目code为key 每科的对应的细目表集合为value
		Map<String,List<TwoWaySpecificationDetailNew>> map = new HashMap<>();
		//每科的对应的细目表集合
		List<TwoWaySpecificationDetailNew> detailList;
		for (TwoWaySpecificationDetailNew twoWaySpecificationDetailNew : resultParam) {//存在
			if (map.containsKey(twoWaySpecificationDetailNew.getSubjectCode())) {
				detailList = map.get(twoWaySpecificationDetailNew.getSubjectCode());
				detailList.add(twoWaySpecificationDetailNew);
				map.put(twoWaySpecificationDetailNew.getSubjectCode(), detailList);
			} else {//不存在
				detailList = new LinkedList<>();
				detailList.add(twoWaySpecificationDetailNew);
				map.put(twoWaySpecificationDetailNew.getSubjectCode(), detailList);
			}
		}
		
		return map;
	}
	
	/**
	 * 根据条件查询教师信息
	 */
	@Override
	@Transactional(readOnly=true,rollbackFor=Exception.class)
	public List<Teacher> queryTeachermsg(Map<String, Object> param) {
		return buttInterFaceDao.queryTeachermsg(param);
	}
	
	/**
	 * 学生考试信息
	 */
	@Override
	@Transactional(readOnly=true,rollbackFor=Exception.class)
	public List<StudentExam> queryStudentExamMsg(Map<String, Object> param) {
		return buttInterFaceDao.queryStudentExamMsg(param);
	}
	
	/**
	 * 修改考试信息
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int updateExamInfo(Map<String, Object> param) {
		return buttInterFaceDao.updateExamInfo(param);
	}
	
	/**
	 * 接收信息并批量插入数据库
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int receiveScore(List<Map<String,Object>> list) {
		//根新分数
		Map<String,Object> param = new HashMap<>();
		param.put("examId", list.get(0).get("examId"));
		buttInterFaceDao.deleteScore(param);
		
		return buttInterFaceDao.beatchInsertScores(list);
	}
	
	/**
	 * 接收试卷信息并批量插入数据库
	 */
	@Override
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public int receiveScoreMsg(Map<String, List<Map<String, Object>>> param) {
		
		//存在一场考试的科目重复接收 那么首先要先删除以往接收的同种信息
		Map<String,Object> relationParam = new HashMap<>();
		relationParam.put("examId", param.get("specificationRelation").get(0).get("examId"));
		relationParam.put("subjectCode", param.get("specification").get(0).get("subjectCode"));
		buttInterFaceDao.deleteSpecificationRelation(relationParam);
		
		//批量插入分步信息
		int i = buttInterFaceDao.beatchInsertSpecification(param.get("specification"));
		i = buttInterFaceDao.beatchInsertSpecificationDetail(param.get("specificationDetail"));
		//如果有分步信息
		if (param.get("detailStep").size() > 0) {
			i = buttInterFaceDao.beatchInsertSpecificationDetailStep(param.get("detailStep"));
		}
		
		//修改关联双性表
		//i = buttInterFaceDao.updateExamSpecificationRelation(param.get("specificationRelation").get(0));
		i = buttInterFaceDao.beatchInsertSpecificationRelation(param.get("specificationRelation"));
		return i;
	}
	
	/**
	 * 修改考试状态 定时任务用
	 */
	@Override
	public int updateExamStatus() {
		return buttInterFaceDao.updateExamStatus();
	}

}
