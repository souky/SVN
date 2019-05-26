package com.jy.moudles.user.entity;

import com.jy.common.entity.BaseEntity;

public class User extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 用户密码
	 */
	private String userPsw;
	
	/**
	 * 用户头像
	 */
	private String userImg;

	/**
	 * 用户类型 0: 超管 1: 管理员 2: 普通用户
	 */
	private int userType;

	/**
	 * 角色ID
	 */
	private String roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * sourceId

	 */
	private String sourceId;

	/**
	 * 组织机构ID
	 */
	private String orgId;

	/**
	 * 是否修改密码 0: 未修改密码 1: 已修改密码
	 */
	private int isChangedPsw;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 组织机构名称
	 */
	private String orgName;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPsw() {
		return userPsw;
	}

	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}
	
	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	
	public String getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
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

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public int getIsChangedPsw() {
		return isChangedPsw;
	}

	public void setIsChangedPsw(int isChangedPsw) {
		this.isChangedPsw = isChangedPsw;
	}
}



