package com.jy.moudles.sipStatus.service;

import java.util.List;
import java.util.Map;

import com.jy.moudles.sipStatus.entity.SipStatus;

public interface SipStatusService {
    int deleteByPrimaryKey(String id);

    int insert(SipStatus record);

    SipStatus selectByPrimaryKey(String id);

    List<SipStatus> selectAll();

    int updateByPrimaryKey(SipStatus record);
    
    public List<SipStatus> querySipStatusFilter(Map<String, Object> filter);
}