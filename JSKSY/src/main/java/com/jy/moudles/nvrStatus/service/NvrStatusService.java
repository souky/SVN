package com.jy.moudles.nvrStatus.service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.nvrStatus.entity.NvrStatus;

public interface NvrStatusService {
    int deleteByPrimaryKey(String id);

    int insert(NvrStatus record);

    NvrStatus selectByPrimaryKey(String id);

    List<NvrStatus> selectAll();

    int updateByPrimaryKey(NvrStatus record);

	List<NvrStatus> queryNvrStatusFilter(Map<String, Object> filter);
}