package InternetExample.UDP;

import java.net.*;
import java.util.*;

public class ZhangSan {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Thread readData;
        ReceiveLetterForZhang receiver = new ReceiveLetterForZhang();
        try {
            readData = new Thread(receiver);
            readData.start();
            byte buffer[] = new byte[1];
            InetAddress address = InetAddress.getByName("localhost");
            DatagramPacket dataPack = new DatagramPacket(buffer, buffer.length, address, 666);
            DatagramSocket postman = new DatagramSocket();
            System.out.println("输入发送给李四的信息：");
            while (scanner.hasNext()) {
                String mess = scanner.nextLine();
                buffer = mess.getBytes();
                if (mess.length() == 0)
                    System.exit(0);
                buffer = mess.getBytes();
                dataPack.setData(buffer);
                postman.send(dataPack);
                System.out.println("继续输入发送给李四的信息：");
            }
            postman.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        scanner.close();
    }
}

class ReceiveLetterForZhang implements Runnable {

    @Override
    public void run() {
        DatagramPacket pack = null;
        byte data[] = new byte[8192];
        try(var postman = new DatagramSocket(888);) {
            pack = new DatagramPacket(data, data.length);
            while (true) {
                if (postman == null)
                    return;
                try {
                    postman.receive(pack);
                    String message = new String(pack.getData(), 0, pack.getLength());
                    System.out.printf("%25s\n", "收到：" + message);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

}