package com.jy.common.persistence.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jy.common.entity.MenuInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.mapper.JsonMapper;
import com.jy.common.utils.auth.AuthBuilder;
import com.jy.common.utils.auth.AuthUser;
import com.jy.moudles.user.entity.User;
import com.jy.moudles.user.service.UserService;

@SuppressWarnings("unused")
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 忽略url
     */
    private final static String[] IGNORE_URI = {"/loginHtml", "/login"};

    private static final ThreadLocal<User> SESSION_VARS = new ThreadLocal<User>();

    private static final ThreadLocal<UserInfo> SESSION_USER_INFO = new ThreadLocal<UserInfo>();

    public static final String SESSION_USER = "user";

    public static final String JY_EDU_EVALUATION_MANAGER = "jy_pc_manager";

    public static final String JY_EDU_EVALUATION_VIEWER = "jy_pc_viewer";

    public static final String JY_EDU_EVALUATION_MB_VIEWER = "jy_mb_viewer";

    public static final String SECRETKEY_JY_EDU_EVALUATION_MANAGER = "JY_EDU_EVALUATION";

    public static final String SECRETKEY_JY_EDU_EVALUATION_VIEWER = "JY_EDU_EVALUATION_VIEWER";

    public static final String SECRETKEY_JY_EDU_EVALUATION_MB_VIEWER = "JY_EDU_EVALUATION_MB_VIEWER";

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object obj, Exception e)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object obj, ModelAndView mv) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object obj) throws Exception {
        boolean flag = false;
        boolean urlFlag = false;

        // 跨域拦截
        String ref = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", ref);

        String url = request.getRequestURL().toString();
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                urlFlag = true;
                break;
            }
        }
        if (!urlFlag) {
            String secretKey = "";
            String strBackUrl = request.getQueryString();

            if (!StringUtils.isBlank(strBackUrl)) {
                if (strBackUrl.contains(JY_EDU_EVALUATION_MANAGER)) {
                    secretKey = SECRETKEY_JY_EDU_EVALUATION_MANAGER;
                } else if (strBackUrl.contains(JY_EDU_EVALUATION_VIEWER)) {
                    secretKey = SECRETKEY_JY_EDU_EVALUATION_VIEWER;
                } else if (strBackUrl.contains(JY_EDU_EVALUATION_MB_VIEWER)) {
                    secretKey = SECRETKEY_JY_EDU_EVALUATION_MB_VIEWER;
                } else {
                    flag = false;
                }
            }

            AuthUser authUser = AuthBuilder.getUser(request, secretKey);

            if (authUser.isAuth()) {
                User user = getCurrentUser();

                BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                UserService service = factory.getBean(UserService.class);
                if (null == user || !user.getId().equals(authUser.getId())) {
                    user = service.getUserById(authUser.getId());
                    SESSION_VARS.set(user);
                }

                if (strBackUrl.contains(JY_EDU_EVALUATION_MANAGER)) {
                    List<MenuInfo> infos = service.getMenusByUserId(authUser.getId());

                    if (null != infos && 0 < infos.size()) {
                        flag = true;
                    }
                } else {
                    // 管理员用户和超级管理员用户不允许登录展示端和APP
                    if (0 != user.getUserType() && 1 != user.getUserType()) {
                        flag = true;
                    }
                }
            }
            if (!flag) {
                AuthBuilder.clearCookie(response, secretKey);

                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write(JsonMapper.toJsonString(AsyncResponseData.getTimeoutrror("用户登录超时，请重新登录!")));
                response.getWriter().close();
            }
        } else {
            flag = true;
        }

        return flag;
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    public static User getCurrentUser() {
        return SESSION_VARS.get();
    }

    /**
     * 用户其他信息
     *
     * @author
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
