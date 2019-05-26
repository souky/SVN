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
import com.jy.moudles.cameraStatus.entity.CameraStatus;
import com.jy.moudles.cameraStatus.service.CameraStatusService;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

/**
 * 提交网上巡查摄像机的工作状态
 * @author ccjy
 *
 */
public class CameraStatusTimerServer {

	public ApplicationContext context;

	public long dateOffset;

	public CameraStatusTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10; // 10分钟
	}

	public void start() {
		Timer timer = new Timer("setCamStatus");
		timer.schedule(new setCamStatus(), 1000 * 5, dateOffset);
	}

	class setCamStatus extends TimerTask {

		@Override
		public void run() {
			System.out.println("***************CameraStatusTimerServer-开始***************");
			CameraStatusService cameraStatusService = context.getBean(CameraStatusService.class);
			String OrgCode = AccessTokenUtil.getOrgCode();
			String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();

			RequestPost requestPost = new RequestPost();
			Map<Object, Object> certification = new HashMap<>();
			certification.put("AccessToken", AccessTokenUtil.getAccessToken());
			Map<Object, Object> data = new HashMap<>();
			data.put("OrgCode", OrgCode);
			data.put("OrgIdenCode", OrgIdenCode);

			List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
			
			Map<String, Object> Filter = new HashMap<>();
			Filter.put("OrgCode", OrgCode);
			Filter.put("OrgIdenCode", OrgIdenCode);
			List<CameraStatus> list = cameraStatusService.queryCameraStatusFilter(Filter);
			
			if(list!=null&&list.size()>0){
				for(CameraStatus ls:list){
					Map<Object, Object> DevStatus = new HashMap<Object, Object>();
					DevStatus.put("SBBH", ls.getSbbh());
					DevStatus.put("SBCSDM", ls.getSbcsdm());
					DevStatus.put("GZZT", ls.getGzzt());
					DevStatus.put("SJPYMS", ls.getSjpyms());
					DevStatus.put("SPZLZD", ls.getSpzlzd());
					DevStatus.put("ZFBL", ls.getZfbl());
					DevStatus.put("FFBL", ls.getFfbl());
					DevStatus.put("ZML", ls.getZml());
					DevStatus.put("FML", ls.getFml());
					Devices.add(DevStatus);
				}
			}
			data.put("DeviceStatus", Devices);
			
			requestPost.setCertification(certification);
			requestPost.setData(data);
			ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICECAMERASTATUS, requestPost);

			boolean result = responsePost.getResult();
			System.out.println("result=" + result);
			if (result) {
				System.out.println("提交成功");
			} else {
				String msg = responsePost.getMessage();
				String code = responsePost.getCode();
				System.out.println("提交出错-Message：" + msg + ";Code:" + code);
			}

			System.out.println("***************CameraStatusTimerServer-结束***************");
		}

	}
	
}
