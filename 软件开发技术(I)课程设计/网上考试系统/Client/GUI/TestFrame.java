package Client.GUI;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

import Client.util.ClientSocket;
import Data.ClientRequest;
import Data.ClientRequest.TYPE;

public class TestFrame extends JFrame {
    private ClientSocket clientSocket;
    private JFrame loginFrame;

    public TestFrame(ClientSocket clientSocket, JFrame loginFrame, Map<String, String> userInfo) {
        this.clientSocket = clientSocket;
        this.loginFrame = loginFrame;
        setBounds(10, 10, 700, 400);
        setTitle(userInfo.get("userName") + "-考生" + " ID:" + userInfo.get("id"));
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
        var startButton = new JButton("开始该题库训练");
        startButton.setName("开始考试");
        var queryScoreButton = new JButton("查询历史分数");
        queryScoreButton.setName("查询分数");
        controlPanel.add(startButton);
        controlPanel.add(queryScoreButton);
        var tabbedPanel = new JTabbedPane();
        var panelArr = new ArrayList<TestQuestionPanel>();
        var controller = new TestController(this, clientSocket, tabbedPanel, panelArr);
        startButton.addActionListener(controller);
        queryScoreButton.addActionListener(controller);
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
        this.remove(waitInform);
        add(controlPanel, BorderLayout.NORTH);
        add(tabbedPanel, BorderLayout.CENTER);
    }

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
