package com.jy.moudles.organization.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.RandomCodeUtil;
import com.jy.moudles.organization.entity.OrgVO;
import com.jy.moudles.organization.entity.Organization;
import com.jy.moudles.organization.service.OrganizationService;
import com.jy.moudles.region.entity.RegionVO;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.school.service.SchoolService;
import com.jy.moudles.user.entity.User;

/**
 * organization实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/organization")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private SchoolService schoolService;

	private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationController.class);

	/**
	 * 新增organization对象
	 *
	 * @param organization
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrganization", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveOrganization(Organization organization) throws Exception{
		LOGGER.info("新增Organization Start");

		User user = LoginInterceptor.getCurrentUser();
		if (null == user){
			return AsyncResponseData.getSessionError("登录已过期，请重新登录");
		}

		//查重
		String orgName = organization.getOrgName();
		if(StringUtils.isBlank(orgName)) {
			return AsyncResponseData.getSuccess().asParamError("请输入机构名称");
		}
		Map<String,Object> filter = new HashMap<>();
        filter.put("orgName", orgName);
        List<Organization> listCheck = new ArrayList<>();
        listCheck = organizationService.queryOrganizationsFilter(filter);
        if(listCheck.size() > 0) {
        	return AsyncResponseData.getSuccess().asParamError("机构名称重复");
        }
		
		organization.setCreateUser(user.getUserName());
		organization.setCreateDate(new Date());
		organization.setUpdateUser(user.getUserName());
		organization.setUpdateDate(new Date());

		organizationService.insertOrganization(organization);

		School school = new School();
		school.setOrgId(organization.getId());
		school.setSchoolName(organization.getOrgName());
		school.setCreateUser(user.getUserName());
		school.setCreateDate(new Date());
		school.setUpdateUser(user.getUserName());
		school.setUpdateDate(new Date());
		
		//学校唯一码
		String schoolOnlyCode = RandomCodeUtil.getStringRandom(10);
		school.setSchoolOnlyCode(schoolOnlyCode);
		
		schoolService.insertSchool(school);

		LOGGER.info("新增Organization End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 修改organization对象
	 *
	 * @param organization
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateOrganization", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateOrganization(Organization organization) throws Exception{
		LOGGER.info("修改Organization Start");

		organizationService.updateOrganization(organization);
		
		//修改学校信息
		String orgId = organization.getId();
		if(StringUtils.isNotBlank(orgId)) {
			School school = schoolService.getSchoolByOrgId(orgId);
			if(null != school) {
				school.setSchoolName(organization.getOrgName());
				schoolService.updateSchool(school);
			}
		}
		
		
		LOGGER.info("修改Organization End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 根据id得到organization对象
	 *
	 * @param organization
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOrganizationById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getOrganizationById(Organization organization) throws Exception{
		LOGGER.info("获取Organization Start");

		Organization org = organizationService.getOrganizationById(organization.getId());

		LOGGER.info("获取Organization End");
		return AsyncResponseData.getSuccess(org);
	}

	/**
	 * 删除organization对象
	 *
	 * @param organization
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteOrganization", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteOrganization(Organization organization) throws Exception{
		LOGGER.info("删除Organization Start");
		
		organization = organizationService.getOrganizationById(organization.getId());
		
		if(null == organization) {
			return AsyncResponseData.getSuccess().asParamError("该组织机构不存在");
		}
		organizationService.deleteOrganizationById(organization.getId());
		
		//删除学校信息
		String orgId = organization.getId();
		if(StringUtils.isNotBlank(orgId)) {
			School school = schoolService.getSchoolByOrgId(orgId);
			if(null != school) {
				schoolService.deleteSchoolById(school.getId());
			}
		}
		
		LOGGER.info("删除Organization End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 查询organization对象
	 *
	 * @param organization
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryOrg", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryOrg(Organization organization) throws Exception{
		LOGGER.info("查询Organization Start");

		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("regionCode", organization.getRegionCode());
		List<Organization> organizations= organizationService.queryOrganizationsFilter(filter);

		LOGGER.info("查询Organization End");
		return AsyncResponseData.getSuccess(organizations);
	}

	/**
	 * 加载全部组织机构
	 *
	 * @param
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/loadOrganizations", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData loadOrganizations(HttpServletRequest request) throws Exception{
		LOGGER.info("获取Organizations Start");

		//查询出所有组织机构对应的省市区县
		List<OrgVO> orgVOList = organizationService.loadOrganizations();

		List<RegionVO> regionOrgVOList = new ArrayList<>();
		Map<String, Object> regionChildMap = new HashMap<>();
		RegionVO provinceRegionVO;
		RegionVO cityRegionVO;
		RegionVO areaRegionVO;
		RegionVO orgRegionVO;
		if (orgVOList!=null && !orgVOList.isEmpty()){
			for(OrgVO orgVO : orgVOList){
				provinceRegionVO = new RegionVO();
				cityRegionVO = new RegionVO();
				areaRegionVO = new RegionVO();
				orgRegionVO = new RegionVO();
				List<RegionVO> provinceChilds = new ArrayList<>();
				List<RegionVO> cityChilds = new ArrayList<>();
				List<RegionVO> areaChilds = new ArrayList<>();
				//该省已存在
				if (regionChildMap.containsKey(orgVO.getProvinceId())){

					provinceChilds = (List<RegionVO>)regionChildMap.get(orgVO.getProvinceId());
					provinceRegionVO.setId(orgVO.getProvinceId());
					provinceRegionVO.setLabel(orgVO.getProvinceName());
					provinceRegionVO.setChildren(provinceChilds);
					RegionVO existRegionVO = new RegionVO();
					for(RegionVO existProvinceVO : regionOrgVOList){
						if (existProvinceVO.getId().equals(provinceRegionVO.getId())){
							existRegionVO = existProvinceVO;
						}
					}
					regionOrgVOList.remove(existRegionVO);

					//该市已存在
					if (regionChildMap.containsKey(orgVO.getCityId())){
						cityChilds = (List<RegionVO>)regionChildMap.get(orgVO.getCityId());
						cityRegionVO.setId(orgVO.getCityId());
						cityRegionVO.setLabel(orgVO.getCityName());

						//该区已存在
						if (regionChildMap.containsKey(orgVO.getAreaId())){
							areaChilds = (List<RegionVO>)regionChildMap.get(orgVO.getAreaId());
							RegionVO cityChildVO = new RegionVO();
							for (RegionVO cityChild : cityChilds){
								if (cityChild.getId().equals(orgVO.getAreaId())){
									cityChildVO = cityChild;
								}
							}
							cityChilds.remove(cityChildVO);
						}

						orgRegionVO.setId(orgVO.getAreaId());
						orgRegionVO.setLabel(orgVO.getOrgName());
						areaRegionVO.setId(orgVO.getAreaId());
						areaRegionVO.setLabel(orgVO.getAreaName());
						areaChilds.add(orgRegionVO);
						areaRegionVO.setChildren(areaChilds);
						cityChilds.add(areaRegionVO);
						cityRegionVO.setChildren(cityChilds);
						RegionVO pChildVO = new RegionVO();
						for (RegionVO pChild : provinceChilds){
							if (pChild.getId().equals(orgVO.getCityId())){
								pChildVO = pChild;
							}
						}
						provinceChilds.remove(pChildVO);
						provinceChilds.add(cityRegionVO);
						provinceRegionVO.setChildren(provinceChilds);
						regionOrgVOList.add(provinceRegionVO);

						regionChildMap.put(areaRegionVO.getId(),areaChilds);
						regionChildMap.put(cityRegionVO.getId(),cityChilds);
						regionChildMap.put(provinceRegionVO.getId(),provinceChilds);

						continue;

					}else{
						//存在该省不存在该市
						orgRegionVO.setId(orgVO.getAreaId());
						orgRegionVO.setLabel(orgVO.getOrgName());
						areaRegionVO.setId(orgVO.getAreaId());
						areaRegionVO.setLabel(orgVO.getAreaName());
						areaChilds.add(orgRegionVO);
						areaRegionVO.setChildren(areaChilds);
						cityRegionVO.setId(orgVO.getCityId());
						cityRegionVO.setLabel(orgVO.getCityName());
						cityChilds.add(areaRegionVO);
						cityRegionVO.setChildren(cityChilds);
						provinceChilds.add(cityRegionVO);
						provinceRegionVO.setChildren(provinceChilds);
						regionOrgVOList.add(provinceRegionVO);

						regionChildMap.put(areaRegionVO.getId(),areaChilds);
						regionChildMap.put(cityRegionVO.getId(),cityChilds);
						regionChildMap.put(provinceRegionVO.getId(),provinceChilds);
					}

				}else {
					orgRegionVO = new RegionVO();
					orgRegionVO.setId(orgVO.getAreaId());
					orgRegionVO.setLabel(orgVO.getOrgName());

					areaRegionVO = new RegionVO();
					areaRegionVO.setId(orgVO.getAreaId());
					areaRegionVO.setLabel(orgVO.getAreaName());
					areaChilds.add(orgRegionVO);
					areaRegionVO.setChildren(areaChilds);
					regionChildMap.put(areaRegionVO.getId(),areaChilds);

					cityRegionVO = new RegionVO();
					cityRegionVO.setId(orgVO.getCityId());
					cityRegionVO.setLabel(orgVO.getCityName());
					cityChilds.add(areaRegionVO);
					cityRegionVO.setChildren(cityChilds);
					regionChildMap.put(cityRegionVO.getId(),cityChilds);

					provinceRegionVO = new RegionVO();
					provinceRegionVO.setId(orgVO.getProvinceId());
					provinceRegionVO.setLabel(orgVO.getProvinceName());
					provinceChilds.add(cityRegionVO);
					provinceRegionVO.setChildren(provinceChilds);
					regionChildMap.put(provinceRegionVO.getId(),provinceChilds);

					regionOrgVOList.add(provinceRegionVO);
				}

			}
		}

		LOGGER.info("获取Organizations End");
		return AsyncResponseData.getSuccess(regionOrgVOList);
	}
}
