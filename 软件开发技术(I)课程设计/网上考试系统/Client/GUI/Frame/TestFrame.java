package Client.GUI.Frame;

import Client.GUI.Controller.*;
import Client.GUI.Panel.*;
import Client.util.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * 考生窗口
 * 
 * @since 10
 */
public class TestFrame extends UserFrame {
    public TestFrame(ClientSocket clientSocket, JFrame loginFrame, Map<String, String> userInfo) {
        super(clientSocket,loginFrame,userInfo,"考生");
    }

    @Override
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
        controlPanel.add(new JLabel("3200608080 陈欣阳"));
        var tabbedPanel = new JTabbedPane();
        var panelArr = new ArrayList<TestQuestionPanel>();
        var controller = new TestController(this, tabbedPanel, panelArr);
        startButton.addActionListener(controller);
        queryScoreButton.addActionListener(controller);
        logoutButton.addActionListener(controller);
        // 从服务器获取题目库列表，初始化每个题目库Panel并添加到tabbedPanel中
        withConnectErrorCatch(()->{
            var list = getQuestionList();
            for (var map : list) {
                var qPanel = new TestQuestionPanel(Integer.parseInt(map.get("id")), controller, map.get("questionTableName"));
                panelArr.add(qPanel);
                tabbedPanel.add(map.get("questionTableName"), qPanel);
            }
        });
        add(controlPanel, BorderLayout.NORTH);
        add(tabbedPanel, BorderLayout.CENTER);
    }
}
