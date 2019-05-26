package shieldSystem;


import java.io.IOException;

import com.jy.common.udp.UdpUtil;
import com.jy.common.utils.TypeConversionUtils;
import com.jy.common.utils.protocol.ClockFrameUtil;

public class TestUdp {

	public static void main(String[] args) {
		String data = "4A59 17000000 00 00 FFFF 7301A8C0 343434 353535 363636";
		data = data.replaceAll(" ","");
		
		//String data = ClockFrameUtil.clockRegister("192.168.1.115", 9000);
		//UdpUtil.guangbo(1025, TypeConversionUtils.hexStringToBytes(data));
		data = ClockFrameUtil.clockUpdateSec(false, "020a0f0e0d06");
		System.out.println(data);
		UdpUtil.sendUdp("192.168.1.112",1025, TypeConversionUtils.hexStringToBytes(data));
		
//		try {
//			UdpUtil.ReceiveUDP();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
