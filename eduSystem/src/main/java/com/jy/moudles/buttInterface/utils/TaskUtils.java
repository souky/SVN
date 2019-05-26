package com.jy.moudles.buttInterface.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jy.moudles.buttInterface.service.ButtInterfaceService;

@Component
public class TaskUtils {
	@Autowired
	private ButtInterfaceService buttInterfaceService;
	
	@Scheduled(cron="0 */1 * * * ?") //间隔5秒执行  
    public void taskCycle(){
		try {
			buttInterfaceService.updateExamStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }  
}
