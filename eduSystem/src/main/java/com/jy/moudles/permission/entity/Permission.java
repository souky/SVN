package com.jy.moudles.permission.entity;

import java.util.List;

import com.jy.common.entity.BaseEntity;

/**
 * 权限基本对象
 * 
 * @author jinxiaoxiang@jrycn.cn
 * @since 2017-12-19
 *
 */
public class Permission extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * BSID
	 */
	private String bsid;
	
	/**
	 * 权限名称
	 */
	private String permissionName;
	
	/**
	 * 权限等级
	 */
	private String permissionLevel;
	
	/**
	 * 权限排序
	 */
	private String permissionOrder;
	
	/**
	 * 子权限
	 */
	private List<Permission> children;
	
	/**
	 * 是否拥有该权限（用户回显属性）
	 */
	private int isHasPermission;

	public int getIsHasPermission() {
		return isHasPermission;
	}

	public void setIsHasPermission(int isHasPermission) {
		this.isHasPermission = isHasPermission;
	}

	public String getBsid() {
		return bsid;
	}

	public void setBsid(String bsid) {
		this.bsid = bsid;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(String permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public String getPermissionOrder() {
		return permissionOrder;
	}

	public void setPermissionOrder(String permissionOrder) {
		this.permissionOrder = permissionOrder;
	}
	
	public List<Permission> getChildren() {
		return children;
	}

	public void setChildren(List<Permission> children) {
		this.children = children;
	}
}
