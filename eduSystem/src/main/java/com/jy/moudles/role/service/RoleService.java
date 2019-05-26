package com.jy.moudles.role.service;

import com.jy.moudles.role.entity.Role;
import java.util.List;
import java.util.Map;

/** 
 * role业务接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
public interface RoleService {

	/**
	 * 新增role对象
	 *
	 * @param role
	 */
	public void insertRole(Role role);
	
	/**
	 * 更新role对象
	 *
	 * @param role
	 */
	public void updateRole(Role role);
	
	/**
	 * 根据ID获取role对象
	 *
	 * @param id
	 */
	public Role getRoleById(String id);
	
	/**
	 * 根据过滤条件获取role列表对象
	 *
	 * @param filter
	 */
	public List<Role> queryRolesFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除role列表对象
	 *
	 * @param id
	 */
	public void deleteRoleById(String id);
	
	/**
	 * 根据Id集合批量删除role列表对象
	 *
	 * @param ids
	 */
	public void deleteRoles(List<String> ids);
	
}

