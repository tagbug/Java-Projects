package SwingExample;

import javax.swing.*;
import java.awt.*;

public class MessageDialogExam {
    public static void main(String[] args) {
        var win = new WindowMess();
        win.setBounds(10, 10, 300, 100);
        win.setTitle("消息提示框");
    }
}

class WindowMess extends JFrame {
    WindowMess() {
        setLayout(new FlowLayout());
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        JTextField t = new JTextField(10);
        t.addActionListener((e) -> {
            String regex = ".*[^a-zA-Z]+.*";// 出现1次以上的非英文字符就能匹配
            if (((JTextField) e.getSource()).getText().matches(regex)) {
                JOptionPane.showMessageDialog(this, "您输入了非法字符", "消息提示框", JOptionPane.WARNING_MESSAGE);
                t.setText(null);
            } else {
                JOptionPane.showMessageDialog(this, "通过！", "消息提示框", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(new JLabel("只能输入英文字符："));
        add(t);
    }
}
