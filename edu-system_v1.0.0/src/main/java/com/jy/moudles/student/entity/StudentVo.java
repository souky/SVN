package com.jy.moudles.student.entity;

import java.util.List;

public class StudentVo {
	
	private String classroomName;
	
	private List<Student> list;

	public String getClassroomName() {
		return classroomName;
	}

	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}

	public List<Student> getList() {
		return list;
	}

	public void setList(List<Student> list) {
		this.list = list;
	}
	
	
}
