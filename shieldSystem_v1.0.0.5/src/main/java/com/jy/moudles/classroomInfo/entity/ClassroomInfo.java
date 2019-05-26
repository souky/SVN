package com.jy.moudles.classroomInfo.entity;

import java.util.List;

import com.jy.common.entity.BaseEntity;

public class ClassroomInfo extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -310453306368023296L;

	/**
     * 楼的名称字符串,存库及查询
     */
    private String buildingNames;

    /**
     * 楼的名称list  用于返回给前端
     */
    private List<String> buildingNameList;

	/**
	 * 楼的数量
	 */
	private int buildingNo;
	
	/**
	 * 每栋楼的层数
	 */
	private int floorNo;

	/**
	 * 每层的教室数量
	 */
	private int classroomNo;

    public String getBuildingNames() {
        return buildingNames;
    }

    public void setBuildingNames(String buildingNames) {
        this.buildingNames = buildingNames;
    }

    public List<String> getBuildingNameList() {
        return buildingNameList;
    }

    public void setBuildingNameList(List<String> buildingNameList) {
        this.buildingNameList = buildingNameList;
    }

    public int getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(int buildingNo) {
		this.buildingNo = buildingNo;
	}
	
	public int getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(int floorNo) {
		this.floorNo = floorNo;
	}
	
	public int getClassroomNo() {
		return classroomNo;
	}

	public void setClassroomNo(int classroomNo) {
		this.classroomNo = classroomNo;
	}

}



