package com.jy.moudles.clockDevice.scheduled;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jy.common.utils.TypeConversionUtils;
import com.jy.common.utils.UUIDUtil;
import com.jy.common.utils.protocol.ClockFrameUtil;
import com.jy.moudles.clockDevice.entity.ClockDevice;
import com.jy.moudles.clockDevice.service.ClockDeviceService;

@Component
public class Clock4IHomeScheduled implements ApplicationListener<ContextRefreshedEvent>{
	
	// 厚盟时钟
	private static Map<String, ClockDevice> iHomeClock = new HashMap<String, ClockDevice>();
	
	// 所有厚盟时钟
	private static Map<String, Date> iHomeClockAll = new HashMap<String, Date>();
	
	
	private static List<String> offlineClockIPs = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Clock4IHomeScheduled.class);
	
	@Autowired
	private ClockDeviceService clockService;
	
	/**
	 * 获取厚盟时钟信息
	 * 
	 * @throws IOException
	 */
	public void getIHomeClockInfo() throws IOException {

		int listenPort = 1025;
		int receviePort = 1025;
		
		String register = ClockFrameUtil.clockRegister(InetAddress.getLocalHost().getHostAddress(), receviePort);
		
		DatagramSocket ds = null;
		int dsPort = -1;
		try {
			ds = new DatagramSocket();
			byte[] b = TypeConversionUtils.hexStringToBytes(register.toString());
			DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName("255.255.255.255"), listenPort);
			dsPort = ds.getLocalPort();
			ds.send(dp);

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			ds.close();
		}

		byte[] buf = new byte[1024];

		DatagramPacket packet = new DatagramPacket(buf, buf.length);

		DatagramSocket responseSocket = new DatagramSocket(listenPort);

		System.out.println("Server started, Listen port: " + listenPort);

		while (true) {

			responseSocket.receive(packet);

			if (26 == packet.getLength()) {
				// 新增和修改时钟信息
				ClockDevice clock = new ClockDevice();
				Date date = new Date();
				iHomeClockAll.put(packet.getAddress().getHostAddress(), date);
				if (iHomeClock.containsKey(packet.getAddress().getHostAddress())) {
					clock = iHomeClock.get(packet.getAddress().getHostAddress());
					valueClockDevice(packet, clock);
					clock.setUpdateDate(date);
					clock.setUpdateUser("服务器");
				} else {
					valueClockDevice(packet, clock);
					clock.setId(UUIDUtil.get32UUID());
					clock.setCreateDate(date);
					clock.setCreateUser("服务器");
					clock.setUpdateDate(date);
					clock.setUpdateUser("服务器");
					clock.setRemark("");
					clock.setShowSec(1);
					clock.setSource(1);
					iHomeClock.put(clock.getIp(), clock);
					clockService.insertClockDevice(clock);
				}
			}
		}

	}
	
	/**
	 * 每5秒更改一次在线时钟状态
	 */
	@Scheduled(cron = "0/5 * * * * ? ")
	public void modifyClockStatus() {
		offlineClockIPs = new ArrayList<String>();
		for (Entry<String, Date> entry : iHomeClockAll.entrySet()) {
			Date date = entry.getValue();
			Date now = new Date();
			if (5000 < now.getTime() - date.getTime()) {
				offlineClockIPs.add(entry.getKey());
			}
		}
		if (0 < offlineClockIPs.size()) {
			// 批量更新状态
			LOGGER.info("批量更新OFFLINE状态");
			clockService.bitchModifyStatus(offlineClockIPs);
		}
		
		if (0 < iHomeClock.size()) {
			LOGGER.info("批量更新时钟信息");
			Collection<ClockDevice> clocksCollention = iHomeClock.values();
			List<ClockDevice> clocks = new ArrayList<ClockDevice>(clocksCollention);
			for(ClockDevice c: clocks) {
				if(offlineClockIPs.contains(c.getIp())) {
					c.setStatus("0");
				}
			}
			clockService.bitchUpdateClock(clocks);
		}
	}
	
	
	/**
	 * 赋值时钟信息
	 * 
	 * @param packet
	 * @return
	 */
	public static void valueClockDevice(DatagramPacket packet, ClockDevice clock) {
		clock.setIp(packet.getAddress().getHostAddress());
		clock.setPort(String.valueOf(packet.getPort()));
		clock.setStatus("1");
		String dates = TypeConversionUtils.bytesToHexString(packet.getData()).replaceAll(" ","");
		String mac = dates.substring(28, 40);
		String hours = dates.substring(46, 48);
		String min = dates.substring(48, 50);
		String sec = dates.substring(50,52);
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), Integer.parseInt(hours, 16),
				Integer.parseInt(min, 16), Integer.parseInt(sec, 16));
		date = c.getTime();
		clock.setClockTime(date);
		clock.setMac(mac);
		
	}
	/**
	 * 获取倒序本地IP
	 * 
	 * @return
	 * @throws UnknownHostException
	 */
	public static String getReverseHexLocalIP() throws UnknownHostException {
		String reverseHexLocalIP = "";

		String localIP = InetAddress.getLocalHost().getHostAddress();

		String[] localIPs = localIP.split("\\.");

		for (int start = 0, end = localIPs.length - 1; start < end; start++, end--) {
			String temp = localIPs[end];
			localIPs[end] = localIPs[start];
			localIPs[start] = temp;
		}

		for (int i = 0; i < localIPs.length; i++) {
			Integer x = Integer.parseInt(localIPs[i]);
			localIPs[i] = x.toHexString(x);
		}

		reverseHexLocalIP = StringUtils.join(localIPs);

		return reverseHexLocalIP;
	}

	/**
	 * 前补0
	 * @param str
	 * @param strLength
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		StringBuffer sb = null;
		while (strLen < strLength) {
			sb = new StringBuffer();
			sb.append("0").append(str);// 左(前)补0
			// sb.append(str).append("0");//右(后)补0
			str = sb.toString();
			strLen = str.length();
		}
		return str;
	}
	
	/**
	 * 获取所有时钟信息
	 */
	public void getBeforeClockInfo() {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("source", 1);
		List<ClockDevice> clocks = clockService.queryClockDevicesFilter(filter);
		for (ClockDevice clock : clocks) {
			iHomeClock.put(clock.getIp(), clock);
			iHomeClockAll.put(clock.getIp(), clock.getClockTime());
		}
	}
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (null != arg0.getApplicationContext().getParent()) {
			getBeforeClockInfo();
			new Thread(new GetHomeClockInfoThread()).start();
		}
	}
	
	public class GetHomeClockInfoThread implements Runnable {

		@Override
		public void run() {
			try {
				getIHomeClockInfo();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	


}
