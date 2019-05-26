package com.jy.moudles.exam.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.exam.dao.ExamDao;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;

/** 
 * Exam业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Service
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamDao ExamDao;
	
	@Override
	public void insertExam(Exam Exam){
		Exam.setId(UUIDUtil.get32UUID());
		ExamDao.insertExam(Exam);
	}
	
	@Override
	public void updateExam(Exam Exam){
		ExamDao.updateExam(Exam);
	}
	
	@Override
	public Exam getExamByNo(String ksbh) {
		return ExamDao.getExamByNo(ksbh);
	}
	
	@Override
	public List<Exam> queryExamsFilter(Map<String, Object> filter){
		return ExamDao.queryExamsFilter(filter);
	}
	
	@Override
	public void deleteExamByNo(String ksbh){
		ExamDao.deleteExamByNo(ksbh);
	}
	
	@Override
	public void deleteExams(List<String> ids){
		ExamDao.deleteExams(ids);
	}

	@Override
	public List<Exam> queryAllExamWithSeason() {
		return ExamDao.queryAllExamWithSeason();
	}
	
	
	
}

