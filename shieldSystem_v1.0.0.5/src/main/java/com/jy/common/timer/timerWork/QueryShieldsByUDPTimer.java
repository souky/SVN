package com.jy.common.timer.timerWork;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.common.schedule.ScheduleUtils;
import com.jy.protocol.jry.utils.JryUdpUtils;

/**
 * Created by wb on 2018/3/15.
 */
public class QueryShieldsByUDPTimer {

    private static final Logger logger = LoggerFactory.getLogger(QueryShieldsByUDPTimer.class);

    public void start(){
        logger.info("udp获取设备信息");
        
        ScheduleUtils.executeTimer(() -> JryUdpUtils.queryBaseInfo("255.255.255.255"), 10, 5);
//        Timer timer = new Timer("query shields by udp");
//        timer.schedule(new QueryShieldsByUDPTask(), (1000 * 10), (1000 * 5));
    }

    class QueryShieldsByUDPTask extends TimerTask{
        @Override
        public void run() {
            JryUdpUtils.queryBaseInfo("255.255.255.255");
        }
    }

}
