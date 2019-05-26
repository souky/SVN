package com.jy.common.timer.timeServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.jy.common.timer.constant.JSKSYTestUrl;
import com.jy.common.utils.https.HttpUtils;
import com.jy.common.utils.https.RequestPost;
import com.jy.common.utils.https.ResponsePost;
import com.jy.common.utils.https.entity.AccessTokenPost;
import com.jy.moudles.systemConfig.entity.SystemConfig;
import com.jy.moudles.systemConfig.service.SystemConfigService;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;




public class AccessTokenTimerServer {

	public ApplicationContext context;
	
	public long dateOffset;
	
	public AccessTokenTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 200;
	}
	
	public void start(){
        Timer timer = new Timer("accessToken");
        timer.schedule(new getAccessTokens(), 10 * 1, dateOffset);
    }
	
	class getAccessTokens extends TimerTask{

			@Override
			public void run() {
				SystemConfigService systemConfigService = context.getBean(SystemConfigService.class);
				String appId = AccessTokenUtil.getAppId();
				String appKey = AccessTokenUtil.getAppKey();
				
				RequestPost requestPost = new RequestPost();
				Map<Object,Object> certification = new HashMap<>();
				certification.put("AppId", appId);
				certification.put("AppKey", appKey);
				requestPost.setCertification(certification);
				ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL+JSKSYTestUrl.GETTOKEN, requestPost);
				
				String data = responsePost.getData();
				if(StringUtils.isNotBlank(data)) {
					AccessTokenPost atp = new AccessTokenPost();
					atp = JSON.parseObject(data, atp.getClass());
					dateOffset = atp.getDateOffset();
					SystemConfig SystemConfig = new SystemConfig();
					SystemConfig.setKeys("accessToken");
					SystemConfig.setValues(atp.getAccessToken());
					systemConfigService.updateSystemConfigByKey(SystemConfig);
				}else {
					dateOffset = 1000 * 20;
				}
				
				
			}
	    	
    }
}
