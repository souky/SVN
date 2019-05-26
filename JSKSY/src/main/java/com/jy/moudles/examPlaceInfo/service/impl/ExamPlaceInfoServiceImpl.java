package com.jy.moudles.examPlaceInfo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.examPlaceInfo.dao.ExamPlaceInfoDao;
import com.jy.moudles.examPlaceInfo.entity.ExamPlaceInfo;
import com.jy.moudles.examPlaceInfo.service.ExamPlaceInfoService;

/** 
 * ExamPlaceInfo业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-04
 */
@Service
public class ExamPlaceInfoServiceImpl implements ExamPlaceInfoService {

	@Autowired
	private ExamPlaceInfoDao ExamPlaceInfoDao;
	
	@Override
	public void insertExamPlaceInfo(ExamPlaceInfo ExamPlaceInfo){
		ExamPlaceInfo.setId(UUIDUtil.get32UUID());
		ExamPlaceInfoDao.insertExamPlaceInfo(ExamPlaceInfo);
	}
	
	@Override
	public void updateExamPlaceInfo(ExamPlaceInfo ExamPlaceInfo){
		ExamPlaceInfoDao.updateExamPlaceInfo(ExamPlaceInfo);
	}
	
	@Override
	public ExamPlaceInfo getExamPlaceInfoById(String id){
		return ExamPlaceInfoDao.getExamPlaceInfoById(id);
	}
	
	@Override
	public List<ExamPlaceInfo> queryExamPlaceInfosFilter(Map<String, Object> filter){
		return ExamPlaceInfoDao.queryExamPlaceInfosFilter(filter);
	}
	
	@Override
	public void deleteExamPlaceInfoByExamNo(String ExamNo){
		ExamPlaceInfoDao.deleteExamPlaceInfoByExamNo(ExamNo);
	}
	
	@Override
	public void deleteExamPlaceInfos(List<String> ids){
		ExamPlaceInfoDao.deleteExamPlaceInfos(ids);
	}

	@Override
	public void insertExamPlaceInfos(List<ExamPlaceInfo> list) {
		ExamPlaceInfoDao.insertExamPlaceInfos(list);
	}
	
}

