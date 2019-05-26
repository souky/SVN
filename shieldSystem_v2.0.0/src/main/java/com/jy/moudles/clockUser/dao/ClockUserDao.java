package com.jy.moudles.clockUser.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jy.moudles.clockUser.entity.ClockUser;
import com.jy.common.persistence.annotation.MyBatisDao;

/** 
 * ClockUser数据接口
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@MyBatisDao
public interface ClockUserDao {

	/**
	 * 新增ClockUser对象
	 *
	 * @param ClockUser
	 */
	public void insertClockUser(ClockUser ClockUser);
	
	/**
	 * 更新ClockUser对象
	 *
	 * @param ClockUser
	 */
	public void updateClockUser(ClockUser ClockUser);
	
	/**
	 * 根据ID获取ClockUser对象
	 *
	 * @param id
	 */
	public ClockUser getClockUserById(String id);
	
	/**
	 * 根据过滤条件获取ClockUser列表对象
	 *
	 * @param filter
	 */
	public List<ClockUser> queryClockUsersFilter(Map<String, Object> filter);
	
	/**
	 * 根据Id删除ClockUser列表对象
	 *
	 * @param id
	 */
	public void deleteClockUserById(String id);
	
	/**
	 * 根据Id集合批量删除ClockUser列表对象
	 *
	 * @param ids
	 */
	public void deleteClockUsers(List<String> ids);
	
	/**
	 * 登陆
	 *
	 * @param ids
	 */
	public ClockUser getUserByPassword(@Param("loginName")String loginName,@Param("password")String password);
	
	
}



