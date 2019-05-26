package com.jy.moudles.satellite.scheduled;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jy.moudles.satellite.entity.Satellite;
import com.jy.moudles.satellite.service.SatelliteService;
import com.jy.moudles.satellite.tools.SerialTool;
import com.jy.moudles.satellite.tools.serialException.NoSuchPort;
import com.jy.moudles.satellite.tools.serialException.NotASerialPort;
import com.jy.moudles.satellite.tools.serialException.PortInUse;
import com.jy.moudles.satellite.tools.serialException.ReadDataFromSerialPortFailure;
import com.jy.moudles.satellite.tools.serialException.SerialPortInputStreamCloseFailure;
import com.jy.moudles.satellite.tools.serialException.SerialPortParameterFailure;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

@Component
public class SatelliteScheduledTest implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SatelliteScheduledTest.class);

	private static Map<String, String> gpMap = new LinkedHashMap<String, String>();

	private static Map<String, String> bdMap = new LinkedHashMap<String, String>();

	private static SerialPort serialPort = null;

	private static String dataGPS = "";

	@Autowired
	private SatelliteService satService;

	/**
	 * 从串口中抓取卫星数据并解析
	 */
	public void loadGPSInfo() {
		try {
			LOGGER.info("************打开串口咯*************");
			if (serialPort == null || "".equals(serialPort)) {
				// serialPort = SerialTool.openPort("/dev/ttyS1", 9600);
				serialPort = SerialTool.openPort("COM3", 9600);
				SerialTool.addListener(serialPort, new GPSSerialListener());
			}
		} catch (SerialPortParameterFailure | NotASerialPort | NoSuchPort | PortInUse e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 30秒解析一次卫星数据并更新DB
	 */
	@Scheduled(cron = "0/30 * * * * ? ")
	private void loadData() {

		LOGGER.info("--------------------一分钟打印一次GPS信息数据开始--------------------");

		analysisData(dataGPS);

	}

	/**
	 * 处理收到的GPS数据，获取UTC时间和卫星的基本信息
	 * 
	 * @param data
	 *            GPS数据串
	 */
	public synchronized void analysisData(String data) {

		// 将获取到的字符串进行分割
		String[] datas = data.split("");

		// 定义一个存放索引的集合 $
		List<Integer> indexList = new ArrayList<Integer>();

		System.out.println("**********************************以下是解析字符串数组******************************************");

		// 找出字符串中 的$ 并向indexList中存放索引所在 位置
		for (int i = 0; i < datas.length; i++) {
			if (datas[i].equals("$")) {
				indexList.add(i);
			}
		}

		// 定义一个存放字符串的集合，用于存放卫星解析数据
		List<String> strList = new ArrayList<String>();

		// 根据 集合中的索引值得到卫星协议数据
		for (int i = 0; i < indexList.size() - 1; i++) {
			String tempStr = "";
			for (int j = indexList.get(i); j < indexList.get(i + 1); j++) {
				tempStr += datas[j];
			}
			strList.add(tempStr);
		}

		// for (String string : strList) {
		// System.out.println(string + " ");
		// }

		// 定义一个存放有效数据的集合
		List<String> validData = new ArrayList<String>();

		// 将strList中需要解析的数据存放到validData中
		for (int i = 0; i < strList.size(); i++) {
			if (strList.get(i).startsWith("$GPGSV") || strList.get(i).startsWith("$GNGGA")
					|| strList.get(i).startsWith("$BDGSV")) {
				validData.add(strList.get(i));
			}
		}

		// 遍历一下validData集合

		for (String string : validData) {
			System.out.println("有效数据" + string + " ");
		}

		for (String string : validData) {
			String[] subData = string.split(",");
			switch (subData[0]) {
			case "$GPGSV":// 当前GPS卫星状态信息
				if (subData.length > 4 && subData.length % 4 == 0) {

					List<Satellite> satelliteInfos = new ArrayList<Satellite>();
					int num = (subData.length - 3) / 4;
					for (int i = 1; i <= num; i++) {
						Satellite sat = new Satellite();
						sat.setSaNo(!subData[(4 * i) + 0].equals("") ? Integer.valueOf(subData[(4 * i) + 0]) + 40 + ""
								: "");
						sat.setElevation(!subData[(4 * i) + 1].equals("") ? subData[(4 * i) + 1] : "");
						sat.setAzimuth(!subData[(4 * i) + 2].equals("") ? subData[(4 * i) + 2] : "");
						sat.setNoiseSignal(isValidData(subData[(4 * i) + 3]));
						sat.setSaType(1);
						satelliteInfos.add(sat);
						System.out.println(sat.toString());
					}

					if (satelliteInfos != null && satelliteInfos.size() > 0) {

						satService.updateSatInfo(satelliteInfos);
					}
					break;
				} else {

					System.out.println("GPGSV无有效数据");
					break;
				}
			case "$GNGGA":// 位置信息
				if (!subData[1].equals("")) {

					String timeStr = subData[1];
					BigDecimal b = new BigDecimal(timeStr);
					String gpsDate = String.format("%09d", b.multiply(new BigDecimal(1000)).intValue());
					// String.subString() 左开右闭
					String hour = gpsDate.substring(0, 2);
					String minute = gpsDate.substring(2, 4);
					String second = gpsDate.substring(4, 6);
					String time = String.valueOf((24 > ((Integer.valueOf(hour) + 8))) ? (Integer.valueOf(hour) + 8)
							: (-(24 - ((Integer.valueOf(hour) + 8))))) + ":" + minute + ":" + second;
					if (time.length() == 7) {
						time = "0" + time;
					}
					System.out.println(time);
					try {
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						modifySysTime(sdf.format(date), time);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				} else {
					System.out.println("GNGGA无有效数据");
					break;

				}
			case "$BDGSV":// 位置信息
				if (subData.length > 4 && subData.length % 4 == 0) {

					List<Satellite> satelliteInfos = new ArrayList<Satellite>();
					int num = (subData.length - 3) / 4;
					for (int i = 1; i <= num; i++) {
						Satellite sat = new Satellite();
						sat.setSaNo(!subData[(4 * i) + 0].equals("") ? subData[(4 * i) + 0] : "");
						sat.setElevation(!subData[(4 * i) + 1].equals("") ? subData[(4 * i) + 1] : "");
						sat.setAzimuth(!subData[(4 * i) + 2].equals("") ? subData[(4 * i) + 2] : "");
						sat.setNoiseSignal(isValidData(subData[(4 * i) + 3]));
						sat.setSaType(2);
						satelliteInfos.add(sat);
						System.out.println(sat.toString());
					}

					if (satelliteInfos != null && satelliteInfos.size() > 0) {

						satService.updateSatInfo(satelliteInfos);
					}
					break;
				} else {
					System.out.println("BDGSV无有效数据");
					break;

				}

			}
		}

		System.out.println();
		System.out.println("**********************************以下是dataGPS********************************************");
	}

	/**
	 * 解析一个字符串是否为有效的数字
	 * 
	 * @param str
	 * @return 数字字符串
	 */
	public static String isValidData(String str) {
		int index = str.indexOf("*");
		if (str.equals("")) {
			return "";
		}
		if (index == 0) {
			return "";
		} else {
			return str.substring(0, 2);
		}

	}

	private static class GPSSerialListener implements SerialPortEventListener {

		@Override
		public void serialEvent(SerialPortEvent serialPortEvent) {

			try {
				Thread.sleep(1000);
				dataGPS = "";
				int eventType = serialPortEvent.getEventType();
				switch (eventType) {
				case SerialPortEvent.DATA_AVAILABLE:
					byte[] data = null;
					if (null != serialPort) {
						data = SerialTool.readFromPort(serialPort);
						if (null != data && 0 < data.length) {
							dataGPS += new String(data);
						} else {
							System.out.println("dataGPS为空");
						}
					} else {
						System.out.println("未获取到信息");
					}
				}
			} catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 修改系统时间
	 * 
	 * @param date
	 * @param time
	 * @throws IOException
	 */
	public static void modifySysTime(String date, String time) throws IOException {
		String osName = System.getProperty("os.name").toLowerCase(Locale.CHINA);
		String osArch = System.getProperty("os.arch").toLowerCase(Locale.CHINA);
		String osVersion = System.getProperty("os.version").toLowerCase(Locale.CHINA);
		LOGGER.info("当前系统信息：" + osName + "---" + osArch + "---" + osVersion);
		if (osName.contains("win")) {
			LOGGER.info("修改win系统时间" + date + ":" + time);
		} else {
			LOGGER.info("修改liunx系统时间");
			Runtime.getRuntime().exec(" sudo date -s " + date);
			Runtime.getRuntime().exec(" sudo date -s " + time);
		}
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		if (null != arg0.getApplicationContext().getParent()) {
			loadGPSInfo();
		}
	}

}
