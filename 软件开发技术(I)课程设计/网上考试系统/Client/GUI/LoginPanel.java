package Client.GUI;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.*;

import Client.util.ClientSocket;
import Data.ClientRequest;
import static Data.ClientRequest.TYPE.*;

/**
 * 登录界面视图
 * 
 * @since 10
 */
public class LoginPanel extends JPanel {
    private ClientSocket clientSocket;
    private JFrame window;

    LoginPanel(ClientSocket clientSocket, JFrame window) {
        this.clientSocket = clientSocket;
        this.window = window;
        setLayout(new FlowLayout());
        init();
    }

    void init() {
        var idText = new JTextField(10);
        var pasText = new JTextField(10);
        var loginButton = new JButton("登录");
        loginButton.addActionListener(event -> {
            ClientRequest request = new ClientRequest();
            request.setRequestType(Login);
            var map = new HashMap<String, String>();
            map.put("name", idText.getText());
            map.put("password", pasText.getText());
            request.setData(map);
            try {
                var response = clientSocket.query(request);
                if (response.isSucceed()) {
                    var userInfo = response.getResult().get(0);
                    var isAdmin = Boolean.parseBoolean(userInfo.get("isAdmin"));
                    if (isAdmin) {
                        EventQueue.invokeLater(() -> {
                            new AdminFrame(clientSocket, window, userInfo);
                        });
                    } else {
                        EventQueue.invokeLater(() -> {
                            new TestFrame(clientSocket, window, userInfo);
                        });
                    }
                    window.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(this, "登录失败！" + response.getFailReason(), "错误",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalStateException | IOException e) {
                // 连接失效
                e.printStackTrace();
                int choose = JOptionPane.showConfirmDialog(this, "与服务器未建立连接或连接失效，是否重新连接？", "提示",
                        JOptionPane.YES_NO_OPTION);
                if (choose == JOptionPane.YES_OPTION) {
                    try {
                        clientSocket.enforceConnect();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(this, "建立连接失败！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(new JLabel("用户名:"));
        add(idText);
        add(new JLabel("密码："));
        add(pasText);
        add(loginButton);
    }
}
