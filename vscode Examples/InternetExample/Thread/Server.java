package InternetExample.Thread;

import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        Socket you = null;
        try (var server = new ServerSocket(2010);){
            while (true) {
                try {
                    System.out.println("等待客户端呼叫...");
                    you = server.accept();
                    System.out.println("客户端地址：" + you.getInetAddress());
                } catch (IOException e) {
                    System.out.println("正在等待客户端..." + e);
                }
                if (you != null) {
                    new ServerThread(you).start();
                }
            }
        } catch (IOException e) {
            System.out.println("正在监听，不能重复创建.." + e);// ServerSocket对象不能重复创建
        }
    }
}

class ServerThread extends Thread{
    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    String s;

    ServerThread(Socket t) {
        socket = t;
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                double r = in.readDouble();// 堵塞状态，除非读取到信息
                double area = Math.PI * r * r;
                out.writeDouble(area);
            } catch (IOException e) {
                System.out.println("客户端断开连接：" + e);
                return;
            }
        }
    }
}