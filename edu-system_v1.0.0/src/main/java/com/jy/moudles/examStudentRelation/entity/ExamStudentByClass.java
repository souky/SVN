package com.jy.moudles.examStudentRelation.entity;

import java.util.ArrayList;
import java.util.List;

public class ExamStudentByClass {

    String classroom;

    List<ExamStudentRelation> examStudentRelationList = new ArrayList<>();

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public List<ExamStudentRelation> getExamStudentRelationList() {
        return examStudentRelationList;
    }

    public void setExamStudentRelationList(List<ExamStudentRelation> examStudentRelationList) {
        this.examStudentRelationList = examStudentRelationList;
    }
}
