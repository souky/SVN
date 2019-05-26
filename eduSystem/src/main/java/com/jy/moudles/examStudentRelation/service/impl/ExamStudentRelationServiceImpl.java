package com.jy.moudles.examStudentRelation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.examStudentRelation.dao.ExamStudentRelationDao;
import com.jy.moudles.examStudentRelation.entity.ExamStudentRelation;
import com.jy.moudles.examStudentRelation.service.ExamStudentRelationService;

/** 
 * examStudentRelation业务实现类
 * 创建人：1
 * 创建时间：2017-11-30
 */
@Service
public class ExamStudentRelationServiceImpl implements ExamStudentRelationService {

	@Autowired
	private ExamStudentRelationDao examStudentRelationdao;
	
	@Override
	public void insertExamStudentRelation(ExamStudentRelation examStudentRelation){
		examStudentRelation.setId(UUIDUtil.get32UUID());
		examStudentRelationdao.insertExamStudentRelation(examStudentRelation);
	}

	@Override
	public void batchInsertExamStudentRelation(List<ExamStudentRelation> examStudentRelations) {
		examStudentRelationdao.batchInsertExamStudentRelation(examStudentRelations);
	}

	@Override
	public void updateExamStudentRelation(ExamStudentRelation examStudentRelation){
		examStudentRelationdao.updateExamStudentRelation(examStudentRelation);
	}
	
	@Override
	public ExamStudentRelation getExamStudentRelationById(String id){
		return examStudentRelationdao.getExamStudentRelationById(id);
	}
	
	@Override
	public List<ExamStudentRelation> queryExamStudentRelationsFilter(Map<String, Object> filter){
		return examStudentRelationdao.queryExamStudentRelationsFilter(filter);
	}
	
	@Override
	public void deleteExamStudentRelationByExamId(String examId){
		examStudentRelationdao.deleteExamStudentRelationByExamId(examId);
	}
	
	@Override
	public void deleteExamStudentRelations(List<String> ids){
		examStudentRelationdao.deleteExamStudentRelations(ids);
	}
	
	@Override
	public List<ExamStudentRelation> getExamStudentRelationByExamId(String examId){
		return examStudentRelationdao.getExamStudentRelationByExamId(examId);
	}
}

