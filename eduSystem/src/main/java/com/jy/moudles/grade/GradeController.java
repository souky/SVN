package com.jy.moudles.grade;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.auth.UserUtils;
import com.jy.moudles.school.constant.GradeInfo;
import com.jy.moudles.school.entity.School;
import com.jy.moudles.school.service.SchoolService;

@Controller
public class GradeController {
	
	@Autowired
	private SchoolService schoolService;
	
	@RequestMapping(value = "/getGrade", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteTeacher(String code) throws Exception{
		
		List<String> list = GradeInfo.GRADE_MAP.get(code);
		
		return AsyncResponseData.getSuccess(list);
	}
	
	@RequestMapping(value = "/getLoingGrade", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getLoingGrade() throws Exception{
		
		String orgId = UserUtils.getLoginUserOrgId();
		School school = schoolService.getSchoolByOrgId(orgId);
		
		List<String> list = new ArrayList<>();
		if(null != school) {
			String codes = school.getSchoolPeriod();
			if(StringUtils.isNotBlank(codes)) {
				String[] codeArray = codes.split(",");
				for(String s:codeArray) {
					List<String> list_stemp = GradeInfo.GRADE_MAP.get(s);
					list.addAll(list_stemp);
				}
			}
		}
		return AsyncResponseData.getSuccess(list);
	}
}
