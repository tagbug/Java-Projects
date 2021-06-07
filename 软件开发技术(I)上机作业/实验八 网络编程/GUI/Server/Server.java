package GUI.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import GUI.QueryRequest;

public class Server {
    public static void main(String[] args) {
        try {
            var dbBridge = new DbBridge();
            try (var serverSocket = new ServerSocket(6666)) {
                System.out.println("学号：3200608080，姓名：陈欣阳");
                System.out.println("正在监听...");
                while (true) {
                    // 监听客户端并建立多线程化连接
                    new Thread(new serverThread(serverSocket.accept(), dbBridge)).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("数据库连接失败！" + e);
        }
    }
}

class serverThread implements Runnable {
    private Socket socket;
    private DbBridge dbBridge;

    serverThread(Socket socket, DbBridge dbBridge) {
        this.socket = socket;
        this.dbBridge = dbBridge;
        System.out.println("IP:" + socket.getInetAddress() + ",客户" + hashCode() + "已建立连接...");
    }

    @Override
    public void run() {
        // 注意：通过Socket建立Object流时，服务端和客户端的输入输出流顺序应相反，否则会导致死锁，详见Object流的JavaDoc
        try (var inputStream = new ObjectInputStream(socket.getInputStream());
                var outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            while (true) {
                var request = (QueryRequest) inputStream.readObject();
                switch (request.getType()) {
                    case ID:
                        outputStream.writeObject(dbBridge.queryById(request.getData()));
                        break;
                    case NAME:
                        outputStream.writeObject(dbBridge.queryByName(request.getData()));
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("由于：" + e + ",客户" + hashCode() + "已断开连接...");
        }
    }
}