package com.jy.moudles.exam.util;

import com.jy.moudles.device.entity.Device;
import com.jy.moudles.device.service.DeviceService;
import com.jy.protocol.jry.utils.JryUdpUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

/**
 * Create by wb
 * TIME: 2018/3/29 9:27
 **/
public class ShieldSwitchUtils4Exam {

    private static Map<String, Timer> timerMap = new HashMap<>();

    /*public static void openAllShields(String name, Date startTime){
        Timer timer = new Timer(name + "-start");
        timer.schedule(new SendComandTask(1), startTime);
    }

    public static void closeAllShields(String name, Date endTime){
        Timer timer = new Timer(name + "-end");
        timer.schedule(new SendComandTask(0), endTime);
    }*/

    /**
     * 根据开始时间,结束时间开启定时器,并将定时器放入缓存
     */
    public static void openTimer(String name, Date startTime, Date endTime){
        Timer openTimer = new Timer(name + "-start");
        openTimer.schedule(new SendComandTask(1), startTime);
        Timer closeTimer = new Timer(name + "-end");
        closeTimer.schedule(new SendComandTask(0), endTime);
        timerMap.put(name + "-start", openTimer);
        timerMap.put(name + "-end", closeTimer);
    }

    /**
     * @param name
     */
    public static void closeTimer(String name){
        Timer openTimer = timerMap.get(name + "-start");
        Timer closeTimer = timerMap.get(name + "-end");
        if(openTimer != null){
            openTimer.cancel();
            openTimer.purge();
        }
        if(closeTimer != null){
            closeTimer.cancel();
            closeTimer.purge();
        }
    }

    static class SendComandTask extends TimerTask{

        /**
         * type: 0-关, 1-开
         */
        private int poweroff;

        public int getPoweroff() {
            return poweroff;
        }

        public void setPoweroff(int poweroff) {
            this.poweroff = poweroff;
        }

        public SendComandTask(int poweroff){
            this.setPoweroff(poweroff);
        }

        @Override
        public void run() {
            WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
            DeviceService deviceService = context.getBean(DeviceService.class);
            Map<String, Object> filter = new HashMap<>();
            filter.put("type", 2);
            filter.put("status", 1);
            List<Device> devices = deviceService.queryDevicesFilter(filter);
            if(devices != null && devices.size() > 0){
                for(Device device: devices){
                    if(device != null && StringUtils.isNotBlank(device.getIp())){
                        JryUdpUtils.poweroff(device.getIp(), this.getPoweroff() + "");
                    }
                }
            }
        }
    }
}
