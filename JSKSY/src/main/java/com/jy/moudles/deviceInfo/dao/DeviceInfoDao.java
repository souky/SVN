package com.jy.moudles.deviceInfo.dao;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.deviceInfo.entity.DeviceInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DeviceInfoDao {
    int deleteByPrimaryKey(String id);

    int insert(DeviceInfo record);

    DeviceInfo selectByPrimaryKey(String id);

    List<DeviceInfo> selectAll();

    int updateByPrimaryKey(DeviceInfo record);

	List<DeviceInfo> queryDeviceInfoFilter(Map<String, Object> filter);
}