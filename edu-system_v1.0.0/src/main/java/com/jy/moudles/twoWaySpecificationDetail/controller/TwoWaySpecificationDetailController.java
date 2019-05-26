package com.jy.moudles.twoWaySpecificationDetail.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.auth.UserUtils;
import com.jy.moudles.twoWaySpecificationDetail.entity.TwoWaySpecificationDetail;
import com.jy.moudles.twoWaySpecificationDetail.service.TwoWaySpecificationDetailService;
import com.jy.moudles.user.entity.User;

/** 
 * twoWaySpecificationDetail实现类
 *
 * 创建人：1
 * 创建时间：2017-11-29
 */
@Controller
@RequestMapping(value="/twowayspecificationdetail")
public class TwoWaySpecificationDetailController {
	
	@Autowired
	private TwoWaySpecificationDetailService twowayspecificationdetailService;

	private static final Logger LOGGER = LoggerFactory.getLogger(TwoWaySpecificationDetailController.class);
	
	/**
	 * 新增twowayspecificationdetail对象
	 * 
	 * @param twowayspecificationdetail
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTwoWaySpecificationDetail", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveTwoWaySpecificationDetail(TwoWaySpecificationDetail twowayspecificationdetail) throws Exception{
		LOGGER.info("新增TwoWaySpecificationDetail Start");

		if(twowayspecificationdetail==null){
			return  AsyncResponseData.getSuccess().asParamError("请输入考试条目相关信息");
		}

		User user = UserUtils.getLoginUser();
        if(null == user) {
        	 return AsyncResponseData.getSuccess().asParamError("登陆超时");
        }
        twowayspecificationdetail.setCreateUser(user.getUserName());
        twowayspecificationdetail.setOrgId(user.getOrgId());
        
		twowayspecificationdetailService.insertTwoWaySpecificationDetail(twowayspecificationdetail);


		LOGGER.info("新增TwoWaySpecificationDetail End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 快速新增twowayspecificationdetail对象
	 *
	 * @param twowayspecificationdetail
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/fastlySaveTwoWaySpecificationDetail", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData fastlySaveTwoWaySpecificationDetail(int startNo,int endNo,TwoWaySpecificationDetail twowayspecificationdetail) throws Exception{
		LOGGER.info("新增TwoWaySpecificationDetail Start");

		if(twowayspecificationdetail==null){
			return  AsyncResponseData.getSuccess().asParamError("请输入快速生成的考试条目相关信息");
		}

		List<TwoWaySpecificationDetail> twoWaySpecificationDetails = new ArrayList<TwoWaySpecificationDetail>();
		TwoWaySpecificationDetail specificationDetail;
		for(int i=startNo; i<=endNo; i++){
			specificationDetail = new TwoWaySpecificationDetail();
			specificationDetail.setItemNo(i);
			specificationDetail.setItemType(twowayspecificationdetail.getItemType());
			specificationDetail.setItemScore(twowayspecificationdetail.getItemScore());
			twoWaySpecificationDetails.add(specificationDetail);
		}

		twowayspecificationdetailService.batchInsertTwoWaySpecificationDetail(twoWaySpecificationDetails);

		LOGGER.info("新增TwoWaySpecificationDetail End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 修改twowayspecificationdetail对象
	 *
	 * @param twowayspecificationdetail
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateTwoWaySpecificationDetail", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateTwoWaySpecificationDetail(TwoWaySpecificationDetail twowayspecificationdetail) throws Exception{
		LOGGER.info("修改TwoWaySpecificationDetail Start");

		twowayspecificationdetailService.updateTwoWaySpecificationDetail(twowayspecificationdetail);

		LOGGER.info("修改TwoWaySpecificationDetail End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 删除twowayspecificationdetail对象
	 * 
	 * @param twowayspecificationdetail
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteTwoWaySpecificationDetail", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteTwoWaySpecificationDetail(TwoWaySpecificationDetail twowayspecificationdetail) throws Exception{

		if(twowayspecificationdetail.getId()==null){
			Map<String,String> errMsg = new HashMap<String,String>();
			errMsg.put("id","请选择要删除的双向细目表条目");
			return AsyncResponseData.getParamError(errMsg);
		}
		LOGGER.info("删除TwoWaySpecificationDetail Start");

		twowayspecificationdetailService.deleteTwoWaySpecificationDetailById(twowayspecificationdetail.getId());
//		//删除双向细目表条目和知识点的关联
//		spDetailKnowledgeRelationService.deleteSDKRelationBySpDetailId(twowayspecificationdetail.getId());

		LOGGER.info("删除TwoWaySpecificationDetail End");
		return AsyncResponseData.getSuccess();
	}

	/**
	 * 获取twowayspecificationdetail对象
	 * 
	 * @param twowayspecificationdetail
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTwoWaySpecificationDetails", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryTwoWaySpecificationDetails(TwoWaySpecificationDetail twowayspecificationdetail) throws Exception{
		LOGGER.info("获取TwoWaySpecificationDetail Start");
		
		Map<String, Object> filter = new HashMap<String, Object>();

		if (StringUtils.isNotBlank(twowayspecificationdetail.getParentId())){
			filter.put("parentId",twowayspecificationdetail.getParentId());
		}
		
		List<TwoWaySpecificationDetail> twowayspecificationdetails= twowayspecificationdetailService.queryTwoWaySpecificationDetailsFilter(filter);
		LOGGER.info("获取TwoWaySpecificationDetail End");
		
		return AsyncResponseData.getSuccess(twowayspecificationdetails);
	}
	
}
