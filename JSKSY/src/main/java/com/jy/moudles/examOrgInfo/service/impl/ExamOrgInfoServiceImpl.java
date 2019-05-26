package com.jy.moudles.examOrgInfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.examOrgInfo.dao.ExamOrgInfoDao;
import com.jy.moudles.examOrgInfo.entity.ExamOrgInfo;
import com.jy.moudles.examOrgInfo.service.ExamOrgInfoService;

/** 
 * ExamOrgInfo业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-04
 */
@Service
public class ExamOrgInfoServiceImpl implements ExamOrgInfoService {

	@Autowired
	private ExamOrgInfoDao ExamOrgInfoDao;
	
	@Override
	public void insertExamOrgInfo(ExamOrgInfo ExamOrgInfo){
		ExamOrgInfo.setId(UUIDUtil.get32UUID());
		ExamOrgInfoDao.insertExamOrgInfo(ExamOrgInfo);
	}
	
	@Override
	public void updateExamOrgInfo(ExamOrgInfo ExamOrgInfo){
		ExamOrgInfoDao.updateExamOrgInfo(ExamOrgInfo);
	}
	
	@Override
	public ExamOrgInfo getExamOrgInfoById(String id){
		return ExamOrgInfoDao.getExamOrgInfoById(id);
	}
	
	@Override
	public List<ExamOrgInfo> queryExamOrgInfosFilter(Map<String, Object> filter){
		return ExamOrgInfoDao.queryExamOrgInfosFilter(filter);
	}
	
	@Override
	public void deleteExamOrgInfoById(String id){
		ExamOrgInfoDao.deleteExamOrgInfoById(id);
	}
	
	@Override
	public void deleteExamOrgInfos(){
		ExamOrgInfoDao.deleteExamOrgInfos();
	}

	@Override
	public void insertExamOrgInfos(List<ExamOrgInfo> examOrgInfos) {
		ExamOrgInfoDao.insertExamOrgInfos(examOrgInfos);
	}
	
}

