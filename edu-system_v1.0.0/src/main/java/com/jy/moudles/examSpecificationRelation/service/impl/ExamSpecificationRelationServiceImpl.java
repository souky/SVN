package com.jy.moudles.examSpecificationRelation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.examSpecificationRelation.dao.ExamSpecificationRelationDao;
import com.jy.moudles.examSpecificationRelation.entity.ExamSpecificationRelation;
import com.jy.moudles.examSpecificationRelation.service.ExamSpecificationRelationService;

/** 
 * examSpecificationRelation业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class ExamSpecificationRelationServiceImpl implements ExamSpecificationRelationService {

	@Autowired
	private ExamSpecificationRelationDao examSpecificationRelationdao;
	
	@Override
	public void insertExamSpecificationRelation(ExamSpecificationRelation examSpecificationRelation){
		examSpecificationRelation.setId(UUIDUtil.get32UUID());
		examSpecificationRelationdao.insertExamSpecificationRelation(examSpecificationRelation);
	}

	@Override
	public void batchInsertExamSpecificationRelation(List<ExamSpecificationRelation> examSpecificationRelations) {
		examSpecificationRelationdao.batchInsertExamSpecificationRelation(examSpecificationRelations);
	}

	@Override
	public void updateExamSpecificationRelation(ExamSpecificationRelation examSpecificationRelation){
		examSpecificationRelationdao.updateExamSpecificationRelation(examSpecificationRelation);
	}
	
	@Override
	public ExamSpecificationRelation getExamSpecificationRelationById(String id){
		return examSpecificationRelationdao.getExamSpecificationRelationById(id);
	}
	
	@Override
	public List<ExamSpecificationRelation> queryExamSpecificationRelationsFilter(Map<String, Object> filter){
		return examSpecificationRelationdao.queryExamSpecificationRelationsFilter(filter);
	}
	
	@Override
	public void deleteExamSpecificationRelationByExamId(String examId){
		examSpecificationRelationdao.deleteExamSpecificationRelationByExamId(examId);
	}
	
	@Override
	public void deleteExamSpecificationRelations(List<String> ids){
		examSpecificationRelationdao.deleteExamSpecificationRelations(ids);
	}
	
}

