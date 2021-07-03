package Server;

import java.net.ServerSocket;
import java.sql.SQLException;

import Server.util.DbBridge;

/**
 * 服务器主类，初始化数据库并开始监听客户连接
 * 
 * @since 10
 */
public class Server {
    public static void main(String[] args) {
        try {
            var dbBridge = new DbBridge();
            try (var serverSocket = new ServerSocket(6666)/* 监听端口6666 */) {
                System.out.println("正在监听...");
                while (true) {
                    // 监听客户端并建立多线程化连接
                    new Thread(new ActionThread(serverSocket.accept(), dbBridge)).start();
                }
            } catch (Exception e) {
                // 运行时发生未知异常
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败！" + e);
        } catch (SQLException e) {
            System.out.println("数据库连接失败！" + e);
        }
    }
}
