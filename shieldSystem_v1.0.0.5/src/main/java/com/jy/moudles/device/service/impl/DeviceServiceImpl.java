package com.jy.moudles.device.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.common.excelUtil.ExportExcel;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.device.dao.DeviceDao;
import com.jy.moudles.device.entity.Device;
import com.jy.moudles.device.service.DeviceService;
import com.jy.moudles.statistics.entity.DeviceCollectVO;

/** 
 * xipeng
 * 2017-10-17
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceDao devicedao;
	
	@Override
	public AsyncResponseData.ResultData insertDevice(Device device){
		
		String ip = device.getIp();
		String mask = device.getMask();
		String gateway = device.getGateway();
		String mac = device.getMac();
		if(StringUtils.isBlank(ip) || StringUtils.isBlank(mask) || StringUtils.isBlank(gateway)) {
			return AsyncResponseData.getSuccess().asParamError("IP信息错误");
		}
		if(StringUtils.isBlank(mac)) {
			return AsyncResponseData.getSuccess().asParamError("MAC信息错误");
		}else {
			device.setMac(mac.toUpperCase());
		}
		
		//基本信息
		device.setType(2);
		device.setId(UUIDUtil.get32UUID());
		device.setStatus(2);
		device.setPoweroff(2);
		device.setControlled(2);
		device.setOrgId("0fae7da3605b4bd687f1f97be25e289e");
		
		devicedao.insertDevice(device);
		
		return AsyncResponseData.getSuccess();
	}
	
	@Override
	public void updateDevice(Device device){
		devicedao.updateDevice(device);
	}
	
	@Override
	public Device getDeviceById(String id){
		return devicedao.getDeviceById(id);
	}
	
	@Override
	public List<Device> queryDevicesFilter(Map<String, Object> filter){
		List<Device> list = devicedao.queryDevicesFilter(filter);
		if(null != list && list.size() > 0) {
			for(Device d : list) {
				String src = d.getOperationType();
				List<Integer> lists = new ArrayList<>();
				if (StringUtils.isNotBlank(src) && src.length() == 12) {
					if ("1111111".equals(src.substring(0, 7))) {
						lists.add(1);
					}
					if ("1111".equals(src.substring(7, 11))) {
						lists.add(2);
					}
					if ("1".equals(src.substring(11, 12))) {
						lists.add(3);
					}
				} else {
					lists.add(0);
				}
				if (lists.isEmpty()) {
					lists.add(0);
				}
				d.setOperationType_(StringUtils.join(lists,";"));
			}
		}
		return list;
	}
	
	@Override
	public void deleteDeviceById(String id){
		devicedao.deleteDeviceById(id);
	}
	
	@Override
	public void deleteDevices(List<String> ids){
		devicedao.deleteDevices(ids);
	}

	@Override
	public void getEquipmentsList(HttpServletResponse response,
			Map<String, Object> filter) {
		List<Device> deviceList = devicedao.queryDevicesFilter(filter);
		ExportExcel ee = new ExportExcel("这是设备列表文件的标题",Device.class);
		ee.setDataList(deviceList);
		try {
			ee.write(response,"DeviceList.xlsx" );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void openOrCloseSelectedDevices(Map<String,Object> params) {
		devicedao.openOrCloseSelectedDevices(params);
	}
	
	@Override
	public void openOrCloseAllDevices(String operationType) {
		devicedao.openOrCloseAllDevices(operationType);
		
	}
	
	 @Override
	public Device getDeviceByMac(String mac) {
		return devicedao.getDeviceByMac(mac);
	}

	@Override
	public List<Device> getDeviceListByIds(List<String> ids){
		return devicedao.getDeviceListByIds(ids);
	}

	@Override
	public List<DeviceCollectVO> queryDeviceCount() {
		return devicedao.queryDeviceCount();
	}
	
	/**
	 * 
	 * @param id 设备分组id
	 * @return
	 */
	@Override
	public List<Device> queryDevicesByGroupNo(String id){
		return devicedao.queryDevicesByGroupNo(id);
	}

	@Override
	public void deleteDeviceGroupNo(String id) {
		devicedao.deleteDeviceGroupNo(id);
	}

	@Override
	public void addDeviceGroupNo(String id, List<String> list) {
		devicedao.addDeviceGroupNo(id, list);		
	}
	@Override
	public void updateDeviceByMac(Device device) {
		devicedao.updateDeviceByMac(device);
	};
	
	@Override
	public void updateDeviceStatusOnAgreement() {
		devicedao.updateDeviceStatusOnAgreement();
	}

	@Override	
	public void deleteDevicesByKafka(Map<String, Object> filter) {
		devicedao.deleteDevicesByKafka(filter);
	}

	@Override
	public void insertDevicesBatch(List<Device> list) {
		if(list != null && list.size() > 0){
			devicedao.insertDevicesBatch(list);
		}
	}

	@Override
	public void clearDeviceData() {
		devicedao.clearDeviceData();
	}

	@Override
	public List<Device> queryDevicesByListOrder(){
		List<Device> list = devicedao.queryDevicesByListOrder();
		if(null != list && list.size() > 0) {
			for(Device d : list) {
				String src = d.getOperationType();
				List<Integer> lists = new ArrayList<>();
				if (StringUtils.isNotBlank(src) && src.length() == 12) {
					if ("1111111".equals(src.substring(0, 7))) {
						lists.add(1);
					}
					if ("1111".equals(src.substring(7, 11))) {
						lists.add(2);
					}
					if ("1".equals(src.substring(11, 12))) {
						lists.add(3);
					}
				} else {
					lists.add(0);
				}
				if (lists.isEmpty()) {
					lists.add(0);
				}
				d.setOperationType_(StringUtils.join(lists,";"));
			}
		}
		return list;
	}

	@Override
    public List<String> queryIPs(){
	    return devicedao.queryIPs();
    }

	@Override
	public List<String> queryMACs(){
		return devicedao.queryMACs();
	}

    @Override
    public void updateShieldListOrder(Map<String,Object> filter){
	    devicedao.updateShieldListOrder(filter);
    }

    @Override
	public void updateAllOnlineShield(String poweroff, String operationType){
		devicedao.updateAllOnlineShield(poweroff, operationType);
	}

	@Override
	public 	void clearListOrder(String id){
		devicedao.clearListOrder(id);
	}

	@Override
	public 	List<Integer> countShiledOnlinAndAbnormal(){
		return devicedao.countShiledOnlinAndAbnormal();
	}

	@Override
	public Device getDeviceByAddress(String address){
		return devicedao.getDeviceByAddress(address);
	}

	@Override
	public void updateDeviceByIp(String ip) {
		devicedao.updateDeviceByIp(ip);
	}

}

