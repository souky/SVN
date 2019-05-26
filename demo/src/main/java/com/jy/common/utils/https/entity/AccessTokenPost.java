package com.jy.common.utils.https.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccessTokenPost {
	
	private String AccessToken;
	
	private String ExpireTime;

	public String getAccessToken() {
		return AccessToken;
	}

	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}

	public String getExpireTime() {
		return ExpireTime;
	}

	public void setExpireTime(String expireTime) {
		ExpireTime = expireTime;
	}
	
	public long getDateOffset() {
		long a = 20000l;
		//2018-05-03 19:38:54
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(ExpireTime);
			Date dateNow = new Date();
			a = date.getTime() - dateNow.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return a;
	}
	
}
