package com.jy.moudles.permission.service;

import java.util.List;

import com.jy.moudles.permission.entity.Permission;
import com.jy.moudles.user.entity.User;

public interface PermissionService {

	/**
	 * 根据用户获取权限
	 * 
	 * @param user
	 * @return
	 */
	public List<Permission> getPermissionsByUser(User user, String roleId);
	
	/**
	 * 授予用户权限(先删除后插入)
	 * 
	 * @param permissionIds
	 * @param roleId
	 */
	public void grantPermissions(List<String> permissionIds, String roleId, User currentUser);
}
