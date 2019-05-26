package com.jy.moudles.osdSettings.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.osdSettings.dao.OsdSettingsDao;
import com.jy.moudles.osdSettings.entity.OsdSettings;
import com.jy.moudles.osdSettings.service.OsdSettingsService;

@Service
public class OsdSettingsServiceImpl implements OsdSettingsService {

	@Autowired
	private OsdSettingsDao OsdSettingsDao;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return OsdSettingsDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(OsdSettings record) {
		record.setId(UUIDUtil.get32UUID());
		return OsdSettingsDao.insert(record);
	}

	@Override
	public OsdSettings selectByPrimaryKey(String id) {
		return OsdSettingsDao.selectByPrimaryKey(id);
	}

	@Override
	public List<OsdSettings> selectAll() {
		return OsdSettingsDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(OsdSettings record) {
		return OsdSettingsDao.updateByPrimaryKey(record);
	}

	@Override
	public List<OsdSettings> queryOsdSettingsFilter(
			Map<String, Object> filter) {
		return OsdSettingsDao.queryOsdSettingsFilter(filter);
	}
	
}

