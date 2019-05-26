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
import com.jy.moudles.organization.entity.Organization;
import com.jy.moudles.organization.service.OrganizationService;
import com.jy.moudles.systemConfig.utils.AccessTokenUtil;

public class OrganizationTimerServer {

	public ApplicationContext context;
	
	public long dateOffset;
	
	public OrganizationTimerServer(ApplicationContext cont) {
		this.context = cont;
		this.dateOffset = 1000 * 60 * 10;
		//this.dateOffset = 1000 * 20;
	}
	
	public void start(){
        Timer timer = new Timer("getOrg");
        timer.schedule(new getAllOrgs(), 1000 * 5, dateOffset);
    }
	
	class getAllOrgs extends TimerTask{

			@Override
			public void run() {
				OrganizationService organizationService = context.getBean(OrganizationService.class);
				
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
				ResponsePost responsePost = HttpUtils.sendPost(JSKSYTestUrl.URL+JSKSYTestUrl.GETALLORGINFO, requestPost);
				
				String datas = responsePost.getData();
				if(StringUtils.isNotBlank(datas)) {
					List<Organization> Organizations = JSON.parseArray(datas, Organization.class);
					if(null != Organizations && Organizations.size() > 0) {
						for(Organization o : Organizations) {
							System.out.println("Jgszdm=================="+o.getJgszdm());
							o.setId(UUIDUtil.get32UUID());
							// 机构地址
							String jgdz = o.getJgdz() == null?"null":o.getJgdz();
							o.setJgdz(jgdz);
						}
						organizationService.deleteOrganizations();
						organizationService.insertOrganizations(Organizations);
					}
					
				}else {
					System.out.println("未获取到机构信息");
				}
				
			}
	    	
    }
}
