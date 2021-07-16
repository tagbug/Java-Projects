package Client.GUI.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * 考生的题目库Panel
 *
 * @since 10
 */
public class TestQuestionPanel extends UserQuestionPanel {
    private final JLabel lastTimeLabel;// 剩余时间Label
    private final JLabel nowStateLabel;// 目前考试状态Label

    public TestQuestionPanel(int questionTableId, ActionListener listener, String questionTableName)
            throws IllegalStateException, IOException {
        super(questionTableId, listener, "<这里会显示题目信息，右边显示题目图片，在下方选择答案>");
        var headPanel = new JPanel();
        headPanel.add(new JLabel("【" + questionTableName + "】"));
        addHeadController(headPanel, listener);
        nowStateLabel = new JLabel("考试未开始");
        headPanel.add(nowStateLabel);
        var submitButton = new JButton("交卷");
        submitButton.setName("交卷");
        submitButton.addActionListener(listener);
        headPanel.add(submitButton);
        headPanel.add(new JLabel("考试剩余时间:"));
        lastTimeLabel = new JLabel();
        headPanel.add(lastTimeLabel);
        add(headPanel, BorderLayout.NORTH);
    }

    @Override
    void init(ActionListener listener) throws IllegalStateException {
        // 视图布局处理
        qPanels = new ArrayList<>();
        qPanels.add(new QuestionPanel("<这里会显示题目信息，右边显示题目图片，在下方选择答案>", "", ""));// 默认提示信息
        add(qPanels.get(0), BorderLayout.CENTER);
        var controlPanel = new JPanel();
        addBottomController(controlPanel, listener);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Getters
    public JLabel getLastTimeLabel() {
        return lastTimeLabel;
    }

    public JLabel getNowStateLabel() {
        return nowStateLabel;
    }
}
