package SwingExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnonymousActionExam {
    public static void main(String[] args) {
        var win = new AnonymousActionWin();
        win.setBounds(10, 10, 260, 200);
        win.setTitle("匿名监视器");
    }
}

class AnonymousActionWin extends JFrame implements ActionListener {

    JTextField[] text = new JTextField[2];

    AnonymousActionWin() {
        setLayout(new FlowLayout());
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {

        for (int i = 0; i < text.length; i++) {
            text[i] = new JTextField(10);
            add(text[i]);
        }
        text[0].addActionListener(this);
        text[0].addActionListener((e) -> {
            if (text[0].getText().equalsIgnoreCase("Exit"))
                System.exit(0);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = text[0].getText();
        try {
            int n = Integer.parseInt(str);
            text[1].setText("" + Math.pow(n, 3));
        } catch (Exception exception) {
            text[1].setText("请输入数字字符");
            text[0].setText(null);
        }
    }

}