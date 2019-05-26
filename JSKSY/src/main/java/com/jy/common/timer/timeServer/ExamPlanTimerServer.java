package com.jy.common.timer.timeServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.jy.common.timer.constant.JSKSYTestUrl;
import com.jy.common.utils.UUIDUtil;
import com.jy.common.utils.https.HttpUtils;
import com.jy.common.utils.https.RequestPost;
import com.jy.common.utils.https.ResponsePost;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;
import com.jy.moudles.examSeason.entity.ExamSeason;
import com.jy.moudles.examSeason.service.ExamSeasonService;
import com.jy.moudles.examSeasonSubject.entity.ExamSeasonSubject;
import com.jy.moudles.examSeasonSubject.service.ExamSeasonSubjectService;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

public class ExamPlanTimerServer {

	public ApplicationContext context;
	
	public long dateOffset;
	
	public ExamPlanTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10;
		//this.dateOffset = 1000 * 20;
	}
	
	public void start(){
        Timer timer = new Timer("getAllExamPlans");
        timer.schedule(new getAllExamPlans(), 1000 * 5, dateOffset);
    }
	
	class getAllExamPlans extends TimerTask{

			@Override
			public void run() {
				ExamService examService = context.getBean(ExamService.class);
				ExamSeasonService examSeasonService = context.getBean(ExamSeasonService.class);
				ExamSeasonSubjectService examSeasonSubjectService = context.getBean(ExamSeasonSubjectService.class); 
				
				String OrgCode = AccessTokenUtil.getOrgCode();
				String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();
				
				RequestPost requestPost = new RequestPost();
				Map<Object,Object> certification = new HashMap<>();
				certification.put("AccessToken", AccessTokenUtil.getAccessToken());
				Map<Object,Object> data = new HashMap<>();
				data.put("OrgCode", OrgCode);
				data.put("OrgIdenCode", OrgIdenCode);
				requestPost.setCertification(certification);
				requestPost.setData(data);
				ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL+JSKSYTestUrl.GETALLEXAMPLAN, requestPost);
				
				String datas = responsePost.getData();
				System.out.println("AllExamPlansdatas="+datas);
				if(StringUtils.isNotBlank(datas)) {
					List<Exam> exams = JSON.parseArray(datas, Exam.class);
					if(null != exams && exams.size() > 0) {
						for(Exam exam : exams) {
							Exam queryExam = examService.getExamByNo(exam.getKsbh());
							if(null == queryExam) {
								// 新增操作
								exam.setId(UUIDUtil.get32UUID());
								examService.insertExam(exam);
							}else {
								// 更新操作
								exam.setId(queryExam.getId());
								examService.updateExam(exam);
							}
							
							// 新增考试场次
							List<ExamSeason> listSeason = exam.getCc();
							
							if(null != listSeason && listSeason.size() > 0) {
								for(ExamSeason e : listSeason) {
									e.setExamNo(exam.getKsbh());
									e.setExamId(exam.getId());
									e.setId(UUIDUtil.get32UUID());
								}
								examSeasonService.deleteExamSeasonByExamNo(exam.getKsbh());
								examSeasonService.insertExamSeasons(listSeason);
								
								// 新增科目
								for(ExamSeason e : listSeason) {
									List<ExamSeasonSubject> listSubject = e.getKm();
									if(null != listSubject && listSubject.size() > 0) {
										for(ExamSeasonSubject es : listSubject) {
											es.setExamSeasonId(e.getId());
											es.setExamSeasonNo(e.getCcbh());
											es.setId(UUIDUtil.get32UUID());
										}
										examSeasonSubjectService.deleteExamSeasonSubjectBySeasonNo(e.getCcbh());
										examSeasonSubjectService.insertExamSeasonSubjects(listSubject);
									}
								}
							}
						}
					}
					
					
				}else {
					System.out.println("未获取到机构信息");
				}
				
			}
	    	
    }
}
