package com.jy.common.timer.timeServer;

import java.util.HashMap;
import java.util.List;
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
import com.jy.moudles.sipAccountInfo.entity.SipAccountInfo;
import com.jy.moudles.sipAccountInfo.service.SipAccountInfoService;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

public class SipAccountInfoTimerServer {

	public ApplicationContext context;
	public long dateOffset;
	
	public SipAccountInfoTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10;
		//this.dateOffset = 1000 * 20;
	}
	
	public void start(){
        Timer timer = new Timer("getAllSipAccount");
        timer.schedule(new getAllSipAccount(), 1000 * 5, dateOffset);
    }
	
	class getAllSipAccount extends TimerTask{

			@Override
			public void run() {
				
				SipAccountInfoService SipAccountInfoService = context.getBean(SipAccountInfoService.class);
				
				String OrgCode = AccessTokenUtil.getOrgCode();
				String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();
				
				RequestPost requestPost = new RequestPost();
				Map<Object,Object> certification = new HashMap<>();
				certification.put("AccessToken", AccessTokenUtil.getAccessToken());
				Map<Object,Object> data = new HashMap<>();
				data.put("OrgCode", OrgCode);
				data.put("OrgIdenCode", OrgIdenCode);
				requestPost.setCertification(certification);
				requestPost.setData(data);
				ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL+JSKSYTestUrl.GETALLSIPACCOUNTINFO, requestPost);
				
				String datas = responsePost.getData();
				if(StringUtils.isNotBlank(datas)) {
					List<SipAccountInfo> sipAccountInfos = JSON.parseArray(datas, SipAccountInfo.class);
					if(null != sipAccountInfos && sipAccountInfos.size() > 0) {
						SipAccountInfoService.deleteSipAccountInfos();
						SipAccountInfoService.insertSipAccountInfos(sipAccountInfos);
					}
				}	
				
			}
	    	
    }
}
