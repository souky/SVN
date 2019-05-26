package com.jy.moudles.score.entityVO;

import java.math.BigDecimal;
import java.util.List;

public class DetailVO {

	
	private String itemNo;
	
	private int itemType;
	
	private BigDecimal score;
	
	private BigDecimal totalScore;
	
	private List<DetailVO> listStep;

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public BigDecimal getScore() {
		return score;
	}

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
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

	public List<DetailVO> getListStep() {
		return listStep;
	}

	public void setListStep(List<DetailVO> listStep) {
		this.listStep = listStep;
	}
	
	
}
