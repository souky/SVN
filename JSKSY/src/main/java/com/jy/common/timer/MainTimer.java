package com.jy.common.timer;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.jy.common.timer.timeServer.AccessTokenTimerServer;
import com.jy.common.timer.timeServer.AllCodeTimerServer;
import com.jy.common.timer.timeServer.CameraStatusTimerServer;
import com.jy.common.timer.timeServer.CheatDeivceStatusTimerServer;
import com.jy.common.timer.timeServer.CheatFrequencyinfoTimerServer;
import com.jy.common.timer.timeServer.DeviceInfoTimerServer;
import com.jy.common.timer.timeServer.ExamOrgInfoTimerServer;
import com.jy.common.timer.timeServer.ExamPlaceInfoTimerServer;
import com.jy.common.timer.timeServer.ExamPlanTimerServer;
import com.jy.common.timer.timeServer.NvrStatusTimerServer;
import com.jy.common.timer.timeServer.OrganizationTimerServer;
import com.jy.common.timer.timeServer.SipAccountInfoTimerServer;
import com.jy.common.timer.timeServer.SipStatusTimerServer;

@Component("BeanDefineConfigue") 
public class MainTimer implements ApplicationListener<ContextRefreshedEvent>{

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() == null){
            new AccessTokenTimerServer(event.getApplicationContext()).start();
            new AllCodeTimerServer(event.getApplicationContext()).start();
            new OrganizationTimerServer(event.getApplicationContext()).start();
            new ExamPlanTimerServer(event.getApplicationContext()).start();
            
            new SipAccountInfoTimerServer(event.getApplicationContext()).start();
            new ExamOrgInfoTimerServer(event.getApplicationContext()).start();
            new ExamPlaceInfoTimerServer(event.getApplicationContext()).start();
            
            new DeviceInfoTimerServer(event.getApplicationContext()).start();
            new SipStatusTimerServer(event.getApplicationContext()).start();
            new NvrStatusTimerServer(event.getApplicationContext()).start();
            new CameraStatusTimerServer(event.getApplicationContext()).start();
            
            new CheatDeivceStatusTimerServer(event.getApplicationContext()).start();
            new CheatFrequencyinfoTimerServer(event.getApplicationContext()).start();
            
            
		}
	}
}
