package com.jy.moudles.examSeason.entity;
import java.util.List;

import com.jy.common.entity.BaseEntity;
import com.jy.moudles.examSeasonSubject.entity.ExamSeasonSubject;

public class ExamSeason extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3533381556340577486L;

	/**
	 * 考试计划id
	 */
	private String examId;
	
	/**
	 * 考试计划编号
	 */
	private String examNo;
	
	/**
	 * 场次编号
	 */
	private String ccbh;
	
	/**
	 * 开始日期
	 */
	private String cckssj;
	
	/**
	 * 结束日期
	 */
	private String ccjssj;
	
	/**
	 * 场次名称
	 */
	private String ccmc;
	
	/**
	 * 科目合集
	 */
	private List<ExamSeasonSubject> km;
	
	
	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	public String getExamNo() {
		return examNo;
	}

	public void setExamNo(String examNo) {
		this.examNo = examNo;
	}
	
	public String getCcbh() {
		return ccbh;
	}

	public void setCcbh(String ccbh) {
		this.ccbh = ccbh;
	}
	
	public String getCckssj() {
		return cckssj;
	}

	public void setCckssj(String cckssj) {
		this.cckssj = cckssj;
	}
	
	public String getCcjssj() {
		return ccjssj;
	}

	public void setCcjssj(String ccjssj) {
		this.ccjssj = ccjssj;
	}
	
	public String getCcmc() {
		return ccmc;
	}

	public void setCcmc(String ccmc) {
		this.ccmc = ccmc;
	}

	public List<ExamSeasonSubject> getKm() {
		return km;
	}

	public void setKm(List<ExamSeasonSubject> km) {
		this.km = km;
	}
	
}



