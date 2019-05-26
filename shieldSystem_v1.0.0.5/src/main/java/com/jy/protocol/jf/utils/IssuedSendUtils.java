package com.jy.protocol.jf.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jy.moudles.device.entity.Device;
import com.jy.moudles.device.service.DeviceService;
import com.jy.moudles.systemConfig.constant.SystemConfigConstants;
import com.jy.moudles.systemConfig.entity.SystemConfig;
import com.jy.moudles.systemConfig.service.SystemConfigService;
import com.jy.protocol.common.constant.DeviceVender;
import com.jy.protocol.common.constant.ParamStatic;
import com.jy.protocol.jf.udp.UdpUtil;

/**
 * 下发工具类
 * */
public class IssuedSendUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(IssuedSendUtils.class);
	private static WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
	
	//侦测UDP端口
	public static final Integer DETECTION_UDP_PORT = 6510;
	//屏蔽UDP端口
	public static final Integer SHIELD_UDP_PORT = 6610;
	//TCP端口
	public static final Integer TCP_PORT = 6600;
	
	//通用模块协议代码
	private static final String BASE_CODE = "03";
	//4G模块协议代码
	private static final String G_CODE = "04";
	
	//设备注册
	@SuppressWarnings("unused")
	private static final String REGISTER_DEVICE = "00";
	
	public static final String SOURCE = "01";
	
	//还原文件保存地址
	//public static final String PATH = "src/main/webapp/static/reductionInfo";
	
	//侦测ip
	public static final String DETECTION_IP = "DETECTION_IP";

	//侦测设备
	public static final String DETECTION_DEVICE = "DETECTION_DEVICE";

	//屏蔽ip
	public static final String SHIELD_IP = "SHIELD_IP";
	//心跳
	public static final String HEART_BREATH = "HEART_BREATH";
	
	//心跳协议命令
	public static final String HEART_CODE = "f2";

	
	/**
	 * 屏蔽设备通用模块开启关闭
	 * @param type 1:开   0:关
	 * */
	public static void shieldControllerBase(int type,String ip,int port) {
		String code = AnalyzeJFOXUtils.getmoduleOpenOperation(BASE_CODE, type);
		UdpUtil.sendUdp(ip, port, TypeConversionUtils.hexStringToBytes(code));
		LOGGER.info("屏蔽通用模块");
	}
	
	/**
	 * 屏蔽设备4G模块开启关闭
	 * @param type 1:开   0:关
	 *
	 * */
	public static void shieldControllerPhone(int type,String ip,int port) {
		String code = AnalyzeJFOXUtils.getmoduleOpenOperation(G_CODE, type);
		UdpUtil.sendUdp(ip, port, TypeConversionUtils.hexStringToBytes(code));
		LOGGER.info("屏蔽4G模块");
	}

	
	/**
	 * 接收侦测设备信息
	 * @return 解析代码错误时 返回null
	 * */
	public static void detectionDeviceWithCode(String code,String ip) {
		if(!StringUtils.isBlank(code)) {
			if(code.length() < 68) {
				LOGGER.warn("协议代码格式有误");
			}else {
				Device device = new Device();
				//设备sn
				String sn = code.substring(20, 36);
				sn = sn.replace("4a46","JY");
				//mac地址
				String mac = code.substring(36, 48);
				mac = MacUtils.coverStringToMac(mac);
				//设备工作状态
				String status = code.substring(48, 50);
				//考试计划执行状态
				String examPlanStatus = code.substring(50, 52);
				
				//版本
				String version_temp = code.substring(60, 76);
				StringBuilder sb = new StringBuilder();
				sb.append(version_temp.substring(1,2)).append(".")
                        .append(version_temp.substring(5,6)).append(".")
                        .append(version_temp.substring(9,10)).append(".")
                        .append(version_temp.substring(13,14));
                System.out.println("版本信息=====" + version_temp);
                device.setVersion(sb.toString());
				/*StringBuffer version = new StringBuffer();
				if(null != version_temp) {
					char[] charArray = version_temp.toCharArray();
					String sb = "";
					for(int i = 0;i < charArray.length;i++) {
						sb += charArray[i];
						if(i != 0 && i % 2 == 1 ) {
							String snum = Integer.valueOf(sb.toString(), 16)+".";
							version.append(snum);
							sb = "";
						}
					}
				}*/
                device.setVender(DeviceVender.JF);
				device.setMac(mac);
				device.setSn(sn);
				device.setType(1);
				device.setIp(ip);
				//device.setVersion(version.toString().substring(0, version.length() - 1));

				//赋值orgid
				SystemConfigService systemConfigService = context.getBean(SystemConfigService.class);
				SystemConfig systemConfig = systemConfigService.getSystemConfigByKey(SystemConfigConstants.ORGANIZATION_ID);
				if(systemConfig != null) {
					device.setOrgId(systemConfig.getSysValue());
				}
				
				if(null != status) {
					if("00".equals(status)) {
						device.setStatus(1);
					}else {
						device.setStatus(2);
					}
				}else {
					device.setStatus(0);
				}
				
				
				switch (examPlanStatus) {
					case "10":
						//无考试计划
						device.setWorkStatus(1);
						break;
					case "12":
						//考中
						device.setWorkStatus(2);
						break;
					case "13":
						//考间
						device.setWorkStatus(3);
						break;
					case "20":
						//空闲
						device.setWorkStatus(4);
						break;
					default:
						device.setWorkStatus(0);
						break;
				}
				
				//获取device对象
				DeviceService deviceService = context.getBean(DeviceService.class);
				try{
					Device deviceTemp = deviceService.getDeviceByMac(mac);
					if(null == deviceTemp) {
						device.setCreateDate(new Date());
						device.setUpdateDate(new Date());
						deviceService.insertDevice(device);
						ParamStatic.issuedStaticMap.put(DETECTION_IP, ip);
						ParamStatic.issuedStaticMap.put(DETECTION_DEVICE, device);
					}else {
						device.setUpdateDate(new Date());
						ParamStatic.issuedStaticMap.put(DETECTION_IP, ip);
						deviceService.updateDeviceByMac(device);
						ParamStatic.issuedStaticMap.put(DETECTION_DEVICE, device);
					}
				}catch (Exception e){

				}
			}
		}else {
			LOGGER.warn("未接收到协议代码");
		}
	}
	
	/**
	 * 接收屏蔽设备信息
	 * @return 解析代码错误时 返回null
	 * */
	public static void shieldDeviceWithCode(String code,String ip) {
		
		DeviceService deviceService = context.getBean(DeviceService.class);
		
		Device device = new Device();
		
		if(!StringUtils.isBlank(code)) {
			if(code.length() < 52) {
				LOGGER.warn("协议代码格式有误");
			}else {
				//设备sn
				String sn = code.substring(20, 36);
				sn = sn.replace("4a46","JY");
				//mac地址
				String mac = code.substring(36, 48);
				mac = MacUtils.coverStringToMac(mac);
				
				
				String opreation = code.substring(48,50);
				String version = code.substring(50,52);
				if(StringUtils.isNotBlank(version) && version.length() >= 2){
					device.setVersion(version.substring(0,1) + "." + version.substring(1,version.length()));
				}

                device.setVender(DeviceVender.JF);
				device.setMac(mac);
				device.setSn(sn);
				device.setType(2);
				device.setStatus(1);
				device.setIp(ip);

				//赋值orgid
				SystemConfigService systemConfigService = context.getBean(SystemConfigService.class);
	            SystemConfig systemConfig = systemConfigService.getSystemConfigByKey(SystemConfigConstants.ORGANIZATION_ID);
	            if(systemConfig != null) {
	            	device.setOrgId(systemConfig.getSysValue());
	            }
				
				switch (opreation) {
					case "00":
						device.setOperationType("1");
						break;
					case "01":
						device.setOperationType("5");
						break;
					case "02":
						device.setOperationType("3");
						break;
					case "03":
						device.setOperationType("4");
						break;
				}
				
				Device devices = deviceService.getDeviceByMac(mac);
				
				//获取device对象
				if(null == devices) {
					device.setCreateDate(new Date());
					device.setUpdateDate(new Date());
					device.setControlled(1);
					deviceService.insertDevice(device);
					ParamStatic.issuedStaticMap.put(SHIELD_IP, ip);
				}else {
					device.setUpdateDate(new Date());
					deviceService.updateDeviceByMac(device);
					ParamStatic.issuedStaticMap.put(SHIELD_IP, ip);
				}
				
			}
		}else {
			LOGGER.warn("未接收到协议代码");
		}
		
		
	}

	
}