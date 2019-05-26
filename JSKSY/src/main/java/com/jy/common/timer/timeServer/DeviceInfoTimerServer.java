package com.jy.common.timer.timeServer;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

/**
 * 提交网上巡查设备基本信息
 * @author ccjy
 *
 */
public class DeviceInfoTimerServer {

	public ApplicationContext context;

	public long dateOffset;

	public DeviceInfoTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10; // 10分钟
	}

	public void start() {
		Timer timer = new Timer("setDevInfo");
		timer.schedule(new setDevInfo(), 1000 * 5, dateOffset);
	}

	class setDevInfo extends TimerTask {

		@Override
		public void run() {
			System.out.println("***************DeviceInfoTimerServer-开始***************");
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
			List<DeviceInfo> list = deviceInfoService.queryDeviceInfoFilter(Filter);
			
			List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
			if(list!=null&&list.size()>0){
				for(DeviceInfo ls:list){
					Map<Object, Object> dev = new HashMap<Object, Object>();
					dev.put("SBBH", ls.getSbbh());
					dev.put("SBMC", ls.getSbmac());
					dev.put("SBLXM", ls.getSblxm());
					dev.put("SBXH", ls.getSbxh());
					dev.put("SIPDZ", ls.getSipdz());
					dev.put("FJSIPDZ", ls.getFjsipdz());
					dev.put("OSDCD", ls.getOsdcd());
					dev.put("SBTDXH", ls.getSbtdxh());
					dev.put("SBMAC", ls.getSbmac());
					dev.put("SBIP", ls.getSbip());
					dev.put("SBDK", ls.getSbdk());
					dev.put("SBYHM", ls.getSbyhm());
					dev.put("SBMM", ls.getSbmm());
					dev.put("SBCSDM", ls.getSbcsdm());
					dev.put("SBRJBB", ls.getSbrjbb());
					dev.put("SBYJBB", ls.getSbyjbb());
					dev.put("SBGJBB", ls.getSbgjbb());
					Devices.add(dev);
				}
			}
			data.put("Devices", Devices);
			requestPost.setCertification(certification);
			requestPost.setData(data);
			ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEINFO, requestPost);

			boolean result = responsePost.getResult();
			System.out.println("result=" + result);
			if (result) {
				System.out.println("提交成功");
			} else {
				String msg = responsePost.getMessage();
				String code = responsePost.getCode();
				System.out.println("提交出错-Message：" + msg + ";Code:" + code);
			}

			System.out.println("***************DeviceInfoTimerServer-结束***************");
		}

	}

	/**
	 * 获取本机器MAC地址
	 * @return
	 */
	public static String getMyMAC() {
		InetAddress ip;
		StringBuilder sb = new StringBuilder();
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return new String(sb);
	}
}
