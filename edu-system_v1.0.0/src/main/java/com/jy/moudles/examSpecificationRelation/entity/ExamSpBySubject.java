package com.jy.moudles.examSpecificationRelation.entity;

import java.util.ArrayList;
import java.util.List;

public class ExamSpBySubject {

    private String subject;

    private List<ExamSpecificationRelation> examSpecificationRelations = new ArrayList<>();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<ExamSpecificationRelation> getExamSpecificationRelations() {
        return examSpecificationRelations;
    }

    public void setExamSpecificationRelations(List<ExamSpecificationRelation> examSpecificationRelations) {
        this.examSpecificationRelations = examSpecificationRelations;
    }
}
