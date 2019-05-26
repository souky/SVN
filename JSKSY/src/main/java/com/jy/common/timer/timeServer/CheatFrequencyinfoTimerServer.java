package com.jy.common.timer.timeServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationContext;

import com.jy.common.timer.constant.JSKSYTestUrl;
import com.jy.common.utils.https.HttpUtils;
import com.jy.common.utils.https.RequestPost;
import com.jy.common.utils.https.ResponsePost;
import com.jy.moudles.cheatFrequencyinfo.entity.CheatFrequencyinfo;
import com.jy.moudles.cheatFrequencyinfo.service.CheatFrequencyinfoService;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;
import com.jy.moudles.examSeason.entity.ExamSeason;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

public class CheatFrequencyinfoTimerServer {

	public ApplicationContext context;

	public long dateOffset;

	public CheatFrequencyinfoTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 5; // 5分钟
	}

	public void start() {
		Timer timer = new Timer("setCheatFrequencyinfo");
		timer.schedule(new setCheatFrequencyinfo(), 1000 * 5, dateOffset);
	}

	class setCheatFrequencyinfo extends TimerTask {

		@Override
		public void run() {
			ExamService examService = context.getBean(ExamService.class);
			CheatFrequencyinfoService cheatFrequencyinfoService = context.getBean(CheatFrequencyinfoService.class);
			
			String OrgCode = AccessTokenUtil.getOrgCode();
			String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();

			RequestPost requestPost = new RequestPost();
			Map<Object, Object> certification = new HashMap<>();
			certification.put("AccessToken", AccessTokenUtil.getAccessToken());
			Map<Object, Object> data = new HashMap<>();
			data.put("OrgCode", OrgCode);
			data.put("OrgIdenCode", OrgIdenCode);
			
			List<Exam> lists = examService.queryAllExamWithSeason();
			if(null != lists && lists.size() > 0) {
				for(Exam e : lists) {
					data.put("ExamPlan", e.getKsbh());	//考试计划编号
					List<ExamSeason> list = e.getCc();	//考试场次合集
					for(ExamSeason ls : list) {
						data.put("ExamSession", ls.getCcbh());	//考试场次序号
						data.put("ExamPlan", e.getKsbh());
						
						Map<String, Object> Filter = new HashMap<>();
						Filter.put("examNo", e.getKsbh());
						Filter.put("examSeasonNo", ls.getCcbh());
						
						List<CheatFrequencyinfo> listCheat = cheatFrequencyinfoService.queryCheatFrequencyinfosFilter(Filter);
						
						
						if(null != listCheat && listCheat.size() > 0){
							List<Map<Object, Object>> ValidateInfo = new ArrayList<Map<Object, Object>>();
							for(CheatFrequencyinfo d : listCheat) {
								
								Map<Object, Object> map = new HashMap<>();
								map.put("ID",d.getId());
								map.put("PL",d.getPl());
								map.put("QD",d.getQd());
								map.put("XXLX",d.getXxlx());
								map.put("XHLX",d.getXhlx());
								map.put("QRBJ",d.getQrbj());
								map.put("WJM",d.getWjm());
								map.put("WJ",d.getWj());
								map.put("SBBH",d.getSbbh());
								map.put("SBCSDM",d.getSbcsdm());
								ValidateInfo.add(map);
							}
							
							data.put("ValidateInfo", ValidateInfo);
							requestPost.setCertification(certification);
							requestPost.setData(data);
							ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETCHEATFREQUENCYINFO, requestPost);

							boolean result = responsePost.getResult();
							System.out.println("result=" + result);
							if (result) {
								System.out.println("提交成功");
							} else {
								String msg = responsePost.getMessage();
								String code = responsePost.getCode();
								System.out.println("提交出错-Message：" + msg + ";Code:" + code);
							}
						}
						
						
					}
				}
			}
			
			

		}

	}
}
