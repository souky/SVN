package com.jy.moudles.shieldMenu.entity;

import java.util.List;

import com.jy.common.entity.BaseEntity;
/**
*屏蔽菜单表，记录屏蔽系统菜单信息；该信息同步至公共数据库
*/
public class ShieldMenu extends BaseEntity{
	
	private static final long serialVersionUID = -1L;
	
	
	/**
	 * 菜单名称（中文）
	 */
	private String menuNameZh;
	
	/**
	 * 菜单层级
	 */
	private int menuLevel;
	
	/**
	 * 菜单排序
	 */
	private int menuOrder;
	
	/**
	 * 菜单指向URL
	 */
	private String menuUrl;
	
	/**
	 * 菜单图片地址
	 */
	private String menuImg;
	
	/**
	 * 上级ID
	 */
	private String pId;
	
	/**
	 * 逻辑删除字段 1:删除，0:未删除
	 */
	private int isDelete;
	
	/**
	 * 开放状态；是否开放该菜单
	 */
	private int isOpen;
	
	// 子菜单
	private List<ShieldMenu> listNextLevel;
		
	
	
	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public List<ShieldMenu> getListNextLevel() {
		return listNextLevel;
	}

	public void setListNextLevel(List<ShieldMenu> listNextLevel) {
		this.listNextLevel = listNextLevel;
	}

	public String getMenuNameZh() {
		return menuNameZh;
	}

	public void setMenuNameZh(String menuNameZh) {
		this.menuNameZh = menuNameZh;
	}
	
	public int getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(int menuLevel) {
		this.menuLevel = menuLevel;
	}
	
	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}
	
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	
	public String getMenuImg() {
		return menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}
	
	public String getPId() {
		return pId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}
	
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	
	public int getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	
}



