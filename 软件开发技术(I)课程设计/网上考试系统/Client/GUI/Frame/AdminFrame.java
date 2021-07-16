package Client.GUI.Frame;

import Client.GUI.Controller.*;
import Client.GUI.Panel.*;
import Client.util.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * 管理员窗口
 *
 * @since 10
 */
public class AdminFrame extends UserFrame {
    public AdminFrame(ClientSocket clientSocket, JFrame loginFrame, Map<String, String> userInfo) {
        super(clientSocket, loginFrame, userInfo, "管理员");
    }

    @Override
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
        controlPanel.add(new JLabel("3200608080 陈欣阳"));
        var tabbedPanel = new JTabbedPane();
        var panelArr = new ArrayList<AdminQuestionPanel>();
        var controller = new AdminController(this, tabbedPanel, panelArr);
        addButton.addActionListener(controller);
        removeButton.addActionListener(controller);
        addFromFileButton.addActionListener(controller);
        logoutButton.addActionListener(controller);
        // 从服务器获取题目库列表，初始化每个题目库Panel并添加到tabbedPanel中
        withConnectErrorCatch(() -> {
            var list = getQuestionList();
            for (var map : list) {
                var qPanel = new AdminQuestionPanel(Integer.parseInt(map.get("id")), controller);
                panelArr.add(qPanel);
                tabbedPanel.add(map.get("questionTableName"), qPanel);
            }
        });
        this.remove(waitInform);// 移除等待提示
        add(controlPanel, BorderLayout.NORTH);
        add(tabbedPanel, BorderLayout.CENTER);
    }
}
