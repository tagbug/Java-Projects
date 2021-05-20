package SwingExample;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MouseEventExam {
    public static void main(String[] args) {
        var win = new WindowMouse();
        win.setBounds(10, 10, 260, 260);
    }
}

class WindowMouse extends JFrame {
    WindowMouse() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        setLayout(new FlowLayout());
        JTextArea showArea = new JTextArea(10, 22);
        JButton button = new JButton("按钮");
        JTextField text = new JTextField(8);
        PoliceListen listen = new PoliceListen();
        listen.setShwoArea(showArea);
        text.addMouseListener(listen);
        button.addMouseListener(listen);
        addMouseListener(listen);
        add(button);
        add(text);
        add(new JScrollPane(showArea));
    }

    class PoliceListen implements MouseListener {
        JTextArea showArea;

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() >= 2)
                showArea.append("鼠标连击，位置：(" + e.getX() + "," + e.getY() + ")\n");
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            showArea.append("鼠标进入");
            if (e.getSource() instanceof JButton)
                showArea.append("按钮");
            if (e.getSource() instanceof JTextField)
                showArea.append("文本框");
            if (e.getSource() instanceof JFrame)
                showArea.append("窗口");
            showArea.append("，位置：(" + e.getX() + "," + e.getY() + ")\n");
        }

        @Override
        public void mouseExited(MouseEvent e) {
            showArea.append("鼠标退出，位置：(" + e.getX() + "," + e.getY() + ")\n");
        }

        @Override
        public void mousePressed(MouseEvent e) {
            showArea.append("鼠标按下，位置：(" + e.getX() + "," + e.getY() + ")\n");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showArea.append("鼠标释放，位置：(" + e.getX() + "," + e.getY() + ")\n");
        }

        public JTextArea getShwoArea() {
            return showArea;
        }

        public void setShwoArea(JTextArea shwoArea) {
            this.showArea = shwoArea;
        }
    }
}