package com.jy.protocol.jry.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.moudles.device.entity.Device;
import com.jy.protocol.jry.udp.SendJryUdpUtils;

/**
 * Create by wb
 * TIME: 2018/3/5 9:12
 **/
public class JryUdpUtils {

    //端口
    private final static int PORT = 1025;

    private static final Logger logger = LoggerFactory.getLogger(JryUdpUtils.class);

    //控制模块对应的线路
//    private final static String MODULE_0 = "000000000000";
//    private final static String MODULE_1 = "111111" + "000000";
//    private final static String MODULE_2 = "00000" + "11" + "00000";
//    private final static String MODULE_3 = "00000" + "111" + "0000";
//    private final static String MODULE_4 = "0000" + "1111111" + "0";
//    private final static String MODULE_5 = "000000000" + "1" + "0" + "1";
//    private final static String MODULE_6 = "111111111111";


    /**
     * 重设ip
     * Command 1:  RIP192.168.66.11#(修改 IP 为 192.168.66.11,需断电生效)
     * ANSWER:     RIP192.168.66.11#
     */
    public static void resetIP(String oldIP, String newIP) {
        logger.info("start to reset ip oldIP=" + oldIP + " and newIP=" + newIP);
        String prefix = "RIP";
        String suffix = "#";
        StringBuffer sb = new StringBuffer();
        sb.append(prefix).append(newIP).append(suffix);
        SendJryUdpUtils.sendUdp(oldIP, PORT, sb.toString());
    }

    /**
     * 查询基础信息
     * Command 2： QBCINFO(查询基础信息)
     * ANSWER:     EQVER:SS_JS_100;FWVER:FW_JS_100;DATE:20180207;IP:192.168.66.11;MAC:00C002120002;
     */
    public static void queryBaseInfo(String ip){
        logger.info("start to get device base info");
        String sendMessage = "QBCINFO";
        SendJryUdpUtils.sendUdp4Query(ip, PORT, sendMessage);
    }

    /**
     * 设备电源开关s
     * @param ip
     * @param poweroff
     */
    public static void poweroff(String ip, String poweroff){
        logger.info("start to poweroff shield ip=" + ip);
        String sendMessage = "CEQSTATUS" + poweroff;
        SendJryUdpUtils.sendUdp(ip, PORT, sendMessage);
    }

    /**
     * 发送开关命令
     * Command 4:  REQSTATUSXXXXXXXXXXXXXXXXXXXXXXXXX;(配置各路RF port工作状态,若第一位配置为0,则整个RF功能关闭，
     * 配置为1后24路 RF port 状态 0 表示关闭该路，1 表示启用该路)
     * ANSWER:     OK:XXXXXXXXXXXXXXXXXXXXXXXXX;(X:0/1;第一位表示 RF 设备总开关状态，24 路 RF port 状态 0 表示已关闭启用，1 表示成功启用)
     *
     * @param ip ip
     * @param list 1:1g,2:2g,3:3g,4:4g,5:5g,0:全部关闭,6:全开
     */
    public static void sendSwitchCommand(String ip, List<String> list) {
        logger.info("start to send switch command, ip=" + ip);
        String sendMessage = "REQSTATUS";
        StringBuffer sb = new StringBuffer();
        sb.append(sendMessage);

        if(list.contains("0")){
            sb.append("000000000000000000000000");
        }else if(list.contains("6")){
            sb.append("111111111111111111111111");
        }else{
            String moduleStr = moduleListToStr(list);
            if(StringUtils.isNotBlank(moduleStr)){
                sb.append(moduleStr);
            }
        }
        if(sb.toString().length() == 33){
            SendJryUdpUtils.sendUdp(ip, PORT, sb.toString());
        }

    }
    /**
     * 发送开关命令
     * Command 4:  REQSTATUSXXXXXXXXXXXXXXXXXXXXXXXXX;(配置各路RF port工作状态,若第一位配置为0,则整个RF功能关闭，
     * 配置为1后24路 RF port 状态 0 表示关闭该路，1 表示启用该路)
     * ANSWER:     OK:XXXXXXXXXXXXXXXXXXXXXXXXX;(X:0/1;第一位表示 RF 设备总开关状态，24 路 RF port 状态 0 表示已关闭启用，1 表示成功启用)
     *
     * @param device 开关状态  模式
     */
    public static void sendSwitchCommand_(Device device,String status,String module){
    	if(null == device) {
    		return;
    	}
    	String ip = device.getIp();
    	String src = device.getOperationType();
    	
    	logger.info("start to send switch command, ip=" + ip);
        String sendMessage = "REQSTATUS";
        StringBuffer sb = new StringBuffer();
        sb.append(sendMessage);
        sb.append(src);
        
        if(sb.indexOf("1") > 0) {
        	sb.append("111111111111");
        }else{
        	sb.append("000000000000");
        }
        if(sb.toString().length() == 33){
            SendJryUdpUtils.sendUdpWithNoRe(ip, PORT, sb.toString());
        }
    }

    //
    public static String moduleListToStr(List<String> list){
        List<String> baseList = new ArrayList<>();
        if(list != null && !list.isEmpty()){
            for(int i = 0; i < 24; i++){
                baseList.add("0");
            }
            if(list.contains("1")){
                baseList.set(0, "1");
                baseList.set(1, "1");
                baseList.set(2, "1");
                baseList.set(3, "1");
                baseList.set(4, "1");
                baseList.set(5, "1");
            }
            if(list.contains("2")){
                baseList.set(5, "1");
                baseList.set(6, "1");
            }
            if(list.contains("3")){
            	baseList.set(4, "1");
                baseList.set(5, "1");
                baseList.set(6, "1");
                baseList.set(7, "1");
                baseList.set(8, "1");
                baseList.set(9, "1");
                baseList.set(10, "1");
                baseList.set(11, "1");
            }
            
        }
        return StringUtils.join(baseList, "");
    }

}
