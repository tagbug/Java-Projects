package Client.GUI.Panel;

import javax.swing.*;
import java.awt.*;
import java.net.*;

/**
 * 题目容器，管理员和考生共用的基础容器
 * 
 * @since 10
 */
public class QuestionPanel extends JPanel {
    private static final String[] chooseStr = new String[] { "A", "B", "C", "D" };
    private JTextArea textArea;// 题目文本容器
    private JRadioButton[] chooses;// 单选按钮
    private final String imgSrc;// 题目图片URL地址

    public QuestionPanel(String questionText, String imgSrc, String answer) {
        this.imgSrc = imgSrc;
        setLayout(new BorderLayout());
        init(questionText, answer);
    }

    void init(String questionText, String answer) {
        JPanel inPanel = new JPanel();
        inPanel.setLayout(new GridLayout(1, 2));
        // 题目文本处理
        textArea = new JTextArea(questionText);
        textArea.setFont(new Font("宋体", Font.BOLD, 17));
        textArea.setLineWrap(true);
        // 题目图片处理
        // 题目图片容器
        JLabel imgArea;
        if (imgSrc == null || imgSrc.isEmpty()) {
            imgArea = new JLabel("此题无图");
            imgArea.setFont(new Font("微软雅黑", Font.BOLD, 30));
        } else {
            try {
                var img = new ImageIcon(new URL(imgSrc));
                img.setImage(img.getImage().getScaledInstance(350, 250, Image.SCALE_DEFAULT));
                imgArea = new JLabel(img);
            } catch (Exception e) {
                imgArea = new JLabel("此题图片损坏");
                imgArea.setFont(new Font("微软雅黑", Font.BOLD, 30));
            }
        }
        inPanel.add(new JScrollPane(textArea), BorderLayout.WEST);
        inPanel.add(imgArea, BorderLayout.EAST);
        add(inPanel, BorderLayout.CENTER);
        // 选择项处理
        var choosePanel = new JPanel();
        var group = new ButtonGroup();
        chooses = new JRadioButton[4];
        choosePanel.add(new JLabel("答案："));
        for (int i = 0; i < chooses.length; i++) {
            chooses[i] = new JRadioButton(chooseStr[i]);
            choosePanel.add(chooses[i]);
            group.add(chooses[i]);
        }
        for (int i = 0; i < chooses.length; i++) {
            if (chooseStr[i].equals(answer))
                chooses[i].setSelected(true);
        }
        add(choosePanel, BorderLayout.SOUTH);
    }

    public String getText() {
        return textArea.getText();
    }

    public String getChoose() {
        for (int i = 0; i < chooses.length; i++) {
            if (chooses[i].isSelected())
                return chooseStr[i];
        }
        return null;
    }

    public String getImgSrc() {
        return imgSrc;
    }
}
