package com.jy.moudles.score.entity;

import java.math.BigDecimal;

public class ScoreVO {
	//率前三为1
	private String type;
	//记录集合的下标
	private Integer index;
    private String classroomId;

    private String classroomName;

    private String classTeacherName;

    private String subject;

    private BigDecimal score;

    private int fullMarks;

    private String scoreLevel;

    private BigDecimal classAvgScore;

    private BigDecimal classSubjectAvgScore;

    private BigDecimal schoolAvgScore;

    private BigDecimal regionAvgScore;

    private BigDecimal classTopScore;

    private BigDecimal schoolTopScore;

    private BigDecimal regionTopScore;

    private BigDecimal classMinScore;

    private BigDecimal schoolMinScore;

    private BigDecimal regionMinScore;

    /**
     * 班级 总分标准分
     */
    private BigDecimal classTotalStandardScore;

    /**
     * 班级 科目标准分
     */
    private BigDecimal classSubjectStandardScore;

    /**
     * 班级总分 标准差
     */
    private BigDecimal classTotalStandardDeviation;

    /**
     * 班级科目 标准差
     */
    private BigDecimal classSubjectStandardDeviation;

    /**
     * 班级总分 差异系数：分化率
     */
    private String classTotalDiffCoefficient;

    /**
     * 班级科目 差异系数：分化率
     */
    private String classSubjectDiffCoefficient;

    /**
     * 贡献率
     */
    private BigDecimal contribution;

    //highrate 高分率
    private BigDecimal highRate;

    //excellentrate 优秀率
    private BigDecimal excellentRate;

    //commissionrate 良好率
    private BigDecimal commissionRate;

    //passrate 及格率
    private BigDecimal passRate;

    //failurerate 不及格率
    private BigDecimal failureRate;

    //班级分化率
    private String classDiffCoefficient;
    //校级分化率
    private String schoolDiffCoefficient;
    //区级分化率
    private String regionDiffCoefficient;

    private int classRanking;

    private int schoolRanking;

    private int regionRanking;

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public int getFullMarks() {
        return fullMarks;
    }

    public void setFullMarks(int fullMarks) {
        this.fullMarks = fullMarks;
    }

    public String getScoreLevel() {
        return scoreLevel;
    }

    public void setScoreLevel(String scoreLevel) {
        this.scoreLevel = scoreLevel;
    }

    public BigDecimal getSchoolAvgScore() {
        return schoolAvgScore;
    }

    public void setSchoolAvgScore(BigDecimal schoolAvgScore) {
        this.schoolAvgScore = schoolAvgScore;
    }

    public BigDecimal getClassTopScore() {
        return classTopScore;
    }

    public void setClassTopScore(BigDecimal classTopScore) {
        this.classTopScore = classTopScore;
    }

    public BigDecimal getSchoolTopScore() {
        return schoolTopScore;
    }

    public void setSchoolTopScore(BigDecimal schoolTopScore) {
        this.schoolTopScore = schoolTopScore;
    }

    public BigDecimal getRegionTopScore() {
        return regionTopScore;
    }

    public void setRegionTopScore(BigDecimal regionTopScore) {
        this.regionTopScore = regionTopScore;
    }

    public BigDecimal getClassAvgScore() {
        return classAvgScore;
    }

    public void setClassAvgScore(BigDecimal classAvgScore) {
        this.classAvgScore = classAvgScore;
    }

    public BigDecimal getRegionAvgScore() {
        return regionAvgScore;
    }

    public void setRegionAvgScore(BigDecimal regionAvgScore) {
        this.regionAvgScore = regionAvgScore;
    }

    public BigDecimal getHighRate() {
        return highRate;
    }

    public void setHighRate(BigDecimal highRate) {
        this.highRate = highRate;
    }

    public BigDecimal getExcellentRate() {
        return excellentRate;
    }

    public void setExcellentRate(BigDecimal excellentRate) {
        this.excellentRate = excellentRate;
    }

    public BigDecimal getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(BigDecimal commissionRate) {
        this.commissionRate = commissionRate;
    }

    public BigDecimal getPassRate() {
        return passRate;
    }

    public void setPassRate(BigDecimal passRate) {
        this.passRate = passRate;
    }

    public BigDecimal getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(BigDecimal failureRate) {
        this.failureRate = failureRate;
    }

    public BigDecimal getClassTotalStandardScore() {
        return classTotalStandardScore;
    }

    public void setClassTotalStandardScore(BigDecimal classTotalStandardScore) {
        this.classTotalStandardScore = classTotalStandardScore;
    }

    public BigDecimal getClassSubjectStandardScore() {
        return classSubjectStandardScore;
    }

    public void setClassSubjectStandardScore(BigDecimal classSubjectStandardScore) {
        this.classSubjectStandardScore = classSubjectStandardScore;
    }

    public BigDecimal getClassTotalStandardDeviation() {
        return classTotalStandardDeviation;
    }

    public void setClassTotalStandardDeviation(BigDecimal classTotalStandardDeviation) {
        this.classTotalStandardDeviation = classTotalStandardDeviation;
    }

    public BigDecimal getClassSubjectStandardDeviation() {
        return classSubjectStandardDeviation;
    }

    public void setClassSubjectStandardDeviation(BigDecimal classSubjectStandardDeviation) {
        this.classSubjectStandardDeviation = classSubjectStandardDeviation;
    }

    public String getClassTotalDiffCoefficient() {
        return classTotalDiffCoefficient;
    }

    public void setClassTotalDiffCoefficient(String classTotalDiffCoefficient) {
        this.classTotalDiffCoefficient = classTotalDiffCoefficient;
    }

    public String getClassSubjectDiffCoefficient() {
        return classSubjectDiffCoefficient;
    }

    public void setClassSubjectDiffCoefficient(String classSubjectDiffCoefficient) {
        this.classSubjectDiffCoefficient = classSubjectDiffCoefficient;
    }

    public BigDecimal getClassMinScore() {
        return classMinScore;
    }

    public void setClassMinScore(BigDecimal classMinScore) {
        this.classMinScore = classMinScore;
    }

    public String getClassTeacherName() {
        return classTeacherName;
    }

    public void setClassTeacherName(String classTeacherName) {
        this.classTeacherName = classTeacherName;
    }

    public BigDecimal getContribution() {
        return contribution;
    }

    public void setContribution(BigDecimal contribution) {
        this.contribution = contribution;
    }

    public BigDecimal getClassSubjectAvgScore() {
        return classSubjectAvgScore;
    }

    public void setClassSubjectAvgScore(BigDecimal classSubjectAvgScore) {
        this.classSubjectAvgScore = classSubjectAvgScore;
    }

    public BigDecimal getRegionMinScore() {
        return regionMinScore;
    }

    public void setRegionMinScore(BigDecimal regionMinScore) {
        this.regionMinScore = regionMinScore;
    }

    public BigDecimal getSchoolMinScore() {
        return schoolMinScore;
    }

    public void setSchoolMinScore(BigDecimal schoolMinScore) {
        this.schoolMinScore = schoolMinScore;
    }

    public String getClassDiffCoefficient() {
        return classDiffCoefficient;
    }

    public void setClassDiffCoefficient(String classDiffCoefficient) {
        this.classDiffCoefficient = classDiffCoefficient;
    }

    public String getSchoolDiffCoefficient() {
        return schoolDiffCoefficient;
    }

    public void setSchoolDiffCoefficient(String schoolDiffCoefficient) {
        this.schoolDiffCoefficient = schoolDiffCoefficient;
    }

    public String getRegionDiffCoefficient() {
        return regionDiffCoefficient;
    }

    public void setRegionDiffCoefficient(String regionDiffCoefficient) {
        this.regionDiffCoefficient = regionDiffCoefficient;
    }

    public int getClassRanking() {
        return classRanking;
    }

    public void setClassRanking(int classRanking) {
        this.classRanking = classRanking;
    }

    public int getSchoolRanking() {
        return schoolRanking;
    }

    public void setSchoolRanking(int schoolRanking) {
        this.schoolRanking = schoolRanking;
    }

    public int getRegionRanking() {
        return regionRanking;
    }

    public void setRegionRanking(int regionRanking) {
        this.regionRanking = regionRanking;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
