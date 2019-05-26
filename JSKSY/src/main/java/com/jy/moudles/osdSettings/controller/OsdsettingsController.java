package com.jy.moudles.osdSettings.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jy.common.timer.constant.JSKSYTestUrl;
import com.jy.common.utils.UUIDUtil;
import com.jy.common.utils.https.HttpUtils;
import com.jy.common.utils.https.RequestPost;
import com.jy.common.utils.https.ResponsePost;
import com.jy.moudles.exam.entity.Exam;
import com.jy.moudles.exam.service.ExamService;
import com.jy.moudles.examSeason.entity.ExamSeason;
import com.jy.moudles.osdSettings.entity.OsdSettings;
import com.jy.moudles.osdSettings.service.OsdSettingsService;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

@Controller
public class OsdsettingsController {
	public ApplicationContext context;
	
	@Autowired
	private OsdSettingsService OsdSettingsService;
	
	@Autowired
	private ExamService examService;
	
	/**
	 * 提交OSD设置结果信息
	 * @throws Exception
	 * http://localhost:8080/JSKSY/osdset/sendOsdSetresult
	 */
	@RequestMapping(value = "/osdset/osdSetresult")
	public  void osdSetresult() throws Exception{
		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", "DCB3C0C2511F11E88E38000C29FEF75A|d94b732d8b726f92a70d761d0bde0aa2");
		Map<Object, Object> data = new HashMap<>();
		data.put("ExamPlan", "20181009"); // 考试计划编号
		data.put("ExamSession", "1"); // 考试场次序号
		List<Map<Object, Object>> OSDStatus = new ArrayList<Map<Object, Object>>();
		Map<Object, Object> sta = new HashMap<Object, Object>();
		sta.put("SBYM", "tzzxxxq.tzgq.jsjy.cnjy"); // 网上巡查设备域名
		sta.put("OSD", "TSosd"); // OSD设置成功后，OSD的真实内容
		sta.put("SZZT", "1");
		sta.put("SZXX", "SUCCESS");
		OSDStatus.add(sta);
		data.put("OSDStatus", OSDStatus);
		requestPost.setCertification(certification);
		requestPost.setData(data);
		ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEOSDSETTINGSSTATUS, requestPost);
		System.out.println(responsePost.getResult());

	}
	
	@RequestMapping(value = "/osdset/sendOsdSetresult")
	public @ResponseBody Object sendOsdSetresult() throws Exception{
		System.out.println("提交OSD设置结果信息开始");
		JSONObject obj = new JSONObject();
		int total = 0;
		int errorNum = 0;
		String errorStr = "";
		
		String OrgCode = AccessTokenUtil.getOrgCode();
		String OrgIdenCode = AccessTokenUtil.getOrgIdenCode();

		RequestPost requestPost = new RequestPost();
		Map<Object, Object> certification = new HashMap<>();
		certification.put("AccessToken", AccessTokenUtil.getAccessToken());
		Map<Object, Object> data = new HashMap<>();
		List<Exam> lists = examService.queryAllExamWithSeason();
		if(null != lists && lists.size() > 0) {
			total = lists.size();
			for(Exam e : lists) {
				data.put("ExamPlan", e.getKsbh());	//考试计划编号
				List<ExamSeason> list = e.getCc();	//考试场次合集
				for(ExamSeason ls : list) {
					data.put("ExamSession", ls.getCcbh());	//考试场次序号
					
					List<Map<Object, Object>> OSDStatus = new ArrayList<Map<Object, Object>>();
					
					Map<String, Object> Filter = new HashMap<>();
					Filter.put("OrgCode", OrgCode);
					Filter.put("OrgIdenCode", OrgIdenCode);
					Filter.put("ExamPlan", e.getKsbh());
					Filter.put("ExamSession", ls.getCcbh());
					
					List<OsdSettings> list2 = OsdSettingsService.queryOsdSettingsFilter(Filter);
					if(list2!=null&&list2.size()>0){
						for(OsdSettings ls2:list2){
							Map<Object, Object> sta = new HashMap<Object, Object>();
							sta.put("SBYM", ls2.getSbym());	//网上巡查设备域名
							sta.put("OSD", ls2.getOsd());	//OSD设置成功后，OSD的真实内容
							int status = Integer.valueOf(ls2.getSetstatus());
							if(status==1){	// 设置状态。-1：表示设置不成功  1：表示设置成功
								sta.put("SZZT", "1");
								sta.put("SZXX", "设置成功");
							}else{
								sta.put("SZZT", "-1");
								if(status==0){	//设置消息，如果不成功，返回不成功的原因，如设备不在线
									sta.put("SZXX", "设备不在线");	
								}else{
									sta.put("SZXX", "其他");	
								}
							}
							OSDStatus.add(sta);
						}
					}else{
						continue;
					}
					data.put("OSDStatus", OSDStatus);
					requestPost.setCertification(certification);
					requestPost.setData(data);
					ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL + JSKSYTestUrl.SETNETVIEWDEVICEOSDSETTINGSSTATUS, requestPost);

					boolean result = responsePost.getResult();
					System.out.println("result=" + result);
					if (result) {
						System.out.println("提交成功");
					} else {
						String msg = responsePost.getMessage();
						String code = responsePost.getCode();
						System.out.println("提交出错-Message：" + msg + ";Code:" + code);
						errorNum +=1;
						errorStr +="("+e.getKsbh()+"-"+ls.getCcbh()+"-"+msg+"),";
					}
					
				}
			}
		}
		if(errorNum==0){
			obj.put("result", true);
			obj.put("msg", "设置成功");
		}else{
			String resultMsg = "设置结果"+(total-errorNum)+"/"+total+"（成功数/总数），设置异常的场次为（考试编号-场次编号-错误信息）："+errorStr;
			obj.put("result", false);
			obj.put("msg", resultMsg);
		}
		System.out.println("提交OSD设置结果信息结束");
		return obj.toString();
	}
	
	
	/**
	 * OSD信息设置接口
	 * @param req
	 * @throws IOException 
	 * @throws Exception
	 * http://localhost:8080/JSKSY/osdset/osdsettings
	 */
	@RequestMapping(value = "/osdsettings", method = RequestMethod.POST)
	public @ResponseBody Object osdsettings(HttpServletRequest req, HttpServletResponse res) throws IOException{
		System.out.println("OSD信息设置接口-开始");
		JSONObject result = new JSONObject();
		String jsonData = "";
		try {
			BufferedReader reader = req.getReader();
			String input = null;
			StringBuffer requestBody = new StringBuffer();
			while((input = reader.readLine()) != null) {
				requestBody.append(input);
			}
			jsonData = requestBody.toString();
			if(jsonData!=null&&!jsonData.equals("")){
				JSONObject jObj = JSON.parseObject(jsonData);
				JSONArray jay = jObj.getJSONArray("OSDInfo");
				List<JSONObject> objlist = JSON.parseArray(jay.toString(),JSONObject.class);
				for(JSONObject ls:objlist){
					OsdSettings osd = new OsdSettings();
					osd.setOsd(ls.getString("OSD"));
					osd.setSbym(ls.getString("SBYM"));
					
					osd.setId(UUIDUtil.get32UUID());
					osd.setSetstatus("0");
					osd.setSipdlm(jObj.getString("SIPDLM"));
					osd.setSipdlmm(jObj.getString("SIPDLMM"));
					osd.setSipdz(jObj.getString("SIPDZ"));
					osd.setExamplan(jObj.getString("ExamPlan"));
					osd.setExamsession(jObj.getString("ExamSession"));
					osd.setOrgcode(jObj.getString("OrgCode"));
					osd.setOrgidencode(jObj.getString("OrgIdenCode"));
					OsdSettingsService.insert(osd);
				}
				result.put("Message", "调用成功");
				result.put("Result", true);
				result.put("Code", 10000);
			}else{
				result.put("Message", "调用失败,传入数据为空");
				result.put("Result", false);
				result.put("Code", 10001);
			}
		} catch (IOException e) {
			result.put("Message", "调用失败,程序出错");
			result.put("Result", false);
			result.put("Code", 10002);
			e.printStackTrace();
		}
		System.out.println("OSD信息设置接口-结束");
		return result.toString();
	}
	
	
	
}
