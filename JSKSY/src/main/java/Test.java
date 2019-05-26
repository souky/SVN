import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jy.common.timer.constant.JSKSYTestUrl;
import com.jy.common.utils.https.HttpUtils;
import com.jy.common.utils.https.RequestPost;
import com.jy.common.utils.https.ResponsePost;

public class Test {

	public static void main(String[] args) {
		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", "89E9D0DC512B11E8B0DA000C29FEF75A|d94b732d8b726f92a70d761d0bde0aa2");
		Map<Object, Object> data = new HashMap<>();
		data.put("ExamPlan", "20181009"); // 考试计划编号
		data.put("ExamSession", "1"); // 考试场次序号
		List<Map<Object, Object>> OSDStatus = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> sta = new HashMap<Object, Object>();
		sta.put("SBYM", "tzzxxxq.tzgq.jsjy.cnjy"); // 网上巡查设备域名
		sta.put("OSD", "测试osd"); // OSD设置成功后，OSD的真实内容
		sta.put("SZZT", "1");
		sta.put("SZXX", "设置成功");
		OSDStatus.add(sta);
		data.put("OSDStatus", OSDStatus);
		requestPost.setCertification(certification);
		requestPost.setData(data);
		ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEOSDSETTINGSSTATUS, requestPost);
		System.out.println(responsePost.getResult());

	}



	/*public static void main(String[] args) {
		
		RequestPost re = new RequestPost();
		Map<Object,Object> certification = new HashMap<>();
		certification.put("AppId", "DA4C96D716F5878FCF643AE1D6EDC5B0");
		certification.put("AppKey", "3EF633E54261BE2D353BAB837071C01C");
		re.setCertification(certification);
		ResponsePost s = HttpUtils.sendPost(JSKSYTestUrl.URL+JSKSYTestUrl.GETTOKEN, re);
		
		System.out.println(s.getData());
		
		//setNetviewDeviceinfo();
		
		//setNetviewDeviceSipStatus();
		
		//setNetviewDeviceNvrStatus();
		
		//setNetviewDeviceCameraStatus();
		
		setNetviewDeviceVideoplanCoverStatus();
		
		//setNetviewDeviceVideosaveCoverStatus();
		
		//getMyMAC();
	}*/
	
	// 6.3.5.8提交考场及保密室考后录像存储是否完整覆盖考试计划的状态
	public static void setNetviewDeviceVideosaveCoverStatus() {
		System.out.println("***************开始提交考场及保密室考后录像存储是否完整覆盖考试计划的状态***************");
		String OrgCode = "86.32";
		String OrgIdenCode = "ABCD1234";
		
		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", "24E816D4501411E8908C000C29FEF75A|3ec8341b583cfa4e8c7834e36d1bf3f3");
		Map<Object, Object> data = new HashMap<>();
		data.put("OrgCode", OrgCode);
		data.put("OrgIdenCode", OrgIdenCode);
		
		data.put("ExamPlan", "20181009");
		data.put("ExamSession", "001");

		List<Map<Object, Object>> Device = new ArrayList<Map<Object, Object>>();
		
		Map<Object, Object> Dev1 = new HashMap<Object, Object>();
		Dev1.put("CSBH", "1243");
		Dev1.put("FGQK", 1); // 覆盖情况1：已完全覆盖 -2：未完全覆盖
		Device.add(Dev1);
		
		Map<Object, Object> Dev2 = new HashMap<Object, Object>();
		Dev2.put("CSBH", "1244");
		Dev2.put("FGQK", 1); // 覆盖情况1：已完全覆盖 -2：未完全覆盖
		Device.add(Dev2);
		
		data.put("Device", Device);
		requestPost.setCertification(certification);
		requestPost.setData(data);
		ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEVIDEOSAVECOVERSTATUS, requestPost);

		boolean result = responsePost.getResult();
		System.out.println("result=" + result);
		if (result) {
			System.out.println("提交考场及保密室考后录像存储是否完整覆盖考试计划的状态");
		} else {
			String msg = responsePost.getMessage();
			String code = responsePost.getCode();
			System.out.println("提交考场及保密室考后录像存储是否完整覆盖考试计划的状态-Message：" + msg + ";Code:" + code);
		}

		System.out.println("***************结束提交考场及保密室考后录像存储是否完整覆盖考试计划的状态***************");
	
	}
	
	// 6.3.5.7提交考场及保密室考前录像计划是否完整覆盖考试计划的状态
	public static void setNetviewDeviceVideoplanCoverStatus() {
		System.out.println("***************开始提交考场及保密室考前录像计划是否完整覆盖考试计划的状态***************");
		String OrgCode = "86.32";
		String OrgIdenCode = "ABCD1234";
		
		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", "24E816D4501411E8908C000C29FEF75A|3ec8341b583cfa4e8c7834e36d1bf3f3");
		Map<Object, Object> data = new HashMap<>();
		data.put("OrgCode", OrgCode);
		data.put("OrgIdenCode", OrgIdenCode);
		
		data.put("ExamPlan", "20181009");
		data.put("ExamSession", "001");

		List<Map<Object, Object>> Device = new ArrayList<Map<Object, Object>>();
		
		Map<Object, Object> Dev1 = new HashMap<Object, Object>();
		Dev1.put("CSBH", "1243");
		Dev1.put("FGQK", 1); // 覆盖情况1：已完全覆盖 -1:未设置录像计划 -2：未完全覆盖
		Device.add(Dev1);
		
		Map<Object, Object> Dev2 = new HashMap<Object, Object>();
		Dev2.put("CSBH", "1244");
		Dev2.put("FGQK", 1); // 覆盖情况1：已完全覆盖 -1:未设置录像计划 -2：未完全覆盖
		Device.add(Dev2);
		
		data.put("Device", Device);
		requestPost.setCertification(certification);
		requestPost.setData(data);
		ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEVIDEOPLANCOVERSTATUS, requestPost);

		boolean result = responsePost.getResult();
		System.out.println("result=" + result);
		if (result) {
			System.out.println("提交考场及保密室考前录像计划是否完整覆盖考试计划的状态");
		} else {
			String msg = responsePost.getMessage();
			String code = responsePost.getCode();
			System.out.println("提交考场及保密室考前录像计划是否完整覆盖考试计划的状态-Message：" + msg + ";Code:" + code);
		}

		System.out.println("***************结束提交考场及保密室考前录像计划是否完整覆盖考试计划的状态***************");
	
	}
		
	// 6.3.5.6提交网上巡查摄像机的工作状态
	public static void setNetviewDeviceCameraStatus() {
		System.out.println("***************开始提交网上巡查摄像机的工作状态***************");
		String OrgCode = "86.32";
		String OrgIdenCode = "ABCD1234";
		
		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", "492BB5A64F7311E8A5C0000C29FEF75A|3ec8341b583cfa4e8c7834e36d1bf3f3");
		Map<Object, Object> data = new HashMap<>();
		data.put("OrgCode", OrgCode);
		data.put("OrgIdenCode", OrgIdenCode);

		List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> DevStatus = new HashMap<Object, Object>();
		DevStatus.put("SBBH", "CCFF0123456");
		DevStatus.put("SBCSDM", "195");
		DevStatus.put("GZZT", "1");
		DevStatus.put("SJPYMS", "0");
		DevStatus.put("SPZLZD", "1");
		DevStatus.put("ZFBL", "1920*1080P");
		DevStatus.put("FFBL", "704*480");
		DevStatus.put("ZML", "4096");
		DevStatus.put("FML", "1024");
		Devices.add(DevStatus);
		data.put("DeviceStatus", Devices);
		requestPost.setCertification(certification);
		requestPost.setData(data);
		ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICECAMERASTATUS, requestPost);

		boolean result = responsePost.getResult();
		System.out.println("result=" + result);
		if (result) {
			System.out.println("提交网上巡查摄像机的工作状态");
		} else {
			String msg = responsePost.getMessage();
			String code = responsePost.getCode();
			System.out.println("提交网上巡查摄像机的工作状态-Message：" + msg + ";Code:" + code);
		}

		System.out.println("***************结束提交网上巡查摄像机的工作状态***************");
	
	}
	
	// 6.3.5.4提交网上巡查NVR存储服务器工作状态
	public static void setNetviewDeviceNvrStatus() {
		System.out.println("***************开始提交NVR存储服务器工作状态***************");
		String OrgCode = "86.32";
		String OrgIdenCode = "ABCD1234";
		
		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", "19D7BC864F6A11E88135000C29FEF75A|3ec8341b583cfa4e8c7834e36d1bf3f3");
		Map<Object, Object> data = new HashMap<>();
		data.put("OrgCode", OrgCode);
		data.put("OrgIdenCode", OrgIdenCode);

		List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> DevStatus = new HashMap<Object, Object>();
		DevStatus.put("SBBH", "CCFF0123456");
		DevStatus.put("SBCSDM", "195");
		DevStatus.put("GZZT", 1);
		DevStatus.put("CCKGZT", 1);
		DevStatus.put("CPYJ", 1);
		DevStatus.put("SJPYMS", 0);
		DevStatus.put("KJZDX", 4096);
		DevStatus.put("KXKJDX", 2048);
		Devices.add(DevStatus);
		data.put("DeviceStatus", Devices);
		requestPost.setCertification(certification);
		requestPost.setData(data);
		ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICENVRSTATUS, requestPost);

		boolean result = responsePost.getResult();
		System.out.println("result=" + result);
		if (result) {
			System.out.println("提交NVR存储服务器工作状态成功");
		} else {
			String msg = responsePost.getMessage();
			String code = responsePost.getCode();
			System.out.println("提交NVR存储服务器工作状态出错-Message：" + msg + ";Code:" + code);
		}

		System.out.println("***************结束提交NVR存储服务器工作状态***************");
	
	}
		
	// 6.3.5.3提交网上巡查SIP网关服务器工作状态
	public static void setNetviewDeviceSipStatus() {
		System.out.println("***************开始提交SIP网关服务器工作状态***************");
		String OrgCode = "86.32";
		String OrgIdenCode = "ABCD1234";
		
		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", "19D7BC864F6A11E88135000C29FEF75A|3ec8341b583cfa4e8c7834e36d1bf3f3");
		Map<Object, Object> data = new HashMap<>();
		data.put("OrgCode", OrgCode);
		data.put("OrgIdenCode", OrgIdenCode);

		List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> DevStatus = new HashMap<Object, Object>();
		DevStatus.put("SBBH", "CCFF0123456");
		DevStatus.put("SBCSDM", "195");
		DevStatus.put("GZZT", 1);
		DevStatus.put("SIPZCZT", 1);
		DevStatus.put("SJPYMS", 0);
		DevStatus.put("SJLLS", 0);
		DevStatus.put("BJLLS", 0);
		DevStatus.put("SJLL", 0);
		DevStatus.put("BJLL", 0);
		DevStatus.put("WLLLUP", 0);
		DevStatus.put("WLLLDOWN", 0);
		Devices.add(DevStatus);
		data.put("DeviceStatus", Devices);
		requestPost.setCertification(certification);
		requestPost.setData(data);
		ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICESIPSTATUS, requestPost);

		boolean result = responsePost.getResult();
		System.out.println("result=" + result);
		if (result) {
			System.out.println("提交SIP网关服务器工作状态成功");
		} else {
			String msg = responsePost.getMessage();
			String code = responsePost.getCode();
			System.out.println("提交SIP网关服务器工作状态出错-Message：" + msg + ";Code:" + code);
		}

		System.out.println("***************结束提交SIP网关服务器工作状态***************");
	
	}
	
	// 6.3.5.2提交网上巡查设备基本信息
	public static void setNetviewDeviceinfo() {

		System.out.println("***************开始提交设备信息***************");
		String OrgCode = "86.32";
		String OrgIdenCode = "ABCD1234";
		
		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", "19D7BC864F6A11E88135000C29FEF75A|3ec8341b583cfa4e8c7834e36d1bf3f3");
		Map<Object, Object> data = new HashMap<>();
		data.put("OrgCode", OrgCode);
		data.put("OrgIdenCode", OrgIdenCode);

		List<Map<Object, Object>> Devices = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> map1 = new HashMap<Object, Object>();
		map1.put("SBBH", "GLFWQ0123456");
		map1.put("SBMC", "管理服务器1");
		map1.put("SBLXM", "201");
		map1.put("SBXH", "sbxh-1");
		map1.put("SIPDZ", "szjy.jsjy.cnjy");
		map1.put("FJSIPDZ", "jsjy.cnjy");
		map1.put("OSDCD", "32");
		map1.put("SBTDXH", "0");
		map1.put("SBMAC", getMyMAC());
		map1.put("SBIP", "192.168.100.101");
		map1.put("SBDK", "22");
		map1.put("SBYHM", "root");
		map1.put("SBMM", "password");
		map1.put("SBCSDM", "195");
		map1.put("SBRJBB", "");
		map1.put("SBYJBB", "");
		map1.put("SBGJBB", "");
		Devices.add(map1);
		data.put("Devices", Devices);
		requestPost.setCertification(certification);
		requestPost.setData(data);
		ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEINFO, requestPost);

		boolean result = responsePost.getResult();
		System.out.println("result=" + result);
		if (result) {
			System.out.println("提交设备信息成功");
		} else {
			String msg = responsePost.getMessage();
			String code = responsePost.getCode();
			System.out.println("提交出错-Message：" + msg + ";Code:" + code);
		}

		System.out.println("***************结束提交设备信息***************");
	
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
			System.out.println(sb);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return new String(sb);
	}
}
