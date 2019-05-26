package com.jy.moudles.shieldUser.VO;

import com.jy.moudles.shieldUser.entity.ShieldUser;

public class ChangepswVO {

	//旧密码
	private String oldpsw;
	//新密码
	private String newpsw;
	//重复新密码
	private String newpsw_;
	//当前用户id
	private ShieldUser shieldUser;
	
	
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
	public ShieldUser getShieldUser() {
		return shieldUser;
	}
	public void setShieldUser(ShieldUser shieldUser) {
		this.shieldUser = shieldUser;
	}
	
	
}
