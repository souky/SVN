package com.jy.moudles.score.entityVO;

import java.math.BigDecimal;

public class KnowDetailVO {
	
	//对应题目
	private String qid;
	//知识点模块
	private String knowledgemodule;
	//知识点
	private String knowledge;
	//个人得分率
	private BigDecimal divideStudent;
	//班级得分率
	private BigDecimal divideClass;
	//校级得分率
	private BigDecimal divideSchool;
	//区县得分率
	private BigDecimal divideAera;
	//差值
	private BigDecimal differenceOfDivide;
	//知识点个数
	private int knowledgeNum;
	
	
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getKnowledgemodule() {
		return knowledgemodule;
	}
	public void setKnowledgemodule(String knowledgemodule) {
		this.knowledgemodule = knowledgemodule;
	}
	public String getKnowledge() {
		return knowledge;
	}
	public void setKnowledge(String knowledge) {
		this.knowledge = knowledge;
	}
	public int getKnowledgeNum() {
		return knowledgeNum;
	}
	public void setKnowledgeNum(int knowledgeNum) {
		this.knowledgeNum = knowledgeNum;
	}
	public BigDecimal getDivideStudent() {
		return divideStudent;
	}
	public void setDivideStudent(BigDecimal divideStudent) {
		this.divideStudent = divideStudent;
	}
	public BigDecimal getDivideClass() {
		return divideClass;
	}
	public void setDivideClass(BigDecimal divideClass) {
		this.divideClass = divideClass;
	}
	public BigDecimal getDivideSchool() {
		return divideSchool;
	}
	public void setDivideSchool(BigDecimal divideSchool) {
		this.divideSchool = divideSchool;
	}
	public BigDecimal getDivideAera() {
		return divideAera;
	}
	public void setDivideAera(BigDecimal divideAera) {
		this.divideAera = divideAera;
	}
	public BigDecimal getDifferenceOfDivide() {
		return differenceOfDivide;
	}
	public void setDifferenceOfDivide(BigDecimal differenceOfDivide) {
		this.differenceOfDivide = differenceOfDivide;
	}
	
	
	
}
