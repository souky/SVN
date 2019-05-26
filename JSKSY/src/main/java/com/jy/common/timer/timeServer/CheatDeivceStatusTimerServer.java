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
import com.jy.moudles.deviceInfo.entity.DeviceInfo;
import com.jy.moudles.deviceInfo.service.DeviceInfoService;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;
import com.jy.moudles.examSeason.entity.ExamSeason;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

public class CheatDeivceStatusTimerServer {

	public ApplicationContext context;

	public long dateOffset;

	public CheatDeivceStatusTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10; // 10分钟
	}

	public void start() {
		Timer timer = new Timer("setCheatDeviceStatus");
		timer.schedule(new setCheatDeviceStatus(), 1000 * 5, dateOffset);
	}

	class setCheatDeviceStatus extends TimerTask {

		@Override
		public void run() {
			ExamService examService = context.getBean(ExamService.class);
			DeviceInfoService deviceInfoService = context.getBean(DeviceInfoService.class);
			
			String OrgCode = AccessTokenUtil.getOrgCode();
			String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();

			RequestPost requestPost = new RequestPost();
			Map<Object, Object> certification = new HashMap<>();
			certification.put("AccessToken", AccessTokenUtil.getAccessToken());
			Map<Object, Object> data = new HashMap<>();
			data.put("OrgCode", OrgCode);
			data.put("OrgIdenCode", OrgIdenCode);

			Map<String, Object> Filter = new HashMap<>();
			Filter.put("OrgCode", OrgCode);
			Filter.put("OrgIdenCode", OrgIdenCode);
			Filter.put("sblxm", "401");
			List<DeviceInfo> listDevice = deviceInfoService.queryDeviceInfoFilter(Filter);
			
			List<Exam> lists = examService.queryAllExamWithSeason();
			if(null != lists && lists.size() > 0) {
				for(Exam e : lists) {
					data.put("ExamPlan", e.getKsbh());	//考试计划编号
					List<ExamSeason> list = e.getCc();	//考试场次合集
					for(ExamSeason ls : list) {
						data.put("ExamSession", ls.getCcbh());	//考试场次序号
						
						
						data.put("ExamPlan", e.getKsbh());
						data.put("ExamSession", ls.getCcbh());
						
						if(null != listDevice && listDevice.size() > 0){
							List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
							for(DeviceInfo d : listDevice) {
								
								Map<Object, Object> map = new HashMap<>();
								map.put("SBBH",d.getSbbh());
								map.put("SBCSDM",d.getSbcsdm());
								map.put("GZZT",1);
								Devices.add(map);
							}
							
							data.put("Devices", Devices);
							requestPost.setCertification(certification);
							requestPost.setData(data);
							ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETCHEATDEVICESTATUS, requestPost);

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
			}
			
			

		}

	}
}
