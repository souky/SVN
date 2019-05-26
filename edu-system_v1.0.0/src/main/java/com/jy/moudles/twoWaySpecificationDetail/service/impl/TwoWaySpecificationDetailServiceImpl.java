package com.jy.moudles.twoWaySpecificationDetail.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.moudles.spDetailStep.entity.SpDetailStep;
import com.jy.moudles.twoWaySpecificationDetail.dao.TwoWaySpecificationDetailDao;
import com.jy.moudles.twoWaySpecificationDetail.entity.TwoWaySpecificationDetail;
import com.jy.moudles.twoWaySpecificationDetail.service.TwoWaySpecificationDetailService;

/** 
 * twoWaySpecificationDetail业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class TwoWaySpecificationDetailServiceImpl implements TwoWaySpecificationDetailService {

	@Autowired
	private TwoWaySpecificationDetailDao twoWaySpecificationDetaildao;
	
	@Override
	public void insertTwoWaySpecificationDetail(TwoWaySpecificationDetail twoWaySpecificationDetail){
		twoWaySpecificationDetaildao.insertTwoWaySpecificationDetail(twoWaySpecificationDetail);
	}

	@Override
	public void batchInsertTwoWaySpecificationDetail(List<TwoWaySpecificationDetail> twoWaySpecificationDetails) {
		twoWaySpecificationDetaildao.batchInsertTwoWaySpecificationDetail(twoWaySpecificationDetails);
	}

	@Override
	public void updateTwoWaySpecificationDetail(TwoWaySpecificationDetail twoWaySpecificationDetail){
		twoWaySpecificationDetaildao.updateTwoWaySpecificationDetail(twoWaySpecificationDetail);
	}
	
	@Override
	public TwoWaySpecificationDetail getTwoWaySpecificationDetailById(String id){
		return twoWaySpecificationDetaildao.getTwoWaySpecificationDetailById(id);
	}
	
	@Override
	public List<TwoWaySpecificationDetail> queryTwoWaySpecificationDetailsFilter(Map<String, Object> filter){
		List<TwoWaySpecificationDetail> list = twoWaySpecificationDetaildao.queryTwoWaySpecificationDetailsFilter(filter);
		for(TwoWaySpecificationDetail e : list) {
			String knows = "";
			List<SpDetailStep> lists = e.getStepList();
			if(null != lists && lists.size() > 0) {
				for(int i = 0;i < lists.size();i++) {
					String names = lists.get(i).getKnowledgePointName();
					if(StringUtils.isNotBlank(names)) {
						if(knows.length() == 0) {
							knows += names;
						}else {
							knows += "," + names;
						}
					}
				}
				e.setKnowledgeName(knows);
			}
		}
		return list;
	}
	
	@Override
	public void deleteTwoWaySpecificationDetailById(String id){
		twoWaySpecificationDetaildao.deleteTwoWaySpecificationDetailById(id);
	}

	@Override
	public void deleteTwoWaySpDetailsByPid(String parentId) {
		twoWaySpecificationDetaildao.deleteTwoWaySpDetailsByPid(parentId);
	}

	@Override
	public void deleteTwoWaySpecificationDetails(List<String> ids){
		twoWaySpecificationDetaildao.deleteTwoWaySpecificationDetails(ids);
	}
	
}

