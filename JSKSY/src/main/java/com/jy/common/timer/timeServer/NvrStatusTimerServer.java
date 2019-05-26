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
import com.jy.moudles.nvrStatus.entity.NvrStatus;
import com.jy.moudles.nvrStatus.service.NvrStatusService;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

/**
 * 提交网上巡查NVR存储服务器工作状态
 * @author ccjy
 *
 */
public class NvrStatusTimerServer {

	public ApplicationContext context;

	public long dateOffset;

	public NvrStatusTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10; // 10分钟
	}

	public void start() {
		Timer timer = new Timer("setNvrStatus");
		timer.schedule(new setNvrStatus(), 1000 * 5, dateOffset);
	}

	class setNvrStatus extends TimerTask {

		@Override
		public void run() {
			System.out.println("***************NvrStatusTimerServer-开始***************");
			NvrStatusService nvrStatusService = context.getBean(NvrStatusService.class);
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
			List<NvrStatus> list = nvrStatusService.queryNvrStatusFilter(Filter);
			
			List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
			if(list!=null&&list.size()>0){
				for(NvrStatus ls:list){
					Map<Object, Object> DevStatus = new HashMap<Object, Object>();
					DevStatus.put("SBBH", ls.getSbbh());
					DevStatus.put("SBCSDM", ls.getSbcsdm());
					DevStatus.put("GZZT", ls.getGzzt());
					DevStatus.put("CCKGZT", ls.getCckgzt());
					DevStatus.put("CPYJ", ls.getCpyj());
					DevStatus.put("SJPYMS", ls.getSjpyms());
					DevStatus.put("KJZDX", ls.getKjzdx());
					DevStatus.put("KXKJDX", ls.getKxkjdx());
					Devices.add(DevStatus);
				}
			}
			data.put("DeviceStatus", Devices);
			
			requestPost.setCertification(certification);
			requestPost.setData(data);
			ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICENVRSTATUS, requestPost);

			boolean result = responsePost.getResult();
			System.out.println("result=" + result);
			if (result) {
				System.out.println("提交成功");
			} else {
				String msg = responsePost.getMessage();
				String code = responsePost.getCode();
				System.out.println("提交出错-Message：" + msg + ";Code:" + code);
			}

			System.out.println("***************NvrStatusTimerServer-结束***************");
		}

	}
	
}
