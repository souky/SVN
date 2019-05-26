package com.jy.common.utils.device;

import java.util.List;

import com.jy.common.config.Global;
import com.jy.moudles.device.entity.Device;
import com.jy.protocol.jry.udp.SendJryUdpUtils;

public class DeviceUtils {
	
	
	private static final int port = 1025;
	
	private static final String queryInfo = "QBCINFO";
	
	//查询所有库里设备
	public static void queryDevice(List<Device> list) {
		Global.mapStatic.put("deviceNum", 0);
		for(Device d:list) {
			//接收屏蔽UDP
			SendJryUdpUtils.sendUdp4Query(d.getIp(), port, queryInfo);
		}
	}
	
	//RIP192.168.1.140;255.255.255.0;192.168.1.1#
	//OK:IP=192.168.1.140,NETMASK=255.255.255.0,GW=192.168.1.1#
	
}

