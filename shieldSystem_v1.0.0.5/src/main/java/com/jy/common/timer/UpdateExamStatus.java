package com.jy.common.timer;

//import com.jy.common.timer.timerWork.UpdateExamStatusTimer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Create by wb
 * TIME: 2018/4/3 15:32
 **/
@Component
public class UpdateExamStatus implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            //new UpdateExamStatusTimer(event.getApplicationContext()).start();
        }
    }
}
