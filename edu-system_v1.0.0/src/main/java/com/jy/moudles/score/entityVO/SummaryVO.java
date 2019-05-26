package com.jy.moudles.score.entityVO;

public class SummaryVO {

    /**
     * 学生报告－总分－学科均衡 标准分总结
     */
    private String stuSubjBalanceST;

    /**
     * 学生报告－总分－学科均衡 得分率总结
     */
    private String stuSubjBalancePR;

    /**
     * 班级报告－总分－班级总成绩 总结
     */
    private String classTotalScore;

    /**
     * 班级报告－总分－分数等级分布图 总结
     */
    private String scoreLevelDistr;

    /**
     * 教研报告－各班级科目报告单 总结
     */
    private String eachClassSubjScore;

    /**
     * 教研报告／学校报告－各班平均分对比 总结
     */
    private String subjAvgComparation;

    /**
     * 教研报告／学校报告－各班级优秀率／及格率对比 总结
     */
    private String classesLevelDistri;

    /**
     * 学校报告－贡献率 总结
     */
    private String contribution;

    /**
     * 学生报告－科目－下次考试可以增加？分 总结1
     */
    private String nextTimeAddScore;

   /**
     * 学生报告－科目－下次考试可以增加？分 总结2
     */
    private String promoteForNextTime;

    /**
     * 难度二维分析
     */
    private String difficultyAnalysis;

    /**
     * 能力点分析
     */
    private String abilityAnalysis;

    /**
     * 知识点分析
     */
    private String knowledgeAnalysis;

    public String getKnowledgeAnalysis() {
        return knowledgeAnalysis;
    }

    public void setKnowledgeAnalysis(String knowledgeAnalysis) {
        this.knowledgeAnalysis = knowledgeAnalysis;
    }

    public String getAbilityAnalysis() {
        return abilityAnalysis;
    }

    public void setAbilityAnalysis(String abilityAnalysis) {
        this.abilityAnalysis = abilityAnalysis;
    }

    public String getDifficultyAnalysis() {
        return difficultyAnalysis;
    }

    public void setDifficultyAnalysis(String difficultyAnalysis) {
        this.difficultyAnalysis = difficultyAnalysis;
    }

    public String getPromoteForNextTime() {
        return promoteForNextTime;
    }

    public void setPromoteForNextTime(String promoteForNextTime) {
        this.promoteForNextTime = promoteForNextTime;
    }

    public String getNextTimeAddScore() {
        return nextTimeAddScore;
    }

    public void setNextTimeAddScore(String nextTimeAddScore) {
        this.nextTimeAddScore = nextTimeAddScore;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public String getClassesLevelDistri() {
        return classesLevelDistri;
    }

    public void setClassesLevelDistri(String classesLevelDistri) {
        this.classesLevelDistri = classesLevelDistri;
    }

    public String getEachClassSubjScore() {
        return eachClassSubjScore;
    }

    public void setEachClassSubjScore(String eachClassSubjScore) {
        this.eachClassSubjScore = eachClassSubjScore;
    }

    public String getSubjAvgComparation() {
        return subjAvgComparation;
    }

    public void setSubjAvgComparation(String subjAvgComparation) {
        this.subjAvgComparation = subjAvgComparation;
    }

    public String getScoreLevelDistr() {
        return scoreLevelDistr;
    }

    public void setScoreLevelDistr(String scoreLevelDistr) {
        this.scoreLevelDistr = scoreLevelDistr;
    }

    public String getClassTotalScore() {
        return classTotalScore;
    }

    public void setClassTotalScore(String classTotalScore) {
        this.classTotalScore = classTotalScore;
    }

    public String getStuSubjBalanceST() {
        return stuSubjBalanceST;
    }

    public void setStuSubjBalanceST(String stuSubjBalanceST) {
        this.stuSubjBalanceST = stuSubjBalanceST;
    }

    public String getStuSubjBalancePR() {
        return stuSubjBalancePR;
    }

    public void setStuSubjBalancePR(String stuSubjBalancePR) {
        this.stuSubjBalancePR = stuSubjBalancePR;
    }
}
