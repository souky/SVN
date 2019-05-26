package com.jy.moudles.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.exam.dao.ExamDao;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;

/** 
 * exam业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamDao examdao;
	
	@Override
	public int insertExam(Exam exam){
		return examdao.insertExam(exam);
	}
	
	@Override
	public void updateExam(Exam exam){
		examdao.updateExam(exam);
	}
	
	@Override
	public Exam getExamById(String id){
		return examdao.getExamById(id);
	}
	
	@Override
	public List<Exam> queryExamsFilter(Map<String, Object> filter){
		return examdao.queryExamsFilter(filter);
	}

	@Override
	public List<Exam> queryExamsFilterNew(Map<String, Object> filter){
		return examdao.queryExamsFilterNew(filter);
	}

	@Override
	public List<Exam> getExamListForTab(Map<String, Object> filter) {
		return examdao.getExamListForTab(filter);
	}

	@Override
	public List<Exam> getExamListForTabNew(Map<String, Object> filter) {
		return examdao.getExamListForTabNew(filter);
	}
	
	@Override
	public void deleteExamById(String id){
		examdao.deleteExamById(id);
	}
	
	@Override
	public void deleteExams(List<String> ids){
		examdao.deleteExams(ids);
	}

	@Override
	public Exam getOrgLastCompletedExam(String orgId) {
		return examdao.getOrgLastCompletedExam(orgId);
	}
	
}

