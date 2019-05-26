package com.jy.moudles.permission.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.permission.dao.PermissionDao;
import com.jy.moudles.permission.entity.Permission;
import com.jy.moudles.permission.entity.RolePermissionRelation;
import com.jy.moudles.permission.service.PermissionService;
import com.jy.moudles.user.entity.User;

@Service
public class PermissionServiceImpl implements PermissionService {
	
	// 5个0字符串
	private static final String FIVE_ZERO_STRING = "00000";

	// 分隔符（-）
	private static final String SEPARATOR_LINE = "-";

	// BSID分割后数组长度
	private static final int BSID_ARRAY_LENGTH = 5;

	// BSID长度
	private static final int BSID_LENGTH = 29;

	// 正则表达式校验BSID
	private static final String PATTERN_BSID_REGEX = "^([0-9]{5}\\-){4}[0-9]{5}$";

	// 系统标识位BSID数组位置
	private static final int SYSTEM_IDENTIFICATION_MENU_INDEX = 0;

	// 操作系统类型BSID数组位置
	private static final int SYSTEM_OS_TYPE_MENU_INDEX = 1;

	// 一级菜单BSID数组位置
	private static final int FIRST_LEVEL_MENU_INDEX = 2;

	// 二级菜单BSID数组位置
	private static final int SECOND_LEVEL_MENU_INDEX = 3;

	// 三级菜单BSID数组位置
	private static final int THIRD_LEVEL_MENU_INDEX = 4;

	@Autowired
	private PermissionDao permissionDao;

	//private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);

	@Override
	public List<Permission> getPermissionsByUser(User user, String roleId) {

		List<Permission> permissions = new ArrayList<Permission>();

		if (0 == user.getUserType()) {
			permissions = permissionDao.getAllPermissions();
		} else {
			permissions = permissionDao.getPermissionsByRoleId(user.getRoleId());
		}
		
		List<String> hasPermissionIds = permissionDao.getHasPermissionIdsByroleId(roleId);
		if (null != hasPermissionIds && 0 != hasPermissionIds.size()) {
			for (Permission permission : permissions) {
				if(hasPermissionIds.contains(permission.getId())) {
					permission.setIsHasPermission(1);
				}
			}
		}
		
		permissions = this.reassemblyPermissions(permissions);
		
		return permissions;
	}
	
	
	@Override
	public void grantPermissions(List<String> permissionIds, String roleId, User currentUser) {
		
		if (StringUtils.isBlank(roleId)) {
			return;
		}
		
		permissionDao.deletePermissionsByRoleId(roleId);
		
		// 修改【系统设置-角色管理-授权】重新修改授权模块后，仍然显示未修改前的信息  2018-1-2 by jinxiaoxiang Start
		if (null != permissionIds && 0 != permissionIds.size()) {
			List<RolePermissionRelation> relations = new ArrayList<RolePermissionRelation>();
			for (String permissionId : permissionIds) {
				RolePermissionRelation relation = new RolePermissionRelation();
				
				relation.setId(UUIDUtil.get32UUID());
				relation.setPermissionId(permissionId);
				relation.setRoleId(roleId);
				String remark = currentUser.getOrgName() + "-" + currentUser.getUserName() + "添加权限";
				relation.setRemark(remark);
				relation.setCreateDate(new Date());
				relation.setCreateUser(currentUser.getUserName());
				relation.setUpdateDate(new Date());
				relation.setUpdateUser(currentUser.getUserName());
				relations.add(relation);
			}
			permissionDao.grantPermissionsForRole(relations);
		}
		// 修改【系统设置-角色管理-授权】重新修改授权模块后，仍然显示未修改前的信息  2018-1-2 by jinxiaoxiang End
	}

	/**
	 * 重新拼装菜单，符合输出菜单数据类型 当前系统最多含有3级菜单
	 * 菜单bsid构成：系统标识-操作系统类型（0：PC，1：安卓，2：IOS）-一级菜单-二级菜单-三级菜单 每级菜单编号从1开始 例：
	 * 一级菜单：00000-00000-00001-00000-00000 二级菜单：00000-00001-00002-00001-00000
	 * 三级菜单：00000-00002-00003-00002-00001
	 * 
	 * @param permissions
	 * @return
	 */
	private List<Permission> reassemblyPermissions(List<Permission> permissions) {
		List<Permission> reassemblyPermissions = new ArrayList<Permission>();
		if (null != permissions) {
			// 一级菜单
			Map<String, List<Permission>> firstLevelPermission = new HashMap<String, List<Permission>>();
			// 二级菜单
			Map<String, List<Permission>> secondLevelPermission = new HashMap<String, List<Permission>>();
			// 三级菜单
			Map<String, List<Permission>> thirdLevelPermission = new HashMap<String, List<Permission>>();

			for (Permission permission : permissions) {
				String bsid = permission.getBsid();
				if (!StringUtils.isBlank(bsid) && BSID_LENGTH == bsid.length()) {
					Pattern p = Pattern.compile(PATTERN_BSID_REGEX);
					Matcher m = p.matcher(bsid);
					if (m.matches()) {
						String[] bsids = bsid.split(SEPARATOR_LINE);

						if (BSID_ARRAY_LENGTH == bsids.length) {
							if (FIVE_ZERO_STRING.equals(bsids[THIRD_LEVEL_MENU_INDEX])
									&& FIVE_ZERO_STRING.equals(bsids[SECOND_LEVEL_MENU_INDEX])) {
								// 一级菜单key
								String key = bsids[SYSTEM_IDENTIFICATION_MENU_INDEX] + SEPARATOR_LINE
										+ bsids[SYSTEM_OS_TYPE_MENU_INDEX];
								List<Permission> firstLevelPermissions = firstLevelPermission.get(key);

								if (null == firstLevelPermissions) {
									firstLevelPermissions = new ArrayList<Permission>();
								}

								firstLevelPermissions.add(permission);
								firstLevelPermission.put(key, firstLevelPermissions);
							} else if (FIVE_ZERO_STRING.equals(bsids[THIRD_LEVEL_MENU_INDEX])) {
								// 二级菜单key
								String key = bsids[SYSTEM_IDENTIFICATION_MENU_INDEX] + SEPARATOR_LINE
										+ bsids[SYSTEM_OS_TYPE_MENU_INDEX] + SEPARATOR_LINE
										+ bsids[FIRST_LEVEL_MENU_INDEX];
								List<Permission> secondLevelPermissions = secondLevelPermission.get(key);

								if (null == secondLevelPermissions) {
									secondLevelPermissions = new ArrayList<Permission>();
								}

								secondLevelPermissions.add(permission);
								secondLevelPermission.put(key, secondLevelPermissions);
							} else {
								// 三级菜单
								String key = bsids[SYSTEM_IDENTIFICATION_MENU_INDEX] + SEPARATOR_LINE
										+ bsids[SYSTEM_OS_TYPE_MENU_INDEX] + SEPARATOR_LINE
										+ bsids[FIRST_LEVEL_MENU_INDEX] + SEPARATOR_LINE
										+ bsids[SECOND_LEVEL_MENU_INDEX];
								List<Permission> thirdLevelPermissions = thirdLevelPermission.get(key);

								if (null == thirdLevelPermissions) {
									thirdLevelPermissions = new ArrayList<Permission>();
								}

								thirdLevelPermissions.add(permission);
								thirdLevelPermission.put(key, thirdLevelPermissions);
							}
						}
					}
				}
			}

			// 先拼接二级菜单
			for (Map.Entry<String, List<Permission>> entry : secondLevelPermission.entrySet()) {
				List<Permission> value = entry.getValue();
				for (Permission permission : value) {
					String thirdLevelMenuKey = permission.getBsid().substring(0, 23);
					List<Permission> thirdLevelPermissions = thirdLevelPermission.get(thirdLevelMenuKey);
					if (null == thirdLevelPermissions) {
						thirdLevelPermissions = new ArrayList<Permission>();
					}
					permission.setChildren(thirdLevelPermissions);
				}
			}

			// 拼接一级菜单
			for (Map.Entry<String, List<Permission>> entry : firstLevelPermission.entrySet()) {
				List<Permission> value = entry.getValue();
				for (Permission permission : value) {
					String secondLevelMenuKey = permission.getBsid().substring(0, 17);
					List<Permission> secondLevelPermissions = secondLevelPermission.get(secondLevelMenuKey);

					if (null == secondLevelPermissions) {
						secondLevelPermissions = new ArrayList<Permission>();
					}
					permission.setChildren(secondLevelPermissions);
				}
			}

			// 拼接一级菜单
			for (Map.Entry<String, List<Permission>> entry : firstLevelPermission.entrySet()) {
				List<Permission> value = entry.getValue();
				for (Permission permission : value) {
					reassemblyPermissions.add(permission);
				}
			}
		}

		return reassemblyPermissions;

	}
}
