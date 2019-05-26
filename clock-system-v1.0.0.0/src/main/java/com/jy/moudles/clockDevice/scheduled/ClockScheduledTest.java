//package com.jy.moudles.clockDevice.scheduled;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.SocketTimeoutException;
//import java.net.UnknownHostException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.UUID;
//import java.util.Map.Entry;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//import com.jy.common.utils.TypeConversionUtils;
//import com.jy.common.utils.UUIDUtil;
//import com.jy.moudles.clockDevice.entity.ClockDevice;
//import com.jy.moudles.clockDevice.service.ClockDeviceService;
//
//@Component
//public class ClockScheduledTest implements ApplicationListener<ContextRefreshedEvent> {
//
//	// 如果抓取的设备有mac地址，存进macMap
//	private static Map<String, ClockDevice> macMap = new HashMap<String, ClockDevice>();
//
//	// 如果抓取的设备没有mac地址，存进ipMap
//	private static Map<String, ClockDevice> ipMap = new HashMap<String, ClockDevice>();
//
//	// 将所有抓取到的设备对象都存放到list中
//	private static List<ClockDevice> list = new ArrayList<ClockDevice>();
//
//	// 普中时钟
//	private static Map<String, ClockDevice> prechinClock = new HashMap<String, ClockDevice>();
//
//	// 所有普中时钟
//	private static Map<String, Date> allClockIPs = new HashMap<String, Date>();
//
//	private static List<String> offlineClockIPs = null;
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(ClockScheduledTest.class);
//
//	private static Date nowTime = new Date();
//
//	@Autowired
//	private ClockDeviceService clockService;
//
//	/**
//	 * 每5秒更改一次在线时钟状态
//	 * 
//	 * @throws ParseException
//	 * @throws IOException
//	 */
//	@Scheduled(cron = "0/5 * * * * ? ")
//	public void modifyClockStatus() throws IOException, ParseException {
//		offlineClockIPs = new ArrayList<String>();
//		for (Entry<String, Date> entry : allClockIPs.entrySet()) {
//			Date date = entry.getValue();
//			Date now = new Date();
//
//			if (null != date && 5000 < now.getTime() - date.getTime()) {
//				offlineClockIPs.add(entry.getKey());
//			}
//		}
//		if (0 < offlineClockIPs.size()) {
//			// 批量更新状态
//			LOGGER.info("批量更新OFFLINE状态");
//			clockService.bitchModifyStatus(offlineClockIPs);
//		}
//
//		nowTime = listenTime();
//
//		Set<String> macSet = macMap.keySet();
//		for (String mac : macSet) {
//			macMap.get(mac).setClockTime(nowTime);
//		}
//
//		if (0 < macMap.size()) {
//			LOGGER.info("根据mac批量更新时钟信息");
//			Collection<ClockDevice> macCollention = macMap.values();
//			List<ClockDevice> clocks = new ArrayList<ClockDevice>(macCollention);
//			for (ClockDevice c : clocks) {
//				if (offlineClockIPs.contains(c.getMac())) {
//					c.setStatus("0");
//				}
//			}
//			clockService.bitchUpdateClock(clocks);
//		}
//
//		Set<String> ipSet = ipMap.keySet();
//		for (String ip : ipSet) {
//			macMap.get(ip).setClockTime(nowTime);
//		}
//
//		if (0 < ipMap.size()) {
//			LOGGER.info("根据ip批量更新时钟信息");
//			Collection<ClockDevice> ipCollention = ipMap.values();
//			List<ClockDevice> clocks = new ArrayList<ClockDevice>(ipCollention);
//			for (ClockDevice c : clocks) {
//				if (offlineClockIPs.contains(c.getIp())) {
//					c.setStatus("0");
//				}
//			}
//			clockService.bitchUpdateClock(clocks);
//		}
//		offlineClockIPs.clear();
//	}
//
//	/**
//	 * 获取普中设备MAC及IP地址
//	 * 
//	 * @throws IOException
//	 */
//	@Scheduled(cron = "0/15 * * * * ? ")
//	public void getPrechinClockMAC() throws IOException {
//
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		DatagramSocket ds = null;
//		int dsPort = 48899;
//
//		try {
//			ds = new DatagramSocket();
//		} catch (SocketException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		// 抓取设备Mac信息的包
//		byte[] b = TypeConversionUtils.hexStringToBytes(
//				"7b224167656e74223a302c2254797065223a2245505f546f6f6c222c2250617373776f7264456e223a302c2253657276506f7274223a34383839382c2256657273696f6e223a22322e302e3130222c22436f6e6e6563744d6f6465223a22466f7262696464656e222c225a6f6e65223a2245506f7274222c2253657276456e223a312c225365727641646472223a22302e302e302e30222c22436c69656e74456e223a307d");
//		try {
//			DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName("255.255.255.255"), dsPort);
//			ds.send(dp);
//
//			byte[] buff = new byte[1024];
//			DatagramPacket p = new DatagramPacket(buff, buff.length);
//			ds.setSoTimeout(5000);
//			while (true) {
//				try {
//					ds.receive(p);
//				} catch (Exception e) {
//					System.out.println("抓取设备结束");
//					continue;
//				}
//				System.out.println("MAC ==========> Received " + TypeConversionUtils.bytesToHexString(p.getData())
//						+ " from address: " + p.getSocketAddress() + " length: " + p.getLength());
//				Date date = new Date();
//				allClockIPs.put(p.getAddress().getHostAddress(), date);
//				byte[] by = Arrays.copyOfRange(p.getData(), 0, p.getLength());
//				String data = decode(TypeConversionUtils.bytesToHexString(by).replaceAll(" ", "").toUpperCase());
//
//				// 将抓取到的信息封装成JSON数据格式
//				JSONObject object = JSONObject.parseObject(data);
//
//				// 定义一个ClockDevice对象来存放时钟设备的信息；
//				ClockDevice clock = new ClockDevice();
//				clock.setIp(p.getAddress().getHostAddress());
//				clock.setMac(object.getString("MAC"));
//				clock.setId(UUIDUtil.get32UUID());
//				clock.setCreateUser("服务器");
//				clock.setCreateDate(date);
//				clock.setUpdateDate(date);
//				clock.setUpdateUser("服务器");
//				clock.setRemark("");
//				clock.setShowSec(1);
//				clock.setSource(2);
//
//				// 将新抓取的时钟设备存放到list集合 中
//				if (list.size() == 0) {
//					list.add(clock);
//				} else {
//					for (int i = 0; i < list.size(); i++) {
//						if (!list.get(i).getIp().equals(clock.getIp())) {
//							list.add(clock);
//						}
//					}
//				}
//
//				if (clock.getMac() != null) {
//					if (!macMap.containsKey(clock.getMac())) {
//						macMap.put(clock.getMac(), clock);
//					}
//				} else {
//					if (!ipMap.containsKey(clock.getIp())) {
//						ipMap.put(clock.getIp(), clock);
//					}
//				}
//
//				Map<String, Object> filter = new HashMap<>();
//				filter.put("ip", clock.getIp());
//				List<ClockDevice> list = clockService.queryClockDevicesFilter(filter);
//				if (null == list || list.size() == 0) {
//					clockService.insertClockDevice(clock);
//				}
//
//			}
//
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
//	 */
//	public static String decode(String bytes) {
//		String hexString = "0123456789ABCDEF";
//		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
//		// 将每2位16进制整数组装成一个字节
//		for (int i = 0; i < bytes.length(); i += 2)
//			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
//		return new String(baos.toByteArray());
//	}
//
//	/**
//	 * 获取所有时钟信息
//	 */
//	public void getBeforeClockInfo() {
//		Map<String, Object> filter = new HashMap<String, Object>();
//		filter.put("source", 2);
//		List<ClockDevice> clocks = clockService.queryClockDevicesFilter(filter);
//		for (ClockDevice clock : clocks) {
//			prechinClock.put(clock.getIp(), clock);
//			allClockIPs.put(clock.getIp(), clock.getClockTime());
//		}
//	}
//
//	/**
//	 * 获取时钟设备的时间信息
//	 * 
//	 * @return Date格式的时间信息
//	 * @throws IOException
//	 * @throws ParseException
//	 */
//	public static Date listenTime() throws IOException, ParseException {
//
//		int listenPort = 9000;
//
//		byte[] buf = new byte[1024];
//
//		DatagramPacket packet = new DatagramPacket(buf, buf.length);
//
//		DatagramSocket responseSocket = new DatagramSocket(listenPort);
//
//		System.out.println("===========>Server started, Listen port: " + listenPort);
//
//		responseSocket.receive(packet);
//
//		String data = TypeConversionUtils.bytesToHexString(packet.getData());
//
//		String timeData = data.substring(0, 8);
//
//		String[] subTimeData = timeData.split(" ");
//
//		Date date = new Date();
//		Calendar c = Calendar.getInstance();
//		c.setTime(date);
//		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), Integer.parseInt(subTimeData[0], 16),
//				Integer.parseInt(subTimeData[1], 16), Integer.parseInt(subTimeData[2], 16));
//		date = c.getTime();
//
//		responseSocket.close();
//
//		return date;
//	}
//
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent arg0) {
//	}
//}
