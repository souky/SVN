package com.jy.moudles.score.entityVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class KnowledgeAnalysisVO {

	//总分
	private BigDecimal totle;
	//知识点得分
	private BigDecimal score;
	//个人得分率
	private BigDecimal divideStudent;
	//班级得分率
	private BigDecimal divideClass;
	//校级得分率
	private BigDecimal divideSchool;
	//区县得分率
	private BigDecimal divideAera;
	
	private List<KnowDetailVO> knowDetail;
	
	public KnowledgeAnalysisVO() {
		this.totle = BigDecimal.ZERO;
		this.totle = BigDecimal.ZERO;
		this.knowDetail = new ArrayList<>();
	}
	
	public BigDecimal getTotle() {
		return totle;
	}
	public void setTotle(BigDecimal totle) {
		this.totle = totle;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
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
	public List<KnowDetailVO> getKnowDetail() {
		return knowDetail;
	}
	public void setKnowDetail(List<KnowDetailVO> knowDetail) {
		this.knowDetail = knowDetail;
	}
	
	
}
