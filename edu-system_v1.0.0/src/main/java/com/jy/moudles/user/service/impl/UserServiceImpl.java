package com.jy.moudles.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.entity.MenuInfo;
import com.jy.common.utils.MD5Util;
import com.jy.common.utils.MenuUtil;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.user.dao.UserDao;
import com.jy.moudles.user.entity.User;
import com.jy.moudles.user.service.UserService;

/** 
 * user业务实现类
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userdao;

	@Override
	public void insertUser(User user) {
		user.setId(UUIDUtil.get32UUID());
		userdao.insertUser(user);
	}

	@Override
	public void updateUser(User user) {
		userdao.updateUser(user);
	}

	@Override
	public User getUserById(String id) {
		return userdao.getUserById(id);
	}

	@Override
	public User getUserByNameAndPwd(String userName, String psw) {
		return userdao.getUserByNameAndPwd(userName, psw);
	}

	@Override
	public User isExistTeacherUser(String phone) {
		return userdao.isExistTeacherUser(phone);
	}

	@Override
	public List<User> queryUsersFilter(Map<String, Object> filter) {
		return userdao.queryUsersFilter(filter);
	}

	@Override
	public void deleteUserById(String id) {
		userdao.deleteUserById(id);
	}

	@Override
	public void deleteUsers(List<String> ids) {
		userdao.deleteUsers(ids);
	}

	@Override
	public List<MenuInfo> getMenusByUserId(String userId) {

		List<MenuInfo> menuInfos = userdao.getMenusByUserId(userId);

		menuInfos = MenuUtil.reassemblyMenuInfo(menuInfos);

		return menuInfos;
	}

	@Override
	public void initPassword(String userId) {
		userdao.initPassword(userId, MD5Util.MD5("Aa111111"));
	}
	
	/**
	 * 根据sourceId删除user列表对象
	 *
	 * @param id
	 */
	public void deleteUserBySourceId(String sourceId) {
		userdao.deleteUserBySourceId(sourceId);
	};
}

