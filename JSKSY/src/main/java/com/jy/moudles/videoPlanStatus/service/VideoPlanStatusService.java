package com.jy.moudles.videoPlanStatus.service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.videoPlanStatus.entity.VideoPlanStatus;

public interface VideoPlanStatusService {
    int deleteByPrimaryKey(String id);

    int insert(VideoPlanStatus record);

    VideoPlanStatus selectByPrimaryKey(String id);

    List<VideoPlanStatus> selectAll();

    int updateByPrimaryKey(VideoPlanStatus record);

	List<VideoPlanStatus> queryVideoPlanStatusFilter(Map<String, Object> filter);
}