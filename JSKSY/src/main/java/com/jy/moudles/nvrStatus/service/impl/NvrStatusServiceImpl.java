package com.jy.moudles.nvrStatus.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.nvrStatus.dao.NvrStatusDao;
import com.jy.moudles.nvrStatus.entity.NvrStatus;
import com.jy.moudles.nvrStatus.service.NvrStatusService;

@Service
public class NvrStatusServiceImpl implements NvrStatusService {

	@Autowired
	private NvrStatusDao NvrStatusDao;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return NvrStatusDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(NvrStatus record) {
		record.setId(UUIDUtil.get32UUID());
		return NvrStatusDao.insert(record);
	}

	@Override
	public NvrStatus selectByPrimaryKey(String id) {
		return NvrStatusDao.selectByPrimaryKey(id);
	}

	@Override
	public List<NvrStatus> selectAll() {
		return NvrStatusDao.selectAll();
	}

	@Override
	public int updateByPrimaryKey(NvrStatus record) {
		return NvrStatusDao.updateByPrimaryKey(record);
	}

	@Override
	public List<NvrStatus> queryNvrStatusFilter(Map<String, Object> filter) {
		return NvrStatusDao.queryNvrStatusFilter(filter);
	}

	
}

