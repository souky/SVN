package com.jy.common.timer.timerWork;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.jy.common.schedule.ScheduleUtils;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;

/**
 * Create by wb
 * TIME: 2018/4/3 15:32
 **/
public class UpdateExamStatusTimer {

    private static final Logger logger = LoggerFactory.getLogger(UpdateExamStatusTimer.class);

    private static ApplicationContext context;

    @SuppressWarnings("static-access")
	public UpdateExamStatusTimer(ApplicationContext context) {
        this.context = context;
    }

    public void start(){
        logger.debug("更改考试计划状态");
        ScheduleUtils.executeTimer(() -> {
        	System.out.println("start to update exam status");
        	ExamService examService = context.getBean(ExamService.class);
            List<Exam> exams = examService.queryExamToSend(null);
            if(exams != null && exams.size() > 0){
                Date now = new Date();
                for(Exam exam : exams){
                    Date startTime = exam.getStartTime();
                    Date endTime = exam.getEndTime();
                    if(now.after(startTime) && now.before(endTime) && exam.getStatus() == 2){
                        exam.setStatus(1);
                        examService.updateExam(exam);
                    }else if((now.before(startTime) || now.after(endTime)) && exam.getStatus() == 1){
                        exam.setStatus(2);
                        examService.updateExam(exam);
                    }
                }
            }
        }, 10, 30);
//        Timer timer = new Timer("update-exam-status");
//        timer.scheduleAtFixedRate(new ChangeExamStatusTask(), 1000 * 10, (1000 * 30));
    }

    class ChangeExamStatusTask extends TimerTask {

        @Override
        public void run() {
            //更改考试计划状态
            ExamService examService = context.getBean(ExamService.class);
            List<Exam> exams = examService.queryExamToSend(null);
            if(exams != null && exams.size() > 0){
                Date now = new Date();
                for(Exam exam : exams){
                    Date startTime = exam.getStartTime();
                    Date endTime = exam.getEndTime();
                    if(now.after(startTime) && now.before(endTime) && exam.getStatus() == 2){
                        exam.setStatus(1);
                        examService.updateExam(exam);
                    }else if((now.before(startTime) || now.after(endTime)) && exam.getStatus() == 1){
                        exam.setStatus(2);
                        examService.updateExam(exam);
                    }
                }
            }
        }
    }
}
