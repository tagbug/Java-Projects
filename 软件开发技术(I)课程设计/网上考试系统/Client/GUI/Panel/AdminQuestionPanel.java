package Client.GUI.Panel;

import Client.GUI.Controller.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * 管理员的题目库Panel
 *
 * @since 10
 */
public class AdminQuestionPanel extends UserQuestionPanel {
    private JButton revokeButton;// 撤销操作的Button

    public AdminQuestionPanel(int questionTableId, AdminController controller) throws IllegalStateException,
            IOException {
        super(questionTableId, controller, "<请在这里写入题目信息，在下面更改选项答案>");
        // 获取题目库中全部题目并将题目库容器初始化，放入ArrayList中
        questionList = controller.getAllQuestions(questionTableId);
        qPanels = new ArrayList<>();
        addQuestionsToPanels(questionList, qPanels, null,true);
        add(qPanels.get(0), BorderLayout.CENTER);
        // 页面布局
        var headPanel = new JPanel();
        addHeadController(headPanel, controller);
        var changeImgButton = new JButton("修改题目图片");
        changeImgButton.setName("修改题目图片");
        changeImgButton.addActionListener(controller);
        headPanel.add(changeImgButton);
        add(headPanel, BorderLayout.NORTH);
    }

    @Override
    void init(ActionListener listener) throws IllegalStateException {
        // 页面布局
        var controlPanel = new JPanel();
        addBottomController(controlPanel, listener);
        var createButton = new JButton("新建题目");
        createButton.setName("新建题目");
        createButton.addActionListener(listener);
        var deleteButton = new JButton("删除题目");
        deleteButton.setName("删除题目");
        deleteButton.addActionListener(listener);
        var saveButton = new JButton("保存操作");
        saveButton.setName("保存操作");
        saveButton.addActionListener(listener);
        revokeButton = new JButton("撤销操作");
        revokeButton.setName("撤销操作");
        revokeButton.setEnabled(false);
        revokeButton.addActionListener(listener);
        controlPanel.add(createButton);
        controlPanel.add(deleteButton);
        controlPanel.add(saveButton);
        controlPanel.add(revokeButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Getters
    public JButton getRevokeButton() {
        return revokeButton;
    }
}
