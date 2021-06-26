package Login;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * 主窗体
 * 
 * @since 10
 * @author TagBug {@link https://github.com/tagbug}
 */
public class MainFrame extends JFrame {
    private DbBridge dbBridge;

    MainFrame() throws ClassNotFoundException, SQLException {
        dbBridge = new DbBridge();
        setTitle("登录&注册");
        setBounds(10, 10, 600, 140);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        setVisible(true);
    }
    
    void init() {
        var tabPanel = new JTabbedPane();
        var loginPanel = new LoginPanel(dbBridge);
        var registerPanel = new RegisterPanel(dbBridge);
        tabPanel.add("我要注册", registerPanel);
        tabPanel.add("我要登陆", loginPanel);
        add(tabPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            new MainFrame();
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败！" + e);
        } catch (SQLException e) {
            System.out.println("数据库连接失败！" + e);
        }
    }
}
