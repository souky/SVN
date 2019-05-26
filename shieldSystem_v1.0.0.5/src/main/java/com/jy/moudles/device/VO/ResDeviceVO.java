package com.jy.moudles.device.VO;

import com.jy.moudles.device.entity.Device;

import java.util.List;
import java.util.Map;

/**
 * Created by wb on 2018/1/9 16:10
 */
public class ResDeviceVO {
	
	public static String TOTAL_SWITCH_STATUS = "1";

    private Map<Integer, Device> deviceMaps;

    private List<Device> devices;
    
    private String totalSwitchStatus;

    /**
     * 在线设备数量
     */
    private int onlineShiled;

    /**
     * 异常设备数量
     */
    private int abnormalShiled;

    /**
     * 组织机构名称
     */
    private String orgName;

    public Map<Integer, Device> getDeviceMaps() {
        return deviceMaps;
    }

    public void setDeviceMaps(Map<Integer, Device> deviceMaps) {
        this.deviceMaps = deviceMaps;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

	public String getTotalSwitchStatus() {
		return totalSwitchStatus;
	}

	public void setTotalSwitchStatus(String totalSwitchStatus) {
		this.totalSwitchStatus = totalSwitchStatus;
	}

    public int getOnlineShiled() {
        return onlineShiled;
    }

    public void setOnlineShiled(int onlineShiled) {
        this.onlineShiled = onlineShiled;
    }

    public int getAbnormalShiled() {
        return abnormalShiled;
    }

    public void setAbnormalShiled(int abnormalShiled) {
        this.abnormalShiled = abnormalShiled;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
