package com.jy.moudles.spDetailKnowledgeRelation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.spDetailKnowledgeRelation.dao.SpDetailKnowledgeRelationDao;
import com.jy.moudles.spDetailKnowledgeRelation.entity.SpDetailKnowledgeRelation;
import com.jy.moudles.spDetailKnowledgeRelation.service.SpDetailKnowledgeRelationService;

/** 
 * spDetailKnowledgeRelation业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class SpDetailKnowledgeRelationServiceImpl implements SpDetailKnowledgeRelationService {

	@Autowired
	private SpDetailKnowledgeRelationDao spDetailKnowledgeRelationdao;
	
	@Override
	public void insertSpDetailKnowledgeRelation(SpDetailKnowledgeRelation spDetailKnowledgeRelation){
		spDetailKnowledgeRelation.setId(UUIDUtil.get32UUID());
		spDetailKnowledgeRelationdao.insertSpDetailKnowledgeRelation(spDetailKnowledgeRelation);
	}
	
	@Override
	public void updateSpDetailKnowledgeRelation(SpDetailKnowledgeRelation spDetailKnowledgeRelation){
		spDetailKnowledgeRelationdao.updateSpDetailKnowledgeRelation(spDetailKnowledgeRelation);
	}
	
	@Override
	public SpDetailKnowledgeRelation getSpDetailKnowledgeRelationById(String id){
		return spDetailKnowledgeRelationdao.getSpDetailKnowledgeRelationById(id);
	}
	
	@Override
	public List<SpDetailKnowledgeRelation> querySpDetailKnowledgeRelationsFilter(Map<String, Object> filter){
		return spDetailKnowledgeRelationdao.querySpDetailKnowledgeRelationsFilter(filter);
	}
	
	@Override
	public void deleteSDKRelationBySpDetailId(String spDetailId){
		spDetailKnowledgeRelationdao.deleteSDKRelationBySpDetailId(spDetailId);
	}
	
	@Override
	public void deleteSpDetailKnowledgeRelations(List<String> ids){
		spDetailKnowledgeRelationdao.deleteSpDetailKnowledgeRelations(ids);
	}
	
}

