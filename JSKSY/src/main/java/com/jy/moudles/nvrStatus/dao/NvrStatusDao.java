package com.jy.moudles.nvrStatus.dao;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.nvrStatus.entity.NvrStatus;

import java.util.List;
import java.util.Map;
@MyBatisDao
public interface NvrStatusDao {
    int deleteByPrimaryKey(String id);

    int insert(NvrStatus record);

    NvrStatus selectByPrimaryKey(String id);

    List<NvrStatus> selectAll();

    int updateByPrimaryKey(NvrStatus record);

	List<NvrStatus> queryNvrStatusFilter(Map<String, Object> filter);
}