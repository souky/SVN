package com.jy.moudles.region.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.region.dao.RegionDao;
import com.jy.moudles.region.entity.Region;
import com.jy.moudles.region.service.RegionService;

/** 
 * region业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionDao regiondao;

	@Override
	public Region getRegionByCode(String regionCode){
		return regiondao.getRegionByCode(regionCode);
	}

	@Override
	public Region getRegionById(String id) {
		return regiondao.getRegionById(id);
	}

	@Override
	public List<Region> queryRegionsFilter(Map<String, Object> filter){
		return regiondao.queryRegionsFilter(filter);
	}

}

