package com.jy.moudles.subjectAblity.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.subjectAblity.dao.SubjectAblityDao;
import com.jy.moudles.subjectAblity.entity.SubjectAblity;
import com.jy.moudles.subjectAblity.service.SubjectAblityService;

/** 
 * subjectAblity业务实现类
 * 创建人：1
 * 创建时间：2018-01-08
 */
@Service
public class SubjectAblityServiceImpl implements SubjectAblityService {

	@Autowired
	private SubjectAblityDao subjectAblitydao;
	
	@Override
	public void insertSubjectAblity(String[] list,String subject,String orgId){
		//删除原有表内容
		subjectAblitydao.deleteSubjectAblityBySubject(subject, orgId);
		int soft = 1;
		for(String s : list) {
			SubjectAblity subjectAblity = new SubjectAblity();
			subjectAblity.setAblityName(s);
			subjectAblity.setOrgId(orgId);
			subjectAblity.setSubjectName(subject);
			subjectAblity.setCreateDate(new Date());
			subjectAblity.setSoft(soft);
			subjectAblity.setId(UUIDUtil.get32UUID());
			subjectAblitydao.insertSubjectAblity(subjectAblity);
			soft++;
		}
		
	}
	
	@Override
	public void updateSubjectAblity(SubjectAblity subjectAblity){
		subjectAblitydao.updateSubjectAblity(subjectAblity);
	}
	
	@Override
	public SubjectAblity getSubjectAblityById(String id){
		return subjectAblitydao.getSubjectAblityById(id);
	}
	
	@Override
	public List<SubjectAblity> querySubjectAblitysFilter(Map<String, Object> filter){
		return subjectAblitydao.querySubjectAblitysFilter(filter);
	}
	
	@Override
	public void deleteSubjectAblityById(String id){
		subjectAblitydao.deleteSubjectAblityById(id);
	}
	
	@Override
	public void deleteSubjectAblitys(List<String> ids){
		subjectAblitydao.deleteSubjectAblitys(ids);
	}
	
	@Override
	public void deleteSubjectAblityBySubject(String subjectName, String orgId) {
		// TODO Auto-generated method stub
		subjectAblitydao.deleteSubjectAblityBySubject(subjectName, orgId);
	}
	
}

