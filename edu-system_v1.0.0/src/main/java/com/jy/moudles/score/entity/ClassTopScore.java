package com.jy.moudles.score.entity;

public class ClassTopScore {

    private String classroomId;

    private String classroomName;

    /**
     * 该班级全校前十名人数
     */
    private int classTopTenStuNum;

    /**
     * 该班级全校前二十名人数
     */
    private int classTopTwentyStuNum;

    /**
     * 该班级全校前五十名人数
     */
    private int classTopFiftyStuNum;

    /**
     * 该班级全校前一百名人数
     */
    private int classTopOneHundredStuNum;

    /**
     * 该班级全校前二百名人数
     */
    private int classTopTwoHundredStuNum;

    /**
     * 该班级全校前五百名人数
     */
    private int classTopFiveHundredStuNum;

    private int classTopOneThousandStuNum;

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public int getClassTopTenStuNum() {
        return classTopTenStuNum;
    }

    public void setClassTopTenStuNum(int classTopTenStuNum) {
        this.classTopTenStuNum = classTopTenStuNum;
    }

    public int getClassTopTwentyStuNum() {
        return classTopTwentyStuNum;
    }

    public void setClassTopTwentyStuNum(int classTopTwentyStuNum) {
        this.classTopTwentyStuNum = classTopTwentyStuNum;
    }

    public int getClassTopFiftyStuNum() {
        return classTopFiftyStuNum;
    }

    public void setClassTopFiftyStuNum(int classTopFiftyStuNum) {
        this.classTopFiftyStuNum = classTopFiftyStuNum;
    }

    public int getClassTopOneHundredStuNum() {
        return classTopOneHundredStuNum;
    }

    public void setClassTopOneHundredStuNum(int classTopOneHundredStuNum) {
        this.classTopOneHundredStuNum = classTopOneHundredStuNum;
    }

    public int getClassTopTwoHundredStuNum() {
        return classTopTwoHundredStuNum;
    }

    public void setClassTopTwoHundredStuNum(int classTopTwoHundredStuNum) {
        this.classTopTwoHundredStuNum = classTopTwoHundredStuNum;
    }

    public int getClassTopFiveHundredStuNum() {
        return classTopFiveHundredStuNum;
    }

    public void setClassTopFiveHundredStuNum(int classTopFiveHundredStuNum) {
        this.classTopFiveHundredStuNum = classTopFiveHundredStuNum;
    }

    public int getClassTopOneThousandStuNum() {
        return classTopOneThousandStuNum;
    }

    public void setClassTopOneThousandStuNum(int classTopOneThousandStuNum) {
        this.classTopOneThousandStuNum = classTopOneThousandStuNum;
    }
}
