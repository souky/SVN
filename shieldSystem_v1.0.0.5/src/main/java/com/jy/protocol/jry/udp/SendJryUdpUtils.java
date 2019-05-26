package com.jy.protocol.jry.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.device.entity.Device;
import com.jy.moudles.device.service.DeviceService;

/**
 * Create by wb TIME: 2018/3/5 9:12
 **/
public class SendJryUdpUtils {

	private static WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();

	/**
	 * 发送udp协议
	 *
	 * @param ip
	 *            ip
	 * @param port
	 *            端口
	 * @param sendMessage
	 *            发送的信息
	 */
	public static void sendUdp(String ip, int port, String sendMessage) {
		DatagramSocket datagramSocket = null;
		try {
			datagramSocket = new DatagramSocket();
			datagramSocket.setSoTimeout(500);
			byte[] buf = sendMessage.getBytes();
			InetAddress address = InetAddress.getByName(ip);
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, port);
			// 发送数据
			datagramSocket.send(datagramPacket);
			// 接收数据
			byte[] receBuf = new byte[1024];
			DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
			while(true) {
				datagramSocket.receive(recePacket);

				String receStr = new String(recePacket.getData(), 0, recePacket.getLength());
				String receIP = recePacket.getAddress().getHostAddress();
				handleResMessage(receStr, receIP);
				// 接收数据
			}
		} catch (IOException e) {
			
		} finally {
			// 关闭socket
			if (datagramSocket != null) {
				datagramSocket.close();
			}
		}
	}
	
	/**
	 * 发送udp协议,不接收回话
	 *
	 * @param ip
	 *            ip
	 * @param port
	 *            端口
	 * @param sendMessage
	 *            发送的信息
	 */
	public static void sendUdpWithNoRe(String ip, int port, String sendMessage) {
		DatagramSocket datagramSocket = null;
		try {
			datagramSocket = new DatagramSocket();
			datagramSocket.setSoTimeout(500);
			byte[] buf = sendMessage.getBytes();
			InetAddress address = InetAddress.getByName(ip);
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, port);
			// 发送数据
			datagramSocket.send(datagramPacket);
		} catch (IOException e) {
			
		} finally {
			// 关闭socket
			if (datagramSocket != null) {
				datagramSocket.close();
			}
		}
	}

	public static void sendUdp4Query(String ip, int port, String sendMessage) {
		DatagramSocket datagramSocket = null;
		//判断成功失败
		boolean flag = false;
		try {
			datagramSocket = new DatagramSocket();
			datagramSocket.setSoTimeout(500);
			byte[] buf = sendMessage.getBytes();
			InetAddress address = InetAddress.getByName(ip);
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, port);
			// 发送数据
			datagramSocket.send(datagramPacket);

			byte[] receBuf = new byte[1024];
			DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
			
			while(true) {
				datagramSocket.receive(recePacket);

				String receStr = new String(recePacket.getData(), 0, recePacket.getLength());
				String receIP = recePacket.getAddress().getHostAddress();
				flag = handleResMessage(receStr, receIP);
				// 接收数据
				
			}
		} catch (IOException e) {
			if(!flag) {
				// 设备离线
				DeviceService deviceService = context.getBean(DeviceService.class);
				deviceService.updateDeviceByIp(ip);
			}
		} finally {
			// 关闭socket
			if (datagramSocket != null) {
				datagramSocket.close();
			}
		}
	}

	private static boolean handleResMessage(String msg, String ip) {
		boolean returnFlag = false;
		if (StringUtils.isNotBlank(msg)) {
			DeviceService deviceService = context.getBean(DeviceService.class);
			if (msg.startsWith("EQVER")) {// 查询设备信息
				// EQVER:SS_JS_100;FWVER:FW_JS_100;SN:JY-ZDSB-20180207-001;IP:192.168.51.100;MA
				// C:00C002120002;T:35;XXXXXXXXXXXXXXXXXXXXXXXXX;
				returnFlag = true;
				System.out.println("处理udp返回信息---查询设备信息命令");
				String[] arr = msg.split(";");
				String mac = arr[4].substring(4).toUpperCase();
				String poweroff = arr[6].substring(0, 1);
				String module = arr[6].substring(1, 13);
				// 根据mac查看数据库中是否已经存在该设备 9C431EC0072D
				Device device = deviceService.getDeviceByMac(mac);
				if("0".equals(module)) {
					module = "000000000000";
				}
				if (device == null) {
					device = new Device();
					device.setId(UUIDUtil.get32UUID());
					device.setMac(mac);
					device.setIp(ip);
					device.setSn(arr[2].substring(3));
					device.setType(2);
					device.setVersion(arr[0].substring(6));
					device.setVender("JY");
					device.setCreateDate(new Date());
					device.setUpdateDate(new Date());
					device.setCreateUser("1");
					device.setStatus(1);
					device.setOrgId("0fae7da3605b4bd687f1f97be25e289e");
					if ("0".equals(poweroff)) {
						device.setPoweroff(2);
						device.setOperationType("000000000000");
					} else if ("1".equals(poweroff)) {
						device.setPoweroff(1);
						device.setOperationType(module);
					}
					deviceService.insertDevice(device);
					
				} else {
					if ("0".equals(poweroff)) {
						device.setPoweroff(2);
						device.setOperationType("000000000000");
					} else if ("1".equals(poweroff)) {
						device.setPoweroff(1);
						device.setOperationType(module);
					}
					device.setIp(ip);
					device.setUpdateDate(new Date());
					device.setStatus(1);
					deviceService.updateDevice(device);
				}
			}

			Map<String, Object> filter = new HashMap<>();
			filter.put("ip", ip.substring(1));
			if (msg.startsWith("OK") && msg.length() < 10) {// 发送开关
				System.out.println("处理udp返回信息---设备电源开关命令");
				List<Device> devices = deviceService.queryDevicesFilter(filter);
				if (devices != null && devices.size() == 1 && devices.get(0) != null) {
					Device device = devices.get(0);
					String poweroff = msg.substring(3, 4);
					if ("0".equals(poweroff)) {
						device.setPoweroff(2);
						device.setOperationType("0");
					} else if ("1".equals(poweroff)) {
						device.setPoweroff(1);
						device.setOperationType("6");
					}
					device.setUpdateDate(new Date());
					System.out.println("ceshi---" + new Date());
					deviceService.updateDevice(device);
					System.out.println("ceshi---" + new Date());
				}
			}

			if (msg.startsWith("OK") && msg.length() > 10) {// 发送控制模块
				System.out.println("处理udp返回信息---设备控制模块命令");
				List<Device> devices = deviceService.queryDevicesFilter(filter);
				if (devices != null && devices.size() == 1 && devices.get(0) != null) {
					Device device = devices.get(0);
					String modules = msg.substring(3, 15);
					device.setOperationType(modules);
					deviceService.updateDevice(device);
				}
			}

			if (msg.startsWith("OK:RUDPPORT")) {// 发送修改端口
				System.out.println("处理udp返回信息---设备修改端口命令");
			}
		}
		return returnFlag;
	}

	// 处理查询状态返回的字符串
	@SuppressWarnings("unused")
	private static String handleResponseStr(String src) {
		List<Integer> list = new ArrayList<>();
		if (StringUtils.isNotBlank(src) && src.length() == 12) {
			if ("111111".equals(src.substring(0, 6))) {
				list.add(1);
			}
			if ("11".equals(src.substring(5, 7))) {
				list.add(2);
			}
			if ("111".equals(src.substring(5, 8))) {
				list.add(3);
			}
			if ("1111111".equals(src.substring(4, 11))) {
				list.add(4);
			}
			if ("1".equals(src.substring(9, 10)) && "1".equals(src.substring(11, 12))) {
				list.add(5);
			}
			if ("111111111111".equals(src)) {
				list.clear();
				list.add(6);
			}
		} else {
			list.add(0);
		}
		if (list.isEmpty()) {
			list.add(0);
		}
		return StringUtils.join(list, ";");
	}
}
