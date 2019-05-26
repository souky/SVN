package com.jy.moudles.role.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.moudles.role.entity.Role;
import com.jy.moudles.role.service.RoleService;
import com.jy.moudles.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * role实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	/**
	 * 新增role对象
	 *
	 * @param role
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveRole(Role role) throws Exception{
		LOGGER.info("新增Role Start");
		
		User user = LoginInterceptor.getCurrentUser();
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("roleName", role.getRoleName());
		List<Role> roleList = roleService.queryRolesFilter(filter);
		if(roleList != null && !roleList.isEmpty()){
			return AsyncResponseData.getSuccess().asParamError("用户已存在");
		}

		role.setOrgId(user.getOrgId());
		if (StringUtils.isBlank(role.getRemark())) {
            role.setRemark(role.getRemark());
		}
		role.setCreateUser(user.getUserName());
		role.setCreateDate(new Date());
		role.setUpdateUser(user.getUserName());
		role.setUpdateDate(new Date());

		roleService.insertRole(role);

		LOGGER.info("新增Role End");

		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 根据id查询
	 *
	 * @param role
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRoleById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getRoleById(Role role) throws Exception{
		LOGGER.info("新增Role Start");

		Role roles = roleService.getRoleById(role.getId());

		LOGGER.info("新增Role End");
		return AsyncResponseData.getSuccess(roles);
	}

	/**
	 * 修改role对象
	 *
	 * @param role
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateRole(Role role) throws Exception{
		LOGGER.info("修改Role Start");

        role.setCreateUser(role.getRoleName());
        role.setCreateDate(new Date());
        role.setUpdateUser(role.getRoleName());
        role.setUpdateDate(new Date());
		roleService.updateRole(role);

		LOGGER.info("修改Role End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 删除role对象
	 *
	 * @param role
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteRole(Role role) throws Exception{
		LOGGER.info("删除Role Start");

		roleService.deleteRoleById(role.getId());

		LOGGER.info("删除Role End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 获取role对象
	 *
	 * @param roleName
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryRoles", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryRoles(int pageNum, int pageSize, @RequestParam(required = false)String roleName) throws Exception{
		LOGGER.info("获取Role Start");
		User user = LoginInterceptor.getCurrentUser();
		Map<String, Object> filter = new HashMap<String, Object>();
        if (0 != user.getUserType()){
        	filter.put("orgId", user.getOrgId());
        }
        if (!StringUtils.isBlank(roleName)) {
            filter.put("roleName", roleName.trim());
        }
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<Role> roles = new PageInfo<Role>(roleService.queryRolesFilter(filter));
		LOGGER.info("获取Role End");

		return AsyncResponseData.getSuccess(roles);
	}

}
