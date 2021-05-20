package 精编p269;

import java.awt.*;
import javax.swing.*;

public class ThreadFrame extends JFrame {
    WordThread giveWord;
    int score;

    ThreadFrame() {
        JTextField showWord = new JTextField(6);
        showWord.setFont(new Font("", Font.BOLD, 72));
        showWord.setHorizontalAlignment(JTextField.CENTER);
        giveWord = new WordThread();
        giveWord.setJTextField(showWord);
        giveWord.setSleepLength(5000);
        JButton button = new JButton("开始");
        JTextField inputText = new JTextField(10), showScore = new JTextField(5);
        showScore.setEditable(false);
        button.addActionListener((e) -> {
            if (!giveWord.isAlive()) {
                giveWord = new WordThread();
                giveWord.setJTextField(showWord);
                giveWord.setSleepLength(5000);
            }
            try {
                giveWord.start();
            } catch (Exception exp) {
            }
        });
        inputText.addActionListener((e) -> {
            if (inputText.getText().equals(showWord.getText()))
                score++;
            showScore.setText("得分：" + score);
            inputText.setText(null);
        });
        add(button, BorderLayout.NORTH);
        add(showWord, BorderLayout.CENTER);
        JPanel southP = new JPanel();
        southP.add(new JLabel("输入汉字（回车）："));
        southP.add(inputText);
        southP.add(showScore);
        add(southP, BorderLayout.SOUTH);
        setBounds(100, 100, 350, 180);
        setVisible(true);
        validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
