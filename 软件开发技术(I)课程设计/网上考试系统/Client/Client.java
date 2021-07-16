package Client;

import Client.GUI.Frame.*;
import Client.util.*;
import com.formdev.flatlaf.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * 客户端主类，配置Socket，连接服务器，并启动登录&注册窗口
 * 
 * @since 10
 */
public class Client {
    public static void main(String[] args) {
        try {
            // Swing主题设置
            FlatDarculaLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Swing全局字体设置
        Font font = new Font("微软雅黑", Font.PLAIN, 13);
        var keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, font);
            }
        }
        var clientSocket = new ClientSocket();
        clientSocket.config("localhost", 6666);// 配置socket连接设置
        try {
            clientSocket.connect();
            EventQueue.invokeLater(() -> {// 出于稳定性考虑，不在main线程中处理Swing
                new LoginFrame(clientSocket);// 登录&注册窗口
            });
        } catch (IllegalStateException | IOException e) {
            System.out.println("与服务器建立连接失败！");
            e.printStackTrace();
        }
    }
}
