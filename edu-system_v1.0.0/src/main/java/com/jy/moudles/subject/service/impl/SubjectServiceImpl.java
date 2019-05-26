package com.jy.moudles.subject.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.subject.dao.SubjectDao;
import com.jy.moudles.subject.entity.Subject;
import com.jy.moudles.subject.service.SubjectService;

/** 
 * subject业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDao subjectdao;
	
	@Override
	public void insertSubject(Subject subject){
		subject.setId(UUIDUtil.get32UUID());
		subjectdao.insertSubject(subject);
	}
	
	@Override
	public void updateSubject(Subject subject){
		subjectdao.updateSubject(subject);
	}
	
	@Override
	public Subject getSubjectById(String id){
		return subjectdao.getSubjectById(id);
	}
	
	@Override
	public List<Subject> querySubjectsFilter(Map<String, Object> filter){
		return subjectdao.querySubjectsFilter(filter);
	}
	
	@Override
	public void deleteSubjectById(String id){
		subjectdao.deleteSubjectById(id);
	}
	
	@Override
	public void deleteSubjects(List<String> ids){
		subjectdao.deleteSubjects(ids);
	}
	public List<Subject> querySubjectsByNames(List<String> list){
		return subjectdao.querySubjectsByNames(list);
	}
	
}

