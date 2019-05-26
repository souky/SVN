package com.jy.moudles.shieldUser.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.jsonadpter.AsyncResponseData.ResultData;
import com.jy.common.utils.MD5Util;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.shieldUser.VO.ChangepswVO;
import com.jy.moudles.shieldUser.dao.ShieldUserDao;
import com.jy.moudles.shieldUser.entity.ShieldUser;
import com.jy.moudles.shieldUser.service.ShieldUserService;

/** 
 * shieldUser业务实现类
 * 创建人：Administrator
 * 创建时间：2018-11-22
 */
@Service
public class ShieldUserServiceImpl implements ShieldUserService {

	@Autowired
	private ShieldUserDao shieldUserDao;
	
	@Override
	public void insertShieldUser(ShieldUser shieldUser){
		shieldUser.setId(UUIDUtil.get32UUID());
		shieldUserDao.insertShieldUser(shieldUser);
	}
	
	@Override
	public void updateShieldUser(ShieldUser shieldUser){
		shieldUserDao.updateShieldUser(shieldUser);
	}
	
	@Override
	public ShieldUser getShieldUserById(String id){
		return shieldUserDao.getShieldUserById(id);
	}
	
	@Override
	public List<ShieldUser> queryShieldUsersFilter(Map<String, Object> filter){
		return shieldUserDao.queryShieldUsersFilter(filter);
	}
	
	@Override
	public void deleteShieldUserById(String id){
		shieldUserDao.deleteShieldUserById(id);
	}
	
	@Override
	public void deleteShieldUsers(List<String> ids){
		shieldUserDao.deleteShieldUsers(ids);
	}

	@Override
	public ShieldUser getUserByPassword(String loginName, String password) {
		return shieldUserDao.getUserByPassword(loginName, password);
	}

	@Override
	public ResultData changePassword(ChangepswVO changepswVO) {
		ShieldUser shieldUser = shieldUserDao.getShieldUserById(changepswVO.getShieldUser().getId());
		String oldpsw = changepswVO.getOldpsw();
		String newpsw = changepswVO.getNewpsw();
		String newpsw_ = changepswVO.getNewpsw_();
		
		//验证信息完整性
		if(null == shieldUser) {
			return AsyncResponseData.getLogicError("用户未登录");
		}
		if(StringUtils.isBlank(oldpsw)) {
			return AsyncResponseData.getSuccess().asParamError("旧密码不能为空");
		}
		if(StringUtils.isBlank(newpsw)) {
			return AsyncResponseData.getSuccess().asParamError("新密码不能为空");
		}
		if(StringUtils.isBlank(newpsw_) || !newpsw.equals(newpsw_)) {
			return AsyncResponseData.getSuccess().asParamError("重复输入密码错误");
		}
		
		//验证密码正确性
		String userPsw = shieldUser.getPassword();
		if(!userPsw.equals(MD5Util.MD5(oldpsw))) {
			return AsyncResponseData.getSuccess().asParamError("原密码错误");
		}
		
		//验证是否重复密码
		String newpswMd5 = MD5Util.MD5(newpsw);
		if(userPsw.equals(newpswMd5)) {
			return AsyncResponseData.getSuccess().asParamError("修改的密码不能与原密码一致");
		}
		
		//更新密码
		shieldUser.setPassword(newpswMd5);
		
		shieldUserDao.updateShieldUser(shieldUser);
		
		return AsyncResponseData.getSuccess();
	}
	
}

