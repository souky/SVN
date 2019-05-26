package com.jy.moudles.osdSettings.dao;

import java.util.List;
import java.util.Map;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.osdSettings.entity.OsdSettings;
@MyBatisDao
public interface OsdSettingsDao {
    int deleteByPrimaryKey(String id);

    int insert(OsdSettings record);

    OsdSettings selectByPrimaryKey(String id);

    List<OsdSettings> selectAll();

    int updateByPrimaryKey(OsdSettings record);

	List<OsdSettings> queryOsdSettingsFilter(Map<String, Object> filter);
}