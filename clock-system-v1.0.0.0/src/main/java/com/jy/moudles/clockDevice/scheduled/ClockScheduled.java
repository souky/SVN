package com.jy.moudles.clockDevice.scheduled;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jy.common.utils.TypeConversionUtils;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.clockDevice.entity.ClockDevice;
import com.jy.moudles.clockDevice.service.ClockDeviceService;

@Component
public class ClockScheduled implements ApplicationListener<ContextRefreshedEvent> {

	// 普中时钟
	private static Map<String, ClockDevice> prechinClock = new HashMap<String, ClockDevice>();

	// 所有普中时钟
	private static Map<String, Date> allClockIPs = new HashMap<String, Date>();

	private static List<String> offlineClockIPs = null;

	private static final Logger LOGGER = LoggerFactory.getLogger(ClockScheduled.class);

	@Autowired
	private ClockDeviceService clockService;

	/**
	 * 每5秒更改一次在线时钟状态
	 */
	@Scheduled(cron = "0/5 * * * * ? ")
	public void modifyClockStatus() {
		offlineClockIPs = new ArrayList<String>();
		for (Entry<String, Date> entry : allClockIPs.entrySet()) {
			Date date = entry.getValue();
			Date now = new Date();

			if (null != date && 5000 < now.getTime() - date.getTime()) {
				offlineClockIPs.add(entry.getKey());
			}
		}
		if (0 < offlineClockIPs.size()) {
			// 批量更新状态
			LOGGER.info("批量更新OFFLINE状态");
			clockService.bitchModifyStatus(offlineClockIPs);
		}

		if (0 < prechinClock.size()) {
			LOGGER.info("批量更新时钟信息");
			Collection<ClockDevice> clocksCollention = prechinClock.values();
			List<ClockDevice> clocks = new ArrayList<ClockDevice>(clocksCollention);
			for (ClockDevice c : clocks) {
				if (offlineClockIPs.contains(c.getIp())) {
					c.setStatus("0");
				}
			}
			clockService.bitchUpdateClock(clocks);
			offlineClockIPs.clear();
		}
	}

	/**
	 * 获取普中时钟信息
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void getprechinClockInfo() throws IOException {

		int listenPort = 9000;

		byte[] buf = new byte[1024];

		DatagramPacket packet = new DatagramPacket(buf, buf.length);

		DatagramSocket responseSocket = new DatagramSocket(listenPort);

		LOGGER.info("Server started, Listen port: " + listenPort);
		// clockService.deleteClockDeviceAll();

		while (true) {

			responseSocket.receive(packet);

			LOGGER.info("Received " + TypeConversionUtils.bytesToHexString(packet.getData()) + " from address: "
					+ packet.getSocketAddress() + " length: " + packet.getLength());

			if (3 == packet.getLength()) {
				// 新增和修改时钟信息
				ClockDevice clock = new ClockDevice();
				Date date = new Date();
				if (packet.getAddress().getHostAddress().startsWith("192")) {
					allClockIPs.put(packet.getAddress().getHostAddress(), date);
					if (prechinClock.containsKey(packet.getAddress().getHostAddress())) {
						clock = prechinClock.get(packet.getAddress().getHostAddress());
						valueClockDevice(packet, clock);
						clock.setUpdateDate(date);
						clock.setUpdateUser("服务器");
						prechinClock.put(clock.getIp(), clock);
					} else {
						valueClockDevice(packet, clock);
						clock.setId(UUIDUtil.get32UUID());
						clock.setCreateDate(date);
						clock.setCreateUser("服务器");
						clock.setUpdateDate(date);
						clock.setUpdateUser("服务器");
						clock.setMac("---");
						clock.setRemark("");
						clock.setShowSec(1);
						clock.setSource(2);
						prechinClock.put(clock.getIp(), clock);
						Map<String, Object> filter = new HashMap<>();
						filter.put("ip", clock.getIp());
						List<ClockDevice> list = clockService.queryClockDevicesFilter(filter);
						if (null == list || list.size() == 0) {
							clockService.insertClockDevice(clock);
						}
					}
				}
			}
		}
	}

	/**
	 * 获取普中设备MAC地址
	 * 
	 * @throws IOException
	 */
	@Scheduled(cron = "0/15 * * * * ? ")
	public void getPrechinClockMAC() throws IOException {

		DatagramSocket ds = null;
		int dsPort = 48899;
		try {
			ds = new DatagramSocket();
			byte[] b = TypeConversionUtils.hexStringToBytes(
					"7b224167656e74223a302c2254797065223a2245505f546f6f6c222c2250617373776f7264456e223a302c2253657276506f7274223a34383839382c2256657273696f6e223a22322e302e3130222c22436f6e6e6563744d6f6465223a22466f7262696464656e222c225a6f6e65223a2245506f7274222c2253657276456e223a312c225365727641646472223a22302e302e302e30222c22436c69656e74456e223a307d");
			DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getByName("255.255.255.255"), dsPort);
			dsPort = ds.getLocalPort();
			ds.send(dp);
			byte[] buff = new byte[1024];
			DatagramPacket p = new DatagramPacket(buff, buff.length);
			ds.setSoTimeout(5000);
			while (true) {
				ds.receive(p);
				LOGGER.info("Received " + TypeConversionUtils.bytesToHexString(p.getData()) + " from address: "
						+ p.getSocketAddress() + " length: " + p.getLength());

				ClockDevice clock = new ClockDevice();
				Date date = new Date();
				byte[] by = Arrays.copyOfRange(p.getData(), 0, p.getLength());
				String data = decode(TypeConversionUtils.bytesToHexString(by).replaceAll(" ", "").toUpperCase());
				JSONObject object = JSONObject.parseObject(data);

				allClockIPs.put(p.getAddress().getHostAddress(), date);
				if (prechinClock.containsKey(p.getAddress().getHostAddress())) {
					clock = prechinClock.get(p.getAddress().getHostAddress());
					clock.setStatus("1");
					clock.setMac(object.getString("MAC"));
					clock.setUpdateDate(date);
					clock.setUpdateUser("服务器");
					prechinClock.put(clock.getIp(), clock);
				} else {
					clock.setId(UUIDUtil.get32UUID());
					clock.setIp(p.getAddress().getHostAddress());
					clock.setStatus("1");
					clock.setCreateDate(date);
					clock.setCreateUser("服务器");
					clock.setUpdateDate(date);
					clock.setUpdateUser("服务器");
					clock.setRemark("");
					clock.setShowSec(1);
					clock.setSource(2);
					prechinClock.put(clock.getIp(), clock);
					Map<String, Object> filter = new HashMap<>();
					filter.put("ip", clock.getIp());
					List<ClockDevice> list = clockService.queryClockDevicesFilter(filter);
					if (null == list || list.size() == 0) {
						clockService.insertClockDevice(clock);
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException) {
				LOGGER.info("抓取设备MAC结束");
			} else {
				e.printStackTrace();
			}
		} finally {
			ds.close();
		}
	}

	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		String hexString = "0123456789ABCDEF";
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	// public static void main(String[] args) {
	// String clockInfoS = decode(
	// "7b0a09224167656e74223a09302c0a092254797065223a092245503230222c0a092256657273696f6e223a0922312e323062222c0a0922436c69656e74456e223a09312c0a0922486f73744e616d65223a092245706f72742d45503230222c0a0922437573746f6d65724944223a092245503230222c0a0922557074696d65223a0922302d44617920303a31303a345c725c6e222c0a09225a6f6e65223a092245506f7274222c0a0922556470537570706f7274223a09312c0a09224d4143223a0922463046453642413536424234222c0a09225374617465223a092249646c65220a7d"
	// .toUpperCase());
	//
	// JSONObject object = JSONObject.parseObject(clockInfoS);
	// System.out.println(object.get("MAC"));
	// }

	/**
	 * 获取所有时钟信息
	 */
	public void getBeforeClockInfo() {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("source", 2);
		List<ClockDevice> clocks = clockService.queryClockDevicesFilter(filter);
		for (ClockDevice clock : clocks) {
			prechinClock.put(clock.getIp(), clock);
			allClockIPs.put(clock.getIp(), clock.getClockTime());
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
		String[] times = TypeConversionUtils.bytesToHexString(packet.getData()).split(" ");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), Integer.parseInt(times[0], 16),
				Integer.parseInt(times[1], 16), Integer.parseInt(times[2], 16));
		date = c.getTime();
		clock.setClockTime(date);

	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (null != arg0.getApplicationContext().getParent()) {
			getBeforeClockInfo();
			new Thread(new GetClockInfoThread()).start();
			// new Thread(new GetClockInfoMACThread()).start();
		}
	}

	public class GetClockInfoThread implements Runnable {

		@Override
		public void run() {
			try {
				getprechinClockInfo();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// public class GetClockInfoMACThread implements Runnable {
	//
	// @Override
	// public void run() {
	// try {
	// getPrechinClockMAC();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
}
