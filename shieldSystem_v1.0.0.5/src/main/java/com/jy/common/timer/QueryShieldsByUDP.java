package com.jy.common.timer;

//import com.jy.common.timer.timerWork.QueryShieldsByUDPTimer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by wb on 2018/3/15.
 */
@Component
public class QueryShieldsByUDP implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            //new QueryShieldsByUDPTimer().start();
        }
    }

}
