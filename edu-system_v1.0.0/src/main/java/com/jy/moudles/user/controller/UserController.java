package com.jy.moudles.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.common.utils.MD5Util;
import com.jy.common.utils.PinYinUtils;
import com.jy.common.utils.RandomCodeUtil;
import com.jy.moudles.role.constant.RoleConstant;
import com.jy.moudles.role.entity.Role;
import com.jy.moudles.role.service.RoleService;
import com.jy.moudles.student.entity.Student;
import com.jy.moudles.student.service.StudentService;
import com.jy.moudles.teacher.entity.Teacher;
import com.jy.moudles.teacher.service.TeacherService;
import com.jy.moudles.user.constants.UserConstants;
import com.jy.moudles.user.entity.User;
import com.jy.moudles.user.service.UserService;

/** 
 * user实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TeacherService teacherService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	// 用户类型 管理员
	private static final int USER_TYPE_ADMIN = 1;
	
	/**
	 * 新增用户
	 *
	 * @param user
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveUser(User user) throws Exception{
		LOGGER.info("新增User Start");
		
		String name = user.getName();
		String userName = user.getUserName();
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(name)) {
			return AsyncResponseData.getSuccess().asParamError("姓名或用户名不能为空");
		}
		user.setUserPsw(MD5Util.MD5(UserConstants.DEFUALT_USER_PASSWORD));
		user.setIsChangedPsw(UserConstants.IS_CHANGE_PASSWORD);
		//加入创建用户和组织机构id
		User currentUser = LoginInterceptor.getCurrentUser();
		if(null == currentUser) {
			return AsyncResponseData.getSuccess().asParamError("登录超时");
		}
		user.setCreateUser(currentUser.getUserName());
        user.setCreateDate(new Date());
        user.setUpdateUser(currentUser.getUserName());
        user.setUpdateDate(new Date());
		user.setOrgId(currentUser.getOrgId());

		userService.insertUser(user);

		LOGGER.info("新增User End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 新增管理员用户
	 *
	 * @param user
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveManagerUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveManagerUser(User user) throws Exception{
		LOGGER.info("新增User Start");

		String name = user.getName();
		String userName = user.getUserName();
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(name)) {
			return AsyncResponseData.getSuccess().asParamError("姓名或用户名不能为空");
		}
		
		user.setUserPsw(MD5Util.MD5(UserConstants.DEFUALT_USER_PASSWORD));
		
		user.setIsChangedPsw(1);
		//加入创建用户和组织机构id
		User currentUser = LoginInterceptor.getCurrentUser();
		if(null == currentUser) {
			return AsyncResponseData.getSuccess().asParamError("登录超时");
		}
		user.setUserType(UserConstants.ADMIN_USER_TYPE);
		//获取角色信息
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("roleName", RoleConstant.ADMIN_ROLE);
		List<Role> list = roleService.queryRolesFilter(filter);
		if(null != list && list.size() > 0) {
			user.setRoleId(list.get(0).getId());
		}
		
		user.setCreateDate(new Date());
		user.setCreateUser(currentUser.getId());
		user.setUpdateDate(new Date());
		user.setUpdateUser(currentUser.getId());
		
		userService.insertUser(user);

		LOGGER.info("新增User End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 新增学生用户
	 * 
	 * @param student
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveStudentUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveStudentUser(Student student) throws Exception{
		LOGGER.info("新增学生用户 Start");

		User user = new User();
		
		//查重
		if(!checkUser(student.getId())) {
			return AsyncResponseData.getSuccess().asParamError("该学生已有账号");
		}
		
		//取出学生对象
		student = studentService.getStudentById(student.getId());
		if(null == student) {
			return AsyncResponseData.getSuccess().asParamError("未查询到该学生");
		}
		//加入创建用户
		User currentUser = LoginInterceptor.getCurrentUser();
		if(null == currentUser) {
			return AsyncResponseData.getSuccess().asParamError("登录超时");
		}
		
		//学生用户名 姓名转拼音
		String name = student.getStudentName();
		
		if(StringUtils.isNotBlank(name)) {
			String userName = "";
			userName = PinYinUtils.converterToSpell(name).split(",")[0] + "_" + RandomCodeUtil.randomCode(4);
			user.setUserName(userName);
			user.setName(student.getStudentName());
			user.setOrgId(student.getOrgId());
			user.setIsChangedPsw(UserConstants.IS_CHANGE_PASSWORD);
			user.setRoleId(RoleConstant.STUDENT_ROLE_ID);
			user.setRoleName(RoleConstant.STUDENT_ROLE);
			user.setUserPsw(MD5Util.MD5(UserConstants.DEFUALT_USER_PASSWORD));
			user.setSourceId(student.getId());
			user.setUserType(UserConstants.INDIVIDUAL_USER_TYPE);
			user.setCreateDate(new Date());
			user.setCreateUser(currentUser.getId());
			user.setUpdateDate(new Date());
			user.setUpdateUser(currentUser.getId());
			
			userService.insertUser(user);
		}else {
			return AsyncResponseData.getSuccess().asParamError("学生姓名不能为空");
		}
		
		
		LOGGER.info("新增学生用户 End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 新增老师用户
	 *
	 * @param teacher
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTeacherUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveTeacherUser(Teacher teacher) throws Exception{
		
		teacher = teacherService.getTeacherById(teacher.getId());
		
		if(null == teacher) {
			return AsyncResponseData.getSuccess().asParamError("未查询到该教师");
		}
		
		//电话校验
		String phone = teacher.getTeacherMobile();
		if(StringUtils.isBlank(phone)) {
			return AsyncResponseData.getSuccess().asParamError("该教师电话不能为空");
		}
		String regex = "^[1][2-9]([0-9]{9})$";
		if(!phone.matches(regex)) {
			return AsyncResponseData.getSuccess().asParamError("该教师电话格式错误");
		}
		
		if(userService.isExistTeacherUser(teacher.getTeacherMobile())!=null) {
			return AsyncResponseData.getSuccess().asParamError("手机号码已存在");
		}
		
		//查重
		if(!checkUser(teacher.getId())) {
			return AsyncResponseData.getSuccess().asParamError("该教师已有账号");
		}
		
		//加入创建用户
		User currentUser = LoginInterceptor.getCurrentUser();
		if(null == currentUser) {
			return AsyncResponseData.getSuccess().asParamError("登录超时");
		}
		
		LOGGER.info("新增老师用户 Start");
		
		User user = new User();
		user.setUserName(teacher.getTeacherMobile());
		user.setName(teacher.getTeacherName());
		user.setUserPsw(MD5Util.MD5(UserConstants.DEFUALT_USER_PASSWORD));
		user.setIsChangedPsw(UserConstants.IS_CHANGE_PASSWORD);
		user.setRoleId(RoleConstant.TEACHER_ROLE_ID);
		user.setRoleName(RoleConstant.TEACHER_ROLE);
		user.setOrgId(currentUser.getOrgId());
		user.setSourceId(teacher.getId());
		user.setCreateDate(new Date());
		user.setCreateUser(currentUser.getId());
		user.setUpdateDate(new Date());
		user.setUpdateUser(currentUser.getId());
		userService.insertUser(user);

		LOGGER.info("新增老师用户 End");
		return AsyncResponseData.getSuccess();
	}
	

	/**
	 * 修改user对象
	 * 
	 * @param user
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateUser(User user) throws Exception{
		LOGGER.info("修改User Start");
		
		User currentUser = LoginInterceptor.getCurrentUser();
		if(null == currentUser) {
			return AsyncResponseData.getSuccess().asParamError("登录超时");
		}
		user.setUpdateDate(new Date());
		user.setUpdateUser(currentUser.getId());
		userService.updateUser(user);
		
		LOGGER.info("修改User End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除user对象
	 * 
	 * @param user
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteUser(User user) throws Exception{
		LOGGER.info("删除User Start");
		
		userService.deleteUserById(user.getId());
		
		LOGGER.info("删除User End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 根据id获取user对象
	 * 
	 * @param user
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getUserById(User user) throws Exception{
		LOGGER.info("删除User Start");
		
		User users= userService.getUserById(user.getId());
		users.setUserPsw("");
		LOGGER.info("删除User End");
		return AsyncResponseData.getSuccess(users);
	}
	
	/**
	 * 获取登陆user对象
	 * 
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLoginUser", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getLoginUser() throws Exception{
		LOGGER.info("删除User Start");
		User user = LoginInterceptor.getCurrentUser();
		user.setUserPsw("");
		LOGGER.info("删除User End");
		return AsyncResponseData.getSuccess(user);
	}
	
	/**
	 * 获取user对象
	 * 
	 * @param user
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryUsers", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryUsers(int pageNum, int pageSize, User user) throws Exception {
		LOGGER.info("获取User Start");
		Map<String, Object> filter = new HashMap<String, Object>();

		User currentUser = LoginInterceptor.getCurrentUser();

		if (0 == currentUser.getUserType()) {
			filter.put("currentUserType", 0);
			filter.put("userType", USER_TYPE_ADMIN);
		} else {
		    filter.put("currentOrgId",currentUser.getOrgId());
        }

		if (user != null) {

            if (StringUtils.isNotBlank(user.getName())) {
                filter.put("name", user.getName());
            }
            // 修改【用户管理-查询】用户名输入“老王”点击查询，列表中显示所有用户数据  by jinxiaoxiang  2018-1-2 Start
            if (StringUtils.isNotBlank(user.getUserName())) {
                filter.put("userName", user.getUserName());
            }
            // 修改【用户管理-查询】用户名输入“老王”点击查询，列表中显示所有用户数据  by jinxiaoxiang  2018-1-2 End
            if (StringUtils.isNotBlank(user.getRoleName())) {
                filter.put("roleName", user.getRoleName());
            }
            if (StringUtils.isNotBlank(user.getRoleId())) {
                filter.put("roleId", user.getRoleId());
            }
            if (0 != currentUser.getUserType()) {
                filter.put("userType", user.getUserType());
            }
//            if (StringUtils.isNotBlank(user.getOrgId())) {
//                filter.put("orgId", user.getOrgId());
//            }
        }
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<User> users = new PageInfo<User>(userService.queryUsersFilter(filter));
		LOGGER.info("获取User End");
		return AsyncResponseData.getSuccess(users);
	}

	/**
	 * 修改user密码
	 *
	 * @param psw
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPsw", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData resetPsw(String psw, String newPsw1, String newPsw2, HttpServletRequest request) throws Exception {
		String errorsMsg = "";
		//从数据库中获取原密码
		User user = LoginInterceptor.getCurrentUser();
		if(null == user) {
			return AsyncResponseData.getDenied("登录信息过期");
		}
		user = userService.getUserByNameAndPwd(user.getUserName(), MD5Util.MD5(psw));
		//如果输入的原密码与数据库中的密码相同可更改
		if (null == user) {
			errorsMsg += "原密码错误";
			return AsyncResponseData.getSuccess().asSystemError(errorsMsg);
		}
		if(!newPsw1.equals(newPsw2)){
			errorsMsg += "两次输入的新密码不一致";
			return AsyncResponseData.getSuccess().asSystemError(errorsMsg);
		}
		newPsw1 = MD5Util.MD5(newPsw1).toLowerCase();
		
		user.setUserPsw(newPsw1);
		
		user.setIsChangedPsw(UserConstants.IS_NOT_CHANGE_PASSWORD);
		
		user.setRoleName(null);
		userService.updateUser(user);
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 重置user密码
	 *
	 * @param id
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/initPsw", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData initPsw(String id) throws Exception {

        User user = LoginInterceptor.getCurrentUser();
        if(null == user) {
            return AsyncResponseData.getDenied("登录信息过期");
        }
        if (UserConstants.SUPER_ADMIN_USER_TYPE != user.getUserType() && UserConstants.ADMIN_USER_TYPE != user.getUserType()){
            return AsyncResponseData.getSuccess().asParamError("用户无权重置密码");
        }

		userService.initPassword(id);
		return AsyncResponseData.getSuccess();
	}
	
	//查重
	public boolean checkUser(String id) {
		
		Map<String,Object> filter = new HashMap<>();
		filter.put("sourceId", id);
		List<User> list = userService.queryUsersFilter(filter);
		if(null != list && list.size() > 0) {
			return false;
		}else {
			return true;
		}
	}
}
