package com.jy.moudles.buttInterface.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jy.common.utils.UUIDUtil;

/**
 * 配置的参数
 * @author Administrator
 *
 */
public class Constant {
	/**商户密钥**/
	public static final String PRIVATE_KEY = "xiIWmGI3ftKPDqdlaIsK5g";
	
	/**生成token密钥**/
	public static final String TOKEN_KEY = "KDDSLSSWD1KL99KDD";
	/**对接地址**/
	public static final String URL = "http://d57dyk.natappfree.cc";
	
	public static Map<String,String> gradeMap = new HashMap<String, String>();
	 static {
		 gradeMap.put("1", "一年级");
		 gradeMap.put("2", "二年级");
		 gradeMap.put("3", "三年级");
		 gradeMap.put("4", "四年级");
		 gradeMap.put("5", "五年级");
		 gradeMap.put("6", "六年级");
		 gradeMap.put("7", "七年级");
		 gradeMap.put("8", "八年级");
		 gradeMap.put("9", "九年级");
		 gradeMap.put("10", "高一");
		 gradeMap.put("11", "高二");
		 gradeMap.put("12", "高三");
	 }
	 
	 public static Map<String,String> gradeTMap = new HashMap<String, String>();
	 static {
		 gradeTMap.put("一年级", "1");
		 gradeTMap.put("二年级", "2");
		 gradeTMap.put("三年级", "3");
		 gradeTMap.put("四年级", "4");
		 gradeTMap.put("五年级", "5");
		 gradeTMap.put("六年级", "6");
		 gradeTMap.put("七年级", "7");
		 gradeTMap.put("八年级", "8");
		 gradeTMap.put("九年级", "9");
		 gradeTMap.put("高一", "10");
		 gradeTMap.put("高二", "11");
		 gradeTMap.put("高三", "12");
	 }
	 
	 /**
	  * 接收score的时候 给没有的值赋值
	  * @param map
	  * @return
	  * @author 黄忠柳
	  * Create on 2018年1月31日 下午9:23:55
	  */
	 public static Map<String,Object> changeScore(Map<String,Object> map) throws Exception{
		 	//如果为空值 移除掉 然后等待抛异常
		 	if (DataUtils.isNull(map.get("examId"))) {
		 		throw new Exception("必要参数为空"); 
		 	}
		 	
		 	if (DataUtils.isNull(map.get("studentId"))) {
		 		throw new Exception("必要参数为空");
		 	}
		 	
		 	if (DataUtils.isNull(map.get("orgId"))) {
		 		throw new Exception("必要参数为空");
		 	}
		 	
		 	if (DataUtils.isNull(map.get("schoolId"))) {
		 		throw new Exception("必要参数为空");
		 	}
		 	
		 	if (DataUtils.isNull(map.get("totalScore"))) {
		 		throw new Exception("必要参数为空");
		 	}
		 	
		 	if (DataUtils.isNull(map.get("detailScore"))) {
		 		throw new Exception("必要参数为空");
		 	}
		 	
		 	
		 	if (!map.containsKey("chineseScore")) {
				map.put("chineseScore", null);
			}
			
			if (!map.containsKey("mathScore")) {
				map.put("mathScore", null);
			}
			
			if (!map.containsKey("englishScore")) {
				map.put("englishScore", null);
			}
			
			if (!map.containsKey("physicalScore")) {
				map.put("physicalScore", null);
			}
			
			if (!map.containsKey("chemicalScore")) {
				map.put("chemicalScore", null);
			}
			
			if (!map.containsKey("biologyScore")) {
				map.put("biologyScore", null);
			}
			
			if (!map.containsKey("geographyScore")) {
				map.put("geographyScore", null);
			}
			
			if (!map.containsKey("scienceScore")) {
				map.put("scienceScore", null);
			}
			
			if (!map.containsKey("politicsScore")) {
				map.put("politicsScore", null);
			}
			
			if (!map.containsKey("historyScore")) {
				map.put("historyScore", null);
			}
			
			if (!map.containsKey("moralScore")) {
				map.put("moralScore", null);
			}
			
			if (!map.containsKey("historySocietyScore")) {
				map.put("historySocietyScore", null);
			}
			
			if (!map.containsKey("qualitySocietyScore")) {
				map.put("qualitySocietyScore", null);
			}
			
			if (!map.containsKey("qualityLifeScore")) {
				map.put("qualityLifeScore", null);
			}
			
			if (!map.containsKey("paintingScore")) {
				map.put("paintingScore", null);
			}
			
			if (!map.containsKey("artScore")) {
				map.put("artScore", null);
			}
			
			if (!map.containsKey("musicScore")) {
				map.put("musicScore", null);
			}
			
			if (!map.containsKey("sportsScore")) {
				map.put("sportsScore", null);
			}
			
			return map;
	 }
	 
	 /**
	  * 处理试卷数据源
	  * @return
	  * @author 黄忠柳
	  * Create on 2018年2月1日 上午10:39:35
	  */
	 public static Map<String, List<Map<String, Object>>> handleScoreMsg (String scoreMsg) throws Exception{
		 	//放置处理过的数据
			Map<String, List<Map<String, Object>>> mapParam = new LinkedHashMap<>();
//			//单个表的数据
			List<Map<String, Object>> listParam;
			
			//存贮 jy_two_way_specification信息
			Map<String,Object> spectificationMap = new HashMap<>();
			//双向细目表id
			String specificationId = UUIDUtil.get32UUID();
			spectificationMap.put("id", specificationId);
			spectificationMap.put("createUser", "jieshou");
			spectificationMap.put("createDate", new Date());
			
			//存jy_exam_specification_relation信息
			Map<String,Object> specificationRelation = new HashMap<>();
			specificationRelation.put("id", UUIDUtil.get32UUID());
			specificationRelation.put("spId", specificationId);
			specificationRelation.put("createUser", "jieshou");
			specificationRelation.put("createDate", new Date());
			
			//转换成json
			JSON json = JSON.parseObject(scoreMsg);
			Map<String,Object> receiveParam = (Map<String, Object>) json;
			//科目编码
			String subjectCode = receiveParam.get("subjectCode").toString();
			spectificationMap.put("specificationName", subjectCode);
			spectificationMap.put("subjectCode", subjectCode);
			
			//年级编码
			String gradeCode = receiveParam.get("gradeCode").toString();
			spectificationMap.put("gradeCode", gradeCode);
			
			//组织机构id
			String orgId = receiveParam.get("orgId").toString();
			spectificationMap.put("orgId", orgId);
			
			//考试id
			String examId = receiveParam.get("examId").toString();
			specificationRelation.put("examId", examId);
			
			//存贮细目表详情的集合
			List<Map<String, Object>> specificationDetailList = new LinkedList<>();
			//存贮分步信息的集合
			List<Map<String, Object>> detailStepList = new LinkedList<>();
			
			
			//双目表详情
			String detailLis = receiveParam.get("detailList").toString();
			JSONArray array = JSONArray.parseArray(detailLis);
			
			for (Object object : array) {
				//存jy_two_way_specification_detail 单个信息
				Map<String,Object> specificationDetail = new HashMap<>();
				String specificationDetailId = UUIDUtil.get32UUID();
				specificationDetail.put("id", specificationDetailId);
				specificationDetail.put("orgId", orgId);
				specificationDetail.put("parentId", specificationId);
				specificationDetail.put("createUser", "jieshou");
				specificationDetail.put("createDate", new Date());
				
				//这个是双目表中每一题的详情
				Map<String,Object> objMap = (Map<String, Object>) object;
				//题号
				String itemNo = objMap.get("itemNo").toString();
				specificationDetail.put("itemNo", itemNo);
				
				//题目类型  0是客观题 1是主观题
				String itemType = objMap.get("itemType").toString();
				specificationDetail.put("itemType", itemType);
				
				//小题满分
				String itemScore = objMap.get("itemScore").toString();
				specificationDetail.put("itemScore", itemScore);
				
				//小题答案
				String itemAnswer = objMap.get("itemAnswer").toString();
				specificationDetail.put("itemAnswer", itemAnswer);
				//放入双目详情集合
				specificationDetailList.add(specificationDetail);
				
				//这个是小题的分步步骤  不是每一题都有分步的 所以要判断有没有分步
				if (objMap.containsKey("stepList")) {
					
					String stepList = objMap.get("stepList").toString();
					JSONArray stepArray = JSONArray.parseArray(stepList);
					
					for (Object step : stepArray) {
						//存单个分步信息
						Map<String,Object> detailStepMap = new HashMap<>();
						detailStepMap.put("id", UUIDUtil.get32UUID());
						detailStepMap.put("createUser", "jieshou");
						detailStepMap.put("createDate", new Date());
						detailStepMap.put("spDetailId", specificationDetailId);
						detailStepMap.put("spId", specificationId);
						
						Map<String,Object> stepMap = (Map<String, Object>) step;
						//分步的排序
						String sort = stepMap.get("sort").toString();
						detailStepMap.put("sort", sort);
						
						//分步的分数
						String stepScore = stepMap.get("stepScore").toString();
						detailStepMap.put("stepScore", stepScore);
						
						//分步的答案
						String stepAnswer = stepMap.get("stepAnswer").toString();
						detailStepMap.put("stepAnswer", stepAnswer);
						//把分步信息加入集合
						detailStepList.add(detailStepMap);
					}
				}
			}
			
			//双向细目表
			listParam = new LinkedList<>();
			listParam.add(spectificationMap);
			mapParam.put("specification", listParam);
			
			//考试双向细目表关系表
			listParam = new LinkedList<>();
			listParam.add(specificationRelation);
			mapParam.put("specificationRelation", listParam);
			
			//双向细目表详情
			mapParam.put("specificationDetail", specificationDetailList);
			//分步表详情
			mapParam.put("detailStep", detailStepList);
			
			return mapParam;
	 }
}
