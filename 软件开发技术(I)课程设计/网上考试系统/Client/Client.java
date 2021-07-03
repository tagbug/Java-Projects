package Client;

import java.awt.EventQueue;
import java.io.IOException;

import Client.GUI.LoginFrame;
import Client.util.ClientSocket;

public class Client {
    public static void main(String[] args) {
        var clientSocket = new ClientSocket();
        clientSocket.config("localhost", 6666);
        try {
            clientSocket.connect();
            EventQueue.invokeLater(() -> {
                new LoginFrame(clientSocket);
            });
        } catch (IllegalStateException | IOException e) {
            System.out.println("与服务器建立连接失败！");
            e.printStackTrace();
        }
    }
}
