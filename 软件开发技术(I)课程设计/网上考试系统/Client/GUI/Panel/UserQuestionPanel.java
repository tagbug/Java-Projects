package Client.GUI.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

/**
 * 用户题库布局，描述题库的基本信息
 */
public abstract class UserQuestionPanel extends JPanel {
    public final int questionTableId;// 题目库ID
    public final String defaultQPanelInfo;// 默认题目提示
    ArrayList<Map<String, String>> questionList;// 题目信息表
    ArrayList<QuestionPanel> qPanels;// 题目容器列表
    JLabel posLabel;// 当前题目位置Label

    UserQuestionPanel(int questionTableId, ActionListener listener, String defaultQPanelInfo)
            throws IllegalStateException {
        setLayout(new BorderLayout());
        this.questionTableId = questionTableId;
        this.defaultQPanelInfo = defaultQPanelInfo;
        this.questionList = new ArrayList<>();
        init(listener);
    }

    abstract void init(ActionListener listener) throws IllegalStateException;

    /**
     * 增加题目导航功能
     *
     * @param headPanel 头部Panel
     * @param listener  监听器
     */
    final void addHeadController(JPanel headPanel, ActionListener listener) {
        headPanel.add(new JLabel("当前题目："));
        posLabel = new JLabel("1/" + qPanels.size());
        headPanel.add(posLabel);
        headPanel.add(new JLabel("跳转到："));
        var goText = new JTextField(4);
        goText.setName("跳转题目");
        goText.addActionListener(listener);
        headPanel.add(goText);
    }

    /**
     * 增加题目跳转功能
     *
     * @param bottomPanel 底部Panel
     * @param listener    监听器
     */
    final void addBottomController(JPanel bottomPanel, ActionListener listener) {
        var preButton = new JButton("上一题目");
        preButton.setName("上一题目");
        preButton.addActionListener(listener);
        var nextButton = new JButton("下一题目");
        nextButton.setName("下一题目");
        nextButton.addActionListener(listener);
        bottomPanel.add(preButton);
        bottomPanel.add(nextButton);
    }

    /**
     * 转换题目信息（键值对）->题目Text并添加到题目容器中
     *
     * @param questionList 题目信息表
     * @param panelList    题目容器列表
     */
    public final void addQuestionsToPanels(List<Map<String, String>> questionList, List<QuestionPanel> panelList,
                                    String defaultInfo, boolean showAnswer) {
        for (Map<String, String> map : questionList) {
            panelList.add(new QuestionPanel(buildQuestionText(map), map.get("imgSrc"), showAnswer ? map.get("answer") : ""));
        }
        if (panelList.size() == 0)// 如果题目库为空，显示默认提示
            panelList.add(new QuestionPanel((defaultInfo == null || defaultInfo.isEmpty()) ? defaultQPanelInfo : defaultInfo, "", "A"));
    }

    /**
     * 转换题目信息（键值对）->题目Text
     *
     * @param map 题目信息
     * @return 题目Text
     */
    final String buildQuestionText(Map<String, String> map) {
        return map.get("text") +
                "\nA. " +
                map.get("chooseA") +
                "\nB. " +
                map.get("chooseB") +
                "\nC. " +
                map.get("chooseC") +
                "\nD. " +
                map.get("chooseD");
    }

    // Getters
    public final ArrayList<Map<String, String>> getQuestionList() {
        return questionList;
    }

    public final ArrayList<QuestionPanel> getQPanels() {
        return qPanels;
    }

    public final int getQuestionTableId() {
        return questionTableId;
    }

    public final JLabel getPosLabel() {
        return posLabel;
    }
}
