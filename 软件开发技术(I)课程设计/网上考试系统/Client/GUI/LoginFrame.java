package Client.GUI;

import javax.swing.*;

import Client.util.ClientSocket;

import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame(ClientSocket clientSocket) {
        setTitle("登录&注册");
        setBounds(10, 10, 600, 140);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init(clientSocket);
        setVisible(true);
    }

    void init(ClientSocket clientSocket) {
        var tabPanel = new JTabbedPane();
        var loginPanel = new LoginPanel(clientSocket, this);
        var registerPanel = new RegisterPanel(clientSocket);
        tabPanel.add("我要登陆", loginPanel);
        tabPanel.add("我要注册", registerPanel);
        add(tabPanel, BorderLayout.CENTER);
    }
}
