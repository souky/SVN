package com.jy.moudles.shieldUser.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.common.persistence.annotation.MyBatisDao;
import com.jy.moudles.shieldUser.entity.ShieldUser;

/** 
 * shieldUser数据接口
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@MyBatisDao
public interface ShieldUserDao {

	/**
	 * 新增shieldUser对象
	 *
	 * @param shieldUser
	 */
	public void insertShieldUser(ShieldUser shieldUser);
	
	/**
	 * 更新shieldUser对象
	 *
	 * @param shieldUser
	 */
	public void updateShieldUser(ShieldUser shieldUser);
	
	/**
	 * 根据ID获取shieldUser对象
	 *
	 * @param id
	 */
	public ShieldUser getShieldUserById(String id);
	
	/**
	 * 根据过滤条件获取shieldUser列表对象
	 *
	 * @param filter
	 */
	public List<ShieldUser> queryShieldUsersFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除shieldUser列表对象
	 *
	 * @param id
	 */
	public void deleteShieldUserById(String id);
	
	/**
	 * 根据Id集合批量删除shieldUser列表对象
	 *
	 * @param ids
	 */
	public void deleteShieldUsers(List<String> ids);
	
	/**
	 * 登陆
	 *
	 * @param ids
	 */
	public ShieldUser getUserByPassword(@Param("loginName")String loginName,@Param("password")String password);
	
}



