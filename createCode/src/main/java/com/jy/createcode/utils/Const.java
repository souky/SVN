package com.jy.createcode.utils;


public class Const {
	
	public static final String PAGELIST_DEFAULTSHOWCOUNT = "PageList-DefaultShowCount";
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_USER = "sessionUser";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_MENULIST = "menuList"; // 当前菜单
	public static final String SESSION_ALLMENULIST = "allmenuList"; // 全部菜单
	public static final String SESSION_QX = "QX";
	public static final String SESSION_USERPDS = "userpds";
	public static final String SESSION_USERROL = "USERROL"; // 用户对象
	public static final String SESSION_USERNAME = "USERNAME"; // 用户名
	public static final String LOGIN = "/login_toLogin.do"; // 登录地址
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*"; // 不对匹配该值的访问路径拦截（正则）

	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[] {
			"countries", "uname", "passwd", "title", "full_name",
			"company_name", "countries_code", "area_code", "telephone",
			"mobile" };
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[] {
			"国籍", "邮箱帐号", "密码", "称谓", "名称", "公司名称", "国家编号", "区号", "电话", "手机号" };

	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[] { "USERNAME" };
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[] { "用户名" };

}
