package SwingExample;

import javax.swing.*;
import java.awt.*;

public class MyDialogExam {
    public static void main(String[] args) {
        var win = new MyDialogWindow();
        win.setBounds(10, 10, 400, 200);
        win.setTitle("自定义对话框");
    }
}

class MyDialogWindow extends JFrame {
    MyDialogWindow() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        JButton button = new JButton("打开对话框");
        MyDialog dialog = new MyDialog(this, "我是对话框");
        dialog.setModal(true);
        button.addActionListener((e) -> {
            dialog.setVisible(true);
            setTitle(dialog.getTitle());
        });
        add(button, BorderLayout.NORTH);
    }

    public class MyDialog extends JDialog {
        String title;

        MyDialog(JFrame f, String s) {
            super(f, s);
            JTextField inputField = new JTextField(10);
            setLayout(new FlowLayout());
            add(new Label("Input the new name of the window"));
            add(new Label("输入窗口的新标题"));
            add(inputField);
            setBounds(60, 60, 200, 150);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            inputField.addActionListener((e) -> {
                title = inputField.getText();
                setVisible(false);
            });
        }

        public String getTitle() {
            return title;
        }
    }
}