package com.jy.moudles.device.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.jsonadpter.AsyncResponseData.ResultData;
import com.jy.common.utils.LogUtil;
import com.jy.common.utils.device.DeviceUtils;
import com.jy.moudles.device.VO.ResDeviceVO;
import com.jy.moudles.device.entity.Device;
import com.jy.moudles.device.service.DeviceService;
import com.jy.moudles.systemConfig.entity.SystemConfig;
import com.jy.moudles.systemConfig.service.SystemConfigService;
import com.jy.moudles.systemLog.constants.LogOperationType;
import com.jy.protocol.common.utils.GeneralProtocol;
import com.jy.protocol.jry.utils.JryUdpUtils;

/** 
 * 设备
 * xipeng
 * 2017-10-17
 */
@Controller
@RequestMapping(value="/device")
public class DeviceController {
	
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private SystemConfigService systemConfigService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);
	
	/**
	 *
	 * @param device
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveDevice(Device device,HttpServletRequest request) throws Exception{
		LOGGER.info("saveDevice Start");
		
		
		
		ResultData ResultData = deviceService.insertDevice(device);
		LogUtil.addLog("新增设备", LogOperationType.ADD,request);
		LOGGER.info("saveDevice End");
		return ResultData;
	}
	
	/**
	 *
	 * @param device
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateDevice(Device device,HttpServletRequest request) throws Exception{
		LOGGER.info("updateDevice Start");
		
		deviceService.updateDevice(device);
		
		LogUtil.addLog("更新设备信息", LogOperationType.EDIT,request);
		LOGGER.info("updateDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 *
	 * @param device
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteDevice(Device device ,HttpServletRequest request) throws Exception{
    	LOGGER.info("删除设备开始");
		
		deviceService.deleteDeviceById(device.getId());
		LogUtil.addLog("删除设备信息", LogOperationType.DELETE,request);
		LOGGER.info("删除设备结束");
		return AsyncResponseData.getSuccess();
	}
	
	/**
     * 批量删除blackwhitelist对象
     *
     * @return ModelAndView
     * @throws Exception
     */
	@RequestMapping(value = "/deleteDevices", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData deleteDevices(String[] ids,HttpServletRequest request) throws Exception {

        if(ids ==null || ids.length==0) {
			return AsyncResponseData.getDenied("没有要删除的设备！");
		}

    	LOGGER.info("批量删除设备开始");
		
        deviceService.deleteDevices(Arrays.asList(ids));
		LogUtil.addLog("批量删除设备", LogOperationType.DELETE,request);
        LOGGER.info("批量删除设备结束");
        return AsyncResponseData.getSuccess();
    }
    
	/**
	 *
	 * @param device
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryDevices", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryDevices(Device device,HttpServletRequest request) throws Exception{
		LOGGER.info("获取设备开始");
		
		Map<String, Object> filter = new HashMap<String, Object>();
		
		if(!"".equals(device.getIp()))
			filter.put("ip", device.getIp());

		if(!"".equals(device.getMac()))
			filter.put("mac", device.getMac());

		if(device.getStatus()!=null && -1 < device.getStatus())
			filter.put("status", device.getStatus());

		if(device.getType()!=null && -1 < device.getType())
			filter.put("type", device.getType());
		
		PageHelper.startPage(device.getPageNum(), device.getPageSize());
        PageInfo<Device> devices = null;
        
        List<Device> list = deviceService.queryDevicesFilter(filter);
		devices = new PageInfo<Device>(list);

        LOGGER.info("获取设备结束");
		
		return AsyncResponseData.getSuccess(devices);
	}

	/**
	 * 屏蔽设备查询专用
	 */
	@RequestMapping(value = "/queryShieldDevices", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryShieldDevices(Device device){
		LOGGER.info("获取屏蔽设备开始");
		Map<String, Object> filter = new HashMap<>();
		if(StringUtils.isNoneBlank(device.getIp())){
            filter.put("ip", device.getIp());
        }

		if(StringUtils.isNoneBlank(device.getMac())) {
            filter.put("mac", device.getMac());
        }

        if(device.getType() != null && -1 < device.getType()) {
            filter.put("type", device.getType());
        }

		Integer status = device.getStatus();
        if(status != -1){
            if(status == 3){
                filter.put("addressFlag", 3);
            }else{
                filter.put("addressFlag", 4);
                filter.put("status", device.getStatus());
            }
            //list = deviceService.queryDevicesAddressIsNotNull(filter);
        }
        
		PageHelper.startPage(device.getPageNum(), 10);
        PageInfo<Device> devices = new PageInfo<>(deviceService.queryDevicesFilter(filter));

		LOGGER.info("获取屏蔽设备结束");

		return AsyncResponseData.getSuccess(devices);
	}
	
	/**
	 * 屏蔽设备刷新专用
	 */
	@RequestMapping(value = "/refreshDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData refreshDevice(Device device){
		LOGGER.info("刷新屏蔽设备开始");
		List<Device> list = deviceService.queryDevicesFilter(null);
		if(null != list && list.size() > 0) {
			DeviceUtils.queryDevice(list);
		}
		return AsyncResponseData.getSuccess();
	}
	
	


	/**
	 * 自研屏蔽,开关模块接口
	 */
	@RequestMapping(value = "/updateSelfShieldStatus", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData updateSelfShieldStatus(String id, String module,String status){
        if(StringUtils.isBlank(id) || StringUtils.isBlank(module)){
            return AsyncResponseData.getSuccess().asParamError("参数错误");
        }
        Device device = deviceService.getDeviceById(id);
        if(device != null && (device.getStatus() != 1 || device.getPoweroff() != 1)){
            return AsyncResponseData.getSuccess().asParamError("当前设备状态为离线或关机,请刷新页面后重试");
        }

        if(device != null && device.getOperationType() != null){
        	String src = device.getOperationType();
			StringBuilder src_ = new StringBuilder(src);
	        
	        //专业作弊
	        if("1".equals(module)) {
	        	if("1".equals(status)) {
	        		src_.replace(0, 7, "1111111");
	        	}else {
	        		src_.replace(0, 7, "0000000");
	        	}
	        	
	        }
	        
	        //电话
	        if("2".equals(module)) {
	        	if("1".equals(status)) {
	        		src_.replace(7, 11, "1111");
	        	}else {
	        		src_.replace(7, 11, "0000");
	        	}
	        	
	        }
	        
	        //上网
	        if("3".equals(module)) {
	        	if("1".equals(status)) {
	        		src_.replace(11, 12, "1");
	        	}else {
	        		src_.replace(11, 12, "0");
	        	}
	        	
	        }
	        device.setOperationType(src_.toString());
	        JryUdpUtils.sendSwitchCommand_(device, status, module);
			deviceService.updateDevice(device);
        }
        return AsyncResponseData.getSuccess();
	}


    /**
     * 开关屏蔽终端设备
     */
    @RequestMapping(value = "/updateShieldPoweroff", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData updateShieldPoweroff(Device device){
        LOGGER.info("更新屏蔽终端设备开关开始");
        if(StringUtils.isBlank(device.getId()) || device.getPoweroff() == null){
            return AsyncResponseData.getSuccess().asParamError("参数错误");
        }
        Device d = deviceService.getDeviceById(device.getId());
        if(d.getStatus() != 1){
            return AsyncResponseData.getSuccess().asParamError("当前设备状态为离线,请刷新页面后重试");
        }

        //发送udp命令
        // 前台未传IP是否有误,暂时现改为d对象获取IP
		String poff = "1";
		if(device.getPoweroff() == 2){
        	poff = "0";
		}
		JryUdpUtils.poweroff(d.getIp(), poff);

        // 2018-3-20 注释开始
        Map<String, Object> filter = new HashMap<>();
        filter.put("poweroff", "1");
        filter.put("status", 1);
        filter.put("type", "2");
        List<Device> devices = deviceService.queryDevicesFilter(filter);
        if(devices != null && devices.size() > 0){
            ResDeviceVO.TOTAL_SWITCH_STATUS = "1";
        }else{
            ResDeviceVO.TOTAL_SWITCH_STATUS = "2";
        }
        // 2018-3-20 注释结束
        LOGGER.info("更新屏蔽终端设备开关结束");
        return AsyncResponseData.getSuccess(ResDeviceVO.TOTAL_SWITCH_STATUS);

    }

    /**
     * 设备总开关(只改在线的设备)
     */
    @RequestMapping(value = "/updateAllOnlineShield", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData updateAllOnlineShield(String poweroff){
        LOGGER.info("屏蔽终端设备总开关开始");
        if(StringUtils.isBlank(poweroff)){
            return AsyncResponseData.getSuccess().asParamError("参数错误");
        }

        Map<String, Object> filter = new HashMap<>();
        filter.put("status", "1");
        String poff = "1";
		if("2".equals(poweroff)){
			//关闭
			poff = "0";
			filter.put("poweroff", 1);
			//更改分体开关
			systemConfigService.setSystemConfigByKey("online","2");
			systemConfigService.setSystemConfigByKey("tel","2");
			systemConfigService.setSystemConfigByKey("pro","2");
		}else if("1".equals(poweroff)){
			//开启
			filter.put("poweroff", 2);
			systemConfigService.setSystemConfigByKey("online","1");
			systemConfigService.setSystemConfigByKey("tel","1");
			systemConfigService.setSystemConfigByKey("pro","1");
		}
        filter.put("type", "2");
        List<Device> devices = deviceService.queryDevicesFilter(filter);
        if(devices != null && devices.size() > 0){
            for(Device device : devices){
                if(device.getListOrder() != null && device.getIp() != null){
                    JryUdpUtils.poweroff(device.getIp(), poff);
                }
            }
        }
        ResDeviceVO.TOTAL_SWITCH_STATUS = poweroff;
        LOGGER.info("屏蔽终端设备总开关结束");
        return AsyncResponseData.getSuccess();
    }
    
    /**
     * 查询分体开关
     */
    @RequestMapping(value = "/querySwitch", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData querySwitch(){
    	
    	SystemConfig online = systemConfigService.getSystemConfigByKey("online");
    	SystemConfig tel = systemConfigService.getSystemConfigByKey("tel");
    	SystemConfig pro = systemConfigService.getSystemConfigByKey("pro");
    	List<String> list = new ArrayList<>();
    	if(null == online) {
    		online = new SystemConfig("online","2");
    		systemConfigService.insertSystemConfig(online);
    		list.add("2");
    	}else {
    		list.add(online.getSysValue());
    	}
    	
    	if(null == tel) {
    		tel = new SystemConfig("tel","2");
    		systemConfigService.insertSystemConfig(tel);
    		list.add("2");
    	}else {
    		list.add(tel.getSysValue());
    	}
    	
    	if(null == pro) {
    		pro = new SystemConfig("pro","2");
    		systemConfigService.insertSystemConfig(pro);
    		list.add("2");
    	}else {
    		list.add(pro.getSysValue());
    	}
    	
        return AsyncResponseData.getSuccess(list);
    }
    
    /**
     * 分体开关
     */
    @RequestMapping(value = "/swicth", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData swicth(String type,String status){
    	if(type.equals("1")) {
    		//专业作弊
    		systemConfigService.setSystemConfigByKey("pro", status);
    	}
    	
    	if(type.equals("2")) {
    		//电话
    		systemConfigService.setSystemConfigByKey("tel", status);
    	}
    	
    	if(type.equals("3")) {
    		//上网
    		systemConfigService.setSystemConfigByKey("online", status);
    	}
    	Map<String,Object> filter = new HashMap<>();
    	filter.put("poweroff", 1);
    	List<Device> lists = deviceService.queryDevicesFilter(filter);
    	
    	if(null != lists && lists.size() > 0) {
    		for(Device device:lists) {
    			String src = device.getOperationType();
    			StringBuilder src_ = new StringBuilder(src);
    	        
    	        //专业作弊
    	        if("1".equals(type)) {
    	        	if("1".equals(status)) {
    	        		src_.replace(0, 7, "1111111");
    	        	}else {
    	        		src_.replace(0, 7, "0000000");
    	        	}
    	        	
    	        }
    	        
    	        //电话
    	        if("2".equals(type)) {
    	        	if("1".equals(status)) {
    	        		src_.replace(7, 11, "1111");
    	        	}else {
    	        		src_.replace(7, 11, "0000");
    	        	}
    	        	
    	        }
    	        
    	        //上网
    	        if("3".equals(type)) {
    	        	if("1".equals(status)) {
    	        		src_.replace(11, 12, "1");
    	        	}else {
    	        		src_.replace(11, 12, "0");
    	        	}
    	        	
    	        }
    	        device.setOperationType(src_.toString());
    	        JryUdpUtils.sendSwitchCommand_(device, status, type);
    			deviceService.updateDevice(device);
    		}
    	}
    	
        return AsyncResponseData.getSuccess();
    }
    

    /**
     * 给屏蔽设备更新list_order
     */
    @RequestMapping(value = "/updateShieldListOrder", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData updateShieldListOrder(String mac, String address, Integer listOrder){
        LOGGER.info("屏蔽终端更新listOrder开始");
        if(StringUtils.isBlank(mac) || listOrder == null || StringUtils.isBlank(address)){
            return AsyncResponseData.getSuccess().asParamError("参数错误");
        }

        Map<String, Object> filter = new HashMap<>();
        filter.put("listOrder", listOrder);
        List<Device> devices = deviceService.queryDevicesFilter(filter);

        if(devices != null){
            if(devices.size() > 1){
                return AsyncResponseData.getSuccess().asParamError("数据错误");
            }else if(devices.size() == 1){
                if(!mac.equals(devices.get(0).getMac())){
                    return AsyncResponseData.getSuccess().asParamError("该MAC已经有对应的排序规则");
                }
            }
        }
        //filter.clear();
        Device device = deviceService.getDeviceByAddress(address.trim());
        if(device != null && !mac.equals(device.getMac())){
            return AsyncResponseData.getSuccess().asParamError("该位置信息已经存在");
        }

        filter.put("address", address);
		filter.put("mac", mac);
        deviceService.updateShieldListOrder(filter);

        LOGGER.info("屏蔽终端更新listOrder结束");
        return AsyncResponseData.getSuccess();
    }

	/**
	 * 屏蔽设备清除listorder
	 */
	@RequestMapping(value = "/clearListOrder", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData clearListOrder(String id){
		LOGGER.info("屏蔽终端清除listOrder开始");
		if(StringUtils.isBlank(id)){
			return AsyncResponseData.getSuccess().asParamError("参数错误");
		}
		deviceService.clearListOrder(id);
		LOGGER.info("屏蔽终端清除listOrder结束");
		return AsyncResponseData.getSuccess();
	}


	/**
     * 根据list_order排序获取屏蔽终端
     */
    @RequestMapping(value = "/queryDevicesByListOrder", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData queryDevicesByListOrder(){
        LOGGER.info("根据list_order排序获取屏蔽终端开始");
        List<Device> devices = deviceService.queryDevicesByListOrder();
        Map<Integer, Device> deviceMaps = new HashMap<>();
        ResDeviceVO resDeviceVO = new ResDeviceVO();
        if(devices != null && devices.size() > 0){
            for (Device device : devices) {
                if(device != null && device.getListOrder() != null){
                    deviceMaps.put(device.getListOrder(), device);
                }
                if(device != null && device.getOperationType_() != null){
                	String[] arr = device.getOperationType_().split(";");
                	device.setModuleList(Arrays.asList(arr));
				}
                if(device != null && (device.getStatus() != 1 || device.getPoweroff() != 1)){
                	List<String> list = new ArrayList<>();
                	list.add("0");
                	device.setModuleList(list);
                }
            }
            resDeviceVO.setOrgName(devices.get(0).getOrganization().getName());
        }

        resDeviceVO.setDeviceMaps(deviceMaps);
        resDeviceVO.setDevices(devices);
        
        Map<String, Object> filter = new HashMap<>();
        filter.put("poweroff", "1");
        filter.put("status", 1);
        filter.put("type", "2");
        List<Device> list = deviceService.queryDevicesFilter(filter);
        if(list != null && list.size() > 0){
            ResDeviceVO.TOTAL_SWITCH_STATUS = "1";
        }else{
            ResDeviceVO.TOTAL_SWITCH_STATUS = "2";
        }
        
        resDeviceVO.setTotalSwitchStatus(ResDeviceVO.TOTAL_SWITCH_STATUS);
        List<Integer> counts = deviceService.countShiledOnlinAndAbnormal();
        if(counts != null){
            resDeviceVO.setOnlineShiled(counts.get(0) == null ? 0 : counts.get(0));
            resDeviceVO.setAbnormalShiled(counts.get(1) == null ? 0 : counts.get(1));
        }
        LOGGER.info("根据list_order排序获取屏蔽终端结束");
        return AsyncResponseData.getSuccess(resDeviceVO);
    }

    /**
     *
     * 查询屏蔽终端mac,过滤list_order不为空的
     */
    @RequestMapping(value = "/queryMACs", method = RequestMethod.POST)
    @ResponseBody
    public AsyncResponseData.ResultData queryMACs(){
        LOGGER.info("查询屏蔽mac__list开始");
        List<String> macs = deviceService.queryMACs();
        LOGGER.info("查询屏蔽mac__list结束");
        return AsyncResponseData.getSuccess(macs);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
	@RequestMapping(value = "/queryDeviceById")
	@ResponseBody
	public AsyncResponseData.ResultData queryDeviceById(String id){
		LOGGER.info("根据id=" + id + "查询设备开始");
		if(StringUtils.isBlank(id)){
			return AsyncResponseData.getSuccess().asParamError("参数不能为空");
		}
		Device device = deviceService.getDeviceById(id);
		LOGGER.info("根据id=" + id + "查询设备结束");
		return AsyncResponseData.getSuccess(device);
	}
	
	/**
	 * 下载equipment列表的Excel文件对象
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportDeviceList", method = RequestMethod.GET)
	@ResponseBody
	public void exportDeviceList(HttpServletResponse response,Device device) throws Exception{
		device.setIp(URLDecoder.decode(device.getIp(), "UTF-8"));
		device.setMac(URLDecoder.decode(device.getMac(), "UTF-8"));
		Map<String, Object> filter = new HashMap<String, Object>();

		if(!"".equals(device.getIp()))
			filter.put("ip", device.getIp());

		if(!"".equals(device.getMac()))
			filter.put("mac", device.getMac());

		if(device.getStatus()!=null && -1 < device.getStatus())
			filter.put("status", device.getStatus());

		if(device.getType()!=null && -1 < device.getType())
			filter.put("type", device.getType());
		
		deviceService.getEquipmentsList(response,filter);
	}
	
	@RequestMapping(value = "/bootDevice", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData bootDevice(Device device) throws Exception{
		LOGGER.info("bootDevice Start");
		Device d = deviceService.getDeviceById(device.getId());
		if(d == null){
			return AsyncResponseData.getSuccess().asParamError("查询不到设备信息");
		}

		if(1 == device.getControlled()){//自动
			device.setOperationType("1");//自动
			GeneralProtocol.sendShieldCommand(d, 1 + "");
		} else if(2 == device.getControlled()){//手动
			if(device.getOperationType1G()==null && device.getOperationTypePhone()==null){
				device.setOperationType("5");//全不选是5
				GeneralProtocol.sendShieldCommand(d, 5 + "");
			}
			if(device.getOperationType1G()==null && device.getOperationTypePhone()!=null){
				device.setOperationType("4");//手机模块是4
                GeneralProtocol.sendShieldCommand(d, 4 + "");
			}
			if(device.getOperationType1G()!=null && device.getOperationTypePhone()==null){
				device.setOperationType("3");//1G模块是3
                GeneralProtocol.sendShieldCommand(d, 3 + "");
			}
			if(device.getOperationType1G()!=null && device.getOperationTypePhone()!=null){
				device.setOperationType("2");//全选是2
                GeneralProtocol.sendShieldCommand(d, 2 + "");
			}
			
		}else {
			device.setControlled(0);//未知
			device.setOperationType("0");//未知
            GeneralProtocol.sendShieldCommand(d,  "");
		}
		deviceService.updateDevice(device);
		
		LOGGER.info("bootDevice End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 根据checkbox批量开启/关闭设备
	 * 
	 * @param  ids , String operationType(开启/关闭)
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/openOrCloseSelectedDevices", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData openOrCloseSelectedDevices(String ids,String operationType) throws Exception{
	    if(StringUtils.isBlank(ids) || StringUtils.isBlank(operationType)){
            return AsyncResponseData.getLogicError("参数错误！");
        }
		List<String> list = new ArrayList<String>();
		if(ids!=null){
			String[] s = ids.split(",");
			if(s.length == 0){
				return AsyncResponseData.getLogicError("请选择要操作的设备！");
			}else{
				list = Arrays.asList(s);
			}
		}

		LOGGER.info("批量开启设备开始");
        if(list != null && list.size() > 0){
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("list", list);
            params.put("operationType", operationType);
            deviceService.openOrCloseSelectedDevices(params);
            //发送协议到阻断
            List<Device> devices = deviceService.getDeviceListByIds(list);
            sendProtocol(devices, operationType);
        }
		
		LOGGER.info("批量开启设备结束");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 一键批量开启/关闭所有设备
	 * @param 
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/openOrCloseAllDevices", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData openOrCloseAllDevices(String operationType) throws Exception{

		LOGGER.info("批量开启设备开始");
		if(StringUtils.isNotBlank(operationType)){
			deviceService.openOrCloseAllDevices(operationType);
			//发送协议,获取所有阻断设备
			Map<String,Object> filter = new HashMap<>();
			filter.put("type","2");
			List<Device> devices = deviceService.queryDevicesFilter(filter);
			sendProtocol(devices, operationType);
		}
		
		LOGGER.info("批量开启设备结束");
		return AsyncResponseData.getSuccess();
	}

	private void sendProtocol(List<Device> devices, String operationType){
	    if(devices != null){
            for(Device device : devices){
				GeneralProtocol.sendShieldCommand(device, operationType);
            }
        }
    }

}
