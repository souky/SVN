package com.jy.moudles.deviceInfo.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jy.common.timer.constant.JSKSYTestUrl;
import com.jy.common.utils.https.HttpUtils;
import com.jy.common.utils.https.RequestPost;
import com.jy.common.utils.https.ResponsePost;
import com.jy.moudles.deviceInfo.entity.DeviceInfo;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

public class DeviceUtil {
	
	public static ResponsePost setCheatDeviceInfo(DeviceInfo deviceInfo) {
		
		ResponsePost responsePost = new ResponsePost();
		if(null == deviceInfo) {
			responsePost.setMessage("参数错误");
			responsePost.setResult(false);
			return responsePost;
		}
		String OrgCode = AccessTokenUtil.getOrgCode();
		String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();

		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", AccessTokenUtil.getAccessToken());
		Map<Object, Object> data = new HashMap<>();
		data.put("OrgCode", OrgCode);
		data.put("OrgIdenCode", OrgIdenCode);

		List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> dev = new HashMap<Object, Object>();
		dev.put("SBBH", deviceInfo.getSbbh());
		dev.put("SBMC", deviceInfo.getSbmac());
		dev.put("SBLXM", deviceInfo.getSblxm());
		dev.put("SBXH", deviceInfo.getSbxh());
		dev.put("SBCSDM", deviceInfo.getSbcsdm());
		dev.put("SBRJBB", deviceInfo.getSbrjbb());
		dev.put("SBYJBB", deviceInfo.getSbyjbb());
		dev.put("SBGJBB", deviceInfo.getSbgjbb());
		Devices.add(dev);
		data.put("Devices", Devices);
		requestPost.setCertification(certification);
		requestPost.setData(data);
		responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETCHEATDEVICEINFO, requestPost);

		return responsePost;
	}
}
