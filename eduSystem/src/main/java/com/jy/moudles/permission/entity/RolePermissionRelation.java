package com.jy.moudles.permission.entity;

import com.jy.common.entity.BaseEntity;

/**
 * 角色和权限关系对象
 * 
 * @author jinxiaoxiang@jrycn.cn
 *
 */
public class RolePermissionRelation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 权限ID
	 */
	private String permissionId;

	/**
	 * 角色ID
	 */
	private String roleId;

	public String getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
