package com.jy.moudles.spDetailStep.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.spDetailStep.dao.SpDetailStepDao;
import com.jy.moudles.spDetailStep.entity.SpDetailStep;
import com.jy.moudles.spDetailStep.service.SpDetailStepService;
import com.jy.moudles.twoWaySpecificationDetail.entity.TwoWaySpecificationDetail;

/** 
 * spDetailStep业务实现类
 * 创建人：1
 * 创建时间：2018-01-08
 */
@Service
public class SpDetailStepServiceImpl implements SpDetailStepService {

	@Autowired
	private SpDetailStepDao spDetailStepdao;
	
	@Override
	public void insertSpDetailStep(SpDetailStep spDetailStep){
		spDetailStep.setId(UUIDUtil.get32UUID());
		spDetailStepdao.insertSpDetailStep(spDetailStep);
	}
	
	@Override
	public void updateSpDetailStep(SpDetailStep spDetailStep){
		spDetailStepdao.updateSpDetailStep(spDetailStep);
	}
	
	@Override
	public SpDetailStep getSpDetailStepById(String id){
		return spDetailStepdao.getSpDetailStepById(id);
	}
	
	@Override
	public List<SpDetailStep> querySpDetailStepsFilter(Map<String, Object> filter){
		return spDetailStepdao.querySpDetailStepsFilter(filter);
	}
	
	@Override
	public void deleteSpDetailStepById(String id){
		spDetailStepdao.deleteSpDetailStepById(id);
	}
	
	@Override
	public void deleteSpDetailSteps(List<String> ids){
		spDetailStepdao.deleteSpDetailSteps(ids);
	}
	
	@Override
	public void insertSpDetailStepList(List<SpDetailStep> list, TwoWaySpecificationDetail ee) {
		if(null != list && list.size() > 0) {
			int i = 1;
			for(SpDetailStep e : list) {
				e.setSpDetailId(ee.getId());
				e.setSpId(ee.getParentId());
				e.setId(UUIDUtil.get32UUID());
				e.setSort(i+"");
				i++;
			}
		}
		spDetailStepdao.insertSpDetailStepList(list);
	}
}

