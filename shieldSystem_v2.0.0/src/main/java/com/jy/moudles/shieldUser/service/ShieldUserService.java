package com.jy.moudles.shieldUser.service;

import java.util.List;
import java.util.Map;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.moudles.shieldUser.VO.ChangepswVO;
import com.jy.moudles.shieldUser.entity.ShieldUser;

/** 
 * shieldUser业务接口
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
public interface ShieldUserService {

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
	public ShieldUser getUserByPassword(String loginName,String password);
	
	/**
	 * 修改密码
	 *
	 * @param oldpsw,newpsw,newpsw_
	 */
	public AsyncResponseData.ResultData changePassword(ChangepswVO changepswVO);
	
}

