package SwingExample;

import java.awt.*;
import java.util.Scanner;
import javax.swing.*;

public class InputMessageExam {
    public static void main(String[] args) {
        var win = new WindowInput();
        win.setBounds(10, 10, 360, 260);
        win.setTitle("消息提示框");
    }
}

class WindowInput extends JFrame {
    WindowInput() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        JButton button = new JButton("弹出输入对话框");
        JTextArea t = new JTextArea();
        button.addActionListener((e) -> {
            String str = JOptionPane.showInputDialog(this, "输入数字，用空格分隔", "输入对话框", JOptionPane.PLAIN_MESSAGE);
            if (str != null) {
                Scanner reader = new Scanner(str);
                double sum = 0;
                int k = 0;
                while (reader.hasNext()) {
                    try {
                        double num = reader.nextDouble();
                        if (k == 0) {
                            t.append("" + num);
                        } else {
                            t.append(" + " + num);
                        }
                        k += 1;
                        sum += num;
                    } catch (Exception ee) {
                        reader.next();
                    }
                }
                reader.close();
                t.append(" = " + sum + '\n');
            }
        });
        add(button, BorderLayout.NORTH);
        add(new JScrollPane(t), BorderLayout.CENTER);
    }
}