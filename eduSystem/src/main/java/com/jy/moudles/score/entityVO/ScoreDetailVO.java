package com.jy.moudles.score.entityVO;

import java.util.List;

public class ScoreDetailVO {
	
	private String subject;
	
	private List<DetailVO> detailList;
	
	private String stuId;
	

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<DetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<DetailVO> detailList) {
		this.detailList = detailList;
	}

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	
	
}
