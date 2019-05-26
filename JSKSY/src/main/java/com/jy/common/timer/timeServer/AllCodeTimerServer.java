package com.jy.common.timer.timeServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.jy.common.timer.constant.JSKSYTestUrl;
import com.jy.common.utils.UUIDUtil;
import com.jy.common.utils.https.HttpUtils;
import com.jy.common.utils.https.RequestPost;
import com.jy.common.utils.https.ResponsePost;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;
import com.jy.moudles.systemCurrency.entity.SystemCurrency;
import com.jy.moudles.systemCurrency.service.SystemCurrencyService;

public class AllCodeTimerServer {

public ApplicationContext context;
	
	public long dateOffset;
	
	public AllCodeTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 60 * 6;
		//this.dateOffset = 1000 * 20;
	}
	
	public void start(){
        Timer timer = new Timer("getAllCode");
        timer.schedule(new getAllCodes(), 1000 * 1, dateOffset);
    }
	
	class getAllCodes extends TimerTask{

			@Override
			public void run() {
				SystemCurrencyService systemCurrencyService = context.getBean(SystemCurrencyService.class);
				
				systemCurrencyService.deleteSystemCurrencyById("");
				
				RequestPost requestPost = new RequestPost();
				Map<Object,Object> certification = new HashMap<>();
				certification.put("AccessToken", AccessTokenUtil.getAccessToken());
				requestPost.setCertification(certification);
				
				ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL+JSKSYTestUrl.GETALLCODE, requestPost);
				String data = responsePost.getData();
				if(StringUtils.isNotBlank(data)) {
					SystemCurrency systemCurrency = JSON.parseObject(data, SystemCurrency.class);
					systemCurrency.setId(UUIDUtil.get32UUID());
					systemCurrencyService.insertSystemCurrency(systemCurrency);
				}else {
					System.out.println("未获取到全部代码信息");
				}
				
			}
	    	
    }
}
