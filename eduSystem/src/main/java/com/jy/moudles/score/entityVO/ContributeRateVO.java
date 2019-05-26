package com.jy.moudles.score.entityVO;

import java.math.BigDecimal;

public class ContributeRateVO {
	
	/**
	 * 教师名称
	 * */
	private String teacherName;
	
	/**
	 * 班级名称
	 * */
	private String classroom;
	
	/**
	 * 总分平均分
	 * */
	private BigDecimal avgTotalScore;
	
	/**
	 * 科目平均分
	 * */
	private BigDecimal classAvgScore;
	
	/**
	 * 科目标准分
	 * */
	private BigDecimal standardTSubject;
	
	/**
	 * 班级总分标准分
	 * */
	private BigDecimal standardTByClass;
	
	/**
	 * 贡献率
	 * */
	private BigDecimal contributeRateSubject;

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public BigDecimal getAvgTotalScore() {
		return avgTotalScore;
	}

	public void setAvgTotalScore(BigDecimal avgTotalScore) {
		this.avgTotalScore = avgTotalScore;
	}

	public BigDecimal getClassAvgScore() {
		return classAvgScore;
	}

	public void setClassAvgScore(BigDecimal classAvgScore) {
		this.classAvgScore = classAvgScore;
	}

	public BigDecimal getStandardTSubject() {
		return standardTSubject;
	}

	public void setStandardTSubject(BigDecimal standardTSubject) {
		this.standardTSubject = standardTSubject;
	}

	public BigDecimal getStandardTByClass() {
		return standardTByClass;
	}

	public void setStandardTByClass(BigDecimal standardTByClass) {
		this.standardTByClass = standardTByClass;
	}

	public BigDecimal getContributeRateSubject() {
		return contributeRateSubject;
	}

	public void setContributeRateSubject(BigDecimal contributeRateSubject) {
		this.contributeRateSubject = contributeRateSubject;
	}
	
	
	
	
}
