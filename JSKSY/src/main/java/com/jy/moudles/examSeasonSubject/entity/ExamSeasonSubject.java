package com.jy.moudles.examSeasonSubject.entity;
import com.jy.common.entity.BaseEntity;

public class ExamSeasonSubject extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7286255249480464886L;

	/**
	 * 考试场次id
	 */
	private String examSeasonId;
	
	/**
	 * 考试场次编号
	 */
	private String examSeasonNo;
	
	/**
	 * 科目编号
	 */
	private String kmbh;
	
	/**
	 * 开始日期
	 */
	private String kmkssj;
	
	/**
	 * 结束日期
	 */
	private String kmjssj;
	
	/**
	 * 科目名称
	 */
	private String kmmc;
	
	
	public String getExamSeasonId() {
		return examSeasonId;
	}

	public void setExamSeasonId(String examSeasonId) {
		this.examSeasonId = examSeasonId;
	}
	
	public String getExamSeasonNo() {
		return examSeasonNo;
	}

	public void setExamSeasonNo(String examSeasonNo) {
		this.examSeasonNo = examSeasonNo;
	}
	
	public String getKmbh() {
		return kmbh;
	}

	public void setKmbh(String kmbh) {
		this.kmbh = kmbh;
	}
	
	public String getKmkssj() {
		return kmkssj;
	}

	public void setKmkssj(String kmkssj) {
		this.kmkssj = kmkssj;
	}
	
	public String getKmjssj() {
		return kmjssj;
	}

	public void setKmjssj(String kmjssj) {
		this.kmjssj = kmjssj;
	}
	
	public String getKmmc() {
		return kmmc;
	}

	public void setKmmc(String kmmc) {
		this.kmmc = kmmc;
	}
	
}



