package com.jy.moudles.knowledgePoint.controller;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.interceptor.LoginInterceptor;
import com.jy.moudles.knowledgePoint.entity.KnowledgePoint;
import com.jy.moudles.knowledgePoint.entity.KnowledgePointVO;
import com.jy.moudles.knowledgePoint.service.KnowledgePointService;
import com.jy.moudles.user.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
 * knowledgePoint实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/knowledgepoint")
public class KnowledgePointController {
	
	@Autowired
	private KnowledgePointService knowledgepointService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgePointController.class);
	
	/**
	 * 新增knowledgepoint对象
	 * 
	 * @param knowledgepoint
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveKnowledgePoint", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveKnowledgePoint(KnowledgePoint knowledgepoint) throws Exception{
		LOGGER.info("新增KnowledgePoint Start");
		
		User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}
		
		knowledgepoint.setCreateUser(user.getUserName());
		knowledgepoint.setOrgId(user.getOrgId());
		knowledgepointService.insertKnowledgePoint(knowledgepoint);
		
		LOGGER.info("新增KnowledgePoint End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改knowledgepoint对象
	 * 
	 * @param knowledgepoint
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateKnowledgePoint", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateKnowledgePoint(KnowledgePoint knowledgepoint) throws Exception{
		LOGGER.info("修改KnowledgePoint Start");

		User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}
		
		knowledgepoint.setUpdateUser(user.getUserName());
		knowledgepointService.updateKnowledgePoint(knowledgepoint);
		
		LOGGER.info("修改KnowledgePoint End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除knowledgepoint对象
	 * 
	 * @param knowledgepoint
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteKnowledgePoint", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteKnowledgePoint(KnowledgePoint knowledgepoint) throws Exception{
		LOGGER.info("删除KnowledgePoint Start");
		
		User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}
		
		knowledgepointService.deleteKnowledgePointById(knowledgepoint.getId());
		
		LOGGER.info("删除KnowledgePoint End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 获取knowledgepoint对象
	 *
	 * @param knowledgepoint
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getKnowledgePointById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getKnowledgePointById(KnowledgePoint knowledgepoint) throws Exception{
		LOGGER.info("获取KnowledgePoint Start");
		
		User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}

		KnowledgePoint kPoint = knowledgepointService.getKnowledgePointById(knowledgepoint.getId());
		
		if (null != kPoint && !kPoint.getOrgId().equals(user.getOrgId())) {
			kPoint = null;
		}

		LOGGER.info("获取KnowledgePoint End");
		return AsyncResponseData.getSuccess(kPoint);
	}

	/**
	 * 获取knowledgepoint对象
	 *
	 * @param subjectName
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryKnowledgePointsBySubjectName", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryKnowledgePointsBySubjectName(String subjectName,String gradeCode) throws Exception{
		LOGGER.info("获取KnowledgePoint Start");

		User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}

		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("subjectId",subjectName);
		filter.put("gradeCode",gradeCode);
		filter.put("orgId",user.getOrgId());
		//根据 学科 & 组织机构 查询出知识点List
		List<KnowledgePoint> knowledgepoints= knowledgepointService.queryKnowledgePointsFilter(filter);

		List<KnowledgePointVO> parentKPVOList = new ArrayList<>();
		KnowledgePointVO parentKPVO;
		for(KnowledgePoint knowledgePoint : knowledgepoints){
			parentKPVO = new KnowledgePointVO();
			if (StringUtils.isBlank(knowledgePoint.getParentId())){
				parentKPVO.setId(knowledgePoint.getId());
				parentKPVO.setKnowledgeCode(knowledgePoint.getKnowledgeCode());
				parentKPVO.setKnowledgeContent(knowledgePoint.getKnowledgeContent());
				parentKPVOList.add(parentKPVO);
			}
		}

		//封装orgVO
		buildOrgVO(knowledgepoints,parentKPVOList);

		LOGGER.info("获取KnowledgePoint End");
		
		return AsyncResponseData.getSuccess(parentKPVOList);
	}

	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData test(KnowledgePoint knowledgepoint) throws Exception{
		LOGGER.info("删除KnowledgePoint Start");
		
		User user = LoginInterceptor.getCurrentUser();
		
		if (null == user){
			return AsyncResponseData.getDenied("登录过期，请重新登录");
		}
		
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("subjectId","语文");
		filter.put("gradeCode","一年级");
		filter.put("orgId",user.getOrgId());
		//根据 学科 & 组织机构 查询出知识点List
		List<KnowledgePoint> knowledgepoints = knowledgepointService.queryKnowledgePointsFilter(filter);
		
		for(KnowledgePoint e : knowledgepoints) {
			if("".equals(e.getParentId())) {
				
			}
		}
		
		LOGGER.info("删除KnowledgePoint End");
		return AsyncResponseData.getSuccess(knowledgepoints);
	}

	private void buildOrgVO(List<KnowledgePoint> knowledgepoints, List<KnowledgePointVO> parentKPVOList){
		List<KnowledgePointVO> allKnowledgepoints = new ArrayList<KnowledgePointVO>();
		for (KnowledgePointVO parentKPVO : parentKPVOList) {
			List<KnowledgePointVO> kpVOChildren = new ArrayList<KnowledgePointVO>();
			for (KnowledgePoint Knowledgepoint : knowledgepoints) {
				if (StringUtils.isNotBlank(Knowledgepoint.getParentId())){
					if (Knowledgepoint.getParentId().equals(parentKPVO.getId())){
						KnowledgePointVO childKPVO  = new KnowledgePointVO();
						childKPVO.setId(Knowledgepoint.getId());
						childKPVO.setKnowledgeCode(Knowledgepoint.getKnowledgeCode());
						childKPVO.setKnowledgeContent(Knowledgepoint.getKnowledgeContent());
						kpVOChildren.add(childKPVO);
						allKnowledgepoints.add(childKPVO);
					}
				}
			}
			parentKPVO.setKpVOChildList(kpVOChildren);
		}
		if(!allKnowledgepoints.isEmpty()){
			buildOrgVO(knowledgepoints,allKnowledgepoints);
		}
	}
}
