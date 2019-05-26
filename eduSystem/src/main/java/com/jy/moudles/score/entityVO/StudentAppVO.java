package com.jy.moudles.score.entityVO;

import java.math.BigDecimal;

public class StudentAppVO {

    private String label;

    private BigDecimal classScore;

    private BigDecimal schoolScore;

    private BigDecimal regionScore;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getClassScore() {
        return classScore;
    }

    public void setClassScore(BigDecimal classScore) {
        this.classScore = classScore;
    }

    public BigDecimal getSchoolScore() {
        return schoolScore;
    }

    public void setSchoolScore(BigDecimal schoolScore) {
        this.schoolScore = schoolScore;
    }

    public BigDecimal getRegionScore() {
        return regionScore;
    }

    public void setRegionScore(BigDecimal regionScore) {
        this.regionScore = regionScore;
    }
}
