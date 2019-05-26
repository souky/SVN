package com.jy.moudles.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.moudles.user.entity.User;
import com.jy.common.entity.MenuInfo;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * user数据接口
 * 创建人：1
 * 创建时间：2017-11-29
 */
@MyBatisDao
public interface UserDao {

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
	 * 根据phone对象
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
	 * 根据sourceId删除user列表对象
	 *
	 * @param id
	 */
	public void deleteUserBySourceId(String sourceId);
	
	/**
	 * 根据Id集合批量删除user列表对象
	 *
	 * @param ids
	 */
	public void deleteUsers(List<String> ids);
	
	/**
	 * 根据用户ID获取菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<MenuInfo> getMenusByUserId(String userId);
	
	/**
	 * 获取所有菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<MenuInfo> getAllMenus();
	
	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @param initPassword
	 */
	public void initPassword(@Param("userId") String userId, @Param("initPassword")String initPassword);
	
}



