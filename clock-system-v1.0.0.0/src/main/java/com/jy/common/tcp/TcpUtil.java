package com.jy.common.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jy.common.utils.TypeConversionUtils;

public class TcpUtil {

	private static Socket socket = null;

	/**
	 * 发送tcp 
	 * @param ip 需要发送的ip的地址
	 * @param code 发送的内容
	 * @param port 端口号
	 * */
	public static void sendTcp(String ip,String code,int port){
		
		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		
        try {
        	//创建Socket对象
        	socket=new Socket(ip,port);
        	
			//根据输入输出流和服务端连接
			//获取一个输出流，向服务端发送信息
        	outputStream=socket.getOutputStream();
            //将输出流包装成打印流
            outputStream.write(TypeConversionUtils.hexStringToBytes(code));
            //关闭输出流
           // socket.shutdownOutput();
            //获取一个输入流，接收服务端的信息
            inputStream=socket.getInputStream();
            //缓冲区
            int count = 0;
            while(count == 0) {
            	count = inputStream.available();
            }
        	byte[] b = new byte[count];
        	inputStream.read(b);
			System.out.println(TypeConversionUtils.bytesToHexString(b));
            
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if(inputStream!=null){
					inputStream.close();
				}
				if(outputStream!=null){
					outputStream.close();
				}
				if(socket!=null){
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
           
		}
	}
	
	/**
	 * 发送tcp 
	 * @param ip 需要发送的ip的地址
	 * @param code 发送的内容
	 * @param port 端口号
	 * */
	public static void registerTcp(String ip,String code,int port){
		
		//Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputStream = null;
		
        try {
        	//创建Socket对象
        	socket=new Socket(ip,port);
        	socket.setKeepAlive(true);
        	
			//根据输入输出流和服务端连接
			//获取一个输出流，向服务端发送信息
        	outputStream=socket.getOutputStream();
            //将输出流包装成打印流
            outputStream.write(TypeConversionUtils.hexStringToBytes(code));
            //关闭输出流
            //socket.shutdownOutput();
            //获取一个输入流，接收服务端的信息
            inputStream=socket.getInputStream();
            System.out.println("平台开始连接-------------------------");
			LinkedList<String> returnStringList = new LinkedList<>();
			while(true) {
			    if(!socket.isConnected()){
                    System.out.println("----未知原因,断开连接,disconnect...");
                }
				int count = 0;
	            while(count == 0) {
	            	count = inputStream.available();
	            }
	        	byte[] b = new byte[count];
	        	inputStream.read(b);
	        	String codeReturn = TypeConversionUtils.bytesToHexString(b).replaceAll(" ", "");
	        	System.out.println(codeReturn);
	        	
	        	
            }
            
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
        	//断开tcp连接
            System.out.println("----未知原因,断开连接,disconnect...");
			e.printStackTrace();
		}finally {
           
		}
	}
	
	
	/**
	 * 接收tcp 
	 * @param returnCode 返回内容
	 * @param port 端口号
	 * */
	public static void receiveTcp(int port,String returnCode){
		
		Socket socket = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		OutputStream outputStream = null;
		PrintWriter printWriter = null;
		
        try {
        	@SuppressWarnings("resource")
			ServerSocket serverSocket=new ServerSocket(port);
            System.out.println("服务端已启动，等待客户端连接..");
        	//侦听并接受到此套接字的连接,返回一个Socket对象
			socket=serverSocket.accept();
			
			//根据输入输出流和客户端连接
			//得到一个输入流，接收客户端传递的信息
            inputStream=socket.getInputStream();
            //提高效率，将自己字节流转为字符流
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            //加入缓冲区
            bufferedReader=new BufferedReader(inputStreamReader);
            String temp=null;
            String info="";
            while((temp=bufferedReader.readLine())!=null){
                info+=temp;
            }
            
            //获取一个输出流，向服务端发送信息
            outputStream=socket.getOutputStream();
            //将输出流包装成打印流
            printWriter=new PrintWriter(outputStream);
            printWriter.print(returnCode);
            printWriter.flush();
            socket.shutdownOutput();//关闭输出流
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//关闭资源
			try {
				if(printWriter!=null){
					printWriter.close();
				}
				if(outputStream!=null){
					outputStream.close();
				}
				if(bufferedReader!=null){
					bufferedReader.close();
				}
				if(inputStream!=null){
					inputStream.close();
				}
				if(socket!=null){
					//socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
		}
	}
	
	
	/**
	 * 接收tcp 带线程
	 * @param returnCode 返回内容
	 * @param port 端口号
	 * */
	public static void receiveTcpThread(int port,String returnCode){
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket=new ServerSocket(port);
			System.out.println("服务端已启动，等待客户端连接..");
			//加入线程池
			ExecutorService pool = Executors.newFixedThreadPool(10);
			
			while (true) {
			    // 侦听并接受到此套接字的连接,返回一个Socket对象
                Socket socket = serverSocket.accept();
                SocketThread socketThread = new SocketThread(socket,returnCode);
                pool.execute(socketThread);
                //socketThread.start();
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
