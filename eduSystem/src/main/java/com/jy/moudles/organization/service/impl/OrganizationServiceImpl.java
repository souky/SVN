package com.jy.moudles.organization.service.impl;

import com.jy.moudles.organization.entity.OrgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.organization.dao.OrganizationDao;
import com.jy.moudles.organization.entity.Organization;
import com.jy.moudles.organization.service.OrganizationService;

/** 
 * organization业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationDao organizationdao;
	
	@Override
	public void insertOrganization(Organization organization){
		organization.setId(UUIDUtil.get32UUID());
		organizationdao.insertOrganization(organization);
	}
	
	@Override
	public void updateOrganization(Organization organization){
		organizationdao.updateOrganization(organization);
	}
	
	@Override
	public Organization getOrganizationById(String id){
		return organizationdao.getOrganizationById(id);
	}
	
	@Override
	public List<Organization> queryOrganizationsFilter(Map<String, Object> filter){
		return organizationdao.queryOrganizationsFilter(filter);
	}
	
	@Override
	public void deleteOrganizationById(String id){
		organizationdao.deleteOrganizationById(id);
	}
	
	@Override
	public void deleteOrganizations(List<String> ids){
		organizationdao.deleteOrganizations(ids);
	}

	@Override
	public List<OrgVO> loadOrganizations() {
		return organizationdao.loadOrganizations();
	}

}

