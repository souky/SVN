package com.jy.moudles.twoWaySpecification.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.twoWaySpecification.dao.TwoWaySpecificationDao;
import com.jy.moudles.twoWaySpecification.entity.TwoWaySpecification;
import com.jy.moudles.twoWaySpecification.service.TwoWaySpecificationService;

/** 
 * twoWaySpecification业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class TwoWaySpecificationServiceImpl implements TwoWaySpecificationService {

	@Autowired
	private TwoWaySpecificationDao twoWaySpecificationdao;
	
	@Override
	public void insertTwoWaySpecification(TwoWaySpecification twoWaySpecification){
		twoWaySpecificationdao.insertTwoWaySpecification(twoWaySpecification);
	}
	
	@Override
	public void updateTwoWaySpecification(TwoWaySpecification twoWaySpecification){
		twoWaySpecificationdao.updateTwoWaySpecification(twoWaySpecification);
	}
	
	@Override
	public TwoWaySpecification getTwoWaySpecificationById(String id){
		return twoWaySpecificationdao.getTwoWaySpecificationById(id);
	}
	
	@Override
	public List<TwoWaySpecification> queryTwoWaySpecificationsFilter(Map<String, Object> filter){
		return twoWaySpecificationdao.queryTwoWaySpecificationsFilter(filter);
	}

	@Override
	public List<TwoWaySpecification> queryTotalTwoWaySpecificationsFilter(Map<String, Object> filter) {
		return twoWaySpecificationdao.queryTotalTwoWaySpecificationsFilter(filter);
	}
	@Override
	public List<TwoWaySpecification> queryTwoWaySpecificationsByExamIdAndSubject(Map<String, Object> filter) {
		return twoWaySpecificationdao.queryTwoWaySpecificationsByExamIdAndSubject(filter);
	}

	@Override
	public void deleteTwoWaySpecificationById(String id){
		twoWaySpecificationdao.deleteTwoWaySpecificationById(id);
	}
	
	@Override
	public void deleteTwoWaySpecifications(List<String> ids){
		twoWaySpecificationdao.deleteTwoWaySpecifications(ids);
	}

	@Override
	public List<TwoWaySpecification> queryTwoWaySpecificationsFilterByCode(Map<String, Object> filter) {
		return twoWaySpecificationdao.queryTwoWaySpecificationsFilterByCode(filter);
	}
	
}

