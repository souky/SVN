package com.jy.moudles.knowledgePoint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.knowledgePoint.dao.KnowledgePointDao;
import com.jy.moudles.knowledgePoint.entity.KnowledgePoint;
import com.jy.moudles.knowledgePoint.service.KnowledgePointService;

/** 
 * knowledgePoint业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class KnowledgePointServiceImpl implements KnowledgePointService {

	@Autowired
	private KnowledgePointDao knowledgePointdao;
	
	@Override
	public void insertKnowledgePoint(KnowledgePoint knowledgePoint){
		knowledgePoint.setId(UUIDUtil.get32UUID());
		knowledgePointdao.insertKnowledgePoint(knowledgePoint);
	}
	
	@Override
	public void updateKnowledgePoint(KnowledgePoint knowledgePoint){
		knowledgePointdao.updateKnowledgePoint(knowledgePoint);
	}
	
	@Override
	public KnowledgePoint getKnowledgePointById(String id){
		return knowledgePointdao.getKnowledgePointById(id);
	}
	
	@Override
	public List<KnowledgePoint> queryKnowledgePointsFilter(Map<String, Object> filter){
		
		return knowledgePointdao.queryKnowledgePointsFilter(filter);
	}
	
	@Override
	public void deleteKnowledgePointById(String id){
		knowledgePointdao.deleteKnowledgePointById(id);
	}
	
	@Override
	public void deleteKnowledgePoints(List<String> ids){
		knowledgePointdao.deleteKnowledgePoints(ids);
	}
	
}

