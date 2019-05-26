package com.jy.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单信息
 * 根据前端Date.js产生的对象
 * 
 * @author jinxiaoxiang@jrycn.cn
 * @since 2017-12-16
 *
 */
public class MenuInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID
	 */
	private String id;
	
	/**
	 * bsid
	 */
	private String bsid;
	
	/**
	 * 菜单图标
	 */
	private String icon;
	
	/**
	 * 菜单名称
	 */
	private String menuName;
	
	/**
	 * 菜单排序
	 */
	private int menuOrder;
	
	/**
	 * 菜单访问路径
	 */
	private String path;
	
	/**
	 * 菜单子菜单
	 */
	private List<MenuInfo> children;
	
	public String getBsid() {
		return bsid;
	}

	public void setBsid(String bsid) {
		this.bsid = bsid;
	}

	public int getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<MenuInfo> getChildren() {
		return children;
	}

	public void setChildren(List<MenuInfo> children) {
		this.children = children;
	}
}
