package Login;

import java.awt.FlowLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 登录界面视图
 * 
 * @since 10
 * @author TagBug {@link https://github.com/tagbug}
 */
public class LoginPanel extends JPanel {
    private DbBridge dbBridge;

    LoginPanel(DbBridge dbBridge) {
        this.dbBridge = dbBridge;
        setLayout(new FlowLayout());
        init();
    }

    void init() {
        var idText = new JTextField(10);
        var pasText = new JTextField(10);
        var loginButton = new JButton("登录");
        loginButton.addActionListener(e -> {
            try {
                if (dbBridge.login(idText.getText(), pasText.getText())) {
                    JOptionPane.showMessageDialog(this, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "登录失败！ID不存在或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e1) {
                // 出现其他异常的处理
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, "注册失败：" + e1, "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(new JLabel("ID:"));
        add(idText);
        add(new JLabel("密码："));
        add(pasText);
        add(loginButton);
    }
}
