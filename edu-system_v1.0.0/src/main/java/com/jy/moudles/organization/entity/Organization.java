package com.jy.moudles.organization.entity;

import com.jy.common.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class Organization extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 组织机构名称
	 */
	private String orgName;
	
	/**
	 * 组织机构bsid
	 */
	private String orgBsid;
	
	/**
	 * 组织机构IP
	 */
	private String orgIp;
	
	/**
	 * 区域code
	 */
	private String regionCode;
	
	/**
	 * 父ID
	 */
	private String parentId;

	/**
	 * 子节点
	 */
	@SuppressWarnings("rawtypes")
	private List orgChildNodes = new ArrayList();

	/**
	 * 备注
	 */
	private String remark;
	
	private String managerCount;
	
	
	public String getManagerCount() {
		return managerCount;
	}

	public void setManagerCount(String managerCount) {
		this.managerCount = managerCount;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getOrgBsid() {
		return orgBsid;
	}

	public void setOrgBsid(String orgBsid) {
		this.orgBsid = orgBsid;
	}
	
	public String getOrgIp() {
		return orgIp;
	}

	public void setOrgIp(String orgIp) {
		this.orgIp = orgIp;
	}
	
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@SuppressWarnings("rawtypes")
	public List getOrgChildNodes() {
		return orgChildNodes;
	}

	public void setOrgChildNodes(@SuppressWarnings("rawtypes") List orgChildNodes) {
		this.orgChildNodes = orgChildNodes;
	}
}



