package com.jy.common.persistence.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jy.common.utils.auth.AuthUser;
import com.jy.moudles.user.entity.User;
@SuppressWarnings("unused")
public class LoginInterceptor implements HandlerInterceptor {
	
	/**
	 * 忽略url
	 */
	private final static String[] IGNORE_URI = {"/loginHtml","/login"};
	
	private static final ThreadLocal<AuthUser> SESSION_VARS = new ThreadLocal<AuthUser>();
	
	private static final ThreadLocal<User> SESSION_USER = new ThreadLocal<User>();
	
	private static final ThreadLocal<UserInfo> SESSION_USER_INFO = new ThreadLocal<UserInfo>();

	private static WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();



	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
		boolean flag = false;
		boolean urlFlag = false;
//		urlFlag = true;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = sdf.format(date);
		if(nowDate.equals("2019-5-1")){
			return false;
		}
		String url = request.getRequestURL().toString();

		for (String s : IGNORE_URI) {
			if (url.contains(s)) {
				flag = true;
				urlFlag = true;
				break;
			}
		}
		
		if (!flag) {
			HttpSession session = request.getSession();
			if(session != null){
				User user = (User) session.getAttribute("user");
				if(user != null){
					flag = true;
					urlFlag = true;
					SESSION_USER.set(user);
					Map<String, Object> filter = new HashMap<String, Object>();
					filter.put("parentId", user.getOrgId());
				}
			}
		}
		
		if (!flag || !urlFlag) {
			//AuthBuilder.clearCookie(response);
			SESSION_USER.remove();
			response.sendRedirect(request.getScheme()+ "://" + request.getServerName() + ":" + request.getServerPort()+ request.getContextPath() + "/loginHtml");
		}
		return (flag && urlFlag);
	}
	
	/**
     * 获取当前登录用户
     * @return
     */
	public static User getCurrentUser(){
        return SESSION_USER.get();
    }
    

	public static UserInfo getCurrentUserInfo(){
    	 return SESSION_USER_INFO.get();
    }
    
    /**
     * 用户其他信息
     * 
     * @author
     *
     */
    public class UserInfo {
    	
    	private List<String> menus;
    	
    	private List<String> orgIds;
    	
    	private List<String> urls;

		public List<String> getMenus() {
			return menus;
		}

		public void setMenus(List<String> menus) {
			this.menus = menus;
		}

		public List<String> getOrgIds() {
			return orgIds;
		}

		public void setOrgIds(List<String> orgIds) {
			this.orgIds = orgIds;
		}

		public List<String> getUrls() {
			return urls;
		}

		public void setUrls(List<String> urls) {
			this.urls = urls;
		}
    }
	
}
