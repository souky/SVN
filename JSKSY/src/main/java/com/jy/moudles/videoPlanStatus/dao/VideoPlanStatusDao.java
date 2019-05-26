package com.jy.moudles.videoPlanStatus.dao;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.videoPlanStatus.entity.VideoPlanStatus;

import java.util.List;
import java.util.Map;
@MyBatisDao
public interface VideoPlanStatusDao {
    int deleteByPrimaryKey(String id);

    int insert(VideoPlanStatus record);

    VideoPlanStatus selectByPrimaryKey(String id);

    List<VideoPlanStatus> selectAll();

    int updateByPrimaryKey(VideoPlanStatus record);

	List<VideoPlanStatus> queryVideoPlanStatusFilter(Map<String, Object> filter);
}