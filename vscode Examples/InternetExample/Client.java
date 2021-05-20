package InternetExample;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        String mess[] = { "刘一松是什么🐮🐴？", "刘一松就是🐮🐴", "傻逼刘一松" };
        Socket mySocket;
        DataInputStream in = null;
        DataOutputStream out = null;
        try {
            mySocket = new Socket("127.0.0.1", 2010);
            in = new DataInputStream(mySocket.getInputStream());
            out = new DataOutputStream(mySocket.getOutputStream());
            for (var s : mess) {
                out.writeUTF(s);
                String str = in.readUTF();// in读取信息，堵塞状态
                System.out.println("收到服务器回答：" + str);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("服务器已断开：" + e);
        }
    }
}
