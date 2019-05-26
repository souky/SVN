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
import com.jy.moudles.examOrgInfo.entity.ExamOrgInfo;
import com.jy.moudles.examOrgInfo.service.ExamOrgInfoService;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

public class ExamOrgInfoTimerServer {

	public ApplicationContext context;
	
	public long dateOffset;
	
	public ExamOrgInfoTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10;
		//this.dateOffset = 1000 * 20;
	}
	
	public void start(){
        Timer timer = new Timer("getExamOrgInfo");
        timer.schedule(new getExamOrgInfo(), 1000 * 5, dateOffset);
    }
	
	class getExamOrgInfo extends TimerTask{

			@Override
			public void run() {
				ExamService examService = context.getBean(ExamService.class);
				ExamOrgInfoService ExamOrgInfoService = context.getBean(ExamOrgInfoService.class);
				
				String OrgCode = AccessTokenUtil.getOrgCode();
				String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();
				
				RequestPost requestPost = new RequestPost();
				Map<Object,Object> certification = new HashMap<>();
				certification.put("AccessToken", AccessTokenUtil.getAccessToken());
				Map<Object,Object> data = new HashMap<>();
				data.put("OrgCode", OrgCode);
				data.put("OrgIdenCode", OrgIdenCode);
				requestPost.setCertification(certification);
				
				List<Exam> lists = examService.queryAllExamWithSeason();
				if(null != lists && lists.size() > 0) {
					ExamOrgInfoService.deleteExamOrgInfos();
					for(Exam e : lists) {
						data.put("ExamPlan", e.getKsbh());
						
						requestPost.setData(data);
						ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL+JSKSYTestUrl.GETALLORGINFOENABLE, requestPost);
						String datas = responsePost.getData();
						if(StringUtils.isNotBlank(datas)) {
							List<ExamOrgInfo> examOrgInfos = JSON.parseArray(datas, ExamOrgInfo.class); 
							if(null != examOrgInfos && examOrgInfos.size() > 0) {
								for(ExamOrgInfo org:examOrgInfos){
									org.setId(UUIDUtil.get32UUID());
									org.setExamNo(e.getKsbh());
								}
								ExamOrgInfoService.insertExamOrgInfos(examOrgInfos);
							}
						}else {
							System.out.println("未获考试机构信息");
						}
						
						
					}
				}
				
			}
	    	
    }
}
