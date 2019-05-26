package com.jy.moudles.score.entity;

import com.jy.common.entity.BaseEntity;

import java.math.BigDecimal;

public class Score extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 考试ID
	 */
	private String examId;
	/**
	 * 全校考试名次
	 */
	private int ranking;

	/**
	 * 班级考试名次
	 */
	private int classRanking;

	/**
	 * 进退步名次
	 */
	private int changedRanking;

	private int isImproved;

	/**
	 * 学生ID
	 */
	private String studentId;

	private String studentNo;

	private String studentName;

	/**
	 * 班级ID
	 */
	private String classroomId;
	
	/**
	 * 班级名称
	 */
	private String classroomName;

	/**
	 * 组织机构ID
	 */
	private String orgId;
	
	/**
	 * 区域code
	 */
	private String regionCode;

	/**
	 * 学校ID
	 */
	private String schoolId;
	
	/**
	 * 总分
	 */
	private BigDecimal totalScore;

	/**
	 * 各科目得分细则
	 */
	private String detailScore;
	
	/**
	 * 语文分数
	 */
	private BigDecimal chineseScore;

	/**
	 * 语文code
	 */
	private BigDecimal code1;

	/**
	 * 数学分数
	 */
	private BigDecimal mathScore;

	/**
	 * 数学code
	 */
	private BigDecimal code2;
	
	/**
	 * 英语分数
	 */
	private BigDecimal englishScore;

	/**
	 * 英语code
	 */
	private BigDecimal code3;

	/**
	 * 物理分数
	 */
	private BigDecimal physicalScore;

	/**
	 * 物理code
	 */
	private BigDecimal code4;

	/**
	 * 化学分数
	 */
	private BigDecimal chemicalScore;

	/**
	 * 化学code
	 */
	private BigDecimal code5;

	/**
	 * 生物分数
	 */
	private BigDecimal biologyScore;

	/**
	 * 生物code
	 */
	private BigDecimal code6;

	/**
	 * 地理分数
	 */
	private BigDecimal geographyScore;

	/**
	 * 地理code
	 */
	private BigDecimal code7;

	/**
	 * 科学分数
	 */
	private BigDecimal scienceScore;

	/**
	 * 科学code
	 */
	private BigDecimal code8;

	/**
	 * 政治分数
	 */
	private BigDecimal politicsScore;

	/**
	 * 政治code
	 */
	private BigDecimal code9;

	/**
	 * 历史分数
	 */
	private BigDecimal historyScore;

	/**
	 * 历史code
	 */
	private BigDecimal code10;

	/**
	 * 思想品德分数
	 */
	private BigDecimal moralScore;

	/**
	 * 思想品德等级
	 */
	private BigDecimal code11;

	/**
	 * 历史与社会分数
	 */
	private BigDecimal historySocietyScore;

	/**
	 * 历史与社会code
	 */
	private BigDecimal code12;

	/**
	 * 品德与社会分数
	 */
	private BigDecimal qualitySocietyScore;

	/**
	 * 品德与社会code
	 */
	private BigDecimal code13;

	/**
	 * 品德与生活分数
	 */
	private BigDecimal qualityLifeScore;

	/**
	 * 品德与生活code
	 */
	private BigDecimal code14;

	/**
	 * 美术分数
	 */
	private BigDecimal paintingScore;

	/**
	 * 美术code
	 */
	private BigDecimal code15;

	/**
	 * 艺术分数
	 */
	private BigDecimal artScore;

	/**
	 * 艺术code
	 */
	private BigDecimal code16;

	/**
	 * 音乐分数
	 */
	private BigDecimal musicScore;

	/**
	 * 音乐code
	 */
	private BigDecimal code17;

	/**
	 * 体育分数
	 */
	private BigDecimal sportsScore;

	/**
	 * 体育code
	 */
	private BigDecimal code18;

	/**
	 * 备用字段1
	 */
	private BigDecimal subject1Score;

	/**
	 * 备用字段2
	 */
	private BigDecimal subject2Score;
	
	/**
	 * 备用字段3
	 */
	private BigDecimal subject3Score;
	
	/**
	 * 备用字段4
	 */
	private BigDecimal subject4Score;
	
	/**
	 * 备用字段5
	 */
	private BigDecimal subject5Score;

	public String getClassroomName() {
		return classroomName;
	}

	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}
	
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getClassroomId() {
		return classroomId;
	}

	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	
	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}
	
	public String getDetailScore() {
		return detailScore;
	}

	public void setDetailScore(String detailScore) {
		this.detailScore = detailScore;
	}
	
	public BigDecimal getChineseScore() {
		return chineseScore;
	}

	public void setChineseScore(BigDecimal chineseScore) {
		this.chineseScore = chineseScore;
		this.code1 = chineseScore;
	}
	
	public BigDecimal getMathScore() {
		return mathScore;
	}

	public void setMathScore(BigDecimal mathScore) {
		this.mathScore = mathScore;
		this.code2 = mathScore;
	}
	
	public BigDecimal getEnglishScore() {
		return englishScore;
	}

	public void setEnglishScore(BigDecimal englishScore) {
		this.englishScore = englishScore;
		this.code3 = englishScore;
	}
	
	public BigDecimal getPhysicalScore() {
		return physicalScore;
	}

	public void setPhysicalScore(BigDecimal physicalScore) {
		this.physicalScore = physicalScore;
		this.code4 = physicalScore;
	}
	
	public BigDecimal getChemicalScore() {
		return chemicalScore;
	}

	public void setChemicalScore(BigDecimal chemicalScore) {
		this.chemicalScore = chemicalScore;
		this.code5 = chemicalScore;
	}
	
	public BigDecimal getBiologyScore() {
		return biologyScore;
	}

	public void setBiologyScore(BigDecimal biologyScore) {
		this.biologyScore = biologyScore;
		this.code6 = biologyScore;
	}
	
	public BigDecimal getGeographyScore() {
		return geographyScore;
	}

	public void setGeographyScore(BigDecimal geographyScore) {
		this.geographyScore = geographyScore;
		this.code7 = geographyScore;
	}
	
	public BigDecimal getScienceScore() {
		return scienceScore;
	}

	public void setScienceScore(BigDecimal scienceScore) {
		this.scienceScore = scienceScore;
		this.code8 = scienceScore;
	}
	
	public BigDecimal getPoliticsScore() {
		return politicsScore;
	}

	public void setPoliticsScore(BigDecimal politicsScore) {
		this.politicsScore = politicsScore;
		this.code9 = politicsScore;
	}
	
	public BigDecimal getHistoryScore() {
		return historyScore;
	}

	public void setHistoryScore(BigDecimal historyScore) {
		this.historyScore = historyScore;
		this.code10 = historyScore;
	}
	
	public BigDecimal getMoralScore() {
		return moralScore;
	}

	public void setMoralScore(BigDecimal moralScore) {
		this.moralScore = moralScore;
		this.code11 = moralScore;
	}
	
	public BigDecimal getHistorySocietyScore() {
		return historySocietyScore;
	}

	public void setHistorySocietyScore(BigDecimal historySocietyScore) {
		this.historySocietyScore = historySocietyScore;
		this.code12 = historySocietyScore;
	}
	
	public BigDecimal getQualitySocietyScore() {
		return qualitySocietyScore;
	}

	public void setQualitySocietyScore(BigDecimal qualitySocietyScore) {
		this.qualitySocietyScore = qualitySocietyScore;
		this.code13 = qualitySocietyScore;
	}
	
	public BigDecimal getQualityLifeScore() {
		return qualityLifeScore;
	}

	public void setQualityLifeScore(BigDecimal qualityLifeScore) {
		this.qualityLifeScore = qualityLifeScore;
		this.code14 = qualityLifeScore;
	}
	
	public BigDecimal getPaintingScore() {
		return paintingScore;
	}

	public void setPaintingScore(BigDecimal paintingScore) {
		this.paintingScore = paintingScore;
		this.code15 = paintingScore;
	}
	
	public BigDecimal getArtScore() {
		return artScore;
	}

	public void setArtScore(BigDecimal artScore) {
		this.artScore = artScore;
		this.code16 = artScore;
	}
	
	public BigDecimal getMusicScore() {
		return musicScore;
	}

	public void setMusicScore(BigDecimal musicScore) {
		this.musicScore = musicScore;
		this.code17 = musicScore;
	}
	
	public BigDecimal getSportsScore() {
		return sportsScore;
	}

	public void setSportsScore(BigDecimal sportsScore) {
		this.sportsScore = sportsScore;
		this.code18 = sportsScore;
	}
	
	public BigDecimal getSubject1Score() {
		return subject1Score;
	}

	public void setSubject1Score(BigDecimal subject1Score) {
		this.subject1Score = subject1Score;
	}
	
	public BigDecimal getSubject2Score() {
		return subject2Score;
	}

	public void setSubject2Score(BigDecimal subject2Score) {
		this.subject2Score = subject2Score;
	}
	
	public BigDecimal getSubject3Score() {
		return subject3Score;
	}

	public void setSubject3Score(BigDecimal subject3Score) {
		this.subject3Score = subject3Score;
	}
	
	public BigDecimal getSubject4Score() {
		return subject4Score;
	}

	public void setSubject4Score(BigDecimal subject4Score) {
		this.subject4Score = subject4Score;
	}
	
	public BigDecimal getSubject5Score() {
		return subject5Score;
	}

	public void setSubject5Score(BigDecimal subject5Score) {
		this.subject5Score = subject5Score;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public int getChangedRanking() {
		return changedRanking;
	}

	public void setChangedRanking(int changedRanking) {
		this.changedRanking = changedRanking;
	}

	public int getIsImproved() {
		return isImproved;
	}

	public void setIsImproved(int isImproved) {
		this.isImproved = isImproved;
	}

	public BigDecimal getCode1() {
		return code1;
	}

	public void setCode1(BigDecimal code1) {
		this.code1 = code1;
	}

	public BigDecimal getCode2() {
		return code2;
	}

	public void setCode2(BigDecimal code2) {
		this.code2 = code2;
	}

	public BigDecimal getCode3() {
		return code3;
	}

	public void setCode3(BigDecimal code3) {
		this.code3 = code3;
	}

	public BigDecimal getCode4() {
		return code4;
	}

	public void setCode4(BigDecimal code4) {
		this.code4 = code4;
	}

	public BigDecimal getCode5() {
		return code5;
	}

	public void setCode5(BigDecimal code5) {
		this.code5 = code5;
	}

	public BigDecimal getCode6() {
		return code6;
	}

	public void setCode6(BigDecimal code6) {
		this.code6 = code6;
	}

	public BigDecimal getCode7() {
		return code7;
	}

	public void setCode7(BigDecimal code7) {
		this.code7 = code7;
	}

	public BigDecimal getCode8() {
		return code8;
	}

	public void setCode8(BigDecimal code8) {
		this.code8 = code8;
	}

	public BigDecimal getCode9() {
		return code9;
	}

	public void setCode9(BigDecimal code9) {
		this.code9 = code9;
	}

	public BigDecimal getCode10() {
		return code10;
	}

	public void setCode10(BigDecimal code10) {
		this.code10 = code10;
	}

	public BigDecimal getCode11() {
		return code11;
	}

	public void setCode11(BigDecimal code11) {
		this.code11 = code11;
	}

	public BigDecimal getCode12() {
		return code12;
	}

	public void setCode12(BigDecimal code12) {
		this.code12 = code12;
	}

	public BigDecimal getCode13() {
		return code13;
	}

	public void setCode13(BigDecimal code13) {
		this.code13 = code13;
	}

	public BigDecimal getCode14() {
		return code14;
	}

	public void setCode14(BigDecimal code14) {
		this.code14 = code14;
	}

	public BigDecimal getCode15() {
		return code15;
	}

	public void setCode15(BigDecimal code15) {
		this.code15 = code15;
	}

	public BigDecimal getCode16() {
		return code16;
	}

	public void setCode16(BigDecimal code16) {
		this.code16 = code16;
	}

	public BigDecimal getCode17() {
		return code17;
	}

	public void setCode17(BigDecimal code17) {
		this.code17 = code17;
	}

	public BigDecimal getCode18() {
		return code18;
	}

	public void setCode18(BigDecimal code18) {
		this.code18 = code18;
	}

	public int getClassRanking() {
		return classRanking;
	}

	public void setClassRanking(int classRanking) {
		this.classRanking = classRanking;
	}
}



