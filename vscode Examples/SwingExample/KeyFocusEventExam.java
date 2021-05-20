package SwingExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KeyFocusEventExam {
    public static void main(String[] args) {
        var win = new KeyFocusWin();
        win.setBounds(10, 10, 260, 200);
        win.setTitle("序列号输入测试");
    }
}

class KeyFocusWin extends JFrame {
    KeyFocusWin() {
        setLayout(new FlowLayout());
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        JTextField[] text = new JTextField[3];
        Police listen = new Police();
        for (int i = 0; i < 3;i++) {
            text[i] = new JTextField(7);
            text[i].addKeyListener(listen);
            text[i].addFocusListener(listen);
            add(text[i]);
        }
        add(new JButton("确定"));
        text[0].requestFocusInWindow();
    }

    class Police implements KeyListener, FocusListener {
        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
            JTextField text = (JTextField) e.getSource();
            if (text.getCaretPosition() >= 6) {
                text.transferFocus();
            }
        }

        @Override
        public void focusGained(FocusEvent e) {
            JTextField t = (JTextField) e.getSource();
            t.setText(null);
        }

        @Override
        public void focusLost(FocusEvent e) {
        }

    }
}
