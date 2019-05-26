package com.jy.moudles.role.entity;

import com.jy.common.entity.BaseEntity;

public class Role extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 角色名称
	 */
	private String roleName;
	
	/**
	 * 组织机构ID
	 */
	private String orgId;
	
	/**
	 * 是否展示
	 */
	private int isShown;

	/**
	 * 备注
	 */
	private String remark;

	public Role(){}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getIsShown() {
		return isShown;
	}

	public void setIsShown(int isShown) {
		this.isShown = isShown;
	}
}



