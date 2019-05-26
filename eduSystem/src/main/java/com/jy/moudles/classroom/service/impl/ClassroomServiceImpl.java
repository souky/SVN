package com.jy.moudles.classroom.service.impl;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.classroom.dao.ClassroomDao;
import com.jy.moudles.classroom.entity.Classroom;
import com.jy.moudles.classroom.service.ClassroomService;
import com.jy.moudles.classroom.service.utils.ClassroomUtil;
import com.jy.moudles.school.dao.SchoolDao;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/** l
 * classroom业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class ClassroomServiceImpl implements ClassroomService {

	@Autowired
	private ClassroomDao classroomdao;
	
	@Autowired
	private SchoolDao schoolDao;
	
	@Override
	public void insertClassroom(User user, Classroom classroom){
		
		School school = schoolDao.getSchoolByOrgId(user.getOrgId());
		
		if (null != school) {
			classroom.setSchoolId(school.getId());
		} else {
			return;
		}
		
		int[] gradeAndClass = ClassroomUtil.getGradeAndClassByClassroomName(classroom.getClassroomName());
		classroom.setGradeCode(gradeAndClass[ClassroomUtil.GRADE_INDEX]);
		classroom.setClassCode(gradeAndClass[ClassroomUtil.CLASS_INDEX]);
		classroom.setOrgId(user.getOrgId());
		classroom.setId(UUIDUtil.get32UUID());
		classroom.setCreateUser(user.getUserName());
		classroom.setCreateDate(new Date());
		classroom.setUpdateUser(user.getUserName());
		classroom.setUpdateDate(new Date());
		
		classroomdao.insertClassroom(classroom);
		
	}
	
	@Override
	public void updateClassroom(Classroom classroom){
		classroomdao.updateClassroom(classroom);
	}
	
	@Override
	public Classroom getClassroomById(String id){
		return classroomdao.getClassroomById(id);
	}
	
	@Override
	public Classroom getClassroomByIdNew(String id){
		return classroomdao.getClassroomByIdNew(id);
	}
	
	@Override
	public Classroom getClassroomByName(String name, String orgId) {
		return classroomdao.getClassroomByName(name,orgId);
	}

	@Override
	public List<Classroom> queryClassroomsFilter(Map<String, Object> filter){
		return classroomdao.queryClassroomsFilter(filter);
	}

	@Override
	public List<Classroom> queryClassroomsFilterNew(Map<String, Object> filter) {
		return classroomdao.queryClassroomsFilterNew(filter);
	}

	@Override
	public void deleteClassroomById(String id){
		classroomdao.deleteClassroomById(id);
	}
	
	@Override
	public void deleteClassrooms(List<String> ids){
		classroomdao.deleteClassrooms(ids);
	}
	
	@Override
	public List<Classroom> queryClassroomsByGrade(Classroom classroom) {
		return classroomdao.queryClassroomsByGrade(classroom);
	}
	
	@Override
	public List<Classroom> getClassroomByNames(List<String> list,String orgId) {
		return classroomdao.getClassroomByNames(list,orgId);
	}
}

