package com.jy.moudles.user.service;

import com.jy.common.entity.MenuInfo;
import com.jy.moudles.user.entity.User;
import java.util.List;
import java.util.Map;

/** 
 * user业务接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
public interface UserService {

	/**
	 * 新增user对象
	 *
	 * @param user
	 */
	public void insertUser(User user);
	
	/**
	 * 更新user对象
	 *
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 根据ID获取user对象
	 *
	 * @param id
	 */
	public User getUserById(String id);

	/**
	 * 根据 userName 和 psw 获取User对象
	 *
	 * @param userName 、psw
	 */
	public User getUserByNameAndPwd(String userName, String psw);

	/**
	 * 根据phone获取User对象
	 *
	 * @param phone
	 */
	public User isExistTeacherUser(String phone);

	/**
	 * 根据过滤条件获取user列表对象
	 *
	 * @param filter
	 */
	public List<User> queryUsersFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除user列表对象
	 *
	 * @param id
	 */
	public void deleteUserById(String id);
	
	/**
	 * 根据Id集合批量删除user列表对象
	 *
	 * @param ids
	 */
	public void deleteUsers(List<String> ids);
	
	/**
	 * 获取当前用户的菜单
	 * 
	 * @param userId
	 */
	public List<MenuInfo> getMenusByUserId(String userId);
	
	/**
	 * 重置密码
	 * 
	 * @param userId
	 */
	public void initPassword(String userId);
	
	/**
	 * 根据sourceId删除user列表对象
	 *
	 * @param id
	 */
	public void deleteUserBySourceId(String sourceId);

}

