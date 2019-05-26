package com.jy.moudles.organization.dao;

import java.util.List;
import java.util.Map;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.organization.entity.Organization;

/** 
 * Organization数据接口
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@MyBatisDao
public interface OrganizationDao {

	/**
	 * 新增Organization对象
	 *
	 * @param Organization
	 */
	public void insertOrganization(Organization Organization);
	
	/**
	 * 更新Organization对象
	 *
	 * @param Organization
	 */
	public void updateOrganization(Organization Organization);
	
	/**
	 * 根据ID获取Organization对象
	 *
	 * @param id
	 */
	public Organization getOrganizationById(String id);
	
	/**
	 * 根据过滤条件获取Organization列表对象
	 *
	 * @param filter
	 */
	public List<Organization> queryOrganizationsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除Organization列表对象
	 *
	 * @param id
	 */
	public void deleteOrganizationById(String id);
	
	/**
	 * 根据Id集合批量删除Organization列表对象
	 *
	 * @param ids
	 */
	public void deleteOrganizations();
	
	/**
	 * 批量添加Organization列表对象
	 *
	 * @param ids
	 */
	public void insertOrganizations(List<Organization> organizations);
	
	
	
	
}



