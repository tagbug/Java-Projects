package Client.GUI.Action;

import Client.GUI.Frame.*;
import Client.util.*;
import Data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static Data.ClientRequest.TYPE.*;

public class LoginAction extends ClientAction<JPanel> {
    private final JTextField idText;
    private final JTextField pasText;
    private final JFrame window;// 登录窗口对象

    public LoginAction(ClientSocket clientSocket, JPanel panel, JTextField idText, JTextField pasText, JFrame window) {
        super("登录", clientSocket, panel);
        this.idText = idText;
        this.pasText = pasText;
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ClientRequest request = new ClientRequest();
        request.setRequestType(Login);
        var map = new HashMap<String, String>();
        map.put("name", idText.getText());
        map.put("password", pasText.getText());
        request.setData(map);
        withConnectionReset(() -> {
            var response = clientSocket.query(request);
            if (response.isSucceed()) {
                var userInfo = response.getResult().get(0);
                var isAdmin = Boolean.parseBoolean(userInfo.get("isAdmin"));
                if (isAdmin) {
                    EventQueue.invokeLater(() -> {
                        new AdminFrame(clientSocket, window, userInfo);// 管理员窗口
                    });
                } else {
                    EventQueue.invokeLater(() -> {
                        new TestFrame(clientSocket, window, userInfo);// 考生窗口
                    });
                }
                window.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(window, "登录失败！" + response.getFailReason(), "错误",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
