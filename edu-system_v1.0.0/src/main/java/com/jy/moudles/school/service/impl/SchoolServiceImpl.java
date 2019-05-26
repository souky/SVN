package com.jy.moudles.school.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.school.dao.SchoolDao;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.school.service.SchoolService;

/** 
 * school业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private SchoolDao schooldao;
	
	@Override
	public void insertSchool(School school){
		school.setId(UUIDUtil.get32UUID());
		schooldao.insertSchool(school);
	}
	
	@Override
	public void updateSchool(School school){
		schooldao.updateSchool(school);
	}
	
	@Override
	public School getSchoolById(String id){
		return schooldao.getSchoolById(id);
	}
	
	@Override
	public List<School> querySchoolsFilter(Map<String, Object> filter){
		return schooldao.querySchoolsFilter(filter);
	}
	
	@Override
	public void deleteSchoolById(String id){
		schooldao.deleteSchoolById(id);
	}
	
	@Override
	public void deleteSchools(List<String> ids){
		schooldao.deleteSchools(ids);
	}

	@Override
	public List<String> getSubjectById(String schoolId) {
		return schooldao.getSubjectById(schoolId);
	}
	
	@Override
	public School getSchoolByOrgId(String orgId) {
		return schooldao.getSchoolByOrgId(orgId);
	}
}

