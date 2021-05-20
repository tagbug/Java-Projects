package SwingExample;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MouseMotionEventExam {
    public static void main(String[] args) {
        var win = new WindowMove();
        win.setBounds(10, 10, 200, 200);
        win.setTitle("处理鼠标拖动事件");
    }
}

class WindowMove extends JFrame {
    WindowMove() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        LP pane = new LP();
        add(pane, BorderLayout.CENTER);
        setBounds(12, 12, 100, 100);
    }

    class LP extends JLayeredPane implements MouseListener, MouseMotionListener {
        JButton button;
        int x, y, a, b, x0, y0;

        LP() {
            button = new JButton("用鼠标拖动我");
            button.addMouseListener(this);
            button.addMouseMotionListener(this);
            setLayout(new FlowLayout());
            add(button, JLayeredPane.DEFAULT_LAYER);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JComponent com = null;
            com = (JComponent) e.getSource();
            setLayer(com, JLayeredPane.DRAG_LAYER);
            a = com.getBounds().x;
            b = com.getBounds().y;
            x0 = e.getX();
            y0 = e.getY(); // 获取鼠标在事件源中的位置坐标
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JComponent com = null;
            com = (JComponent) e.getSource();
            setLayer(com, JLayeredPane.DRAG_LAYER);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Component com = null;
            if (e.getSource() instanceof Component) {
                com = (Component) e.getSource();
                a = com.getBounds().x;
                b = com.getBounds().y;
                x = e.getX();
                y = e.getY();
                a += x;
                b += y;
                com.setLocation(a - x0, b - y0);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }

    }
}