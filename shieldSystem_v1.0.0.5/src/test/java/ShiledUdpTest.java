import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ShiledUdpTest {
	public static void main(String[] args) {
		
		String s = "1111";
		StringBuilder sc = new StringBuilder(s);
		sc.replace(3, 4, "0");
		
		System.out.println(sc.toString().substring(3, 4));
	}
	
	public static String sendUdp(String ip, int port, String sendMessage) {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            byte[] buf = sendMessage.getBytes();
            InetAddress address = InetAddress.getByName(ip);
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, address, port);
            //发送数据
            datagramSocket.send(datagramPacket);
            //接收数据
            byte[] receBuf = new byte[1024];
            DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
            datagramSocket.receive(recePacket);

            String receStr = new String(recePacket.getData(), 0, recePacket.getLength());
            System.out.println("Client Rece Ack:" + receStr);
            return receStr;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭socket
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
        return null;
    }

}
