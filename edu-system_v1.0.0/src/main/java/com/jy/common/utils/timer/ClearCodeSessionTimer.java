package com.jy.common.utils.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClearCodeSessionTimer
{
    public static Map<String, Object> SMS_CODE_MAP = new HashMap<String, Object>();
    private static Map<String, Object> SMS_CODE_DATE = new HashMap<String, Object>();
    public static Map<String, Object> SMS_CODE_DATE_SS = new HashMap<String, Object>();
    
    private static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    
    private static final Logger LOG = LoggerFactory.getLogger(ClearCodeSessionTimer.class);
    
    public ClearCodeSessionTimer(String mobile, String code){
        
        Date date = new Date();
        
        SMS_CODE_MAP.put(mobile, code);
        
        SMS_CODE_DATE_SS.put(mobile, date);
        
        String mobiles = (String) SMS_CODE_DATE.get(String.valueOf(date.getTime()));
        if (null == mobiles) {
            SMS_CODE_DATE.put(String.valueOf(date.getTime()), mobile);
        } else {
            mobiles += (";" + mobile);
        }
    }
    
    
    public ClearCodeSessionTimer() {
        
    }
    
    public static void addMobile(String mobile, String code){
        Date date = new Date();
        SMS_CODE_MAP.put(mobile, code);
        // 由于考虑数据偏大，故采用分钟点放置code
        String mobiles = (String) SMS_CODE_DATE.get(SDF_YYYY_MM_DD_HH_MM.format(date));
        if (null == mobiles) {
            SMS_CODE_DATE.put(SDF_YYYY_MM_DD_HH_MM.format(date), mobile);
        } else {
            mobiles += (";" + mobile);
        }
        SMS_CODE_DATE_SS.put(mobile, date);
    }
    
    public void start(){
        LOG.debug("清除短信验证码方法开始");
        Timer timer = new Timer();
        timer.schedule(new ClearCodeTask(), 0, (1 * 60 * 1000));
    }
    
    static class ClearCodeTask extends TimerTask {
        
        @Override
        public void run()
        {
            Date currentDate = new Date();
            
//            LOG.debug(currentDate +"清除短信验证码方法开始");
            // 删除code
            Date date = new Date(currentDate.getTime() - (5 * 60 * 1000));
            String mobiles = (String) SMS_CODE_DATE.get(SDF_YYYY_MM_DD_HH_MM.format(date));
            if (null != mobiles){
                String[] mobiless = mobiles.split(";");
                for (String mobile : mobiless){
                    String code = (String) SMS_CODE_MAP.get(mobile);
                    if (null != code) {
                        SMS_CODE_MAP.remove(mobile);
                        LOG.debug("清除" + mobile + "的验证码");
                    }
                }
            }
            
            SMS_CODE_DATE.remove(SDF_YYYY_MM_DD_HH_MM.format(date));
//            LOG.debug("清除" + SDF_YYYY_MM_DD_HH_MM.format(date) + "时间的验证码完成");
        }
        
    }
}
