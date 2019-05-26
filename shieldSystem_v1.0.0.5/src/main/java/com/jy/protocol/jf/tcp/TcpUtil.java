package com.jy.protocol.jf.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.jy.protocol.common.constant.ParamStatic;
import com.jy.protocol.jf.utils.IssuedSendUtils;
import com.jy.protocol.jf.utils.TypeConversionUtils;

public class TcpUtil {

    private static Socket socket = null;

    /**
     * 发送tcp
     *
     * @param ip   需要发送的ip的地址
     * @param code 发送的内容
     * @param port 端口号
     */
    public static void sendTcp(String ip, String code, int port) {

        Socket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            //创建Socket对象
            socket = new Socket(ip, port);

            //根据输入输出流和服务端连接
            //获取一个输出流，向服务端发送信息
            outputStream = socket.getOutputStream();
            //将输出流包装成打印流
            outputStream.write(TypeConversionUtils.hexStringToBytes(code));

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public static void shutdownTcp() {
        if (socket != null) {
            try {
                socket.close();
                ParamStatic.issuedStaticMap.put(IssuedSendUtils.HEART_BREATH, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
