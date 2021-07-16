package Client.GUI.Panel;

import Client.GUI.Action.*;
import Client.util.*;

import javax.swing.*;
import java.awt.*;

/**
 * 注册界面视图
 *
 * @since 10
 */
public class RegisterPanel extends JPanel {
    private final ClientSocket clientSocket;

    public RegisterPanel(ClientSocket clientSocket) {
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
        var registerAction = new RegisterAction(clientSocket, this, idText, pasText, chooseB);
        registerButton.addActionListener(registerAction);
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
