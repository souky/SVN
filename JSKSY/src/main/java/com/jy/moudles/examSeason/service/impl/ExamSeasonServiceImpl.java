package com.jy.moudles.examSeason.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.examSeason.dao.ExamSeasonDao;
import com.jy.moudles.examSeason.entity.ExamSeason;
import com.jy.moudles.examSeason.service.ExamSeasonService;

/** 
 * ExamSeason业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Service
public class ExamSeasonServiceImpl implements ExamSeasonService {

	@Autowired
	private ExamSeasonDao ExamSeasonDao;
	
	@Override
	public void insertExamSeason(ExamSeason ExamSeason){
		ExamSeason.setId(UUIDUtil.get32UUID());
		ExamSeasonDao.insertExamSeason(ExamSeason);
	}
	
	@Override
	public void updateExamSeason(ExamSeason ExamSeason){
		ExamSeasonDao.updateExamSeason(ExamSeason);
	}
	
	@Override
	public ExamSeason getExamSeasonById(String id){
		return ExamSeasonDao.getExamSeasonById(id);
	}
	
	@Override
	public List<ExamSeason> queryExamSeasonsFilter(Map<String, Object> filter){
		return ExamSeasonDao.queryExamSeasonsFilter(filter);
	}
	
	@Override
	public void deleteExamSeasonByExamNo(String ExamNo){
		ExamSeasonDao.deleteExamSeasonByExamNo(ExamNo);
	}
	
	@Override
	public void deleteExamSeasons(List<String> ids){
		ExamSeasonDao.deleteExamSeasons(ids);
	}

	@Override
	public void insertExamSeasons(List<ExamSeason> examSeasons) {
		ExamSeasonDao.insertExamSeasons(examSeasons);
	}
	
}

