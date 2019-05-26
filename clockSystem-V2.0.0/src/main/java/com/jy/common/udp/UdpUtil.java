package com.jy.common.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpUtil {


	/**
	 * 发送udp协议
	 * @param ip ip地址
	 * @param port 端口号
	 * @param data  发送数据
	 * */
	public static void sendUdp(String ip,int port,byte[] data) {
		
		try {
			//定义服务器的地址，端口号，数据
	        InetAddress address = InetAddress.getByName(ip);
	        //创建数据报
	        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
	        //创建DatagramSocket，实现数据发送和接收
	        DatagramSocket socket;
	        
			socket = new DatagramSocket();
			//向服务器端发送数据报
	        socket.send(packet);
	        
	        //接收服务器响应数据
	        byte[] data2 = new byte[1024];
	        DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
	        socket.receive(packet2);
	        String info = new String(data2, 0, packet2.getLength());
	        System.out.println("我是客户端，服务器说："+info);
	        socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public static void guangbo(int port,byte[] data) {
		 
        try {  
        	DatagramSocket ds = new DatagramSocket();// 创建用来发送数据报包的套接字  
            DatagramPacket dp = new DatagramPacket(data, data.length,InetAddress.getByName("255.255.255.255"), port); 
            ds.send(dp); 
            System.out.println("发送广播");
            ds.close(); 
            System.out.println("发送广播 --end");
        } catch (IOException e) {  
            e.printStackTrace();  
        }  finally {
        	 
		}
        
	}
	
	public static void ReceiveUDP() throws IOException {
		int listenPort = 1025;

        byte[] buf = new byte[1024];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);

        DatagramSocket responseSocket = new DatagramSocket(listenPort);

        System.out.println("Server started, Listen port: " + listenPort);

        while (true) {

            responseSocket.receive(packet);

            String rcvd = "Received "

                    + new String(packet.getData(), 0, packet.getLength())

                    + " from address: " + packet.getSocketAddress();

            System.out.println(rcvd);
 

            // Send a response packet to sender

            String backData = "DCBA";

            byte[] data = backData.getBytes();

            System.out.println("Send " + backData + " to " + packet.getSocketAddress());

            DatagramPacket backPacket = new DatagramPacket(data, 0,

                    data.length, packet.getSocketAddress());

            responseSocket.send(backPacket);

        }
	}
	
}
