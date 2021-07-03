package Client.GUI;

import javax.swing.*;

import Client.util.ClientSocket;
import Data.ClientRequest;
import Data.ClientRequest.TYPE;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 管理员窗口
 * 
 * @since 10
 */
public class AdminFrame extends JFrame {
    private ClientSocket clientSocket;
    private JFrame loginFrame;

    public AdminFrame(ClientSocket clientSocket, JFrame loginFrame, Map<String, String> userInfo) {
        this.clientSocket = clientSocket;
        this.loginFrame = loginFrame;
        setBounds(10, 10, 700, 400);
        setTitle(userInfo.get("userName") + "-管理员" + " ID:" + userInfo.get("id"));// 标题显示用户信息
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        validate();
        repaint();
    }

    void init() {
        var waitInform = new JLabel("加载题目库中，请等待...");
        waitInform.setFont(new Font("宋体", Font.BOLD, 16));
        add(waitInform, BorderLayout.CENTER);
        setVisible(true);
        var controlPanel = new JPanel();
        var addButton = new JButton("增加新题库");
        addButton.setName("增加新题库");
        var removeButton = new JButton("删除该题库");
        removeButton.setName("删除该题库");
        var addFromFileButton = new JButton("从文件中批量导入题库");
        addFromFileButton.setName("批量导入题库");
        var logoutButton = new JButton("注销");
        logoutButton.setName("注销");
        controlPanel.add(addButton);
        controlPanel.add(removeButton);
        controlPanel.add(addFromFileButton);
        controlPanel.add(logoutButton);
        var tabbedPanel = new JTabbedPane();
        var panelArr = new ArrayList<AdminQuestionPanel>();
        var controller = new AdminController(this, clientSocket, tabbedPanel, panelArr);
        addButton.addActionListener(controller);
        removeButton.addActionListener(controller);
        addFromFileButton.addActionListener(controller);
        logoutButton.addActionListener(controller);
        // 从服务器获取题目库列表，初始化每个题目库Panel并添加到tabbedPanel中
        try {
            var list = getQuestionList();
            for (var map : list) {
                var qPanel = new AdminQuestionPanel(clientSocket, Integer.parseInt(map.get("id")), controller);
                panelArr.add(qPanel);
                tabbedPanel.add(map.get("questionTableName"), qPanel);
            }
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "连接异常！即将退回登陆页面...\n原因：" + e.getMessage(), "错误",
                    JOptionPane.ERROR_MESSAGE);
            loginFrame.setVisible(true);
            this.dispose();
        }
        this.remove(waitInform);// 移除等待提示
        add(controlPanel, BorderLayout.NORTH);
        add(tabbedPanel, BorderLayout.CENTER);
    }

    // 从服务器获取题目库列表
    private ArrayList<Map<String, String>> getQuestionList() throws IllegalStateException, IOException {
        var request = new ClientRequest();
        request.setRequestType(TYPE.GetQuestionList);
        var rs = clientSocket.query(request);
        if (rs.isSucceed()) {
            return rs.getResult();
        }
        throw new IllegalStateException("连接错误！" + rs.getFailReason());
    }

    public JFrame getLoginFrame() {
        return loginFrame;
    }
}
