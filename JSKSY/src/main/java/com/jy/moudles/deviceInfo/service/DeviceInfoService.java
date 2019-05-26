package com.jy.moudles.deviceInfo.service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.deviceInfo.entity.DeviceInfo;

public interface DeviceInfoService {
    int deleteByPrimaryKey(String id);

    int insert(DeviceInfo record);

    DeviceInfo selectByPrimaryKey(String id);

    List<DeviceInfo> selectAll();

    int updateByPrimaryKey(DeviceInfo record);
    
    public List<DeviceInfo> queryDeviceInfoFilter(Map<String, Object> filter);
}