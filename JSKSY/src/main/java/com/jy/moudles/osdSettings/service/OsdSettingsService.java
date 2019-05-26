package com.jy.moudles.osdSettings.service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.osdSettings.entity.OsdSettings;

public interface OsdSettingsService {
    int deleteByPrimaryKey(String id);

    int insert(OsdSettings record);

    OsdSettings selectByPrimaryKey(String id);

    List<OsdSettings> selectAll();

    int updateByPrimaryKey(OsdSettings record);

	List<OsdSettings> queryOsdSettingsFilter(Map<String, Object> filter);
}