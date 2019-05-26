package shieldSystem;

import com.jy.common.tcp.TcpUtil;
import com.jy.common.utils.TypeConversionUtils;
import com.jy.common.utils.protocol.ClockFrameUtil;

public class TestTcp {

	public static void main(String[] args) {
		
		String data = ClockFrameUtil.clockUpdateIp("192.168.1.244", "192.168.1.113", "020a0f0e0d06");
		System.out.println(data);
		TcpUtil.sendTcp("192.168.1.113", data, 1026);
	}
}
