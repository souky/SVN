package com.jy.moudles.organization.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.organization.dao.OrganizationDao;
import com.jy.moudles.organization.entity.Organization;
import com.jy.moudles.organization.service.OrganizationService;

/** 
 * Organization业务实现类
 * 创建人：Administrator
 * 创建时间：2018-05-03
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao OrganizationDao;
	
	@Override
	public void insertOrganization(Organization Organization){
		Organization.setId(UUIDUtil.get32UUID());
		OrganizationDao.insertOrganization(Organization);
	}
	
	@Override
	public void updateOrganization(Organization Organization){
		OrganizationDao.updateOrganization(Organization);
	}
	
	@Override
	public Organization getOrganizationById(String id){
		return OrganizationDao.getOrganizationById(id);
	}
	
	@Override
	public List<Organization> queryOrganizationsFilter(Map<String, Object> filter){
		return OrganizationDao.queryOrganizationsFilter(filter);
	}
	
	@Override
	public void deleteOrganizationById(String id){
		OrganizationDao.deleteOrganizationById(id);
	}
	
	@Override
	public void deleteOrganizations(){
		OrganizationDao.deleteOrganizations();
	}

	@Override
	public void insertOrganizations(List<Organization> organizations) {
		OrganizationDao.insertOrganizations(organizations);
	}
	
}

