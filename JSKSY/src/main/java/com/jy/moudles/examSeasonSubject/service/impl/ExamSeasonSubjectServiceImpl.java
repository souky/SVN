package com.jy.moudles.examSeasonSubject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.examSeasonSubject.dao.ExamSeasonSubjectDao;
import com.jy.moudles.examSeasonSubject.entity.ExamSeasonSubject;
import com.jy.moudles.examSeasonSubject.service.ExamSeasonSubjectService;

/** 
 * ExamSeasonSubject业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Service
public class ExamSeasonSubjectServiceImpl implements ExamSeasonSubjectService {

	@Autowired
	private ExamSeasonSubjectDao ExamSeasonSubjectDao;
	
	@Override
	public void insertExamSeasonSubject(ExamSeasonSubject ExamSeasonSubject){
		ExamSeasonSubject.setId(UUIDUtil.get32UUID());
		ExamSeasonSubjectDao.insertExamSeasonSubject(ExamSeasonSubject);
	}
	
	@Override
	public void updateExamSeasonSubject(ExamSeasonSubject ExamSeasonSubject){
		ExamSeasonSubjectDao.updateExamSeasonSubject(ExamSeasonSubject);
	}
	
	@Override
	public ExamSeasonSubject getExamSeasonSubjectById(String id){
		return ExamSeasonSubjectDao.getExamSeasonSubjectById(id);
	}
	
	@Override
	public List<ExamSeasonSubject> queryExamSeasonSubjectsFilter(Map<String, Object> filter){
		return ExamSeasonSubjectDao.queryExamSeasonSubjectsFilter(filter);
	}
	
	@Override
	public void deleteExamSeasonSubjectBySeasonNo(String examSeasonNo){
		ExamSeasonSubjectDao.deleteExamSeasonSubjectBySeasonNo(examSeasonNo);
	}
	
	@Override
	public void deleteExamSeasonSubjects(List<String> ids){
		ExamSeasonSubjectDao.deleteExamSeasonSubjects(ids);
	}

	@Override
	public void insertExamSeasonSubjects(List<ExamSeasonSubject> examSeasonSubjects) {
		ExamSeasonSubjectDao.insertExamSeasonSubjects(examSeasonSubjects);
	}
	
}

