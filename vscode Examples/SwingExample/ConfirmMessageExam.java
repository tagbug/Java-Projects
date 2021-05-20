package SwingExample;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ConfirmMessageExam {
    public static void main(String[] args) {
        var win = new WindowConfirm();
        win.setBounds(10, 10, 360, 260);
        win.setTitle("确认对话框");
    }
}

class WindowConfirm extends JFrame {
    WindowConfirm() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        JTextField input = new JTextField();
        JTextArea show = new JTextArea();
        input.addActionListener((e) -> {
            int choose = JOptionPane.showConfirmDialog(this, "请确认输入是否正确？", "确认对话框", JOptionPane.INFORMATION_MESSAGE);
            if (choose == JOptionPane.YES_OPTION) {
                show.append(input.getText() + "\n");
            } else if (choose == JOptionPane.NO_OPTION) {
                System.out.println("取消输入！");
            } else if (choose == JOptionPane.CANCEL_OPTION) {
                System.out.println("点击取消按钮！");
            }
        });
        add(input, BorderLayout.NORTH);
        add(new JScrollPane(show), BorderLayout.CENTER);
    }
}
