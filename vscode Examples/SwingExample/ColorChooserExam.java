package SwingExample;

import javax.swing.*;
import java.awt.*;

public class ColorChooserExam {
    public static void main(String[] args) {
        var win = new WindowColor();
        win.setBounds(10, 10, 360, 260);
        win.setTitle("颜色选择器");
    }
}

class WindowColor extends JFrame {
    WindowColor() {
        setLayout(new FlowLayout());
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        JButton button = new JButton("调出颜色选择对话框");
        button.addActionListener((e) -> {
            Color color = JColorChooser.showDialog(this, "调色板", getContentPane().getBackground());
            if (color != null) {
                getContentPane().setBackground(color);
            }
        });
        add(button);
    }
}
