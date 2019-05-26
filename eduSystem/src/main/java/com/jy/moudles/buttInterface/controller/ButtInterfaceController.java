package com.jy.moudles.buttInterface.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.buttInterface.service.ButtInterfaceService;
import com.jy.moudles.buttInterface.utils.Constant;
import com.jy.moudles.buttInterface.utils.DataUtils;
import com.jy.moudles.buttInterface.utils.VerificationUtils;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.teacher.entity.Teacher;
import com.jy.moudles.user.entity.User;

/**
 * 对接接口
 * @author hzl
 *
 */
@RestController
@RequestMapping("buttInterface")
public class ButtInterfaceController {
	@Autowired
	private ButtInterfaceService buttInterfaceService;
	
	Logger logger = LoggerFactory.getLogger(ButtInterfaceController.class);
	//首次访问对方接口 存我方带过去的codeMsg key是codeMsg value是user信息
	public static Map<String,Object> codeMsgMap = new HashMap<>();
	//专门存放token
	private static Map<String,Object> tokenMap = new HashMap<>();
	
	
	
	/**
	 * 获得token
	 * @param curryTime 时间戳
	 * @param codeMsg codeMsg 是随机数和时间戳的加密字符串
	 * @return token
	 * @author 黄忠柳
	 * Create on 2018年1月24日 上午9:00:52
	 */
	@RequestMapping(value = "/getToken", method = RequestMethod.POST)
	public AsyncResponseData.ResultData getToken(String codeMsg,long curryTime,String accessKey) {
		//先判空参数
		if (DataUtils.isNull(curryTime) || DataUtils.isNull(codeMsg) || DataUtils.isNull(accessKey)) {
			return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
 		}
		
		//验证是否是从我方点击过去的  我方点击对方项目时会带codemsg过去并且保存在map中 对方调用获取token时候要存
		if (!codeMsgMap.containsKey(codeMsg)) {
			return AsyncResponseData.getSuccess().asParamError("拒绝访问");
		}
		
		//直接根据codeMsg获取map中的值
		User user = (User) codeMsgMap.get(codeMsg);
		//如果codeMsg验证通过了我们就清除当前key
		codeMsgMap.remove(codeMsg);
		
		TreeMap<String,Object> param = new TreeMap<>();
		param.put("codeMsg", codeMsg);
		param.put("curryTime", curryTime);
		//验证签名
		if (!VerificationUtils.checkParamKey(param,accessKey,Constant.PRIVATE_KEY)) {
 			return AsyncResponseData.getSuccess().asParamError("签名验证失败");
		}
		
 		
 		//加密生成token
 		String token = VerificationUtils.createToken(user,Constant.TOKEN_KEY);
 		if (DataUtils.isNull(token)) {
 			return AsyncResponseData.getSuccess().asParamError("生成token失败");
 		}
 		
 		//把token存入map中 以token为key值 以user为value值
 		tokenMap.put(token, user);
 		
		return AsyncResponseData.getSuccess(token);
	}
	
	
	/**
	 * 获取用户信息
	 * @param token 令牌
	 * @param curryTime 时间戳
	 * @param accessKey 验签签名
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月24日 下午5:06:15
	 */
	@RequestMapping(value = "/getUserMsg", method = RequestMethod.POST)
	public AsyncResponseData.ResultData getUserMsg(String token,long curryTime,String accessKey) {
		
		if (DataUtils.isNull(token) || DataUtils.isNull(curryTime) || DataUtils.isNull(accessKey)) {
			return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
		}
		
		//验证签名
		TreeMap<String,Object> param = new TreeMap<>();
		param.put("token", token);
		param.put("curryTime", curryTime);
		if (!VerificationUtils.checkParamKey(param,accessKey,Constant.PRIVATE_KEY)) {
 			return AsyncResponseData.getSuccess().asParamError("签名验证失败");
		}
		
		//验证token
		if (!tokenMap.containsKey(token)) {
 			return AsyncResponseData.getSuccess().asParamError("拒绝访问");
		}
		
		//获取用户信息
		User user =  (User) tokenMap.get(token);
		//删除tokenMap对应的token
		tokenMap.remove(token);
		
		return AsyncResponseData.getSuccess(user);
	}
	
	/**
	 * 根据条件查询考试信息
	 * @param curryTime 当前毫秒数
	 * @param accessKey 签名
	 * @param orgId 组织id（非必须）
	 * @param examId 考试id（非必须）
	 * @param examStatus 考试状态  未开始、正在进行、已结束（0、1、2）
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月25日 下午1:48:26
	 */
	@RequestMapping(value = "/queryExamInfoMsg", method = RequestMethod.POST)
	public AsyncResponseData.ResultData queryExamInfoMsg(String curryTime,String accessKey,String orgId,String examId,String examStatus) {
		
		if (DataUtils.isNull(curryTime) || DataUtils.isNull(accessKey) || DataUtils.isNull(examId)) {
 			return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
		}
		
		//存放条件参数
		Map<String,Object> param = new HashMap<>();
		//验证签名
		TreeMap<String,Object> paramCheck = new TreeMap<>();
		paramCheck.put("curryTime", curryTime);
		
		if (DataUtils.isNotNull(orgId)) {
			paramCheck.put("orgId", orgId);
			param.put("orgId", orgId);
		}
		
		if (DataUtils.isNotNull(examId)) {
			paramCheck.put("examId", examId);
			param.put("examId", examId);
		}
		
		if (DataUtils.isNotNull(examStatus)) {
			paramCheck.put("examStatus", examStatus);
			param.put("examStatus", examStatus);
		}
		
		if (!VerificationUtils.checkParamKey(paramCheck,accessKey,Constant.PRIVATE_KEY)) {
 			return AsyncResponseData.getSuccess().asParamError("签名验证失败");
		}
		
		//只为了加上gradeCode
		List<Exam> examList = buttInterfaceService.queryExamInfoMsg(param);
		for (Exam exam : examList) {
			String classRoom = exam.getClassroom();
			if (DataUtils.isNull(classRoom)) {
				continue;
			}
			
			if (Constant.gradeTMap.containsKey(classRoom.substring(0,3))) {
				exam.setGradeCode(Constant.gradeTMap.get(classRoom.substring(0,3)));
			} else if (Constant.gradeTMap.containsKey(classRoom.substring(0,2))) {
				exam.setGradeCode(Constant.gradeTMap.get(classRoom.substring(0,2)));
			} else {
				continue;
			}
			
		}
		
		//考试信息列表
		return AsyncResponseData.getSuccess(examList);
	}
	
	
	/**
	 * 根据条件查询场考试的所有科目双向细目表
	 * @param curryTime 时间戳
	 * @param accessKey 签名
	 * @param examId 考试id 
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月26日 上午10:06:49
	 */
	@RequestMapping(value = "/queryTwoWaySpecificationMsg", method = RequestMethod.POST)
	public AsyncResponseData.ResultData queryTwoWaySpecificationMsg(String curryTime,String accessKey,String examId) {
		
		if (DataUtils.isNull(curryTime) || DataUtils.isNull(accessKey) || DataUtils.isNull(examId)) {
 			return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
		}
		
		//验证签名
		TreeMap<String,Object> paramCheck = new TreeMap<>();
		paramCheck.put("curryTime", curryTime);
		paramCheck.put("examId", examId);
		if (!VerificationUtils.checkParamKey(paramCheck,accessKey,Constant.PRIVATE_KEY)) {
 			return AsyncResponseData.getSuccess().asParamError("签名验证失败");
		}
		
		//根据条件查询场考试的所有科目双向细目表
		Map<String,Object> param = new HashMap<>();
		param.put("examId", examId);
		
		return AsyncResponseData.getSuccess(buttInterfaceService.queryTwoWaySpecificationMsg(param));
	}
	
	/**
	 * 根据条件查询教师信息
	 * @param curryTime 时间戳
	 * @param accessKey 签名
	 * @param schoolId 学校id（必要）
	 * @param orgId 组织id（不必要）
	 * @param gradeCode 年级编码（不必要）
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月26日 上午10:51:04
	 */
	@RequestMapping(value = "/queryTeacherMsg", method = RequestMethod.POST)
	public AsyncResponseData.ResultData queryTeachermsg(String curryTime, String accessKey, String schoolId,String orgId,String gradeCode) {
		
		if (DataUtils.isNull(curryTime) || DataUtils.isNull(accessKey) || DataUtils.isNull(schoolId) || DataUtils.isNull(gradeCode)) {
 			return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
		}	
		
		
		//存放条件参数
		Map<String,Object> param = new HashMap<>();
		//验证签名
		TreeMap<String,Object> paramCheck = new TreeMap<>();
		paramCheck.put("curryTime", curryTime);
		if (DataUtils.isNotNull(schoolId)) {
			paramCheck.put("schoolId", schoolId);
			param.put("schoolId", schoolId);
		}
		
		if (DataUtils.isNotNull(gradeCode)) {
			String gradeName = Constant.gradeMap.get(gradeCode);
			paramCheck.put("gradeCode", gradeCode);
			param.put("gradeCode", gradeName);
		}
		
		
		if (DataUtils.isNotNull(orgId)) {
			paramCheck.put("orgId", orgId);
			param.put("orgId", orgId);
		}
		
		if (!VerificationUtils.checkParamKey(paramCheck,accessKey,Constant.PRIVATE_KEY)) {
			return AsyncResponseData.getSuccess().asParamError("签名验证失败");
		}
		
		//只为加上年纪编码
		List<Teacher> teacherList = buttInterfaceService.queryTeachermsg(param);
		for (Teacher teacher : teacherList) {
			teacher.setGradeCode(gradeCode);
		}
		
		//获得教师信息
		return AsyncResponseData.getSuccess(teacherList);
	}
	
	/**
	 * 考试的学生信息
	 * @param curryTime 时间戳
	 * @param accessKey 签名
	 * @param examId 考试id (不必要)
	 * @param orgId 组织id (不必要)
	 * @param schoolId 学校id (不必要)
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月26日 下午2:12:50
	 */
	@RequestMapping(value = "/queryStudentExamMsg", method = RequestMethod.POST)
	public AsyncResponseData.ResultData queryStudentExamMsg(String curryTime, String accessKey,String examId
			,String orgId,String schoolId) {
		
		if (DataUtils.isNull(curryTime) || DataUtils.isNull(accessKey) || DataUtils.isNull(examId)) {
			return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
		}	
		
		//存放条件参数
		Map<String,Object> param = new HashMap<>();
		//验证签名
		TreeMap<String,Object> paramCheck = new TreeMap<>();
		paramCheck.put("curryTime", curryTime);
		if (DataUtils.isNotNull(schoolId)) {
			paramCheck.put("schoolId", schoolId);
			param.put("schoolId", schoolId);
		}
		
		if (DataUtils.isNotNull(orgId)) {
			paramCheck.put("orgId", orgId);
			param.put("orgId", orgId);
		}
		
		if (DataUtils.isNotNull(examId)) {
			paramCheck.put("examId", examId);
			param.put("examId", examId);
		}
		
		if (!VerificationUtils.checkParamKey(paramCheck,accessKey,Constant.PRIVATE_KEY)) {
 			return AsyncResponseData.getSuccess().asParamError("签名验证失败");
		}
		
		//获得学生考试信息
		return AsyncResponseData.getSuccess(buttInterfaceService.queryStudentExamMsg(param));
	}
	
	/**
	 * 修改考试信息
	 * @param curryTime 时间戳
	 * @param accessKey 签名
	 * @param examId 考试id
	 * @param examStatus 考试状态 3正在阅卷 4阅卷完成 
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月30日 下午1:58:52
	 */
	@RequestMapping(value = "/updateExamInfo", method = RequestMethod.POST)
	public AsyncResponseData.ResultData updateExamInfo(String curryTime, String accessKey,String examId,String examStatus) {
		if (DataUtils.isNull(curryTime) || DataUtils.isNull(accessKey) || DataUtils.isNull(examId) || DataUtils.isNull(examStatus)) {
			return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
		}	
		
		//如果传来的状态不符合3和4就错误提示
		if (!"3".equals(examStatus) && !"4".equals(examStatus)) {
			return AsyncResponseData.getSuccess().asParamError("考试状态错误");
		}
		
		//存放条件参数
		Map<String,Object> param = new HashMap<>();
		param.put("examId", examId);
		param.put("examStatus", examStatus);
		
		//验证签名
		TreeMap<String,Object> paramCheck = new TreeMap<>();
		paramCheck.put("curryTime", curryTime);
		paramCheck.put("examId", examId);
		paramCheck.put("examStatus", examStatus);
		if (!VerificationUtils.checkParamKey(paramCheck,accessKey,Constant.PRIVATE_KEY)) {
 			return AsyncResponseData.getSuccess().asParamError("签名验证失败");
		}
		
		try {
			int i = buttInterfaceService.updateExamInfo(param);
			if (i < 1) {
				return AsyncResponseData.getSuccess().asParamError("更新状态失败");
			}
			
			return AsyncResponseData.getSuccess("更新状态成功");
					
		} catch (Exception e) {
			e.printStackTrace();
			return AsyncResponseData.getSuccess().asParamError("更新状态失败");
		}
		
	}
	
	/**
	 * 接收分数信息
	 * @param score  json格式
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年1月31日 下午5:25:38
	 */
	@RequestMapping(value = "/receiveScore", method = RequestMethod.POST)
	public AsyncResponseData.ResultData receiveScore(String curryTime, String accessKey,String score) {
		System.out.println("score----------------------------:"+score);
		try {
			
			if (DataUtils.isNull(score) || DataUtils.isNull(curryTime) || DataUtils.isNull(accessKey)) {
				return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
			}
			
			//验证签名
			TreeMap<String,Object> paramCheck = new TreeMap<>();
			paramCheck.put("curryTime", curryTime);
			paramCheck.put("score", score);
			if (!VerificationUtils.checkParamKey(paramCheck,accessKey,Constant.PRIVATE_KEY)) {
	 			return AsyncResponseData.getSuccess().asParamError("签名验证失败");
			}
			
			//学生分数信息
			List<Map<String,Object>> params = new LinkedList<>();
			//处理接收的数据
			JSONArray array = JSONArray.parseArray(score);
			for (Object object : array) {
				Map<String,Object> map = (Map<String, Object>) object;
				map.put("id", UUIDUtil.get32UUID());
				map.put("createUser", "jieshou");
				map.put("createDate", new Date());
				//空值转换
				map = Constant.changeScore(map);
				params.add(map);
			}
			
			//批量插入分数信息
			int row = buttInterfaceService.receiveScore(params);
			if (row < 1) {
				return AsyncResponseData.getSuccess().asParamError("接收分数信息失败");
			}
			
			return AsyncResponseData.getSuccess("接收分数信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			return AsyncResponseData.getSuccess().asParamError("接收分数信息失败");
		}
	}
	
	/**
	 *  接收试卷信息
	 * @param ExamMsg json格式考试信息
	 * @param curryTime 时间戳
	 * @param accessKey 签名
	 * @return
	 * @author 黄忠柳
	 * Create on 2018年2月1日 上午9:36:53
	 */
	@RequestMapping(value = "/receiveExamMsg", method = RequestMethod.POST)
	public AsyncResponseData.ResultData receiveExamMsg(HttpServletRequest request,String curryTime, String accessKey,String examMsg) {
		
		try {
			
			if (DataUtils.isNull(examMsg) || DataUtils.isNull(curryTime) || DataUtils.isNull(accessKey)) {
				return AsyncResponseData.getSuccess().asParamError("缺少必要参数");
			}
			
			//验证签名
			TreeMap<String,Object> paramCheck = new TreeMap<>();
			paramCheck.put("curryTime", curryTime);
			paramCheck.put("examMsg", examMsg);
			
			if (!VerificationUtils.checkParamKey(paramCheck,accessKey,Constant.PRIVATE_KEY)) {
	 			return AsyncResponseData.getSuccess().asParamError("签名验证失败");
			}
			
			int i = buttInterfaceService.receiveScoreMsg(Constant.handleScoreMsg(examMsg));
			
			if (i < 1) {
				return AsyncResponseData.getSuccess().asParamError("接收试卷信息失败");
			}
			
			return AsyncResponseData.getSuccess("接收试卷信息成功");
		} catch (Exception e) {
			e.printStackTrace();
			return AsyncResponseData.getSuccess().asParamError("接收试卷信息失败");
		}
	}
	
}
