package p282;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (var serverSocket = new ServerSocket(6666)) {
            System.out.println("学号：3200608080，姓名：陈欣阳");
            System.out.println("正在监听...");
            while (true) {
                // 监听客户端并建立多线程化连接
                new Thread(new serverThread(serverSocket.accept())).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class serverThread implements Runnable {
    private Socket socket;

    serverThread(Socket socket) {
        this.socket = socket;
        System.out.println("IP:" + socket.getInetAddress() + ",客户" + hashCode() + "已建立连接...");
    }

    @Override
    public void run() {
        try (var inputStream = new DataInputStream(socket.getInputStream());
                var outputStream = new DataOutputStream(socket.getOutputStream())) {
            while (true) {
                double r = inputStream.readDouble();
                double result = Math.PI * r * r;// 计算圆的面积并返回结果
                outputStream.writeDouble(result);
            }
        } catch (Exception e) {
            System.out.println("由于：" + e + ",客户" + hashCode() + "已断开连接...");
        }
    }
}