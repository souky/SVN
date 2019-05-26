package com.jy.moudles.organization.dao;

import java.util.List;
import java.util.Map;

import com.jy.moudles.organization.entity.OrgVO;
import com.jy.moudles.organization.entity.Organization;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * organization数据接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
@MyBatisDao
public interface OrganizationDao {

	/**
	 * 新增organization对象
	 *
	 * @param organization
	 */
	public void insertOrganization(Organization organization);
	
	/**
	 * 更新organization对象
	 *
	 * @param organization
	 */
	public void updateOrganization(Organization organization);
	
	/**
	 * 根据ID获取organization对象
	 *
	 * @param id
	 */
	public Organization getOrganizationById(String id);
	
	/**
	 * 根据过滤条件获取organization列表对象
	 *
	 * @param filter
	 */
	public List<Organization> queryOrganizationsFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除organization列表对象
	 *
	 * @param id
	 */
	public void deleteOrganizationById(String id);
	
	/**
	 * 根据Id集合批量删除organization列表对象
	 *
	 * @param ids
	 */
	public void deleteOrganizations(List<String> ids);

	/**
	 * 查询出所有组织机构对应的省市区县
	 *
	 * @param
	 */
	public List<OrgVO> loadOrganizations();
}



