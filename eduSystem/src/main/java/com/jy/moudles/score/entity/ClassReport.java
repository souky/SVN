package com.jy.moudles.score.entity;

import java.math.BigDecimal;

public class ClassReport {

    private String name;

    private String classScore;

    private String schoolScore;

    private String regionScore;

    private BigDecimal classScoreNum;

    private BigDecimal schoolScoreNum;

    private BigDecimal regionScoreNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassScore() {
        return classScore;
    }

    public void setClassScore(String classScore) {
        this.classScore = classScore;
    }

    public String getSchoolScore() {
        return schoolScore;
    }

    public void setSchoolScore(String schoolScore) {
        this.schoolScore = schoolScore;
    }

    public String getRegionScore() {
        return regionScore;
    }

    public void setRegionScore(String regionScore) {
        this.regionScore = regionScore;
    }

    public BigDecimal getClassScoreNum() {
        return classScoreNum;
    }

    public void setClassScoreNum(BigDecimal classScoreNum) {
        this.classScoreNum = classScoreNum;
    }

    public BigDecimal getSchoolScoreNum() {
        return schoolScoreNum;
    }

    public void setSchoolScoreNum(BigDecimal schoolScoreNum) {
        this.schoolScoreNum = schoolScoreNum;
    }

    public BigDecimal getRegionScoreNum() {
        return regionScoreNum;
    }

    public void setRegionScoreNum(BigDecimal regionScoreNum) {
        this.regionScoreNum = regionScoreNum;
    }
}
