package com.jy.moudles.cameraStatus.dao;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.cameraStatus.entity.CameraStatus;

import java.util.List;
import java.util.Map;
@MyBatisDao
public interface CameraStatusDao {
    int deleteByPrimaryKey(String id);

    int insert(CameraStatus record);

    CameraStatus selectByPrimaryKey(String id);

    List<CameraStatus> selectAll();

    int updateByPrimaryKey(CameraStatus record);

	List<CameraStatus> queryCameraStatusFilter(Map<String, Object> filter);
}