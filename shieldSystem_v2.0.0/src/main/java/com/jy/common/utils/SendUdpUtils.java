package com.jy.common.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUdpUtils {
	
	public static void sendUdpNoRecv(String ip, String sendMessage) {

		DatagramSocket datagramSocket = null;
		try {
			datagramSocket = new DatagramSocket();
			datagramSocket.setSoTimeout(3000);
			byte[] buf = TypeConversionUtils.hexStringToBytes(sendMessage);
			InetAddress address = InetAddress.getByName(ip);
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, 1025);
			// 发送数据
			datagramSocket.send(datagramPacket);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭socket
			if (datagramSocket != null) {
				datagramSocket.close();
			}
		}
	}
}
