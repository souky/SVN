package com.jy.moudles.permission.dao;

import java.util.List;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.permission.entity.Permission;
import com.jy.moudles.permission.entity.RolePermissionRelation;
import org.apache.ibatis.annotations.Param;

/**
 * 权限Dao层
 * 
 * @author jinxiaoxiang@jrycn.cn
 *
 */
@MyBatisDao
public interface PermissionDao {
	
	/**
	 * 根据用户ID取得权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Permission> getPermissionsByRoleId(String roleId);
	
	/**
	 * 获取所有的用户权限
	 * 
	 * @return
	 */
	public List<Permission> getAllPermissions();
	
	/**
	 * 根据角色ID删除角色的权限
	 * 
	 * @param roleId
	 */
	public void deletePermissionsByRoleId(String roleId);
	
	/**
	 * 向用户授权
	 * 
	 * @param relations
	 */
	public void grantPermissionsForRole(@Param("relations") List<RolePermissionRelation> relations);
	
	
	/**
	 * 根据角色ID获取用户已用权限ID
	 * 
	 * @param roleId
	 * @return
	 */
	public List<String> getHasPermissionIdsByroleId(String roleId);

}
