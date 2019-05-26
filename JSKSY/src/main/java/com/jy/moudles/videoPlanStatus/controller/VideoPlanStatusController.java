package com.jy.moudles.videoPlanStatus.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jy.common.timer.constant.JSKSYTestUrl;
import com.jy.common.utils.https.HttpUtils;
import com.jy.common.utils.https.RequestPost;
import com.jy.common.utils.https.ResponsePost;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;
import com.jy.moudles.examSeason.entity.ExamSeason;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;
import com.jy.moudles.videoPlanStatus.entity.VideoPlanStatus;
import com.jy.moudles.videoPlanStatus.service.VideoPlanStatusService;

@Controller
@RequestMapping(value="/videoPlanStatus")
public class VideoPlanStatusController {
	
	@Autowired
	private VideoPlanStatusService videoPlanStatusService;
	
	@Autowired
	private ExamService examService;

	/**
	 * 提交考前计划
	 * @return
	 * @throws Exception
	 * 
	 *http://localhost:19902/JSKSY/videoPlanStatus/videoPlan
	 */
	@RequestMapping(value = "/videoPlan")
	public @ResponseBody Object videoPlan() throws Exception{
		JSONObject obj = new JSONObject();
		//默认操作成功
		obj.put("result", true);
		obj.put("msg", "提交成功");
		System.out.println("***************提交考前录像计划-开始***************");
		String OrgCode = AccessTokenUtil.getOrgCode();
		String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();

		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", AccessTokenUtil.getAccessToken());
		Map<Object, Object> data = new HashMap<>();
		data.put("OrgCode", OrgCode);
		data.put("OrgIdenCode", OrgIdenCode);

		List<Exam> lists = examService.queryAllExamWithSeason();
		if(null != lists && lists.size() > 0) {
			for(Exam e : lists) {
				data.put("ExamPlan", e.getKsbh());	//考试计划编号
				List<ExamSeason> list = e.getCc();	//考试场次合集
				for(ExamSeason ls : list) {
					data.put("ExamSession", ls.getCcbh());	//考试场次序号
					List<Map<Object, Object>> Device = new ArrayList<Map<Object, Object>>();
					
					Map<String, Object> Filter = new HashMap<>();
					Filter.put("OrgCode", OrgCode);
					Filter.put("OrgIdenCode", OrgIdenCode);
					Filter.put("ExamPlan", e.getKsbh());
					Filter.put("ExamSession", ls.getCcbh());
					List<VideoPlanStatus> list2 = videoPlanStatusService.queryVideoPlanStatusFilter(Filter);
					
					if(list2!=null&&list2.size()>0){
						for(VideoPlanStatus ls2:list2){
							Map<Object, Object> Dev = new HashMap<Object, Object>();
							Dev.put("CSBH", ls2.getCsbh());
							Dev.put("FGQK", ls2.getFgqk()); // 覆盖情况1：已完全覆盖 -1:未设置录像计划 -2：未完全覆盖
							Device.add(Dev);
						}
					}else{
						continue;
					}
					
					data.put("Device", Device);
					requestPost.setCertification(certification);
					requestPost.setData(data);
					ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEVIDEOPLANCOVERSTATUS, requestPost);

					boolean result = responsePost.getResult();
					System.out.println("result=" + result);
					if (result) {
						System.out.println("提交成功");
						obj.put("result", true);
						obj.put("msg", "提交成功");
					} else {
						String msg = responsePost.getMessage();
						String code = responsePost.getCode();
						System.out.println("提交出错-Message：" + msg + ";Code:" + code);
						obj.put("result", false);
						obj.put("msg", "提交出错-Message：" + msg + ";Code:" + code);
					}
					
				}
			}
		}
		System.out.println("***************提交考前录像计划-结束***************");
		return obj.toString();
	}
	
	/**
	 * 提交考后计划
	 * @return
	 * @throws Exception
	 * http://localhost:19902/JSKSY/videoPlanStatus/videoSave
	 */
	@RequestMapping(value = "/videoSave")
	public @ResponseBody Object videoSave() throws Exception{
		JSONObject obj = new JSONObject();
		//默认操作成功
		obj.put("result", true);
		obj.put("msg", "提交成功");
		System.out.println("***************提交考后录像计划-开始***************");
		String OrgCode = AccessTokenUtil.getOrgCode();
		String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();

		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", AccessTokenUtil.getAccessToken());
		Map<Object, Object> data = new HashMap<>();
		data.put("OrgCode", OrgCode);
		data.put("OrgIdenCode", OrgIdenCode);

		List<Exam> lists = examService.queryAllExamWithSeason();
		if(null != lists && lists.size() > 0) {
			for(Exam e : lists) {
				data.put("ExamPlan", e.getKsbh());	//考试计划编号
				List<ExamSeason> list = e.getCc();	//考试场次合集
				for(ExamSeason ls : list) {
					data.put("ExamSession", ls.getCcbh());	//考试场次序号
					
					List<Map<Object, Object>> Device = new ArrayList<Map<Object, Object>>();
					
					Map<String, Object> Filter = new HashMap<>();
					Filter.put("OrgCode", OrgCode);
					Filter.put("OrgIdenCode", OrgIdenCode);
					Filter.put("ExamPlan", e.getKsbh());
					Filter.put("ExamSession", ls.getCcbh());
					List<VideoPlanStatus> list2 = videoPlanStatusService.queryVideoPlanStatusFilter(Filter);
					
					if(list2!=null&&list2.size()>0){
						for(VideoPlanStatus ls2:list2){
							Map<Object, Object> Dev = new HashMap<Object, Object>();
							Dev.put("CSBH", ls2.getCsbh());
							Dev.put("FGQK", ls2.getStatustype()); // 覆盖情况1：已完全覆盖 -2：未完全覆盖
							Device.add(Dev);
						}
					}else{
						continue;
					}
					
					data.put("Device", Device);
					
					requestPost.setCertification(certification);
					requestPost.setData(data);
					ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEVIDEOSAVECOVERSTATUS, requestPost);

					boolean result = responsePost.getResult();
					System.out.println("result=" + result);
					if (result) {
						System.out.println("提交成功");
						obj.put("result", true);
						obj.put("msg", "提交成功");
					} else {
						String msg = responsePost.getMessage();
						String code = responsePost.getCode();
						System.out.println("提交出错-Message：" + msg + ";Code:" + code);
						obj.put("result", false);
						obj.put("msg", "提交出错-Message：" + msg + ";Code:" + code);
					}
				}
			}
		}
		System.out.println("***************提交考后录像计划-结束***************");
		return obj.toString();
	}
}
