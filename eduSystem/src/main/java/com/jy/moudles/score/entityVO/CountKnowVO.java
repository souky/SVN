package com.jy.moudles.score.entityVO;

import java.math.BigDecimal;
import java.util.List;

public class CountKnowVO {

	private List<DetailVO> listSchool;
	
	private List<DetailVO> listClass;
	
	private DetailVO studentDetailVO;
	
	private String qid;
	
	private BigDecimal score;
	
	private BigDecimal totalScore;
	
	private int index;
	
	public CountKnowVO copy() {
		CountKnowVO cv = new CountKnowVO();
		cv.setListSchool(this.listSchool);
		cv.setListClass(this.listClass);
		cv.setStudentDetailVO(this.studentDetailVO);
		cv.setQid(this.qid);
		cv.setScore(this.score);
		cv.setTotalScore(this.totalScore);
		return cv;
	}

	public List<DetailVO> getListSchool() {
		return listSchool;
	}

	public void setListSchool(List<DetailVO> listSchool) {
		this.listSchool = listSchool;
	}

	public List<DetailVO> getListClass() {
		return listClass;
	}

	public void setListClass(List<DetailVO> listClass) {
		this.listClass = listClass;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public DetailVO getStudentDetailVO() {
		return studentDetailVO;
	}

	public void setStudentDetailVO(DetailVO studentDetailVO) {
		this.studentDetailVO = studentDetailVO;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}
	
	
}
