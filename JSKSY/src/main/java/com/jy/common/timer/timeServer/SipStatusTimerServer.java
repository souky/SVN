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
import com.jy.moudles.sipStatus.entity.SipStatus;
import com.jy.moudles.sipStatus.service.SipStatusService;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

/**
 * 提交网上巡查SIP网关服务器工作状态
 * @author ccjy
 *
 */
public class SipStatusTimerServer {

	public ApplicationContext context;

	public long dateOffset;

	public SipStatusTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10; // 10分钟
	}

	public void start() {
		Timer timer = new Timer("setSipStatus");
		timer.schedule(new setSipStatus(), 1000 * 5, dateOffset);
	}

	class setSipStatus extends TimerTask {

		@Override
		public void run() {
			System.out.println("***************SipStatusTimerServer-开始***************");
			SipStatusService sipStatusService = context.getBean(SipStatusService.class);
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
			List<SipStatus> list = sipStatusService.querySipStatusFilter(Filter);
			
			List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
			if(list!=null&&list.size()>0){
				for(SipStatus ls:list){
					Map<Object, Object> DevStatus = new HashMap<Object, Object>();
					DevStatus.put("SBBH", ls.getSbbh());
					DevStatus.put("SBCSDM", ls.getSbcsdm());
					DevStatus.put("GZZT", ls.getGzzt());
					DevStatus.put("SIPZCZT", ls.getSipzczt());
					DevStatus.put("SJPYMS", ls.getSjpyms());
					DevStatus.put("SJLLS", ls.getSjlls());
					DevStatus.put("BJLLS", ls.getBjlls());
					DevStatus.put("SJLL", ls.getSjll());
					DevStatus.put("BJLL", ls.getBjll());
					DevStatus.put("WLLLUP", ls.getWlllup());
					DevStatus.put("WLLLDOWN", ls.getWllldown());
					Devices.add(DevStatus);
				}
			}
			data.put("DeviceStatus", Devices);
			
			requestPost.setCertification(certification);
			requestPost.setData(data);
			ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICESIPSTATUS, requestPost);

			boolean result = responsePost.getResult();
			System.out.println("result=" + result);
			if (result) {
				System.out.println("提交成功");
			} else {
				String msg = responsePost.getMessage();
				String code = responsePost.getCode();
				System.out.println("提交出错-Message：" + msg + ";Code:" + code);
			}

			System.out.println("***************SipStatusTimerServer-结束***************");
		}

	}
	
}
