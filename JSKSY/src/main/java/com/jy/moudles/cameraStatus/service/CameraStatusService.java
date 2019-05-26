package com.jy.moudles.cameraStatus.service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.cameraStatus.entity.CameraStatus;

public interface CameraStatusService {
    int deleteByPrimaryKey(String id);

    int insert(CameraStatus record);

    CameraStatus selectByPrimaryKey(String id);

    List<CameraStatus> selectAll();

    int updateByPrimaryKey(CameraStatus record);

	List<CameraStatus> queryCameraStatusFilter(Map<String, Object> filter);
}