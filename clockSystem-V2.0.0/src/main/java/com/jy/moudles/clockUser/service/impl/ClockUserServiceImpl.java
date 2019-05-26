package com.jy.moudles.clockUser.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.MD5Util;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.clockUser.VO.ChangepswVO;
import com.jy.moudles.clockUser.dao.ClockUserDao;
import com.jy.moudles.clockUser.entity.ClockUser;
import com.jy.moudles.clockUser.service.ClockUserService;

/** 
 * ClockUser业务实现类
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Service
public class ClockUserServiceImpl implements ClockUserService {

	@Autowired
	private ClockUserDao ClockUserDao;
	
	@Override
	public void insertClockUser(ClockUser ClockUser){
		ClockUser.setId(UUIDUtil.get32UUID());
		ClockUserDao.insertClockUser(ClockUser);
	}
	
	@Override
	public void updateClockUser(ClockUser ClockUser){
		ClockUserDao.updateClockUser(ClockUser);
	}
	
	@Override
	public ClockUser getClockUserById(String id){
		return ClockUserDao.getClockUserById(id);
	}
	
	@Override
	public List<ClockUser> queryClockUsersFilter(Map<String, Object> filter){
		return ClockUserDao.queryClockUsersFilter(filter);
	}
	
	@Override
	public void deleteClockUserById(String id){
		ClockUserDao.deleteClockUserById(id);
	}
	
	@Override
	public void deleteClockUsers(List<String> ids){
		ClockUserDao.deleteClockUsers(ids);
	}

	@Override
	public ClockUser getUserByPassword(String loginName, String password) {
		return ClockUserDao.getUserByPassword(loginName, password);
	}
	
	//修改密码
	public AsyncResponseData.ResultData changePassword(ChangepswVO changepswVO){
		ClockUser clockUser = ClockUserDao.getClockUserById(changepswVO.getClockUser().getId());
		String oldpsw = changepswVO.getOldpsw();
		String newpsw = changepswVO.getNewpsw();
		String newpsw_ = changepswVO.getNewpsw_();
		
		//验证信息完整性
		if(null == clockUser) {
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
		String userPsw = clockUser.getPassword();
		if(!userPsw.equals(MD5Util.MD5(oldpsw))) {
			return AsyncResponseData.getSuccess().asParamError("原密码错误");
		}
		
		//验证是否重复密码
		String newpswMd5 = MD5Util.MD5(newpsw);
		if(userPsw.equals(newpswMd5)) {
			return AsyncResponseData.getSuccess().asParamError("修改的密码不能与原密码一致");
		}
		
		//更新密码
		clockUser.setPassword(newpswMd5);
		
		ClockUserDao.updateClockUser(clockUser);
		return AsyncResponseData.getSuccess();
	}
	
}

