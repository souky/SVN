package com.jy.common.timer.timeServer;

import java.util.ArrayList;
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
import com.jy.moudles.examPlaceInfo.entity.ExamPlaceInfo;
import com.jy.moudles.examPlaceInfo.service.ExamPlaceInfoService;
import com.jy.moudles.examSeason.entity.ExamSeason;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

public class ExamPlaceInfoTimerServer {

	public ApplicationContext context;
	
	public long dateOffset;
	
	public ExamPlaceInfoTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10;
		//this.dateOffset = 1000 * 20;
	}
	
	public void start(){
        Timer timer = new Timer("getExamPlaceInfo");
        timer.schedule(new getExamPlaceInfo(), 1000 * 5, dateOffset);
    }
	
	class getExamPlaceInfo extends TimerTask{

			@Override
			public void run() {
				System.out.println("ExamPlaceInfo 开始");
				ExamService examService = context.getBean(ExamService.class);
				ExamPlaceInfoService ExamPlaceInfoService = context.getBean(ExamPlaceInfoService.class);
				
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
					for(Exam e : lists) {
						data.put("ExamPlan", e.getKsbh());
						List<ExamSeason> listSeanson = e.getCc();
						if(null != listSeanson && listSeanson.size() > 0) {
							//shanchu 
							ExamPlaceInfoService.deleteExamPlaceInfoByExamNo(e.getKsbh());
							for(ExamSeason ee : listSeanson) {
								data.put("ExamSession", ee.getCcbh());
								requestPost.setData(data);
								ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL+JSKSYTestUrl.GETALLPLACEINFOENABLE, requestPost);
								String datas = responsePost.getData();
								if(StringUtils.isNotBlank(datas)) {
									List<ExamPlaceInfo> examPlaceInfos = JSON.parseArray(datas, ExamPlaceInfo.class);
									List<ExamPlaceInfo> examPlaceInfosNew = new ArrayList<>();
									if(null != examPlaceInfos && examPlaceInfos.size() > 0) {
										for(ExamPlaceInfo ep : examPlaceInfos) {
											List<Map<String,Object>> sblist = ep.getSb();
											if(null != sblist && sblist.size() > 0) {
												for(Map<String,Object> m : sblist) {
													ExamPlaceInfo ExamPlaceInfo = new ExamPlaceInfo();
													ExamPlaceInfo.setCsbh(ep.getCsbh());
													ExamPlaceInfo.setSbbh((String)m.get("sbbh"));
													ExamPlaceInfo.setExamNo(e.getKsbh());
													ExamPlaceInfo.setExamSeasonNo(ee.getCcbh());
													ExamPlaceInfo.setId(UUIDUtil.get32UUID());
													examPlaceInfosNew.add(ExamPlaceInfo);
												}
											}
										}
										ExamPlaceInfoService.insertExamPlaceInfos(examPlaceInfosNew);
									}
									
								}else {
									System.out.println("未获场所设备信息");
								}
							}
						}
					}
				}
				System.out.println("ExamPlaceInfo 结束");
				
			}
	    	
    }
}
