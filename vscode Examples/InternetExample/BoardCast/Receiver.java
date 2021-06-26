package InternetExample.BoardCast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver {
    public static void main(String[] args) {
        int port = 5858;// 组播的端口
        InetAddress group = null;// 组播组的地址
        try (var socket = new MulticastSocket(port);/*多点广播套接字*/){
            group = InetAddress.getByName("239.255.8.0");
            // （此方法已被弃用）socket.joinGroup(group);
            while (true) {
                byte data[] = new byte[8192];
                DatagramPacket packet = null;
                packet = new DatagramPacket(data, data.length, group, port);
                try {
                    socket.receive(packet);
                    String message = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("接收的内容：\n" + message);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }
}
