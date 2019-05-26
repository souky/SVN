package com.jy.common.timer.timeServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;

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

/**
 * 提交考场及保密室考后录像存储是否完整覆盖考试计划的状态
 * @author ccjy
 *
 */
public class VideoSaveCoverStatusTimerServer {

	public ApplicationContext context;

	public long dateOffset;

	public VideoSaveCoverStatusTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10; // 10分钟
	}

	public void start() {
		Timer timer = new Timer("setVideoSaveStatus");
		timer.schedule(new setVideoSaveStatus(), 1000 * 5, dateOffset);
	}

	class setVideoSaveStatus extends TimerTask {

		@Override
		public void run() {
			System.out.println("***************VideoSaveCoverStatusTimerServer-开始***************");
			VideoPlanStatusService videoPlanStatusService = context.getBean(VideoPlanStatusService.class);
			String OrgCode = AccessTokenUtil.getOrgCode();
			String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();

			RequestPost requestPost = new RequestPost();
			Map<Object, Object> certification = new HashMap<>();
			certification.put("AccessToken", AccessTokenUtil.getAccessToken());
			Map<Object, Object> data = new HashMap<>();
			data.put("OrgCode", OrgCode);
			data.put("OrgIdenCode", OrgIdenCode);

			ExamService examService = context.getBean(ExamService.class);
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
						Filter.put("STATUSTYPE", 2);	//1-考前，2-考后
						List<VideoPlanStatus> list2 = videoPlanStatusService.queryVideoPlanStatusFilter(Filter);
						
						if(list2!=null&&list2.size()>0){
							for(VideoPlanStatus ls2:list2){
								Map<Object, Object> Dev = new HashMap<Object, Object>();
								Dev.put("CSBH", ls2.getCsbh());
								Dev.put("FGQK", ls2.getFgqk()); // 覆盖情况1：已完全覆盖 -2：未完全覆盖
								Device.add(Dev);
							}
						}
						
						data.put("Device", Device);
						
						requestPost.setCertification(certification);
						requestPost.setData(data);
						ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEVIDEOSAVECOVERSTATUS, requestPost);

						boolean result = responsePost.getResult();
						System.out.println("result=" + result);
						if (result) {
							System.out.println("提交成功");
						} else {
							String msg = responsePost.getMessage();
							String code = responsePost.getCode();
							System.out.println("提交出错-Message：" + msg + ";Code:" + code);
						}
						
					}
				}
			}

			System.out.println("***************VideoSaveCoverStatusTimerServer-结束***************");
		}

	}
	
}
