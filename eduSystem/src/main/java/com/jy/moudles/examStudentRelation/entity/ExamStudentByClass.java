package com.jy.moudles.examStudentRelation.entity;

import java.util.ArrayList;
import java.util.List;

public class ExamStudentByClass {

    String classroomName;

    List<ExamStudentRelation> list = new ArrayList<>();

    

    public String getClassroomName() {
		return classroomName;
	}

	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}

	public List<ExamStudentRelation> getList() {
        return list;
    }

    public void setList(List<ExamStudentRelation> list) {
        this.list = list;
    }
}
