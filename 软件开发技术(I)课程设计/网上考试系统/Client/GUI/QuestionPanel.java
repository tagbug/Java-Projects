package Client.GUI;

import java.net.URL;

import javax.swing.*;
import java.awt.*;

public class QuestionPanel extends JPanel {
    private JTextArea textArea;
    private JLabel imgArea;
    private JPanel inPanel;
    private static final String[] chooseStr = new String[] { "A", "B", "C", "D" };
    private JRadioButton[] chooses;
    private String imgSrc;

    QuestionPanel(String questionText, String imgSrc, String answer) {
        this.imgSrc = imgSrc;
        setLayout(new BorderLayout());
        init(questionText, answer);
    }

    void init(String questionText, String answer) {
        inPanel = new JPanel();
        inPanel.setLayout(new GridLayout(1, 2));
        textArea = new JTextArea(questionText);
        textArea.setFont(new Font("宋体", Font.BOLD, 17));
        textArea.setLineWrap(true);
        if (imgSrc == null || imgSrc.isEmpty()) {
            imgArea = new JLabel("此题无图");
            imgArea.setFont(new Font("宋体", Font.BOLD, 30));
        } else {
            try {
                var img = new ImageIcon(new URL(imgSrc));
                img.setImage(img.getImage().getScaledInstance(350, 250, Image.SCALE_DEFAULT));
                imgArea = new JLabel(img);
            } catch (Exception e) {
                imgArea = new JLabel("此题图片损坏");
                imgArea.setFont(new Font("宋体", Font.BOLD, 30));
            }
        }
        inPanel.add(new JScrollPane(textArea), BorderLayout.WEST);
        inPanel.add(imgArea, BorderLayout.EAST);
        add(inPanel, BorderLayout.CENTER);
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
