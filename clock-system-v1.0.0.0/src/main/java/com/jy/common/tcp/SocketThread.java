package com.jy.common.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.jy.common.utils.TypeConversionUtils;

public class SocketThread extends Thread{
	
	
	private Socket socket;
	private Object returnCode;
	
    public SocketThread(Socket socket,String returnCode) {
        this.socket = socket;
        this.returnCode = returnCode;
    }
    
	@Override
	public void run() {
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;
		OutputStream outputStream = null;
		PrintWriter printWriter = null;
		
        try {
			//根据输入输出流和客户端连接
			//得到一个输入流，接收客户端传递的信息
            inputStream=socket.getInputStream();
            int count = 0;
            while(count == 0) {
            	count = inputStream.available();
            }
            byte[] b = new byte[count];
            inputStream.read(b);
            
            String info = new String(b);
            
            System.out.println("已接收到客户端连接");
            System.out.println("服务端接收到客户端信息："+info+",当前客户端ip为："+socket.getInetAddress().getHostAddress());
            //获取一个输出流，向服务端发送信息
            outputStream=socket.getOutputStream();
            outputStream.write(TypeConversionUtils.hexStringToBytes("A5f00b00000001f0f087"));
            socket.shutdownOutput();//关闭输出流
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(printWriter!=null){
				//printWriter.close();
			}
			if(outputStream!=null){
				//outputStream.close();
			}
			if(bufferedReader!=null){
				//bufferedReader.close();
			}
			if(inputStream!=null){
				//inputStream.close();
			}
			if(socket!=null){
				//socket.close();
			}
           
		}
	}

}
