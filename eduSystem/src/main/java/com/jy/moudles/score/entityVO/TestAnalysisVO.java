package com.jy.moudles.score.entityVO;

import java.math.BigDecimal;

/**
 * 试题分析
 * */
public class TestAnalysisVO {
	
	/**
	 * 试题序号
	 * */
	private String qid;
	/**
	 * 题型
	 * */
	private String topic;
	/**
	 * 分值
	 * */
	private int fractionalValue;
	/**
	 * 平均分
	 * */
	private BigDecimal divide;
	/**
	 * 个人得分率
	 * */
	private String divideStudent;
	/**
	 * 班级得分率
	 * */
	private String divideClass;
	/**
	 * 校级得分率
	 * */
	private String divideSchool;
	/**
	 * 区县得分率
	 * */
	private String divideAera;
	/**
	 * 个人得分率对班级
	 * */
	private String divideStudentToClass;
	/**
	 * 个人得分率对校级
	 * */
	private String divideStudentToSchool;
	/**
	 * 个人得分率对区级
	 * */
	private String divideStudentToArea;
	/**
	 * 满分人数
	 * */
	private int trueNum;
	/**
	 * 答错人数
	 * */
	private int falseNum;
	/**
	 * 难度
	 * */
	private BigDecimal difficulty;
	/**
	 * 区分度
	 * */
	private BigDecimal differentiation;
	/**
	 * 知识点
	 * */
	private String knowPoint;
	/**
	 * 能力值
	 * */
	private String ablity;
	/**
	 * 个人分值
	 * */
	private BigDecimal studentScore;
	/**
	 * 个人是否满分
	 * */
	private String rightOrNot;
	
	
	
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getFractionalValue() {
		return fractionalValue;
	}
	public void setFractionalValue(int fractionalValue) {
		this.fractionalValue = fractionalValue;
	}
	public BigDecimal getDivide() {
		return divide;
	}
	public void setDivide(BigDecimal divide) {
		this.divide = divide;
	}
	public String getDivideClass() {
		return divideClass;
	}
	public void setDivideClass(String divideClass) {
		this.divideClass = divideClass;
	}
	public String getDivideSchool() {
		return divideSchool;
	}
	public void setDivideSchool(String divideSchool) {
		this.divideSchool = divideSchool;
	}
	public String getDivideAera() {
		return divideAera;
	}
	public void setDivideAera(String divideAera) {
		this.divideAera = divideAera;
	}
	public int getTrueNum() {
		return trueNum;
	}
	public void setTrueNum(int trueNum) {
		this.trueNum = trueNum;
	}
	public int getFalseNum() {
		return falseNum;
	}
	public void setFalseNum(int falseNum) {
		this.falseNum = falseNum;
	}
	public BigDecimal getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(BigDecimal difficulty) {
		this.difficulty = difficulty;
	}
	public BigDecimal getDifferentiation() {
		return differentiation;
	}
	public void setDifferentiation(BigDecimal differentiation) {
		this.differentiation = differentiation;
	}
	public String getKnowPoint() {
		return knowPoint;
	}
	public void setKnowPoint(String knowPoint) {
		this.knowPoint = knowPoint;
	}
	public String getAblity() {
		return ablity;
	}
	public void setAblity(String ablity) {
		this.ablity = ablity;
	}
	public BigDecimal getStudentScore() {
		return studentScore;
	}
	public void setStudentScore(BigDecimal studentScore) {
		this.studentScore = studentScore;
	}
	public String getRightOrNot() {
		return rightOrNot;
	}
	public void setRightOrNot(String rightOrNot) {
		this.rightOrNot = rightOrNot;
	}
	public String getDivideStudent() {
		return divideStudent;
	}
	public void setDivideStudent(String divideStudent) {
		this.divideStudent = divideStudent;
	}
	public String getDivideStudentToClass() {
		return divideStudentToClass;
	}
	public void setDivideStudentToClass(String divideStudentToClass) {
		this.divideStudentToClass = divideStudentToClass;
	}
	public String getDivideStudentToSchool() {
		return divideStudentToSchool;
	}
	public void setDivideStudentToSchool(String divideStudentToSchool) {
		this.divideStudentToSchool = divideStudentToSchool;
	}
	public String getDivideStudentToArea() {
		return divideStudentToArea;
	}
	public void setDivideStudentToArea(String divideStudentToArea) {
		this.divideStudentToArea = divideStudentToArea;
	}
	
	
	
	
}
