package com.jy.common.timer.timerWork;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.jy.common.schedule.ScheduleUtils;
import com.jy.moudles.device.service.DeviceService;


public class UpdateDeviceStatusTimer {

    private static final Logger logger = LoggerFactory.getLogger(UpdateDeviceStatusTimer.class);

    private static ApplicationContext context;

    @SuppressWarnings("static-access")
	public UpdateDeviceStatusTimer(ApplicationContext context) {
        this.context = context;
    }
    
    public void start(){
        logger.debug("检测设备更新");
        ScheduleUtils.executeTimer(() -> {
        	System.out.println("start to update device status");
        	DeviceService deviceService = context.getBean(DeviceService.class);
			deviceService.updateDeviceStatusOnAgreement();
        }, 10, 5);
//        Timer timer = new Timer("update-device-status");
//        timer.schedule(new ChangeDeviceStatusTask(), 1000 * 10, (1000 * 5));
    }
    
    class ChangeDeviceStatusTask extends TimerTask{

		@Override
		public void run() {
			//更改设备状态
			DeviceService deviceService = context.getBean(DeviceService.class);
			deviceService.updateDeviceStatusOnAgreement();

		}
    	
    }
    
    
}
