package com.jy.protocol.common.utils;

import com.jy.moudles.device.entity.Device;
import com.jy.protocol.common.constant.DeviceVender;
import com.jy.protocol.jf.utils.IssuedSendUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Create by wb
 * TIME: 2018/1/29 10:24
 * 发送协议通用接口,根据设备中的厂家选择调用对应的协议
 **/
public class GeneralProtocol {


    /**
     * 发送屏蔽
     * @param device
     * @param operationType 控制模块
     */
    public static void sendShieldCommand(Device device, String operationType){
        ////ip不为空,且状态在线
        if(device != null && StringUtils.isNotBlank(device.getIp()) && device.getStatus() == 1){
            if(DeviceVender.JF.equalsIgnoreCase(device.getVender())){
                //0：未知、1：自动、2：手动-全部、3：手动---1G、4：手动---手机、5：手动---未选
                if("1".equals(operationType) || "2".equals(operationType)){
                    IssuedSendUtils.shieldControllerBase(1, device.getIp(), IssuedSendUtils.SHIELD_UDP_PORT);
                    IssuedSendUtils.shieldControllerPhone(1, device.getIp(), IssuedSendUtils.SHIELD_UDP_PORT);
                }else if("3".equals(operationType)){
                    IssuedSendUtils.shieldControllerBase(1, device.getIp(), IssuedSendUtils.SHIELD_UDP_PORT);
                    IssuedSendUtils.shieldControllerPhone(0, device.getIp(), IssuedSendUtils.SHIELD_UDP_PORT);
                }else if("4".equals(operationType)){
                    IssuedSendUtils.shieldControllerBase(0, device.getIp(), IssuedSendUtils.SHIELD_UDP_PORT);
                    IssuedSendUtils.shieldControllerPhone(1, device.getIp(), IssuedSendUtils.SHIELD_UDP_PORT);
                }else{
                    IssuedSendUtils.shieldControllerBase(0, device.getIp(), IssuedSendUtils.SHIELD_UDP_PORT);
                    IssuedSendUtils.shieldControllerPhone(0, device.getIp(), IssuedSendUtils.SHIELD_UDP_PORT);
                }
            }
            if(DeviceVender.JY.equalsIgnoreCase(device.getVender())){
                //
            }
        }
    }

}
