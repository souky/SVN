package com.jy.moudles.satellite.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.satellite.dao.SatelliteDao;
import com.jy.moudles.satellite.entity.Satellite;
import com.jy.moudles.satellite.service.SatelliteService;

/** 
 * Satellite业务实现类
 * 创建人：1
 * 创建时间：2018-03-20
 */
@Service
public class SatelliteServiceImpl implements SatelliteService {

	@Autowired
	private SatelliteDao SatelliteDao;
	
	@Override
	public void insertSatellite(Satellite Satellite){
		Satellite.setId(UUIDUtil.get32UUID());
		SatelliteDao.insertSatellite(Satellite);
	}
	
	@Override
	public void updateSatellite(Satellite Satellite){
		SatelliteDao.updateSatellite(Satellite);
	}
	
	@Override
	public Satellite getSatelliteById(String id){
		return SatelliteDao.getSatelliteById(id);
	}
	
	@Override
	public List<Satellite> querySatellitesFilter(Map<String, Object> filter){
		return SatelliteDao.querySatellitesFilter(filter);
	}
	
	@Override
	public void deleteSatelliteById(String id){
		SatelliteDao.deleteSatelliteById(id);
	}
	
	@Override
	public void deleteSatellites(List<String> ids){
		SatelliteDao.deleteSatellites(ids);
	}
	
	@Override
	public Satellite getSatelliteBySaNo(String saNo) {
		return SatelliteDao.getSatelliteBySaNo(saNo);
	}
	
	@Override
	public void deleteSatelliteAll() {
		SatelliteDao.deleteSatelliteAll();
	}

	@Override
	public void updateSatInfo(List<Satellite> sats) {
		// TODO Auto-generated method stub
		// 先将所有的卫星下线
		SatelliteDao.deleteSatelliteAll();
		
		for (Satellite sat : sats) {
			// 错误数据就跳过
			if (StringUtils.isBlank(sat.getSaNo())) {
				continue;
			}
			if (sat.getNoiseSignal().length() >2) {
				continue;
			}
			if (sat.getSaNo().length() > 2) {
				continue;
			}
			if (sat.getSaNo().length() == 1) {// 因为卫星有时候返回1 有时候是01
				sat.setSaNo("0" + sat.getSaNo());
			}
			
			// 先查此颗卫星在数据库中是否存在
			Satellite singleInfo = SatelliteDao.getSatelliteBySaNo(sat.getSaNo());
			if(StringUtils.isNotBlank(sat.getNoiseSignal())) {
				if(sat.getNoiseSignal().length() != 0) {
					sat.setIsCalculation(1);
				}else {
					sat.setIsCalculation(0);
				}
			}else {
				sat.setIsCalculation(0);
			}
			sat.setUpdateDate(new Date());
			if (singleInfo == null) {// 不存在
				sat.setId(UUIDUtil.get32UUID());
				SatelliteDao.insertSatellite(sat);
			} else {
				SatelliteDao.updateSatellite(sat);
			}
		}
	}
	
}

