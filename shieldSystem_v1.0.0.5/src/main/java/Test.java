import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Test {
	
	private static int errorNum = 0;
	public static void main(String[] args) {

		for (int i = 130, j = 1; i <= 189; i++, j++) {
			StringBuffer sb = new StringBuffer();
			String ip = sb.append("10.135.88.").append(i).toString();
			sb = new StringBuffer();
			String sendMessage = sb.append("RIP").append(ip).append("#").toString();
			System.out.println("第" + j + "条数据IP为:" + ip + "----发送的内容为：" + sendMessage);
			sendUdp4Query(ip, 1025, sendMessage);
		}
		System.out.println("共修改错误数据：" + errorNum);

	}

	public static void sendUdp4Query(String ip, int port, String sendMessage) {
		DatagramSocket datagramSocket = null;
		try {
			datagramSocket = new DatagramSocket();
			datagramSocket.setSoTimeout(3000);
			byte[] buf = sendMessage.getBytes();
			InetAddress address = InetAddress.getByName(ip);
			DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, port);
			// 发送数据
			datagramSocket.send(datagramPacket);

			byte[] receBuf = new byte[1024];
			DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
			datagramSocket.receive(recePacket);

			String receStr = new String(recePacket.getData(), 0, recePacket.getLength());
			String receIP = recePacket.getAddress().getHostAddress();
			String successMassage = "OK:IP=" + ip + "#";
			System.out.println("-----------返回消息为：" + receStr);
			if (receStr.equals(successMassage) || receStr.contains(successMassage)) {
				System.out.println(ip + "修改成功");
			} else {
				System.out.println(ip + "修改未其他IP，修改后IP" + receIP);
			}
			// 接收数据
		} catch (IOException e) {
			System.err.println("修改错误，IP为：" + ip);
			errorNum = errorNum + 1;
		} finally {
			// 关闭socket
			if (datagramSocket != null) {
				datagramSocket.close();
			}
		}
	}
}
