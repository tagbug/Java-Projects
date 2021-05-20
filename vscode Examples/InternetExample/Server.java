package InternetExample;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        String answer[] = { "确实", "太对了，哥", "😂" };
        ServerSocket serverForClient = null;
        Socket socketOnServer = null;
        DataOutputStream out = null;
        DataInputStream in = null;
        try {
            serverForClient = new ServerSocket(2010);
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            System.out.println("等待客户端呼叫...");
            socketOnServer = serverForClient.accept();// 堵塞状态，除非有客户呼叫
            out = new DataOutputStream(socketOnServer.getOutputStream());
            in = new DataInputStream(socketOnServer.getInputStream());
            for (var s : answer) {
                String str = in.readUTF();// in读取信息，堵塞状态
                System.out.println("服务器收到客户端信息：" + str);
                out.writeUTF(s);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("客户端已断开："+e);
        }
    }
}
