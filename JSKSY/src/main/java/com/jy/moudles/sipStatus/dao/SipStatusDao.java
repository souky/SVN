package com.jy.moudles.sipStatus.dao;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.sipStatus.entity.SipStatus;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface SipStatusDao{
    int deleteByPrimaryKey(String id);

    int insert(SipStatus record);

    SipStatus selectByPrimaryKey(String id);

    List<SipStatus> selectAll();

    int updateByPrimaryKey(SipStatus record);

	List<SipStatus> querySipStatusFilter(Map<String, Object> filter);
}