package Client.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

import Client.util.ClientSocket;
import Data.ClientRequest;
import Data.ClientRequest.TYPE;

/**
 * 考生窗口
 * 
 * @since 10
 */
public class TestFrame extends JFrame {
    private ClientSocket clientSocket;
    private JFrame loginFrame;

    public TestFrame(ClientSocket clientSocket, JFrame loginFrame, Map<String, String> userInfo) {
        this.clientSocket = clientSocket;
        this.loginFrame = loginFrame;
        setBounds(10, 10, 700, 400);
        setTitle(userInfo.get("userName") + "-考生" + " ID:" + userInfo.get("id"));// 标题显示用户信息
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        setVisible(true);
        validate();
        repaint();
    }

    void init() {
        var controlPanel = new JPanel();
        var startButton = new JButton("开始该题库训练");
        startButton.setName("开始考试");
        var queryScoreButton = new JButton("查询历史分数");
        queryScoreButton.setName("查询分数");
        var logoutButton = new JButton("注销");
        logoutButton.setName("注销");
        controlPanel.add(startButton);
        controlPanel.add(queryScoreButton);
        controlPanel.add(logoutButton);
        var tabbedPanel = new JTabbedPane();
        var panelArr = new ArrayList<TestQuestionPanel>();
        var controller = new TestController(this, clientSocket, tabbedPanel, panelArr);
        startButton.addActionListener(controller);
        queryScoreButton.addActionListener(controller);
        logoutButton.addActionListener(controller);
        // 从服务器获取题目库列表，初始化每个题目库Panel并添加到tabbedPanel中
        try {
            var list = getQuestionList();
            for (var map : list) {
                var qPanel = new TestQuestionPanel(Integer.parseInt(map.get("id")), controller,
                        map.get("questionTableName"));
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
