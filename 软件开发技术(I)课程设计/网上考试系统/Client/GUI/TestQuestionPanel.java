package Client.GUI;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * 考生的题目库Panel
 * 
 * @since 10
 */
public class TestQuestionPanel extends JPanel {
    private int questionTableId;
    private ArrayList<Map<String, String>> questionList;
    private ArrayList<QuestionPanel> qPanels;
    private JLabel posLabel;
    private JLabel lastTimeLabel;
    private JLabel nowStateLabel;

    TestQuestionPanel(int questionTableId, ActionListener listener, String questionTableName)
            throws IllegalStateException, IOException {
        setLayout(new BorderLayout());
        this.questionTableId = questionTableId;
        questionList = new ArrayList<Map<String, String>>();
        init(listener, questionTableName);
    }

    void init(ActionListener listener, String questionTableName) throws IllegalStateException, IOException {
        // 视图布局处理
        qPanels = new ArrayList<QuestionPanel>();
        qPanels.add(new QuestionPanel("<这里会显示题目信息，右边显示题目图片，在下方选择答案>", "", ""));// 默认提示信息
        add(qPanels.get(0), BorderLayout.CENTER);
        var headPanel = new JPanel();
        headPanel.add(new JLabel("【" + questionTableName + "】"));
        headPanel.add(new JLabel("当前题目："));
        posLabel = new JLabel("1/" + qPanels.size());
        headPanel.add(posLabel);
        headPanel.add(new JLabel("跳转到："));
        var goText = new JTextField(4);
        goText.setName("跳转题目");
        goText.addActionListener(listener);
        headPanel.add(goText);
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
        var controlPanel = new JPanel();
        var preButton = new JButton("上一题目");
        preButton.setName("上一题目");
        preButton.addActionListener(listener);
        var nextButton = new JButton("下一题目");
        nextButton.setName("下一题目");
        nextButton.addActionListener(listener);
        controlPanel.add(preButton);
        controlPanel.add(nextButton);
        add(controlPanel, BorderLayout.SOUTH);
    }

    // Getters
    public ArrayList<Map<String, String>> getQuestionList() {
        return questionList;
    }

    public ArrayList<QuestionPanel> getQPanels() {
        return qPanels;
    }

    public int getQuestionTableId() {
        return questionTableId;
    }

    public JLabel getPosLabel() {
        return posLabel;
    }

    public JLabel getLastTimeLabel() {
        return lastTimeLabel;
    }

    public JLabel getNowStateLabel() {
        return nowStateLabel;
    }
}
