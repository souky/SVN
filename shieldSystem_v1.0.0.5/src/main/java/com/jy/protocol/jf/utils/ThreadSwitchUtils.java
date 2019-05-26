package com.jy.protocol.jf.utils;


import com.jy.protocol.common.constant.ParamStatic;
import com.jy.protocol.common.listener.IssuedListener;
import com.jy.protocol.jf.tcp.TcpUtil;
import com.jy.protocol.jf.thread.RegisterThread;

/**
 * @author wb
 * 开关线程
 */
public class ThreadSwitchUtils {
	
	/**
	 * 关闭服务的线程
	 */
	public static void closeServerThread() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();  
		int threads = group.activeCount();
		Thread[] lstThreads = new Thread[threads];
		group.enumerate(lstThreads);
		for(Thread thread : lstThreads) {
            System.out.println(thread.getName());
		}
		//断开注册tcp协议
		ParamStatic.issuedStaticMap.put("flag", false);
		TcpUtil.shutdownTcp();
	}
	
	/**
	 * 开启服务的线程
	 */
	public static void startServerThread() {

		ThreadGroup group = Thread.currentThread().getThreadGroup();
		int threads = group.activeCount();
		Thread[] lstThreads = new Thread[threads];
		group.enumerate(lstThreads);
		boolean flagReg = false;

		for(Thread thread : lstThreads){
			if(IssuedListener.REGISTER.equals(thread.getName())) {
				ParamStatic.issuedStaticMap.put("flag", true);
				flagReg = true;
			}
		}
		if(!flagReg){
			Thread registerThread = new RegisterThread(IssuedListener.REGISTER);
			if(null != registerThread) {
                ParamStatic.issuedStaticMap.put("flag", true);
                registerThread.start();
            }
        }
	}

}
