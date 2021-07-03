package Client.GUI;

import java.awt.FlowLayout;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.*;

import Data.ClientRequest;
import Client.util.ClientSocket;
import static Data.ClientRequest.TYPE.*;

/**
 * 注册界面视图
 * 
 * @since 10
 */
public class RegisterPanel extends JPanel {
    private ClientSocket clientSocket;

    RegisterPanel(ClientSocket clientSocket) {
        this.clientSocket = clientSocket;
        setLayout(new FlowLayout());
        init();
    }

    void init() {
        var idText = new JTextField(10);
        var pasText = new JTextField(10);
        var chooseA = new JRadioButton("考生");
        var chooseB = new JRadioButton("管理员");
        var chooseGroup = new ButtonGroup();
        chooseGroup.add(chooseA);
        chooseGroup.add(chooseB);
        chooseA.setSelected(true);
        var registerButton = new JButton("注册");
        registerButton.addActionListener(event -> {
            ClientRequest request = new ClientRequest();
            request.setRequestType(Register);
            var map = new HashMap<String, String>();
            map.put("name", idText.getText());
            map.put("password", pasText.getText());
            map.put("isAdmin", String.valueOf(chooseGroup.getSelection() == chooseB));
            request.setData(map);
            try {
                var response = clientSocket.query(request);
                if (response.isSucceed()) {
                    JOptionPane.showMessageDialog(this, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "注册失败！" + response.getFailReason(), "错误",
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
        add(new JLabel("用户类别："));
        add(chooseA);
        add(chooseB);
        add(registerButton);
    }
}
