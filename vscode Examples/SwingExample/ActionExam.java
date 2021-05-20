package SwingExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ActionExam {
    public static void main(String[] args) {
        var win = new WindowActionEvent();
        win.setBounds(100, 100, 260, 160);
        win.setTitle("处理ActionEvent事件");
    }
}

class WindowActionEvent extends JFrame {
    JTextField text;

    public WindowActionEvent() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        setLayout(new FlowLayout());
        text = new JTextField(10);
        JTextArea area = new JTextArea(5, 18);
        PoliceAction action = new PoliceAction();
        action.setInput(text);
        action.setShow(area);
        text.addActionListener(action);
        add(text);
        add(new JScrollPane(area));
    }
    
    class PoliceAction implements ActionListener {
        JTextField textInput;
        JTextArea textShow;

        public void setInput(JTextField text) {
            textInput = text;
        }

        public void setShow(JTextArea area) {
            textShow = area;
        }

        public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();
            textShow.append(str + "的长度" + str.length() + "\n");
        }
    }
}
