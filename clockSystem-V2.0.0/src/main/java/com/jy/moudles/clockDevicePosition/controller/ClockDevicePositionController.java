package com.jy.moudles.clockDevicePosition.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.jy.common.jsonadpter.AsyncResponseData;
import com.jy.common.persistence.annotation.logAspect.JRYLogging;
import com.jy.common.persistence.annotation.logAspect.OpType;
import com.jy.common.utils.UUIDUtil;
import com.jy.moudles.clockConfig.entity.ClockConfig;
import com.jy.moudles.clockConfig.service.ClockConfigService;
import com.jy.moudles.clockDevicePosition.VO.MakeInitVO;
import com.jy.moudles.clockDevicePosition.entity.ClockDevicePosition;
import com.jy.moudles.clockDevicePosition.service.ClockDevicePositionService;

/** 
 * clockDevicePosition实现类
 *
 * 创建人：Administrator
 * 创建时间：2018-10-30
 */
@Controller
@RequestMapping(value="/clockdeviceposition")
public class ClockDevicePositionController {
	
	@Autowired
	private ClockDevicePositionService clockdevicepositionService;
	
	@Autowired
	private ClockConfigService clockconfigService;
	
	private static final Logger logger = LoggerFactory.getLogger(ClockDevicePositionController.class);
	
	/**
	 * 新增clockdeviceposition对象
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveClockDevicePosition", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData saveClockDevicePosition(ClockDevicePosition clockdeviceposition) throws Exception{
		logger.info("新增ClockDevicePosition Start");
		
		clockdevicepositionService.insertClockDevicePosition(clockdeviceposition);
		
		logger.info("新增ClockDevicePosition End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 新增clockdeviceposition对象
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/makePosition", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.ADD, opInfo = "楼层布局初始化", opReplaceParams = {""})
	public AsyncResponseData.ResultData makePosition(String str) throws Exception{
		logger.info("生成ClockDevicePosition Start");
		
		List<MakeInitVO> list = JSON.parseArray(str, MakeInitVO.class);
		
		List<ClockDevicePosition> listPosition = new ArrayList<>();
		List<String> listName = new ArrayList<>();
		int builds = 1;
		
		for(MakeInitVO e : list) {
			listName.add(e.getName());
			int floors = e.getFloors();
			int rooms = e.getRooms();
			for(int i = 1;i <= floors;i++) {
				for(int r = 1;r <= rooms;r++) {
					ClockDevicePosition clockdeviceposition = new ClockDevicePosition();
					clockdeviceposition.setClockPositionBuilings(builds);
					clockdeviceposition.setClockPositionFloor(i);
					clockdeviceposition.setClockPositionRoom(r);
					clockdeviceposition.setId(UUIDUtil.get32UUID());
					clockdeviceposition.setCreateDate(new Date());
					listPosition.add(clockdeviceposition);
				}
			}
			builds++;
		}
		
		//批量添加数据
		clockdevicepositionService.deleteClockDevicePositionAll();
		clockdevicepositionService.insertBatch(listPosition);
		
		//系统参数设置
		ClockConfig cg = clockconfigService.getClockConfigByKey("buildingsname");
		if(null == cg) {
			cg = new ClockConfig();
			cg.setSysKey("buildingsname");
			cg.setSysVal(JSON.toJSONString(listName));
			clockconfigService.insertClockConfig(cg);
		}else {
			cg.setSysVal(JSON.toJSONString(listName));
			clockconfigService.updateClockConfig(cg);
		}
		
		logger.info("生成ClockDevicePosition End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 修改clockdeviceposition对象
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateClockDevicePosition", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.EDIT, opInfo = "绑定时钟：{1}。", opReplaceParams = {"clockId"})
	public AsyncResponseData.ResultData updateClockDevicePosition(ClockDevicePosition clockdeviceposition) throws Exception{
		logger.info("修改ClockDevicePosition Start");
		
		clockdevicepositionService.updateClockDevicePosition(clockdeviceposition);
		
		logger.info("修改ClockDevicePosition End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 解绑时钟
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/unBinding", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.EDIT, opInfo = "解绑房间：{1}。", opReplaceParams = {"id"})
	public AsyncResponseData.ResultData unBinding(ClockDevicePosition clockdeviceposition) throws Exception{
		logger.info("修改ClockDevicePosition Start");
		
		clockdevicepositionService.unBinding(clockdeviceposition.getId());
		
		logger.info("修改ClockDevicePosition End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 删除clockdeviceposition对象
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteClockDevicePosition", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData deleteClockDevicePosition(ClockDevicePosition clockdeviceposition) throws Exception{
		logger.info("删除ClockDevicePosition Start");
		
		clockdevicepositionService.deleteClockDevicePositionById(clockdeviceposition.getId());
		
		logger.info("删除ClockDevicePosition End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 创建新房间
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/creatNewPosition", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.ADD, opInfo = "创建新房间{1}栋{2}层{3}房间。", opReplaceParams = {"builds,floors,rooms"})
	public AsyncResponseData.ResultData creatNewPosition(int builds,int floors,int rooms) throws Exception{
		logger.info("创建新房间 Start");
		
		ClockDevicePosition clockDevicePosition = new ClockDevicePosition();
		clockDevicePosition.setClockPositionBuilings(builds);
		clockDevicePosition.setClockPositionFloor(floors);
		clockDevicePosition.setClockPositionRoom(rooms);
		
		clockdevicepositionService.insertClockDevicePosition(clockDevicePosition);
		
		logger.info("创建新房间   End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 创建新楼层
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/creatNewFloors", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.ADD, opInfo = "创建新楼层{1}栋{2}层。", opReplaceParams = {"builds,floors"})
	public AsyncResponseData.ResultData creatNewFloors(int builds,int floors) throws Exception{
		logger.info("创建新楼层 Start");
		
		ClockDevicePosition clockDevicePosition = new ClockDevicePosition();
		clockDevicePosition.setClockPositionBuilings(builds);
		clockDevicePosition.setClockPositionFloor(floors);
		clockDevicePosition.setClockPositionRoom(1);
		
		clockdevicepositionService.insertClockDevicePosition(clockDevicePosition);
		
		logger.info("创建新楼层   End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 创建新栋
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/creatNewBuilds", method = RequestMethod.POST)
	@ResponseBody
	@JRYLogging(opType = OpType.ADD, opInfo = "创建新栋{1}栋,名称：{2}。", opReplaceParams = {"builds,names"})
	public AsyncResponseData.ResultData creatNewBuilds(int builds,int floors,int rooms,String names) throws Exception{
		logger.info("创建新栋 Start");
		
		List<ClockDevicePosition> list = new ArrayList<>();
		for(int i = 1;i <= floors;i++) {
			for(int r = 1;r <= rooms;r++) {
				ClockDevicePosition clockdeviceposition = new ClockDevicePosition();
				clockdeviceposition.setClockPositionBuilings(builds);
				clockdeviceposition.setClockPositionFloor(i);
				clockdeviceposition.setClockPositionRoom(r);
				clockdeviceposition.setId(UUIDUtil.get32UUID());
				clockdeviceposition.setCreateDate(new Date());
				list.add(clockdeviceposition);
			}
		}
		clockdevicepositionService.insertBatch(list);
		
		//系统参数设置修改
		ClockConfig cg = clockconfigService.getClockConfigByKey("buildingsname");
		List<String> s = JSON.parseArray(cg.getSysVal(),String.class);
		s.add(names);
		cg.setSysVal(JSON.toJSONString(s));
		clockconfigService.updateClockConfig(cg);
		
		logger.info("创建新栋   End");
		return AsyncResponseData.getSuccess();
	}
	
	/**
	 * 获取clockdeviceposition对象
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryClockDevicePositions", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData queryClockDevicePositions(ClockDevicePosition clockdeviceposition) throws Exception{
		logger.info("获取ClockDevicePosition Start");
		
		List<ClockDevicePosition> clockdevicepositions = clockdevicepositionService.queryClockDevicePositionsFilter(null);
		// 处理数据
		List<Map<String,Object>> list = new ArrayList<>();
		if(null != clockdevicepositions && clockdevicepositions.size() > 0) {
			int builds = 1;
			int floors = 1;
			
			// 楼栋map
			Map<String,Object> buildMap = new HashMap<>();
			// 楼层map
			List<Object> map_floor = new ArrayList<>();
			List<ClockDevicePosition> list_room = new ArrayList<>();
			
			// 秒钟显示
			SimpleDateFormat sdf = null;
			ClockConfig cg = clockconfigService.getClockConfigByKey("showSec");
			if(null == cg) {
				cg = new ClockConfig();
				cg.setSysKey("showSec");
				cg.setSysVal("0");
				clockconfigService.insertClockConfig(cg);
				sdf = new SimpleDateFormat("HH:mm:ss");
			}else {
				if(cg.getSysVal().equals("0")) {
					sdf = new SimpleDateFormat("HH:mm:ss");
				}else {
					sdf = new SimpleDateFormat("HH:mm");
				}
			}
			
			for(ClockDevicePosition e : clockdevicepositions) {
				
				//自检状态拆分
				if(e.getClockStatus() == 3 && StringUtils.isNotBlank(e.getSelfCheckInfo())) {
					String self = e.getSelfCheckInfo();
					List<char[]> list_ = new ArrayList<>();
					String[] self_ = self.split(";");
					for(int i = 0;i < self_.length;i++) {
						list_.add(self_[i].toCharArray());
					}
					e.setSelfArray(list_);
				}
				
				//放入时间
				if(e.getClockStatus() == 0) {
					e.setClockTime(sdf.format(new Date()));
				}else {
					e.setClockTime(sdf.format(e.getUpdateDate()));
				}
				
				if(e.getClockPositionBuilings() == builds) {
					// 放入名字
					if(e.getClockPositionFloor() == floors) {
						list_room.add(e);
					}else {
						//切换楼层
						map_floor.add(list_room);
						floors++;
						list_room = new ArrayList<>();
						list_room.add(e);
					}
				}else {
					// 切换楼栋  重载楼层
					map_floor.add(list_room);
					buildMap.put("floors", map_floor);
					list.add(buildMap);
					buildMap = new HashMap<>();
					builds++;
					floors = 1;
					map_floor = new ArrayList<>();
					
					list_room = new ArrayList<>();
					// 放入名字
					if(e.getClockPositionFloor() == floors) {
						list_room.add(e);
					}else {
						map_floor.add(list_room);
						floors++;
						list_room = new ArrayList<>();
						list_room.add(e);
					}
					
				}
			}
			map_floor.add(list_room);
			buildMap.put("floors", map_floor);
			list.add(buildMap);
		}
		logger.info("获取ClockDevicePosition End");
		
		return AsyncResponseData.getSuccess(list);
	}
	
	/**
	 * 根据ID获取clockdeviceposition对象
	 * 
	 * @param clockdeviceposition
	 * @throws Exception
	 */
	@RequestMapping(value = "/getClockDevicePositionById", method = RequestMethod.POST)
	@ResponseBody
	public AsyncResponseData.ResultData getClockDevicePositionById(String id) throws Exception{
		logger.info("获取ClockDevicePosition Start");
		
		ClockDevicePosition clockdeviceposition = new ClockDevicePosition();
		
		clockdeviceposition = clockdevicepositionService.getClockDevicePositionById(id);
		
		logger.info("获取ClockDevicePosition End");
		
		return AsyncResponseData.getSuccess(clockdeviceposition);
	}
	
}
