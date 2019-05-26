//package com.jy.moudles.satellite.scheduled;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cont数ext.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.jy.moudles.satellite.entity.Satellite;
//import com.jy.moudles.satellite.service.SatelliteService;
//import com.jy.moudles.satellite.tools.SerialTool;
//import com.jy.moudles.satellite.tools.serialException.NoSuchPort;
//import com.jy.moudles.satellite.tools.serialException.NotASerialPort;
//import com.jy.moudles.satellite.tools.serialException.PortInUse;
//import com.jy.moudles.satellite.tools.serialException.ReadDataFromSerialPortFailure;
//import com.jy.moudles.satellite.tools.serialException.SerialPortInputStreamCloseFailure;
//import com.jy.moudles.satellite.tools.serialException.SerialPortParameterFailure;
//
//import gnu.io.SerialPort;
//import gnu.io.SerialPortEvent;
//import gnu.io.SerialPortEventListener;
//
//@Component
//public class SatelliteScheduled implements ApplicationListener<ContextRefreshedEvent> {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(SatelliteScheduled.class);
//
//	private static Map<String, String> gpMap = new LinkedHashMap<String, String>();
//
//	private static Map<String, String> bdMap = new LinkedHashMap<String, String>();
//
//	private static SerialPort serialPort = null;
//
//	private static String dataGPS = "";
//
//	@Autowired
//	private SatelliteService satService;
//
//	public void loadGPSInfo() {
//		try {
//			LOGGER.info("************打开串口咯*************");
//			if (serialPort == null || "".equals(serialPort)) {
//				// serialPort = SerialTool.openPort("/dev/ttyS1", 9600);
//				serialPort = SerialTool.openPort("COM3", 9600);
//				SerialTool.addListener(serialPort, new GPSSerialListener());
//			}
//		} catch (SerialPortParameterFailure | NotASerialPort | NoSuchPort | PortInUse e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 解析卫星数据
//	 */
////	@Scheduled(cron = "0/15 * *  * * ? ")
//	private void loadData() {
//		Date date = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			LOGGER.info("--------------------15秒打印一次GPS信息数据开始--------------------");
//
//			if (0 < dataGPS.length()) {// 查时间同步修改电脑本地时间
//				List<Integer> indexs = new ArrayList<Integer>();
//				indexs.add(dataGPS.lastIndexOf("GGA"));
//				indexs.add(dataGPS.lastIndexOf("GLL"));
//				indexs.add(dataGPS.lastIndexOf("RMC"));
//				indexs.add(dataGPS.lastIndexOf("ZDA"));
//
//				Collections.sort(indexs);
//				BigDecimal b = BigDecimal.ZERO;
//				String type = "";
//				for (int i = indexs.size() - 1; i >= 0; i--) {
//					if (indexs.get(i) < 0) {
//						// LOGGER.info("小于0");
//						continue;
//					}
//					String dataGNNS = dataGPS.substring(indexs.get(i));
//					String[] dataGNNSes = dataGNNS.split(",");
//					type = dataGNNSes[0];
//					if (null != dataGNNSes && 1 < dataGNNSes.length) {
//						LOGGER.info("ro:" + dataGNNS);
//						try {
//							if (dataGNNS.startsWith("GGA") || dataGNNS.startsWith("RMC")
//									|| dataGNNS.startsWith("ZDA")) {
//								if (dataGNNS.startsWith("RMC") && 8 < dataGNNSes.length) {
//									SimpleDateFormat sdfRMC = new SimpleDateFormat("ddMMyy");
//									date = sdfRMC.parse(dataGNNSes[9]);
//									LOGGER.info(sdf.format(date));
//								}
//								if (10 == dataGNNSes[1].length()) {
//									LOGGER.info(dataGNNSes[1]);
//									b = new BigDecimal(dataGNNSes[1]);
//								} else {
//									continue;
//								}
//							} else if (dataGNNS.startsWith("GLL")) {
//								if (5 < dataGNNSes.length && 10 == dataGNNSes[5].length()) {
//									LOGGER.info(dataGNNSes[5]);
//									b = new BigDecimal(dataGNNSes[5]);
//								} else {
//									continue;
//								}
//							}
//						} catch (NumberFormatException | ParseException e) {
//							continue;
//						}
//						break;
//					}
//				}
//				LOGGER.info(type + "---" + String.format("%09d", b.multiply(new BigDecimal(1000)).intValue()));
//				String gpsDate = String.format("%09d", b.multiply(new BigDecimal(1000)).intValue());
//				//String.subString() 左开右闭
//				String hour = gpsDate.substring(0, 2);
//				String minute = gpsDate.substring(2, 4);
//				String second = gpsDate.substring(4, 6);
//				String time = String.valueOf((24 > ((Integer.valueOf(hour) + 8))) ? (Integer.valueOf(hour) + 8)
//						: (-(24 - ((Integer.valueOf(hour) + 8))))) + ":" + minute + ":" + second;
//				if (time.length() == 7) {
//					time = "0" + time;
//				}
//				modifySysTime(sdf.format(date), time);
//			}
//			// 将卫星数据存到数据库
//			if (dataGPS.length() > 0) {
//				String[] infos = dataGPS.split("\\$");
//				// 需要存库的GP卫星
//				List<Satellite> satelliteInfos = new ArrayList<Satellite>();
//				for (String info : infos) {
//					if (info.startsWith("GPGSV")) {
//						try {
//							String[] gps = info.split(",");
//							if (info.contains("\\*")) {
//								gps[gps.length - 1] = "";
//							}
//							if (gps.length == 8 || gps.length == 12 || gps.length == 16 || gps.length == 20) {// 正确数据的长度
//								for (int i = 4; i <= 19; i++) {
//									if (i < gps.length) {
//										if (i == 4 || i == 8 || i == 12 || i == 16) {// 每个卫星信息的初始位置
//											if (gpMap.get(gps[i]) == null) {
//												if (StringUtils.isBlank(gps[i])) {
//													continue;
//												}
//												Satellite sat = new Satellite();
//												sat.setSaNo(gps[i]);
//												sat.setElevation(gps[i + 1]);
//												sat.setAzimuth(gps[i + 2]);
//												sat.setNoiseSignal(gps[i + 3]);
//												sat.setSaType(1);
//												satelliteInfos.add(sat);
//												gpMap.put(gps[i], "1");
//											}
//										}
//									}
//								}
//							}
//						} catch (Exception e) {
//							LOGGER.info("error info:" + info);
//							e.printStackTrace();
//						}
//					} else if (info.startsWith("BDGSV")) {
//						try {
//
//							String[] gps = info.split(",");
//							if (info.contains("\\*")) {
//								gps[gps.length - 1] = "";
//							}
//							if (gps.length == 8 || gps.length == 12 || gps.length == 16 || gps.length == 20) {
//								for (int i = 4; i <= 19; i++) {
//									if (i < gps.length) {
//										// 每个卫星信息的初始位置
//										if (i == 4 || i == 8 || i == 12 || i == 16) {
//											if (bdMap.get(gps[i]) == null) {
//												Satellite sat = new Satellite();
//												if (StringUtils.isBlank(gps[i])) {
//													continue;
//												}
//												sat.setSaNo(gps[i]);
//												sat.setElevation(gps[i + 1]);
//												sat.setAzimuth(gps[i + 2]);
//												sat.setNoiseSignal(gps[i + 3]);
//												sat.setSaType(2);
//												satelliteInfos.add(sat);
//												bdMap.put(gps[i], "1");
//											}
//										}
//									}
//								}
//							}
//						} catch (Exception e) {
//							LOGGER.info("error info:" + info);
//							e.printStackTrace();
//						}
//
//					}
//				}
//				if (satelliteInfos != null && satelliteInfos.size() > 0) {
//
//					satService.updateSatInfo(satelliteInfos);
//				}
//			}
//			dataGPS = "";
//			LOGGER.info("--------------------15秒打印一次GPS信息数据结束 --------------------");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static class GPSSerialListener implements SerialPortEventListener {
//
//		@Override
//		public void serialEvent(SerialPortEvent serialPortEvent) {
//
//			try {
//				int eventType = serialPortEvent.getEventType();
//				switch (eventType) {
//				case SerialPortEvent.DATA_AVAILABLE:
//					byte[] data = null;
//					if (null != serialPort) {
//						data = SerialTool.readFromPort(serialPort);
//						if (null != data && 0 < data.length) {
//							dataGPS += new String(data);
//						} else {
//							System.out.println("dataGPS为空");
//						}
//					} else {
//						System.out.println("未获取到信息");
//					}
//				}
//			} catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
//				e.printStackTrace();
//			}
//
//		}
//	}
//
//	/**
//	 * 修改系统时间
//	 * 
//	 * @param date
//	 * @param time
//	 * @throws IOException
//	 */
//	public static void modifySysTime(String date, String time) throws IOException {
//		String osName = System.getProperty("os.name").toLowerCase(Locale.CHINA);
//		String osArch = System.getProperty("os.arch").toLowerCase(Locale.CHINA);
//		String osVersion = System.getProperty("os.version").toLowerCase(Locale.CHINA);
//		LOGGER.info("当前系统信息：" + osName + "---" + osArch + "---" + osVersion);
//		if (osName.contains("win")) {
//			LOGGER.info("修改win系统时间" + date + ":" + time);
//		} else {
//			LOGGER.info("修改liunx系统时间");
//			Runtime.getRuntime().exec(" sudo date -s " + date);
//			Runtime.getRuntime().exec(" sudo date -s " + time);
//		}
//	}
//
//	@Override
//	public void onApplicationEvent(ContextRefreshedEvent arg0) {
//		if (null != arg0.getApplicationContext().getParent()) {
//			loadGPSInfo();
//		}
//	}
//
//}
