package Client.GUI.Panel;

import Client.GUI.Action.*;
import Client.util.*;

import javax.swing.*;
import java.awt.*;

/**
 * 登录界面视图
 *
 * @since 10
 */
public class LoginPanel extends JPanel {
    private final ClientSocket clientSocket;
    private final JFrame window;// 登录窗口对象

    public LoginPanel(ClientSocket clientSocket, JFrame window) {
        this.clientSocket = clientSocket;
        this.window = window;
        setLayout(new FlowLayout());
        init();
    }

    void init() {
        var idText = new JTextField(10);
        var pasText = new JTextField(10);
        var loginButton = new JButton("登录");
        var loginAction = new LoginAction(clientSocket, this, idText, pasText, window);
        loginButton.addActionListener(loginAction);
        add(new JLabel("用户名:"));
        add(idText);
        add(new JLabel("密码："));
        add(pasText);
        add(loginButton);
    }
}
