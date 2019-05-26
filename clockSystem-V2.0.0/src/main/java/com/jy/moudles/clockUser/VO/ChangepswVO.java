package com.jy.moudles.clockUser.VO;

import com.jy.moudles.clockUser.entity.ClockUser;

public class ChangepswVO {

	//旧密码
	private String oldpsw;
	//新密码
	private String newpsw;
	//重复新密码
	private String newpsw_;
	//当前用户id
	private ClockUser clockUser;
	
	
	public String getOldpsw() {
		return oldpsw;
	}
	public void setOldpsw(String oldpsw) {
		this.oldpsw = oldpsw;
	}
	public String getNewpsw() {
		return newpsw;
	}
	public void setNewpsw(String newpsw) {
		this.newpsw = newpsw;
	}
	public String getNewpsw_() {
		return newpsw_;
	}
	public void setNewpsw_(String newpsw_) {
		this.newpsw_ = newpsw_;
	}
	public ClockUser getClockUser() {
		return clockUser;
	}
	public void setClockUser(ClockUser clockUser) {
		this.clockUser = clockUser;
	}
	
	
	
	
}
